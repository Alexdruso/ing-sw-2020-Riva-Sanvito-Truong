package it.polimi.ingsw.utils.networking;

import it.polimi.ingsw.utils.config.ConfigParser;
import it.polimi.ingsw.utils.networking.transmittables.DisconnectionMessage;
import it.polimi.ingsw.utils.networking.transmittables.KeepAlive;
import it.polimi.ingsw.utils.networking.transmittables.Transmittable;
import it.polimi.ingsw.utils.observer.LambdaObservable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A bidirectional connection to a remote host.
 * A thread is spawned once for each run of the software to handle the execution of periodic events,
 * such as the sending of keep alive messages.
 */
public class Connection extends LambdaObservable<Transmittable> {
    private static final Logger LOGGER = Logger.getLogger(Connection.class.getName());
    private static final Timer KEEP_ALIVE_TIMER = new Timer("connectionKeepAliveTimer", true);
    private static final int KEEP_ALIVE_TIMER_INTERVAL_MS = ConfigParser.getInstance().getIntProperty("keepAliveIntervalMs");
    public static final int SOCKET_CONNECTION_TIMEOUT_MS = ConfigParser.getInstance().getIntProperty("socketConnectionTimeoutMs");
    private final Socket socket;
    private final ObjectInputStream socketIn;
    private final ObjectOutputStream socketOut;
    private final Thread receiveThread;
    private final AtomicBoolean isActive;
    private final AtomicBoolean isClosing;

    /**
     * Instantiates a new Connection from a given Socket.
     * Each Connections spawns a new Thread to receive incoming data from the socket.
     *
     * @param socket the socket
     * @throws IOException if it is not possible to get the input or output stream for the socket
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        log(Level.FINE, "Connection established");
        isActive = new AtomicBoolean(true);
        isClosing = new AtomicBoolean(false);
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketOut.flush(); // This is required otherwise the following instantiation of ObjectInputStream will block forever
        socketIn = new ObjectInputStream(socket.getInputStream());
        receiveThread = startSocketReceiveThread();
        scheduleKeepAlive();
    }

    /**
     * Instantiates a new Connection from a given host and port.
     * Each Connections spawns a new Thread to receive incoming data from the socket.
     *
     * @param host the host
     * @param port the port
     * @throws IOException if it is not possible to setup the socket
     */
    @SuppressWarnings("java:S2095") // We cannot wrap the socket creation in a try-with-resources because the socket will be used outside of this method
    public Connection(String host, int port) throws IOException {
        this(new Callable<Socket>(){
            @Override
            public Socket call() throws IOException {
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(host, port), SOCKET_CONNECTION_TIMEOUT_MS);
                return socket;
            }
        }.call());
    }

    /**
     * Schedules the periodic sending of "keep alive" packets to the other end of the socket,
     * in order not to let the connection time out if no other packet is sent for a long period.
     */
    private void scheduleKeepAlive() {
        Connection.KEEP_ALIVE_TIMER.schedule(new TimerTask() {
            @Override
            public void run() {
                if (isActive()) {
                    send(new KeepAlive());
                }
                else {
                    cancel();
                }
            }
        }, Connection.KEEP_ALIVE_TIMER_INTERVAL_MS, Connection.KEEP_ALIVE_TIMER_INTERVAL_MS);
    }

    /**
     * Prints a log message on the console, prepending the information about the socket.
     *
     * @param level   the log level
     * @param message the log message
     */
    private void log(Level level, String message) {
        LOGGER.log(level, () -> String.format("[socket %s] %s", socket.getRemoteSocketAddress().toString().substring(1), message));
    }

    /**
     * Checks if the connection is active.
     *
     * @return whether or not the connection is active
     */
    public boolean isActive() {
        return isActive.get();
    }

    /**
     * Closes the connection and stops the receiving thread.
     */
    public void close() {
        if (!isActive.getAndSet(false)) {
            return;
        }
        log(Level.FINE, "Closing the connection");
        try {
            socketOut.close();
        } catch (IOException ignored) {
            log(Level.FINER, "Unable to close out socket");
        }
        try {
            socketIn.close();
        } catch (IOException ignored) {
            log(Level.FINER, "Unable to close in socket");
        }
        try {
            socket.close();
        } catch (IOException ignored) {
            log(Level.FINER, "Unable to close socket");
        }
        try {
            receiveThread.interrupt();
            receiveThread.join();
        } catch (Exception ignored) {
            log(Level.FINER, "Unable to close receiving thread");
        }
    }

    /**
     * Notifies the observers of this Connection that the socket was closed, if not already done.
     */
    private void notifyDisconnection() {
        if (!isClosing.getAndSet(true)) {
            notify(new DisconnectionMessage());
        }
    }

    /**
     * Sends a message to the remote host.
     * The message is sent synchronously.
     *
     * @param message the message to be sent
     */
    public void send(Transmittable message) {
        try {
            synchronized (socketOut) {
                if (!isActive()) {
                    return;
                }
                log(Level.FINE, String.format("Sending message %s...", message.getClass().getName()));
                socketOut.writeObject(message);
            }
        } catch (IOException e) {
            notifyDisconnection();
        }
    }

    /**
     * Starts the thread that handles the messages incoming from the remote host.
     *
     * @return the thread that handles the messages incoming from the remote host
     */
    private Thread startSocketReceiveThread() {
        Thread t = new Thread(() -> {
            log(Level.FINE, "Receive thread ready");
            while (isActive() && !Thread.currentThread().isInterrupted()) {
                try {
                    Transmittable inputObject = (Transmittable) socketIn.readObject();
                    if (inputObject instanceof KeepAlive) {
                        log(Level.FINER, "Received keep alive");
                    }
                    else {
                        log(Level.FINE, String.format("Received message %s", inputObject.getClass().getName()));
                        notify(inputObject, true);
                    }
                } catch (IOException e) {
                    notifyDisconnection();
                } catch (ClassNotFoundException | ClassCastException e) {
                    LOGGER.log(Level.SEVERE, "Exception in receive thread", e);
                }
            }
        }, String.format("ConnectionReceive-%s", socket.getRemoteSocketAddress().toString()));
        t.start();
        return t;
    }

}

package it.polimi.ingsw.utils.networking;

import it.polimi.ingsw.observer.LambdaObservable;
import it.polimi.ingsw.utils.StringCapturedStackTrace;
import it.polimi.ingsw.utils.messages.DisconnectionMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * A bidirectional connection to a remote host.
 */
public class Connection extends LambdaObservable<Transmittable> {
    private static final Logger LOGGER = Logger.getLogger(Connection.class.getName());
    private final Socket socket;
    private final ObjectInputStream socketIn;
    private final ObjectOutputStream socketOut;
    private final Thread receiveThread;
    private AtomicBoolean isActive;

    /**
     * Instantiates a new Connection from a given Socket.
     *
     * @param socket the socket
     * @throws IOException if it is not possible to get the input or output stream for the socket
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        logInfo("Connection established");
        isActive = new AtomicBoolean(true);
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());
        receiveThread = startSocketReceiveThread();
    }

    /**
     * Instantiates a new Connection from a given host and port.
     *
     * @param host the host
     * @param port the port
     * @throws IOException if it is not possible to setup the socket
     */
    public Connection(String host, int port) throws IOException {
        this(new Socket(host, port));
    }

    private void log(Level level, String message) {
        LOGGER.log(level, () -> String.format("[socket %s] %s", socket.getRemoteSocketAddress().toString().substring(1), message));
    }

    private void logFine(String message) {
        log(Level.FINE, message);
    }

    private void logInfo(String message) {
        log(Level.INFO, message);
    }

    private void logSevere(String message) {
        log(Level.SEVERE, message);
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
     * Closes the connection.
     */
    public void close() {
        if (!isActive.getAndSet(false)) {
            return;
        }
        logFine("Closing the connection");
        try {
            socketOut.close();
        } catch (IOException ignored) {}
        try {
            socketIn.close();
        } catch (IOException ignored) {}
        try {
            socket.close();
        } catch (IOException ignored) {}
        try {
            receiveThread.interrupt();
            receiveThread.join();
        } catch (Exception ignored) {}
    }

    private void close(Exception e) {
        close(new StringCapturedStackTrace(e).toString());
    }

    private void close(String message) {
        if (isActive()) {
            logSevere("Abruptly closing the connection: " + message);
        }
        close();
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
                logFine(String.format("Sending message %s...", message.getClass().getName()));
                socketOut.writeObject(message);
            }
        } catch (IOException e) {
            notify(new DisconnectionMessage());
        }
    }

    /**
     * Starts the thread that handles the messages incoming from the remote host.
     *
     * @return the thread that handles the messages incoming from the remote host
     */
    private Thread startSocketReceiveThread() {
        Connection connectionInstance = this;
        Thread t = new Thread(() -> {
            connectionInstance.logInfo("Receive thread ready");
            while (connectionInstance.isActive() && !Thread.currentThread().isInterrupted()) {
                try {
                    Transmittable inputObject = (Transmittable) connectionInstance.socketIn.readObject();
                    logFine(String.format("Received message %s", inputObject.getClass().getName()));
                    connectionInstance.notify(inputObject, true);
                } catch (IOException e) {
                    connectionInstance.notify(new DisconnectionMessage());
                } catch (ClassNotFoundException | ClassCastException e) {
                    LOGGER.log(Level.SEVERE, "Exception in receive thread", e);
                }
            }
        }, String.format("ConnectionReceive-%s", socket.getRemoteSocketAddress().toString()));
        t.start();
        return t;
    }

}

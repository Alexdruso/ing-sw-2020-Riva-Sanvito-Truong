package it.polimi.ingsw.utils.networking;

import it.polimi.ingsw.observer.Observable;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * A bidirectional connection to a remote host.
 */
public class Connection extends Observable<Transmittable> {
    private Socket socket;
    private ObjectInputStream socketIn;
    private ObjectOutputStream socketOut;
    private Thread receiveThread;
    private Thread sendThread;
    /**
     * The queue containing the messages to be sent out.
     */
    BlockingQueue<Transmittable> sendQueue;
    private boolean isActive;

    /**
     * Instantiates a new Connection from a given Socket.
     *
     * @param socket the socket
     * @throws IOException if it is not possible to get the input or output stream for the socket
     */
    public Connection(Socket socket) throws IOException {
        this.socket = socket;
        logInfo("Connection established");
        sendQueue = new LinkedBlockingQueue<>();
        isActive = true;
        socketOut = new ObjectOutputStream(socket.getOutputStream());
        socketIn = new ObjectInputStream(socket.getInputStream());
        receiveThread = startSocketReceiveThread();
        sendThread = startSocketSendThread();
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

    private void log(String message) {
        System.err.println(String.format("[socket %s] %s", socket.getRemoteSocketAddress().toString().substring(1), message));
    }

    private void logInfo(String message) {
        log(String.format("[INFO] %s", message));
    }

    private void logError(String message) {
        log(String.format("[ERROR] %s", message));
    }

    /**
     * Checks if the connection is active.
     *
     * @return whether or not the connection is active
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Closes the connection.
     */
    public void close() {
        isActive = false;
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
            sendThread.interrupt();
            sendThread.join();
        } catch (Exception ignored) {}
        try {
            receiveThread.interrupt();
            receiveThread.join();
        } catch (Exception ignored) {}
    }

    private void close(Exception e) {
        logError("Closing the connection: " + (e.getMessage() != null ? e.getMessage() : e.getClass().getName()));
        close();
    }

    /**
     * Sends a message to the remote host.
     * The message is sent asynchronously (i.e., it is added to a FIFO sending queue and delivered best-effort by another thread).
     *
     * @param message the message to be sent
     */
    public void send(Transmittable message) {
        try {
            sendQueue.put(message);
        } catch (InterruptedException ignored) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Starts the thread that handles the messages incoming from the remote host.
     *
     * @return the thread that handles the messages incoming from the remote host
     */
    private Thread startSocketReceiveThread(){
        Connection connectionInstance = this;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                connectionInstance.logInfo("Receive thread ready");
                while (connectionInstance.isActive() && !Thread.currentThread().isInterrupted()) {
                    try {
                        Transmittable inputObject = (Transmittable) connectionInstance.socketIn.readObject();
                        connectionInstance.notify(inputObject);
                    } catch (IOException e) {
                        connectionInstance.close(e);
                    } catch (ClassNotFoundException | ClassCastException e) {
                        logError("Exception in receive thread");
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
        return t;
    }

    /**
     * Starts the thread that handles the messages to be sent to the remote host.
     *
     * @return the thread that handles the messages to be sent to the remote host
     */
    private Thread startSocketSendThread(){
        Connection connectionInstance = this;
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                connectionInstance.logInfo("Send thread ready");
                while (connectionInstance.isActive() && !Thread.currentThread().isInterrupted()) {
                    try {
                        connectionInstance.socketOut.writeObject(connectionInstance.sendQueue.take());
                    } catch (IOException e) {
                        connectionInstance.close(e);
                    } catch (InterruptedException ignored) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        });
        t.start();
        return t;
    }

}

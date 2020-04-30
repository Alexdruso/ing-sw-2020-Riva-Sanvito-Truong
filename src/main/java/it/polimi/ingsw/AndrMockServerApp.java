//TODO: REMOVE ME WHEN CLIENT TESTING IS FINISHED!!

package it.polimi.ingsw;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.observer.LambdaObserver;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AndrMockServerApp {
    private static final Logger LOGGER = Logger.getLogger(AndrMockServerApp.class.getName());
    private static boolean isActive = true;
    public static void stop() {
        isActive = false;
    }

    public static void main(String[] args) {
        //TODO: write real implementation. At the moment, this is just a boilerplate
        try (
                ServerSocket ss = new ServerSocket(1337)
        ){
            while (isActive) {
                acceptConnections(ss);
            }
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    public static void acceptConnections(ServerSocket ss) {
        try {
            Socket s = ss.accept();
            LOGGER.log(Level.INFO, "accepted connection");
            Connection connection = new Connection(s);
            connection.addObserver(new AndrServerTestReceiver(connection), (o, m) ->
                    ((AndrServerTestReceiver)o).update(m));
        }
        catch (Exception e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }
}

class AndrServerTestReceiver implements LambdaObserver {
    private static final Logger LOGGER = Logger.getLogger(AndrServerTestReceiver.class.getName());
    Connection connection;

    public AndrServerTestReceiver(Connection connection) {
        this.connection = connection;
    }

    void waitAndSendMatchStart() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
            Thread.currentThread().interrupt();
        }
        User[] users = new User[]{new User("nick1"), new User("nick2"), new User("nick3")};
        connection.send(new ServerStartSetupMatchMessage(users));
    }

    public void update(Transmittable message) {
        LOGGER.log(Level.INFO, "received message");
        if (message instanceof ClientDisconnectMessage) {
            LOGGER.log(Level.INFO, "client disconnected");
            connection.close();
            return;
        }
        if (message instanceof ClientSetNicknameMessage) {
            String nick = ((ClientSetNicknameMessage) message).getNickname();
            LOGGER.log(Level.INFO, "set nickname: " + nick);
            if (nick.equals("nak")) {
                connection.send(StatusMessages.CLIENT_ERROR);
                return;
            }
        }
        if (message instanceof ClientJoinLobbyMessage) {
//            if (false && Math.random() > 0.5) {
//                connection.send(StatusMessages.CONTINUE);
//                return;
//            }
//            else {
//                connection.send(StatusMessages.OK);
            waitAndSendMatchStart();
            return;
//            }
        }
        if (message instanceof ClientSetPlayersCountMessage) {
            connection.send(StatusMessages.OK);
            waitAndSendMatchStart();
            return;
        }
        if (message instanceof StatusMessages && ((StatusMessages)message).equals(StatusMessages.TEAPOT)) {
            AndrMockServerApp.stop();
        }
        connection.send(StatusMessages.OK);
    }
}
//TODO: REMOVE ME WHEN CLIENT TESTING IS FINISHED!!

package it.polimi.ingsw;

import it.polimi.ingsw.controller.User;
import it.polimi.ingsw.observer.LambdaObserver;
import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class AndrMockServerApp {
    public static boolean isActive = true;
    public static void main(String[] args) {
        //TODO: write real implementation. At the moment, this is just a boilerplate
        ServerSocket ss = null;
        try {
            ss = new ServerSocket(1337);
            while (isActive) {
                try {
                    Socket s = ss.accept();
                    System.out.println("accepted connection");
                    Connection connection = new Connection(s);
                    connection.addObserver(new AndrServerTestReceiver(connection), (o, m) ->
                            ((AndrServerTestReceiver)o).update(m));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

class AndrServerTestReceiver implements LambdaObserver {
    Connection connection;

    public AndrServerTestReceiver(Connection connection) {
        this.connection = connection;
    }

    void waitAndSendMatchStart() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
        User[] users = new User[]{new User("nick1"), new User("nick2"), new User("nick3")};
        connection.send(new ServerStartSetupMatchMessage(users));
    }

    public void update(Transmittable message) {
        System.out.println("received message");
        if (message instanceof ClientDisconnectMessage) {
            System.out.println("client disconnected");
            connection.close();
            return;
        }
        if (message instanceof ClientSetNicknameMessage) {
            String nick = ((ClientSetNicknameMessage) message).getNickname();
            System.out.println("was set nickname: " + nick);
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
            AndrMockServerApp.isActive = false;
        }
        connection.send(StatusMessages.OK);
    }
}
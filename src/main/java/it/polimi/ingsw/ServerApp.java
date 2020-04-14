//TODO: DO NOT MERGE ME!!

package it.polimi.ingsw;

import it.polimi.ingsw.observer.Observer;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ClientSetNicknameMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp {
    public static void main(String[] args) {
        //TODO: write real implementation. At the moment, this is just a boilerplate
        try {
            ServerSocket ss = new ServerSocket(1337);
            while (true) {
                try {
                    Socket s = ss.accept();
                    System.out.println("accepted connection");
                    Connection connection = new Connection(s);
                    connection.addObserver(new ServerTestReceiver(connection));
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerTestReceiver implements Observer<Transmittable> {
    Connection connection;

    public ServerTestReceiver(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void update(Transmittable message) {
        System.out.println("received message");
        if (message instanceof ClientSetNicknameMessage) {
            System.out.println("was set nickname: " + ((ClientSetNicknameMessage) message).getNickname());
        }
        connection.send(StatusMessages.OK);
    }
}
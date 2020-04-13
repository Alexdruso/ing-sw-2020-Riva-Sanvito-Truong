package it.polimi.ingsw;

import it.polimi.ingsw.server.Server;

import java.io.IOException;

public class ServerApp {
    public static void main(String[] args) {
        Server server;
        try{
            server = new Server();
            server.start();
        } catch (IOException e){
            e.printStackTrace();
            System.out.println("Could not initialize server");
        }
    }
}

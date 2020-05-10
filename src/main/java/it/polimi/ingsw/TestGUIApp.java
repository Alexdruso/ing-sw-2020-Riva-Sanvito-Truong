package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.gui.GUI;

public class TestGUIApp {
    static Thread clientThread;
    static Client client;
    public static void main(String[] args) {
        GUI gui = new GUI();
        client = new Client(gui);
        clientThread = new Thread(client::run);
        clientThread.start();
        JavaFXApp.launchGUI(gui);
    }

    public static void stop(){
        //Try to disconnect
        client.disconnect();
        client.requestRender();
        //Disconnect forcibly
        clientThread.interrupt();
    }
}


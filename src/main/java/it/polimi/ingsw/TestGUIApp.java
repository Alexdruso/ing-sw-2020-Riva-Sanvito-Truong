package it.polimi.ingsw;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.gui.GUI;

public class TestGUIApp {
    static Thread clientThread;
    public static void main(String[] args) {
        GUI gui = new GUI();
        Client client = new Client(gui);
        clientThread = new Thread(client::run);
        clientThread.start();
        JavaFXApp.launchGUI(gui);
    }

    public static void stop(){
        clientThread.interrupt();
    }
}


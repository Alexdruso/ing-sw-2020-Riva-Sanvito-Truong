package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractConnectToServerClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.utils.SceneLoaderFactory;

public class ConnectToServerGUIClientState extends AbstractConnectToServerClientState implements GUIClientState{
    /**
     * Instantiates a new JOIN_LOBBY ClientState.
     *
     * @param client the client
     */
    public ConnectToServerGUIClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        super.setup();
        client.closeConnection();
    }

    public void returnToMenu(){
        client.moveToState(ClientState.WELCOME_SCREEN);
    }

    public void setHostPort(String host, String port){
        this.host = host;
        this.port = Integer.parseInt(port);
        notifyUiInteraction();
    }


    @Override
    public void render() {
        SceneLoaderFactory sceneLoaderFactory = new SceneLoaderFactory("/fxml/ConnectToServer.fxml", client);
        sceneLoaderFactory.setState(ClientState.CONNECT_TO_SERVER, this).build().executeSceneChange();
    }
}

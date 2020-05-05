package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.JavaFXApp;
import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AbstractController;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Optional;

public class GUI extends UI {
    private Scene mainScene;
    private final HashMap<ClientState, SavedScene> sceneMap = new HashMap<>();

    @Override
    public void init() {
        System.out.println("Do we get to the GUI?");
    }

    public void addScene(ClientState clientState, Pane pane, AbstractController controller){
        //TODO: see if we can load all scenes at startup time
        sceneMap.put(clientState, new SavedScene(controller, pane));
    }

    public Optional<SavedScene> getScene(ClientState clientState){
        if(sceneMap.containsKey(clientState)){
            return Optional.of(sceneMap.get(clientState));
        } else {
            return Optional.empty();
        }
    }

    public void setRootScene(Scene scene){
        mainScene = scene;
    }

    void removeScene(ClientState clientState){
        sceneMap.remove(clientState);
    }

    void loadScene(ClientState clientState){
        //mainScene.setRoot(sceneMap.get(clientState));
    }

    @Override
    public AbstractClientState getClientState(ClientState clientState, Client client) {
        return switch (clientState) {
            case ASK_GOD_FROM_LIST -> new AskGodFromListGUIClientState(client);
            case ASK_GODS_FROM_LIST -> new AskGodsFromListGUIClientState(client);
            case ASK_START_PLAYER -> new AskStartPlayerGUIClientState(client);
            case CONNECT_TO_SERVER -> new ConnectToServerGUIClientState(client);
            case DISCONNECT -> new DisconnectGUIClientState(client);
            case JOIN_LOBBY -> new JoinLobbyGUIClientState(client);
            case IN_GAME -> new InGameGUIClientState(client);
            case SET_NICKNAME -> new SetNicknameGUIClientState(client);
            case SET_PLAYERS_COUNT -> new SetPlayersCountGUIClientState(client);
            case SHOW_GAME_PASSIVE -> new ShowGamePassiveGUIClientState(client);
            case WAIT_PLAYERS -> new WaitPlayersGUIClientState(client);
            case WELCOME_SCREEN -> new WelcomeScreenGUIClientState(client);
        };
    }

    @Override
    public AbstractClientTurnState getClientTurnState(ClientTurnState clientTurnState, Client client) {
        try {
            return switch (clientTurnState) {
                case ASK_WORKER_POSITION -> new AskWorkerPositionGUIClientTurnState(client, (InGameGUIClientState) client.getCurrentState());
                case BUILD -> new BuildGUIClientTurnState(client, (InGameGUIClientState) client.getCurrentState());
                case MOVE -> new MoveGUIClientTurnState(client, (InGameGUIClientState) client.getCurrentState());
                case PASSIVE -> new PassiveGUIClientTurnState(client, (InGameGUIClientState) client.getCurrentState());
            };
        }
        catch (ClassCastException e) {
            // We can't have a ClientTurnState if we aren't within InGame ClientState
            throw new IllegalStateException();
        }
    }

    @Override
    public void notifyError(String message) {

    }
}

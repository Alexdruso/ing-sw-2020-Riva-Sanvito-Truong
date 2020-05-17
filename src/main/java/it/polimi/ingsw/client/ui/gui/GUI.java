package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;
import javafx.scene.Scene;

import java.util.HashMap;
import java.util.Optional;

public class GUI extends UI {
    private Scene mainScene;
    private final HashMap<ClientState, SavedScene> sceneMapOld = new HashMap<>();
    private final HashMap<String, SavedScene> sceneMap = new HashMap<>();
    private SavedScene currentScene;

    @Override
    public void init() {
    }

    public void addScene(ClientState clientState, SavedScene savedScene) {
        //TODO: see if we can load all scenes at startup time
        //TODO: index this map by FXML name since there are states with multiple scenes (e.g. AskGodsFromList VS AskGodsFromListPassive)
        sceneMapOld.put(clientState, savedScene);
    }

    public void addScene(String fxmlFile, SavedScene savedScene) {
        sceneMap.put(fxmlFile, savedScene);
    }

    //TODO remove this
    public Optional<SavedScene> getScene(ClientState clientState){
        if(sceneMap.containsKey(clientState)){
            return Optional.of(sceneMap.get(clientState));
        } else {
            return Optional.empty();
        }
    }

    public SavedScene getScene(String fxmlFile){
        return sceneMap.get(fxmlFile);
    }

    void removeScene(ClientState clientState){
        sceneMap.remove(clientState);
    }

    public void setCurrentScene(SavedScene current){
        currentScene = current;
    }

    public SavedScene getCurrentScene(){
        return currentScene;
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
            };
        }
        catch (ClassCastException e) {
            // We can't have a ClientTurnState if we aren't within InGame ClientState
            throw new IllegalStateException();
        }
    }

    @Override
    public void notifyError(String message) {
        //TODO: For cleanliness of code, I think we should set a localization file and pass enums
        currentScene.controller.handleError(message);
    }
}

package it.polimi.ingsw.client.ui.gui;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.client.ui.gui.utils.SavedScene;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represents the GUI instance
 */
public class GUI implements UI {
    private static final Logger LOGGER = Logger.getLogger(GUI.class.getName());
    private final HashMap<String, SavedScene> sceneMap = new HashMap<>();
    private SavedScene currentScene;

    @Override
    public void init(Runnable onExit) {
        JavaFXGUI.onExit = onExit;
        new Thread(JavaFXGUI::launchJavaFX).start();
    };

    /**
     * This method adds a scene in the sceneMap in order to be retrieved when the scene is shown again later.
     * @param fxmlFile a String representing the path of the fxml file
     * @param savedScene the SavedScene instance containing all the elements of the scene
     */
    public void addScene(String fxmlFile, SavedScene savedScene) {
        sceneMap.put(fxmlFile, savedScene);
    }

    /**
     * This method retrieves a scene in the sceneMap
     * @param fxmlFile a String representing the path of the fxml file
     * @return the SavedScene associated with the fxmlFile if present, otherwise null
     */
    public SavedScene getScene(String fxmlFile){
        return sceneMap.get(fxmlFile);
    }

    /**
     * This method clears the sceneMap in order to invalidate all cached scenes
     */
    public void clearSceneMap(){
        sceneMap.clear();
    }

    /**
     * This method is used to set the current SavedScene
     * @param current the SavedScene instance to be set as the current one
     */
    public void setCurrentScene(SavedScene current){
        currentScene = current;
    }

    /**
     * This method is used to retrieve the current SavedScene
     * @return the current SavedScene instance
     */
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
            case LOSE_GAME -> new LoseGameGUIClientState(client);
            case SET_NICKNAME -> new SetNicknameGUIClientState(client);
            case SET_PLAYERS_COUNT -> new SetPlayersCountGUIClientState(client);
            case WIN_GAME -> new WinGameGUIClientState(client);
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
        LOGGER.log(Level.WARNING, message);
        currentScene.controller.handleError(message);
    }
}

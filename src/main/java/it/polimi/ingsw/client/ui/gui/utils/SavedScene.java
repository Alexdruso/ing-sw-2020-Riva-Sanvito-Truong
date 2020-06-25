package it.polimi.ingsw.client.ui.gui.utils;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AbstractController;
import javafx.scene.Parent;

import java.util.Objects;

/**
 * This class saves a set of elements which compose a scene and are used to change it externally
 */
public class SavedScene {
    /**
     * The scene controller
     */
    public final AbstractController controller;
    /**
     * The scene itself as a JavaFX Parent node
     */
    public final Parent root;
    /**
     * The ClientState relative to the scene
     */
    public final ClientState clientState;
    /**
     * The .fxml file used to load the scene
     */
    public final String fxmlFile;

    /**
     * Class constructor.
     * Sets ClientState to null in case the scene does not have any state attached (e.g. credits)
     * @param fxmlFile the .fxml file used to load the scene
     * @param controller the controller of the scene
     * @param root the Parent node representing the scene
     */
    public SavedScene(String fxmlFile, AbstractController controller, Parent root){
        this(fxmlFile, controller, root, null);
    }

    /**
     * Class constructor.
     * @param fxmlFile the .fxml file used to load the scene
     * @param controller the controller of the scene
     * @param root the Parent node representing the scene
     * @param clientState the ClientState associated with the scene
     */
    public SavedScene(String fxmlFile, AbstractController controller, Parent root, ClientState clientState){
        this.fxmlFile = fxmlFile;
        this.controller = controller;
        this.root = root;
        this.clientState = clientState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SavedScene that = (SavedScene) o;
        return controller.equals(that.controller) &&
                root.equals(that.root) &&
                clientState == that.clientState;
    }

    @Override
    public int hashCode() {
        return Objects.hash(controller, root, clientState);
    }
}

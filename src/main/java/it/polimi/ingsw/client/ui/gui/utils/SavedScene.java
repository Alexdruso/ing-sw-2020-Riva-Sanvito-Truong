package it.polimi.ingsw.client.ui.gui.utils;

import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.AbstractController;
import javafx.scene.Parent;

import java.util.Objects;

public class SavedScene {
    public final AbstractController controller;
    public final Parent root;
    public final ClientState clientState;

    public SavedScene(AbstractController controller, Parent root, ClientState clientState){
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

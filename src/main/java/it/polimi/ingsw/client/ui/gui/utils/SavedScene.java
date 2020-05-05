package it.polimi.ingsw.client.ui.gui.utils;

import it.polimi.ingsw.client.ui.gui.guicontrollers.AbstractController;
import javafx.scene.Parent;

public class SavedScene {
    public final AbstractController controller;
    public final Parent root;

    public SavedScene(AbstractController controller, Parent root){
        this.controller = controller;
        this.root = root;
    }
}

package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.ui.gui.ConnectToServerGUIClientState;
import it.polimi.ingsw.client.ui.gui.InGameGUIClientState;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class InGameController extends AbstractController{

    private final double BOTTOM_ANCHOR_DISTANCE = 20.0;

    @FXML
    GridPane boardPane;

    @FXML
    AnchorPane boardContainer;

    @FXML
    public void initialize(){
        boardContainer.widthProperty().addListener((o, oldWidth, newWidth) -> setBoardSize());
        boardContainer.heightProperty().addListener((o, oldHeight, newHeight) -> setBoardSize());
    }

    public void setBoardSize(){
        double width = boardContainer.getWidth();
        double height = boardContainer.getHeight();
        if(width > height){
            AnchorPane.setBottomAnchor(boardPane, 0.0);
            AnchorPane.setTopAnchor(boardPane, 0.0);
            AnchorPane.setLeftAnchor(boardPane, (width-height)/2);
            AnchorPane.setRightAnchor(boardPane, (width-height)/2);
        } else {
            AnchorPane.setBottomAnchor(boardPane, (height-width)/2);
            AnchorPane.setTopAnchor(boardPane, (height-width)/2);
            AnchorPane.setLeftAnchor(boardPane, 0.0);
            AnchorPane.setRightAnchor(boardPane, 0.0);
        }
    }

    @FXML
    public void handleMenuButton(ActionEvent event){
        ((InGameGUIClientState)state).returnToMenu();
    }

    @Override
    public void handleError(String message) {

    }
}

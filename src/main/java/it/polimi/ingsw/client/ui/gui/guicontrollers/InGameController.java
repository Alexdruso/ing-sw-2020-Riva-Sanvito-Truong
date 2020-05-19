package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.client.ui.gui.AskWorkerPositionGUIClientTurnState;
import it.polimi.ingsw.client.ui.gui.InGameGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.List;
import java.util.stream.Collectors;

public class InGameController extends AbstractController{
    @FXML
    GridPane boardPane;

    @FXML
    AnchorPane boardContainer;

    private List<StackPane> cellPanes;

    @FXML
    public void initialize(){
        boardContainer.widthProperty().addListener((o, oldWidth, newWidth) -> setBoardSize());
        boardContainer.heightProperty().addListener((o, oldHeight, newHeight) -> setBoardSize());
        //Children of a GridPane are positioned exactly in the expected order, with the first one in the top-left corner
        //and the last one in the bottom-right corner
        cellPanes = boardPane.getChildren().stream().map(node -> (StackPane)node).collect(Collectors.toList());
        assert(cellPanes.size() == 25);
        for(StackPane pane: cellPanes){
            pane.setOnMouseClicked(e -> selectCell(pane));
        }
    }

    public void setLabel(String label){

    }

    public void setPrompt(String prompt){

    }

    public void setBoardClickableStatus(boolean enabled){

    }

    private void redrawBoard(){

    }


    private void selectCell(StackPane pane){
        Integer x = GridPane.getColumnIndex(pane);
        Integer y = GridPane.getRowIndex(pane);
        System.out.println("Selected (x,y): " + x + ", " + y);
        ReducedCell targetCell = new ReducedCell(x, y);
        ((AskWorkerPositionGUIClientTurnState)client.getGame().getTurn().getTurnState()).selectCell(targetCell);
    }

    private void setBoardSize(){
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

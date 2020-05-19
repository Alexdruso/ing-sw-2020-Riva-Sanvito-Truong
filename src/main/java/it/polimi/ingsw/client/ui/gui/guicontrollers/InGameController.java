package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.client.ui.gui.AskWorkerPositionGUIClientTurnState;
import it.polimi.ingsw.client.ui.gui.InGameGUIClientState;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class InGameController extends AbstractController{
    @FXML
    GridPane boardPane;

    @FXML
    AnchorPane boardContainer;

    @FXML
    Label mainLabel;

    @FXML
    Label prompt;

    private List<StackPane> cellPanes;
    private boolean active = false;

    private enum BoardElements {
        BLOCK_0,
        BLOCK_1,
        BLOCK_2,
        DOME,
        WORKER_A,
        WORKER_B,
        WORKER_C,
    }

    private final HashMap<BoardElements, Image> assets = new HashMap<>();

    @FXML
    public void initialize(){
        assets.put(BoardElements.BLOCK_0, new Image("/assets/board/block_0.png"));
        assets.put(BoardElements.BLOCK_1, new Image("/assets/board/block_1.png"));
        assets.put(BoardElements.BLOCK_2, new Image("/assets/board/block_2.png"));
        assets.put(BoardElements.DOME, new Image("/assets/board/dome.png"));
        assets.put(BoardElements.WORKER_A, new Image("/assets/board/worker_a.png"));
        assets.put(BoardElements.WORKER_B, new Image("/assets/board/worker_b.png"));
        assets.put(BoardElements.WORKER_C, new Image("/assets/board/worker_c.png"));

        boardContainer.widthProperty().addListener((o, oldWidth, newWidth) -> setBoardSize());
        boardContainer.heightProperty().addListener((o, oldHeight, newHeight) -> setBoardSize());

        //Children of a GridPane are positioned exactly in the expected order, with the first one in the top-left corner
        //and the last one in the bottom-right corner
        cellPanes = boardPane.getChildren().stream().map(node -> (StackPane)node).collect(Collectors.toList());
        assert(cellPanes.size() == 25);
        for(StackPane pane: cellPanes){
            pane.setOnMouseClicked(e -> {
                if(active){
                    selectCell(pane);
                }
            });
        }
    }

    public void setLabel(String label){
        mainLabel.setText(label);
    }

    public void setPrompt(String prompt){
        this.prompt.setText(prompt);
    }

    public void setBoardClickableStatus(boolean enabled){
        //Note: the following hack is needed since JavaFX doesn't handle removing by string very well
        //instead, it requires us to find the index and removing at the index
        this.active = enabled;
        if(enabled){
            for(StackPane pane: cellPanes){
                int toRemove = pane.getStyleClass().indexOf("cell-container-passive");
                if(toRemove != -1){
                    pane.getStyleClass().remove(toRemove);
                    pane.getStyleClass().add("cell-container-active");
                }
            }
        } else {
            for(StackPane pane: cellPanes){
                int toRemove = pane.getStyleClass().indexOf("cell-container-active");
                if(toRemove != -1){
                    pane.getStyleClass().remove(toRemove);
                    pane.getStyleClass().add("cell-container-passive");
                }
            }
        }
    }

    public void redrawBoard(){

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

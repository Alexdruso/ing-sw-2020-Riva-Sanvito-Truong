package it.polimi.ingsw.client.ui.gui.guicontrollers;

import it.polimi.ingsw.client.reducedmodel.ReducedBoard;
import it.polimi.ingsw.client.reducedmodel.ReducedCell;
import it.polimi.ingsw.client.reducedmodel.ReducedPlayer;
import it.polimi.ingsw.client.ui.gui.BuildGUIClientTurnState;
import it.polimi.ingsw.client.ui.gui.GUIClientTurnState;
import it.polimi.ingsw.client.ui.gui.guicontrollers.elements.LateralGodCard;
import it.polimi.ingsw.client.ui.gui.guicontrollers.elements.MenuConfirmation;
import it.polimi.ingsw.client.ui.gui.guicontrollers.elements.PlayerLateralLabel;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedComponent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller for the InGame state
 */
public class InGameController extends AbstractController{
    @FXML StackPane rootPane;
    @FXML GridPane boardPane;
    @FXML AnchorPane boardContainer;
    @FXML Label mainLabel;
    @FXML Label prompt;
    @FXML VBox sideButtons;
    @FXML VBox lateralLabelsContainer;

    private List<StackPane> cellPanes;
    private boolean active = false;

    private boolean isSkipDisplayed = false;
    private boolean isCancelDisplayed = false;
    private boolean isComponentSelectionDisplayed = false;

    /**
     * This enum represents all the possible board elements that should be rendered on screen
     */
    private enum BoardElement {
        /**
         * A block on the ground level
         */
        BLOCK_0,
        /**
         * A block on the first level
         */
        BLOCK_1,
        /**
         * A block on the second level
         */
        BLOCK_2,
        /**
         * A dome
         */
        DOME,
        /**
         * A worker of the first player
         */
        WORKER_A,
        /**
         * A worker of the second player
         */
        WORKER_B,
        /**
         * A worker of the third player
         */
        WORKER_C,
    }

    private final HashMap<ReducedPlayer, PlayerLateralLabel> lateralLabels = new HashMap<>();
    private final EnumMap<BoardElement, Image> boardAssets = new EnumMap<>(BoardElement.class);
    private ImageView blockIcon;
    private ImageView domeIcon;
    private LateralGodCard lateralGodCard;

    /**
     * This method, when given an Image instance, returns an ImageView with the correct dimensions, which are bound to the size of the board
     * @param image the Image instance whose ImageView is to be resized accordingly to the board's size
     * @return the resulting ImageView
     */
    private ImageView getImageView(Image image){
        final double cellContentMargin = 5.0;
        ImageView imageView = new ImageView(image);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.fitWidthProperty().bind(boardPane.widthProperty().divide(5).subtract(cellContentMargin));
        imageView.fitHeightProperty().bind(boardPane.heightProperty().divide(5).subtract(cellContentMargin));
        return imageView;
    }

    /**
     * This method returns the correct rendering for a cell, including blocks, domes and workers
     * @param cell the cell to be rendered
     * @return a list of ImageView in the order in which they should be rendered, from the bottom up
     */
    private List<ImageView> getImageStack(ReducedCell cell){
        List<ReducedPlayer> players = new ArrayList<>(client.getGame().getPlayersList());
        //Temporary rendering, until we find a better way to render stuff
        List<ImageView> imageStack = new ArrayList<>();
        for(int i = 0; i < cell.getTowerHeight(); i++){
            imageStack.add(getImageView(boardAssets.get(BoardElement.values()[i])));
        }
        if(cell.hasDome()){
            imageStack.add(getImageView(boardAssets.get(BoardElement.DOME)));
        }
        if(cell.getWorker().isPresent()){
           int playerID = players.indexOf(cell.getWorker().get().getPlayer());
           imageStack.add(getImageView(boardAssets.get(BoardElement.values()[playerID + 4])));
        }
        return imageStack;
    }

    @Override
    public void onSceneShow(){
        lateralLabels.clear();
        lateralLabelsContainer.getChildren().clear();
        int playerNumber = 0;
        for(ReducedPlayer player: client.getGame().getPlayersList()){
            PlayerLateralLabel label = new PlayerLateralLabel(player.getNickname(), playerNumber, player.getGod().getName());
            label.setOnMouseClicked(e -> {
                lateralGodCard.setDescription(String.format(I18n.string(I18nKey.S_GOD), player.getNickname()));
                lateralGodCard.clickGod(player.getGod());
            });
            if(player.equals(client.getGame().getTurn().getPlayer())){
                label.setActiveStatus(true);
            } else if (player.isInGame()) {
                label.setActiveStatus(false);
            } else {
                label.setSpectatingStatus();
            }
            lateralLabelsContainer.getChildren().add(label);
            lateralLabels.put(player, label);
            playerNumber++;
        }
    }

    @Override
    public void setupController(){
        lateralGodCard.setGods(client.getGame().getPlayersList().stream().map(ReducedPlayer::getGod).collect(Collectors.toList()));
    }

    /**
     * JavaFX initialization method
     */
    @FXML
    void initialize(){
        boardAssets.put(BoardElement.BLOCK_0, new Image("/assets/board/block_0.png"));
        boardAssets.put(BoardElement.BLOCK_1, new Image("/assets/board/block_1.png"));
        boardAssets.put(BoardElement.BLOCK_2, new Image("/assets/board/block_2.png"));
        boardAssets.put(BoardElement.DOME, new Image("/assets/board/dome.png"));
        boardAssets.put(BoardElement.WORKER_A, new Image("/assets/board/worker_a.png"));
        boardAssets.put(BoardElement.WORKER_B, new Image("/assets/board/worker_b.png"));
        boardAssets.put(BoardElement.WORKER_C, new Image("/assets/board/worker_c.png"));

        domeIcon = new ImageView("/assets/dome_icon.png");
        domeIcon.setPreserveRatio(true);
        domeIcon.fitWidthProperty().bind(sideButtons.widthProperty());
        domeIcon.setOnMouseClicked(e -> selectComponent(ReducedComponent.DOME));

        blockIcon = new ImageView("/assets/block_icon.png");
        blockIcon.setPreserveRatio(true);
        blockIcon.fitWidthProperty().bind(sideButtons.widthProperty());
        blockIcon.setOnMouseClicked(e -> selectComponent(ReducedComponent.BLOCK));

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

        lateralLabelsContainer.setPickOnBounds(false);

        lateralGodCard = new LateralGodCard(false);
        rootPane.getChildren().add(lateralGodCard);
    }

    /**
     * This method is used to set the label at the top of the scene
     * @param label the String to be used as the label text
     */
    public void setLabel(String label){
        mainLabel.setText(label);
    }

    /**
     * This method is used to set the prompt at the top of the scene
     * @param prompt the String to be used as the prompt text
     */
    public void setPrompt(String prompt){
        this.prompt.setText(prompt);
    }

    /**
     * This method sets whether the board is clickable by the player or not
     * @param enabled if true, the board becomes clickable.
     */
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

    /**
     * This method is used to redraw the board elements
     */
    public void redrawBoard(){
        ReducedBoard board = client.getGame().getBoard();
        for(int x = 0; x < 5; x++){
            for(int y = 0; y < 5; y++){
                ReducedCell cell = board.getCell(x, y);
                StackPane cellPane = cellPanes.get(5*y+x);
                final String CELL_HIGHLIGHTED = "cell-highlighted";
                if(cell.isHighlighted()){
                    if(!cellPane.getStyleClass().contains(CELL_HIGHLIGHTED)){
                        cellPane.getStyleClass().add(CELL_HIGHLIGHTED);
                    }
                } else {
                    int toRemove = cellPane.getStyleClass().indexOf(CELL_HIGHLIGHTED);
                    if(toRemove != -1){
                        cellPane.getStyleClass().remove(toRemove);
                    }
                }
                cellPane.getChildren().clear();
                cellPane.getChildren().addAll(getImageStack(cell));
            }
        }
    }

    /**
     * This method retrieves the coordinate of the clicked StackPane and sends the information to the TurnState
     * @param pane the StackPane which has been clicked by the user
     */
    private void selectCell(StackPane pane){
        Integer x = GridPane.getColumnIndex(pane);
        Integer y = GridPane.getRowIndex(pane);
        ((GUIClientTurnState)client.getGame().getTurn().getTurnState()).selectCell(x, y);
    }

    /**
     * This method sends information to the TurnState that a particular component has been selected
     * @param component the ReducedComponent that has been selected
     */
    private void selectComponent(ReducedComponent component){
        clearSideButtons();
        ((BuildGUIClientTurnState)client.getGame().getTurn().getTurnState()).selectComponent(component);
    }

    /**
     * This method displays the cancel button on the left side
     */
    public void displayCancelButton(){
        if(!isCancelDisplayed){
            Button button = new Button();
            button.setText(I18n.string(I18nKey.CANCEL));
            button.setOnAction(e -> cancel());
            button.getStyleClass().add("bigbutton");
            sideButtons.getChildren().add(button);
            isCancelDisplayed = true;
        }
    }

    /**
     * This method displays the skip button on the left side
     */
    public void displaySkipButton(){
        if(!isSkipDisplayed){
            Button button= new Button();
            button.setText(I18n.string(I18nKey.SKIP));
            button.setOnAction(e -> skip());
            button.getStyleClass().add("bigbutton");
            sideButtons.getChildren().add(button);
            isSkipDisplayed = true;
        }
    }

    /**
     * This method displays the component selection on the left side
     */
    public void displayComponentSelection(){
        if(!isComponentSelectionDisplayed){
            sideButtons.getChildren().add(domeIcon);
            sideButtons.getChildren().add(blockIcon);
            isComponentSelectionDisplayed = true;
        }
    }

    /**
     * This method clears all the buttons on the left side of the screen
     */
    public void clearSideButtons(){
        sideButtons.getChildren().clear();
        isSkipDisplayed = false;
        isCancelDisplayed = false;
        isComponentSelectionDisplayed = false;
    }

    /**
     * This method sends to the TurnState the information that the player wants to skip the turn
     */
    private void skip(){
        ((GUIClientTurnState)client.getGame().getTurn().getTurnState()).skip();
        clearSideButtons();
    }

    /**
     * This method sends to the TurnState the information that the player wants to cancel the last action
     */
    private void cancel(){
        ((GUIClientTurnState)client.getGame().getTurn().getTurnState()).cancel();
        clearSideButtons();
    }

    /**
     * This method sets the board size with respect to its containing Pane
     */
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

    /**
     * Handles menu button on screen
     * @param event the mouse click event
     */
    @FXML
    void handleMenuButton(ActionEvent event){
        MenuConfirmation.showMenuConfirmation(client);
    }

    @Override
    public void handleError(String message) {
        ((GUIClientTurnState)client.getGame().getTurn().getTurnState()).handleError();
    }
}

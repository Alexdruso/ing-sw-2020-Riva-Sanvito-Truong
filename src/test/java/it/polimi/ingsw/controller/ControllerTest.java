package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.ViewClientMessage;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

class ControllerTest {

    @Test
    void updateWithPositiveQueries() {
        //setup a mock game to test all the possible outcomes
        Game myGame = mock(Game.class);
        //create mock view
        View myView = mock(View.class);
        User myUser = mock(User.class);
        //mock the actions
        ClientChooseGodsMessage myChooseGodsCommand = spy(
                new ClientChooseGodsMessage(
                        Arrays.stream((new ReducedGod[]{new ReducedGod("Pippo")})).collect(Collectors.toList())
                )
        );
        ClientChooseGodMessage myChooseGodCommand = spy(
                new ClientChooseGodMessage(
                        null
                )
        );
        ClientSetStartPlayerMessage mySetStartPlayerCommand = spy(
                new ClientSetStartPlayerMessage(
                        null
                )
        );
        ClientSetWorkerStartPositionMessage mySetStartPositionMessage = spy(
                new ClientSetWorkerStartPositionMessage(
                        0,
                        0,
                        null
                )
        );
        ClientDisconnectMessage myDisconnectMessage = spy(
                new ClientDisconnectMessage()
        );
        ClientBuildMessage myBuildCommand = spy(
                new ClientBuildMessage(
                        0,
                        0,
                        Component.BLOCK,
                        0,
                        null
                )
        );
        ClientMoveMessage myMoveCommand = spy(
                new ClientMoveMessage(
                        0,
                        0,
                        0,
                        0,
                        null)
        );
        ClientSkipMessage mySkipCommand = spy(new ClientSkipMessage());

        ViewClientMessage chooseGodsViewClientMessage = new ViewClientMessage(myChooseGodsCommand, myView, myUser);
        ViewClientMessage chooseGodViewClientMessage = new ViewClientMessage(myChooseGodCommand, myView, myUser);
        ViewClientMessage setStartPlayerViewClientMessage = new ViewClientMessage(
                mySetStartPlayerCommand,
                myView,
                myUser
        );
        ViewClientMessage setStartPositionViewClientMessage = new ViewClientMessage(
                mySetStartPositionMessage,
                myView,
                myUser
        );
        ViewClientMessage disconnectViewClientMessage = new ViewClientMessage(myDisconnectMessage, myView, myUser);
        ViewClientMessage buildViewClientMessage = new ViewClientMessage(myBuildCommand, myView, myUser);
        ViewClientMessage moveViewClientMessage = new ViewClientMessage(myMoveCommand, myView, myUser);
        ViewClientMessage skipViewClientMessage = new ViewClientMessage(mySkipCommand, myView, myUser);
        //establish mock behavior, in this case a positive behavior
        when(
                myGame
                        .isValidGodsChoice(
                                myChooseGodsCommand.getGods(),
                                myUser
                        )
        )
                .thenReturn(true);
        when(
                myGame
                        .isValidGodChoice(
                                myChooseGodCommand.getGod(),
                                myUser
                        )
        )
                .thenReturn(true);
        when(
                myGame
                        .isValidStartPlayerChoice(
                                mySetStartPlayerCommand.startPlayer,
                                myUser
                        )
        )
                .thenReturn(true);
        when(
                myGame.
                        isValidPositioning(
                                anyInt(),
                                anyInt(),
                                any(),
                                any()
                        )
        )
                .thenReturn(true);
        when(
                myGame.
                        isInGame(
                                myUser
                        )
        )
                .thenReturn(true);
        when(
                myGame
                        .isValidBuild(
                                myBuildCommand.targetCellX, myBuildCommand.targetCellY,
                                myBuildCommand.component, myBuildCommand.performer,
                                myUser
                        )
        )
                .thenReturn(true);
        when(
                myGame
                        .isValidMove(
                                myMoveCommand.sourceCellX, myMoveCommand.sourceCellY,
                                myMoveCommand.targetCellX, myMoveCommand.targetCellY,
                                myMoveCommand.performer, myUser
                        )
        )
                .thenReturn(true);
        when(myGame.isValidSkip(myUser)).thenReturn(true);
        //ready steady go
        //create a new controller
        Controller myController = new Controller(myGame);
        //add all the updates
        myController.update(chooseGodsViewClientMessage);
        myController.update(chooseGodViewClientMessage);
        myController.update(setStartPlayerViewClientMessage);
        myController.update(setStartPositionViewClientMessage);
        myController.update(disconnectViewClientMessage);
        myController.update(buildViewClientMessage);
        myController.update(moveViewClientMessage);
        myController.update(skipViewClientMessage);
        //process the updates
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        //verify the right calls
        verify(myGame, times(1)).isValidBuild(
                myBuildCommand.targetCellX, myBuildCommand.targetCellY,
                myBuildCommand.component, myBuildCommand.performer,
                myUser);
        verify(myGame, times(1)).build(
                myBuildCommand.targetCellX, myBuildCommand.targetCellY,
                myBuildCommand.component, myBuildCommand.performer,
                myUser);
        verify(myGame, times(1)).isValidMove(
                myMoveCommand.sourceCellX, myMoveCommand.sourceCellY,
                myMoveCommand.targetCellX, myMoveCommand.targetCellY,
                myMoveCommand.performer, myUser);
        verify(myGame, times(1)).move(
                myMoveCommand.targetCellX, myMoveCommand.targetCellY,
                myMoveCommand.performer, myUser);
        verify(myGame, times(1)).isValidSkip(myUser);
        verify(myGame, times(1)).skip();
        verify(myView, times(0)).handleMessage(StatusMessages.CLIENT_ERROR);
    }

    @Test
    void updateWithNegativeQueries() {
        //setup a mock game to test all the possible outcomes
        Game myGame = mock(Game.class);
        //create mock view
        View myView = mock(View.class);
        User myUser = mock(User.class);
        //mock the actions
        ClientChooseGodsMessage myChooseGodsCommand = spy(
                new ClientChooseGodsMessage(
                        Arrays.stream((new ReducedGod[]{new ReducedGod("Pippo")})).collect(Collectors.toList())
                )
        );
        ClientChooseGodMessage myChooseGodCommand = spy(
                new ClientChooseGodMessage(
                        null
                )
        );
        ClientSetStartPlayerMessage mySetStartPlayerCommand = spy(
                new ClientSetStartPlayerMessage(
                        null
                )
        );
        ClientSetWorkerStartPositionMessage mySetStartPositionMessage = spy(
                new ClientSetWorkerStartPositionMessage(
                        0,
                        0,
                        null
                )
        );
        ClientDisconnectMessage myDisconnectMessage = spy(
                new ClientDisconnectMessage()
        );
        ClientBuildMessage myBuildCommand = spy(
                new ClientBuildMessage(
                        0,
                        0,
                        Component.BLOCK,
                        0,
                        null)
        );
        ClientMoveMessage myMoveCommand = spy(
                new ClientMoveMessage(
                        0,
                        0,
                        0,
                        0,
                        null)
        );
        ClientSkipMessage mySkipCommand = spy(new ClientSkipMessage());


        ViewClientMessage chooseGodsViewClientMessage = new ViewClientMessage(
                myChooseGodsCommand,
                myView,
                myUser
        );
        ViewClientMessage chooseGodViewClientMessage = new ViewClientMessage(myChooseGodCommand, myView, myUser);
        ViewClientMessage setStartPlayerViewClientMessage = new ViewClientMessage(
                mySetStartPlayerCommand,
                myView,
                myUser
        );
        ViewClientMessage setStartPositionViewClientMessage = new ViewClientMessage(
                mySetStartPositionMessage,
                myView,
                myUser
        );
        ViewClientMessage disconnectViewClientMessage = new ViewClientMessage(
                myDisconnectMessage,
                myView,
                myUser);
        ViewClientMessage buildViewClientMessage = new ViewClientMessage(myBuildCommand, myView, myUser);
        ViewClientMessage moveViewClientMessage = new ViewClientMessage(myMoveCommand, myView, myUser);
        ViewClientMessage skipViewClientMessage = new ViewClientMessage(mySkipCommand, myView, myUser);
        //establish mock behavior, in this case a negative behavior
        when(
                myGame
                        .isValidGodsChoice(
                                myChooseGodsCommand.getGods(),
                                myUser
                        )
        )
                .thenReturn(false);
        when(
                myGame
                        .isValidGodChoice(
                                myChooseGodCommand.getGod(),
                                myUser
                        )
        )
                .thenReturn(false);
        when(
                myGame
                        .isValidStartPlayerChoice(
                                mySetStartPlayerCommand.startPlayer,
                                myUser
                        )
        )
                .thenReturn(false);
        when(
                myGame.
                        isValidPositioning(
                                anyInt(),
                                anyInt(),
                                any(),
                                any()
                        )
        )
                .thenReturn(false);
        when(
                myGame.
                        isInGame(
                                myUser
                        )
        )
                .thenReturn(false);
        when(myGame
                .isValidBuild(
                        myBuildCommand.targetCellX, myBuildCommand.targetCellY,
                        myBuildCommand.component, myBuildCommand.performer,
                        myUser))
                .thenReturn(false);
        when(myGame
                .isValidMove(
                        myMoveCommand.sourceCellX, myMoveCommand.sourceCellY,
                        myMoveCommand.targetCellX, myMoveCommand.targetCellY,
                        myMoveCommand.performer, myUser))
                .thenReturn(false);
        when(myGame.isValidSkip(myUser)).thenReturn(false);
        //ready, steady, go
        //ready steady go
        //create a new controller
        Controller myController = new Controller(myGame);
        //add all the updates
        myController.update(chooseGodsViewClientMessage);
        myController.update(chooseGodViewClientMessage);
        myController.update(setStartPlayerViewClientMessage);
        myController.update(setStartPositionViewClientMessage);
        myController.update(disconnectViewClientMessage);
        myController.update(buildViewClientMessage);
        myController.update(moveViewClientMessage);
        myController.update(skipViewClientMessage);
        //process the updates
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        myController.dispatchViewClientMessages();
        //verify right calls
        verify(myGame, times(1)).isValidBuild(
                myBuildCommand.targetCellX, myBuildCommand.targetCellY,
                myBuildCommand.component, myBuildCommand.performer,
                myUser);
        verify(myGame, times(0)).build(
                myBuildCommand.targetCellX, myBuildCommand.targetCellY,
                myBuildCommand.component, myBuildCommand.performer,
                myUser);
        verify(myGame, times(1)).isValidMove(
                myMoveCommand.sourceCellX, myMoveCommand.sourceCellY,
                myMoveCommand.targetCellX, myMoveCommand.targetCellY,
                myMoveCommand.performer, myUser);
        verify(myGame, times(0)).move(
                myMoveCommand.targetCellX, myMoveCommand.targetCellY,
                myMoveCommand.performer, myUser);
        verify(myGame, times(1)).isValidSkip(myUser);
        verify(myGame, times(0)).skip();
        verify(myView, times(7)).handleMessage(StatusMessages.CLIENT_ERROR);
    }
}
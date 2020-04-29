package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ClientBuildMessage;
import it.polimi.ingsw.utils.messages.ClientMoveMessage;
import it.polimi.ingsw.utils.messages.ClientSkipMessage;
import it.polimi.ingsw.view.View;
import it.polimi.ingsw.view.ViewClientMessage;
import org.junit.jupiter.api.Test;

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
        ClientBuildMessage myBuildCommand = spy(new ClientBuildMessage(0, 0, Component.BLOCK, 0, null));
        ClientMoveMessage myMoveCommand = spy(new ClientMoveMessage(0, 0, 0, 0, null));
        ClientSkipMessage mySkipCommand = spy(new ClientSkipMessage());
        ViewClientMessage buildViewClientMessage = new ViewClientMessage(myBuildCommand, myView, myUser);
        ViewClientMessage moveViewClientMessage = new ViewClientMessage(myMoveCommand, myView, myUser);
        ViewClientMessage skipViewClientMessage = new ViewClientMessage(mySkipCommand, myView, myUser);
        //establish mock behavior, in this case a positive behavior
        when(myGame
                .isValidBuild(
                        myBuildCommand.targetCellX, myBuildCommand.targetCellY,
                        myBuildCommand.component, myBuildCommand.performer,
                        myUser))
                .thenReturn(true);
        when(myGame
                .isValidMove(
                        myMoveCommand.sourceCellX, myMoveCommand.sourceCellY,
                        myMoveCommand.targetCellX, myMoveCommand.targetCellY,
                        myMoveCommand.performer, myUser))
                .thenReturn(true);
        when(myGame.isValidSkip(myUser)).thenReturn(true);
        //ready steady go
        //create a new controller
        Controller myController = new Controller(myGame);
        //add all the updates
        myController.update(buildViewClientMessage);
        myController.update(moveViewClientMessage);
        myController.update(skipViewClientMessage);
        //process the updates
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
        verify(myGame, times(1)).skip(myUser);
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
        ClientBuildMessage myBuildCommand = spy(new ClientBuildMessage(0, 0, Component.BLOCK, 0, null));
        ClientMoveMessage myMoveCommand = spy(new ClientMoveMessage(0, 0, 0, 0, null));
        ClientSkipMessage mySkipCommand = spy(new ClientSkipMessage());
        ViewClientMessage buildViewClientMessage = new ViewClientMessage(myBuildCommand, myView, myUser);
        ViewClientMessage moveViewClientMessage = new ViewClientMessage(myMoveCommand, myView, myUser);
        ViewClientMessage skipViewClientMessage = new ViewClientMessage(mySkipCommand, myView, myUser);
        //establish mock behavior, in this case a negative behavior
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
        myController.update(buildViewClientMessage);
        myController.update(moveViewClientMessage);
        myController.update(skipViewClientMessage);
        //process the updates
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
        verify(myGame, times(0)).skip(myUser);
        verify(myView, times(3)).handleMessage(StatusMessages.CLIENT_ERROR);
    }
}
package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.board.Component;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

class ControllerTest {

    @Test
    void updateWithPositiveQueries() {
        //setup a mock game to test all the possible outcomes
        Game myGame = mock(Game.class);
        //create mock view
        View myView = mock(View.class);
        //mock the actions
        ClientBuildMessage myBuildCommand = spy(new ClientBuildMessage(null, myView,0,0, Component.BLOCK,0,null));
        ClientMoveMessage myMoveCommand = spy(new ClientMoveMessage(null, myView,0,0,0,0,null));
        ClientSkipMessage mySkipCommand = spy(new ClientSkipMessage(null, myView));
        //establish mock behavior, in this case a positive behavior
        when(myGame.isValidBuild(myBuildCommand)).thenReturn(true);
        when(myGame.isValidMove(myMoveCommand)).thenReturn(true);
        when(myGame.isValidSkip(mySkipCommand)).thenReturn(true);
        //ready steady go
        //create a new controller
        Controller myController = new Controller(myGame);
        //call all the updates
        myController.update(myBuildCommand);
        myController.update(myMoveCommand);
        myController.update(mySkipCommand);
        //verify the right calls
        verify(myGame, times(1)).isValidBuild(myBuildCommand);
        verify(myGame, times(1)).build(myBuildCommand);
        verify(myGame, times(1)).isValidMove(myMoveCommand);
        verify(myGame, times(1)).move(myMoveCommand);
        verify(myGame, times(1)).isValidSkip(mySkipCommand);
        verify(myGame, times(1)).skip(mySkipCommand);
        verify(myView, times(0)).handleMessage(StatusMessages.CLIENT_ERROR);
    }

    @Test
    void updateWithNegativeQueries() {
        //setup a mock game to test all the possible outcomes
        Game myGame = mock(Game.class);
        //create mock view
        View myView = mock(View.class);
        //mock the actions
        ClientBuildMessage myBuildCommand = spy(new ClientBuildMessage(null, myView,0,0, Component.BLOCK,0,null));
        ClientMoveMessage myMoveCommand = spy(new ClientMoveMessage(null, myView,0,0,0,0,null));
        ClientSkipMessage mySkipCommand = spy(new ClientSkipMessage(null, myView));
        //establish mock behavior, in this case a negative behavior
        when(myGame.isValidBuild(myBuildCommand)).thenReturn(false);
        when(myGame.isValidMove(myMoveCommand)).thenReturn(false);
        when(myGame.isValidSkip(mySkipCommand)).thenReturn(false);
        //ready, steady, go
        //ready steady go
        //create a new controller
        Controller myController = new Controller(myGame);
        //call all the updates
        myController.update(myBuildCommand);
        myController.update(myMoveCommand);
        myController.update(mySkipCommand);
        //verify right calls
        verify(myGame, times(1)).isValidBuild(myBuildCommand);
        verify(myGame, times(0)).build(myBuildCommand);
        verify(myGame, times(1)).isValidMove(myMoveCommand);
        verify(myGame, times(0)).move(myMoveCommand);
        verify(myGame, times(1)).isValidSkip(mySkipCommand);
        verify(myGame, times(0)).skip(mySkipCommand);
        verify(myView, times(3)).handleMessage(StatusMessages.CLIENT_ERROR);
    }
}
package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.utils.networking.Connection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ViewTest {

    @Test
    public void viewTest() {
        //Create all needed classes
        Connection myConnection = mock(Connection.class);
        Controller myController = mock(Controller.class);
        String myUsername = "Jotaro Kujo";
        View myView = new View(myConnection, myUsername);
        //the controller observes the view
        myView.addObserver(myController, (obs, message) ->
                ((Controller) obs).update(message));
        //test getters and setters
        assertEquals(myUsername, myView.getUser().nickname);
        //test all update methods
        ServerMessage myServerMessage = new ServerStartPlayMatchMessage();
        myView.updateFromGame(myServerMessage);
        verify(myConnection).send(myServerMessage);
        myView.handleDisconnection();
        verify(myConnection).close();
        StatusMessages myStatusMessage = StatusMessages.CLIENT_ERROR;
        myView.handleMessage(myStatusMessage);
        verify(myConnection).send(myStatusMessage);
        ClientMessage myClientMessage = new ClientSetStartPlayerMessage(myView.getUser().toReducedUser());
        myView.updateFromClient(myClientMessage);
        verify(myController).update(any(ViewClientMessage.class));
        DisconnectionMessage myClientDisconnectMessage = new DisconnectionMessage();
        myView.updateFromClient(myClientDisconnectMessage);
        verify(myConnection, times(2)).close();
    }


}
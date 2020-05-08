package it.polimi.ingsw.view;

import it.polimi.ingsw.controller.Controller;
import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.utils.networking.Connection;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

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
        DisconnectMessage myServerDisconnectMessage = new ServerDisconnectMessage();
        myView.updateFromGame(myServerDisconnectMessage);
        verify(myConnection).close(myServerDisconnectMessage);
        StatusMessages myStatusMessage = StatusMessages.CLIENT_ERROR;
        myView.handleMessage(myStatusMessage);
        verify(myConnection).send(myStatusMessage);
        ClientMessage myClientMessage = new ClientSetStartPlayerMessage(myView.getUser().toReducedUser());
        myView.updateFromClient(myClientMessage);
        verify(myController).update(any(ViewClientMessage.class));
        DisconnectMessage myClientDisconnectMessage = new ClientDisconnectMessage();
        myView.updateFromClient(myClientDisconnectMessage);
        verify(myConnection).close();
    }


}
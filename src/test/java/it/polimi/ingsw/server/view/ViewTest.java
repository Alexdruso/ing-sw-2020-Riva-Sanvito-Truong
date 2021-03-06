package it.polimi.ingsw.server.view;

import it.polimi.ingsw.server.controller.Controller;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.transmittables.DisconnectionMessage;
import it.polimi.ingsw.utils.networking.transmittables.StatusMessages;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientMessage;
import it.polimi.ingsw.utils.networking.transmittables.clientmessages.ClientSetStartPlayerMessage;
import it.polimi.ingsw.utils.networking.transmittables.servermessages.ServerMessage;
import it.polimi.ingsw.utils.networking.transmittables.servermessages.ServerStartPlayMatchMessage;
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
        myView.requestDisconnection();
        verify(myConnection).close();
        StatusMessages myStatusMessage = StatusMessages.CLIENT_ERROR;
        myView.handleStatusMessage(myStatusMessage);
        verify(myConnection).send(myStatusMessage);
        ClientMessage myClientMessage = new ClientSetStartPlayerMessage(myView.getUser().toReducedUser());
        myView.updateFromClient(myClientMessage);
        verify(myController).update(any(ViewClientMessage.class));
        DisconnectionMessage myClientDisconnectMessage = new DisconnectionMessage();
        myView.updateFromClient(myClientDisconnectMessage);
        verify(myConnection, times(2)).close();
    }


}
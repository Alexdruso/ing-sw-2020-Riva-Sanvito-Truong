package it.polimi.ingsw.server;

import it.polimi.ingsw.model.gods.GodCard;
import it.polimi.ingsw.utils.messages.*;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchTest {

    @Test
    void setupRun() {
        /*
        In this method we test the setup of a 3-players match without starting the infinite loop of the controller.
        Keep in mind that this method will be running on a separate thread which sets up all the classes needed.
         */

        //create a fake server
        Server myServer = mock(Server.class);
        //setup test source, this is going to be a quite weird and important match
        LinkedHashMap<String, Connection> myMap = new LinkedHashMap<>();
        String[] nicknames = new String[]{"Alan Turing", "Steve Wozniak", "Edsger W. Dijkstra"};
        myMap.put(nicknames[0], mock(Connection.class));
        myMap.put(nicknames[1], mock(Connection.class));
        myMap.put(nicknames[2], mock(Connection.class));
        //establish behaviour
        myMap.forEach((nickname, connection) -> when(connection.isActive()).thenReturn(true));
        //initialize a match
        Match myMatch = new Match(myServer);
        //now try first to add as a LinkedHashMap
        myMatch.addParticipants(myMap);
        //invoke myMatch.run
        (new Thread(myMatch)).start();
        await().until(() -> myMatch.getModel() != null);
        List<ReducedGod> gods = Arrays
                .asList(new ReducedGod("APOLLO"), new ReducedGod("ARTEMIS"), new ReducedGod("ATHENA"));
        await().until(() -> myMatch.getModel()
                .isValidGodsChoice(gods, myMatch.getVirtualViews().get(0).getUser())
        );
        myMatch.getVirtualViews().get(0).updateFromClient(new DisconnectionMessage());
        await().until(() -> !myMatch.isPlaying());
        verify(myServer).removeMatch(myMatch);
        //verify all fields are set
        assertNotNull(myMatch.getModel());
        assertNotNull(myMatch.getController());
        assertNotNull(myMatch.getVirtualViews());
        for (View view : myMatch.getVirtualViews()) {
            assertNotNull(view);
        }
        assertEquals(3, myMatch.getVirtualViews().size());
        //Verify calls
        for (Connection connection : myMap.values()) {
            verify(connection, times(1)).addObserver(any(View.class), any(BiConsumer.class));
        }
        //check that ServerStartSetupMatchMessage has been sent
        ArgumentCaptor<ServerMessage> myMessageCaptor = ArgumentCaptor.forClass(ServerMessage.class);
        for (Connection connection : myMap.values()) {
            if (
                    connection != myMatch.getParticipantsNicknameToConnection()
                            .get(myMatch.getVirtualViews().get(0).getUser().nickname)
            ) {
                verify(connection, times(2)).send(myMessageCaptor.capture());
                verify(connection).close(any(DisconnectionMessage.class));
            } else {
                verify(connection, times(2)).send(myMessageCaptor.capture());
                verify(connection).close();
            }
        }
        assertEquals(6, myMessageCaptor.getAllValues().size());
        List<ServerStartSetupMatchMessage> startSetupMatchMessageList = myMessageCaptor.getAllValues().stream()
                .filter(x -> x instanceof ServerStartSetupMatchMessage).map(x -> (ServerStartSetupMatchMessage) x)
                .collect(Collectors.toList());
        assertEquals(3, startSetupMatchMessageList.size());
        assertEquals(myMap.keySet().size(), startSetupMatchMessageList.get(0).userList.length);
        assertEquals(
                Arrays.stream(nicknames).collect(Collectors.toSet()),
                Arrays.stream(startSetupMatchMessageList.get(0).userList).map(x -> x.nickname).collect(Collectors.toSet())
        );
        List<ServerAskGodsFromListMessage> askGodsFromListMessages = myMessageCaptor.getAllValues().stream()
                .filter(x -> x instanceof ServerAskGodsFromListMessage).map(x -> (ServerAskGodsFromListMessage) x)
                .collect(Collectors.toList());
        assertEquals(3, askGodsFromListMessages.size());
        assertEquals(nicknames[0], askGodsFromListMessages.get(0).user.nickname);
        assertEquals(
                Arrays.stream(GodCard.values()).map(Enum::toString).collect(Collectors.toSet()),
                askGodsFromListMessages.get(0).getGodsList().stream().map(x -> x.name.toUpperCase()).collect(Collectors.toSet())
        );
        for (ReducedUser user : startSetupMatchMessageList.get(0).userList) {
            assertTrue(myMap.containsKey(user.nickname));
        }

    }

    @Test
    void addAndGetParticipants() {
        /*
        In this method we will just check if getter and adders work on the Match class.
        Keep in mind that no check on the maximum number of participants is done, as the Server is
        supposed to call adders and getters in the right numbers at the right time, which is before run.
        Also this part is supposed to be run on the Server thread.
         */

        //create a fake server
        Server myServer = mock(Server.class);
        //setup test source, this is going to be a quite weird and important match
        LinkedHashMap<String, Connection> myMap = new LinkedHashMap<>();
        String[] nicknames = new String[]{"Alan Turing", "Steve Wozniak", "Edsger W. Dijkstra", "Ada Lovelace", "James Gosling"};
        myMap.put(nicknames[0], mock(Connection.class));
        myMap.put(nicknames[1], mock(Connection.class));
        myMap.put(nicknames[2], mock(Connection.class));
        myMap.put(nicknames[3], mock(Connection.class));
        myMap.put(nicknames[4], mock(Connection.class));
        //initialize a match
        Match myMatch = new Match(myServer);
        //now try first to add as a LinkedHashMap
        myMatch.addParticipants(myMap);
        //see if it was added
        assertEquals(myMap, myMatch.getParticipantsNicknameToConnection());
        assertNotSame(myMap, myMatch.getParticipantsNicknameToConnection());
        //initialize a new match
        myMatch = new Match(myServer);
        //try sequential adds
        for (String nickname : nicknames) myMatch.addParticipant(nickname, myMap.get(nickname));
        //check if values inside are the same and in the correct order
        assertEquals(myMap, myMatch.getParticipantsNicknameToConnection());
        assertNotSame(myMap, myMatch.getParticipantsNicknameToConnection());
        //initialize a new match
        myMatch = new Match(myServer);
        //check empty participants
        assertTrue(myMatch.getParticipantsNicknameToConnection().isEmpty());
    }
}
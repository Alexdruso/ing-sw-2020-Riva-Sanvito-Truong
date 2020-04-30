package it.polimi.ingsw.server;

import it.polimi.ingsw.utils.messages.ReducedUser;
import it.polimi.ingsw.utils.messages.ServerMessage;
import it.polimi.ingsw.utils.messages.ServerStartSetupMatchMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.view.View;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MatchTest {

    @Test
    void setupRun() {
        /*
        In this method we test the setup of a 3-players match without starting the infinite loop of the controller.
        Keep in mind that this method will be running on a separate thread which sets up all the classes needed.
         */

        //setup test source, this is going to be a quite weird and important match
        LinkedHashMap<String, Connection> myMap = new LinkedHashMap<String, Connection>();
        String[] nicknames = new String[]{"Alan Turing", "Steve Wozniak", "Edsger W. Dijkstra"};
        myMap.put(nicknames[0], mock(Connection.class));
        myMap.put(nicknames[1], mock(Connection.class));
        myMap.put(nicknames[2], mock(Connection.class));
        //initialize a match
        Match myMatch = new Match();
        //now try first to add as a LinkedHashMap
        myMatch.addParticipants(myMap);
        //make myMatch not enter the loop
        myMatch.setIsPlaying(false);
        //invoke myMatch.run
        myMatch.run();
        //verify all fields are set
        assertNotNull(myMatch.getModel());
        assertNotNull(myMatch.getController());
        assertNotNull(myMatch.getVirtualViews());
        assertEquals(3, myMatch.getVirtualViews().size());
        //Verify calls
        for (Connection connection : myMap.values()) {
            verify(connection, times(1)).addObserver(any(View.class), any(BiConsumer.class));
        }
        //check that ServerStartSetupMatchMessage has been sent
        ArgumentCaptor<ServerMessage> myMessageCaptor = ArgumentCaptor.forClass(ServerMessage.class);
        for (Connection connection : myMap.values()) {
            verify(connection, times(2)).send(myMessageCaptor.capture());
        }
        assertEquals(6, myMessageCaptor.getAllValues().size());
        List<ServerStartSetupMatchMessage> startSetupMatchMessageList = myMessageCaptor.getAllValues().stream()
                .filter(x -> x instanceof ServerStartSetupMatchMessage).map(x -> (ServerStartSetupMatchMessage) x)
                .collect(Collectors.toList());
        assertEquals(3, startSetupMatchMessageList.size());
        assertEquals(myMap.keySet().size(), startSetupMatchMessageList.get(0).userList.length);
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

        //setup test source, this is going to be a quite weird and important match
        LinkedHashMap<String, Connection> myMap = new LinkedHashMap<String, Connection>();
        String[] nicknames = new String[]{"Alan Turing","Steve Wozniak","Edsger W. Dijkstra","Ada Lovelace","James Gosling"};
        myMap.put(nicknames[0], mock(Connection.class));
        myMap.put(nicknames[1], mock(Connection.class));
        myMap.put(nicknames[2], mock(Connection.class));
        myMap.put(nicknames[3], mock(Connection.class));
        myMap.put(nicknames[4], mock(Connection.class));
        //initialize a match
        Match myMatch = new Match();
        //now try first to add as a LinkedHashMap
        myMatch.addParticipants(myMap);
        //see if it was added
        assertEquals(myMap, myMatch.getParticipantsNicknameToConnection());
        assertNotSame(myMap, myMatch.getParticipantsNicknameToConnection());
        //initialize a new match
        myMatch = new Match();
        //try sequential adds
        for (String nickname : nicknames) myMatch.addParticipant(nickname, myMap.get(nickname));
        //check if values inside are the same and in the correct order
        assertEquals(myMap, myMatch.getParticipantsNicknameToConnection());
        assertNotSame(myMap, myMatch.getParticipantsNicknameToConnection());
        //initialize a new match
        myMatch = new Match();
        //check empty participants
        assertTrue(myMatch.getParticipantsNicknameToConnection().isEmpty());
    }
}
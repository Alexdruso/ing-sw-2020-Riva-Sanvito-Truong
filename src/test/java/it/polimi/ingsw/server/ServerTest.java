package it.polimi.ingsw.server;

import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ClientJoinLobbyMessage;
import it.polimi.ingsw.utils.messages.ClientSetNicknameMessage;
import it.polimi.ingsw.utils.messages.ClientSetPlayersCountMessage;
import it.polimi.ingsw.utils.messages.ServerStartSetupMatchMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServerTest {
    Server server = null;
    ServerLobbyBuilder lobbyBuilder;
    Connection[] connections = new Connection[3];
    Queue<Transmittable>[] queues = new LinkedList[3];
    ServerConnectionSetupHandler[] connHandlers = new ServerConnectionSetupHandler[3];

    @BeforeEach
    void initServer(){
        try{
            server = new Server();
        } catch (IOException e) {
            e.printStackTrace();
        }
        lobbyBuilder = server.getLobbyBuilder();
    }

    @AfterEach
    void cleanServer(){
        server.shutdown();
    }

    void mockConnections(){
        for(int j = 0; j < 3; j++){
            Connection mock = mock(Connection.class);
            when(mock.isActive()).thenReturn(true);
            LinkedList<Transmittable> q = new LinkedList<>();
            ServerConnectionSetupHandler ch = new ServerConnectionSetupHandler(server, mock);
            doAnswer(i -> q.add((Transmittable) i.getArguments()[0]))
                    .when(mock)
                    .send(any(Transmittable.class));
            connections[j] = mock;
            queues[j] = q;
            connHandlers[j] = ch;
        }
    }

    @Test
    void serverStartAndStop(){
        Thread t = new Thread(server::start);
        t.start();
        server.shutdown();
    }

    Transmittable waitForMessage(Queue<Transmittable> queue) {
        await().until(() -> queue.size() > 0);
        return queue.remove();
    }

    void setCheckNickname(int index, String nickname){
        connHandlers[index].update(new ClientSetNicknameMessage(nickname));
        Transmittable message = waitForMessage(queues[index]);
        assertEquals(StatusMessages.OK, message); //Should receive OK for nickname
    }

    void setCheckJoinLobby(int index, boolean isWaitingForCount, int count){
        Thread t = new Thread( () ->
                connHandlers[index].update(new ClientJoinLobbyMessage())
        );
        t.start();

        if(isWaitingForCount){
            Transmittable message = waitForMessage(queues[index]);
            assertEquals(StatusMessages.CONTINUE, message); //Should receive request for playerCount
            connHandlers[index].update(new ClientSetPlayersCountMessage(count));
        }

        await().until(() -> !t.isAlive()); //Thread should terminate here
    }

    @Test
    void twoPlayersJoining1() {
        Transmittable message = null;
        mockConnections();

        assertEquals(0, server.getOngoingMatches().size()); //There should be no matches

        setCheckNickname(0, "Boris");
        setCheckJoinLobby(0, true, 2);

        assertEquals(lobbyBuilder.getCurrentLobbyPlayerCount(), 2);

        setCheckNickname(1, "Rene Ferretti");
        setCheckJoinLobby(1, false, 0);

        assertEquals(1, server.getOngoingMatches().size()); //There should be one match

        Match match = server.getOngoingMatches().get(0);

        //TODO: check the match
    }

    @Test
    void threePlayersJoiningWithInterleaving() throws InterruptedException {
        //The sequence is as follows:
        //Player1 registers his nickname
        //Player1 joins, doesn't send the playerCount
        //Player3 registers his nickname
        //Player2 registers his nickname. His nickname is rejected
        //Player2 registers another nickname.
        //Player2 joins, should be blocked during Server::getLobby
        //Player3 joins, should be blocked during Server::getLobby
        //Player1 sends playerCount (2)
        //Player1 and Player2 get inserted into the match
        //Player3 should be inserted into the next lobby and is requested a playerCount

        Transmittable message;
        mockConnections();

        assertEquals(0, server.getOngoingMatches().size()); //There should be no matches

        setCheckNickname(0, "Boris");

        Thread t = new Thread(() -> connHandlers[0].update(new ClientJoinLobbyMessage()));
        t.start();

        //Player1 is requested a playerCount
        message = waitForMessage(queues[0]);
        assertEquals(StatusMessages.CONTINUE, message);

        //Player3 registers his nickname
        setCheckNickname(2, "Stanis");

        //Player2 registers his nickname and is rejected
        connHandlers[1].update(new ClientSetNicknameMessage("Stanis"));

        message = waitForMessage(queues[1]);
        assertEquals(StatusMessages.CLIENT_ERROR, message); //Nickname should get rejected

        setCheckNickname(1, "Rene Ferretti");

        Thread t1 = new Thread(() -> connHandlers[1].update(new ClientJoinLobbyMessage()));

        Thread t2 = new Thread(() -> connHandlers[2].update(new ClientJoinLobbyMessage()));

        t1.start();
        t2.start();

        //Player3 tries to set player count, should get rejected
        connHandlers[2].update(new ClientSetPlayersCountMessage(3));
        message = waitForMessage(queues[2]);
        assertEquals(StatusMessages.CLIENT_ERROR, message);

        //Player1 sets player count
        connHandlers[0].update(new ClientSetPlayersCountMessage(2));

        Transmittable message1 = waitForMessage(queues[1]);
        Transmittable message2 = waitForMessage(queues[2]);

        assertTrue(message1.equals(StatusMessages.CONTINUE) ^ message2.equals(StatusMessages.CONTINUE));

        int index;
        String nick;
        if(message1.equals(StatusMessages.CONTINUE)){
            assertTrue(message2 instanceof ServerStartSetupMatchMessage);
            index = 1;
            nick = "Rene Ferretti";
        } else {
            assertTrue(message1 instanceof ServerStartSetupMatchMessage);
            index = 2;
            nick = "Stanis";
        }

        connHandlers[index].update(new ClientSetPlayersCountMessage(3));

        message = waitForMessage(queues[index]);
        assertEquals(StatusMessages.OK, message);
    }

    @Test
    void threePlayersJoining1() {
        mockConnections();

        assertEquals(0, server.getOngoingMatches().size()); //There should be no matches

        setCheckNickname(0, "Boris");
        setCheckJoinLobby(0, true, 3);

        assertEquals(3, lobbyBuilder.getCurrentLobbyPlayerCount());

        setCheckNickname(1, "Rene Ferretti");
        setCheckJoinLobby(1, false, 3);

        //Check if maintains playerCount
        assertEquals(3, lobbyBuilder.getCurrentLobbyPlayerCount());

        setCheckNickname(2, "Stanis");
        setCheckJoinLobby(2, false, 0);

        assertEquals(1, server.getOngoingMatches().size()); //There should be one match

        //Check match
        Match match = server.getOngoingMatches().get(0);
    }
}

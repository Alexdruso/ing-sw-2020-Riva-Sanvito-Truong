package it.polimi.ingsw.server;

import it.polimi.ingsw.utils.StatusMessages;
import it.polimi.ingsw.utils.messages.ClientJoinLobbyMessage;
import it.polimi.ingsw.utils.messages.ClientSetNicknameMessage;
import it.polimi.ingsw.utils.messages.ClientSetPlayersCountMessage;
import it.polimi.ingsw.utils.networking.Connection;
import it.polimi.ingsw.utils.networking.Transmittable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.beans.Transient;
import java.io.IOException;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import static java.lang.Thread.sleep;
import static org.awaitility.Awaitility.await;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServerTest {
    Server server = null;
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
    }

    @AfterEach
    void cleanServer(){
        server.shutdown();
    }

    void mockConnections(){
        for(int j = 0; j < 3; j++){
            Connection mock = mock(Connection.class);
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

    Transmittable waitForMessage(Queue queue){
        await().until(() -> queue.size() > 0);
        return (Transmittable)queue.remove();
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

        Transmittable message = waitForMessage(queues[index]);

        if(isWaitingForCount){
            assertEquals(StatusMessages.CONTINUE, message); //Should receive request for playerCount
            connHandlers[index].update(new ClientSetPlayersCountMessage(count));
            message = waitForMessage(queues[index]);
            assertEquals(StatusMessages.OK, message);
        } else {
            assertEquals(StatusMessages.OK, message);
        }

        await().until(() -> !t.isAlive()); //Thread should terminate here
    }

    @Test
    void twoPlayersJoining1() throws InterruptedException {
        Transmittable message = null;
        mockConnections();

        assertEquals(0, server.getOngoingMatches().size()); //There should be no matches

        setCheckNickname(0, "Boris");
        setCheckJoinLobby(0, true, 2);

        assertEquals(server.getLobby().getLobbyMaxPlayerCount(), 2);
        Map<String, Connection> connectedUsers = server.getLobby().getConnectedUsers();
        assertEquals(connectedUsers.size(), 1);
        assertTrue(connectedUsers.containsKey("Boris"));
        assertSame(connectedUsers.get("Boris"), connections[0]);

        setCheckNickname(1, "Rene Ferretti");
        setCheckJoinLobby(1, false, 0);

        assertEquals(1, server.getOngoingMatches().size()); //There should be one match
        assertEquals(0, server.getLobby().getLobbyMaxPlayerCount()); //The lobby should be reset

        Match match = server.getOngoingMatches().get(0);

        //TODO: check the match
    }

    //@Test
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
        //Player3 sends the playerCount

        Transmittable message = null;
        mockConnections();

        assertEquals(0, server.getOngoingMatches().size()); //There should be no matches

        //PlayerOne joins Lobby
        Thread t1 = new Thread( () -> {
            server.registerNickname("Boris", connections[0]);
            server.getLobby().joinLobby("Boris", connections[0]);
        });
        t1.start();

        message = waitForMessage(queues[0]);
        assertEquals(message, StatusMessages.CONTINUE); //Should receive request for playerCount

        //Player 3 registers his nickname
        Thread t2 = new Thread(()-> {
            server.registerNickname("Stanis", connections[2]);
        });
        t2.start();

        //PlayerTwo registers his nickname and is rejected
        Thread t3 = new Thread( () -> {
            server.registerNickname("Rene Ferretti", connections[1]);
        });
        t3.start();

        await().until(() -> !t2.isAlive());


        await().until(() -> !t2.isAlive());

        Thread t4 = new Thread( () -> {
            server.getLobby().joinLobby("Rene Ferretti", connections[1]);
        });
        t4.start();

        assertTrue(t1.isAlive()); //First thread should be waiting
        assertTrue(t2.isAlive()); //Second thread should be waiting

        server.getLobby().setLobbyMaxPlayerCount(2, "Boris", connections[0]); //Player1 sets the playerCount

        message = waitForMessage(queues[0]);
        assertEquals(message, StatusMessages.OK); // Player1 should join lobby correctly

        message = waitForMessage(queues[1]);
        assertEquals(message, StatusMessages.OK); //Player2 should join lobby correctly

        assertEquals(server.getLobby().getLobbyMaxPlayerCount(), 2);
        Map<String, Connection> connectedUsers = server.getLobby().getConnectedUsers();
        assertEquals(connectedUsers.size(), 1);
        assertTrue(connectedUsers.containsKey("Boris"));
        assertSame(connectedUsers.get("Boris"), connections[0]);



        /*
        Thread finalT1 = t;
        await().until(() -> !finalT1.isAlive());

        assertEquals(1, server.getOngoingMatches().size()); //There should be one match
        assertEquals(0, server.getLobby().getLobbyMaxPlayerCount()); //The lobby should be reset

        //Check match
        Match match = server.getOngoingMatches().get(0);

        //TODO: check the match

         */
    }

    @Test
    void threePlayersJoining1() throws InterruptedException {
        Transmittable message = null;
        mockConnections();

        assertEquals(0, server.getOngoingMatches().size()); //There should be no matches

        //PlayerOne joins Lobby
        Thread t = new Thread( () -> {
            server.registerNickname("Boris", connections[0]);
            server.getLobby().joinLobby("Boris", connections[0]);
        });
        t.start();

        message = waitForMessage(queues[0]);
        assertEquals(message, StatusMessages.CONTINUE); //Should receive request for playerCount

        server.getLobby().setLobbyMaxPlayerCount(3, "Boris", connections[0]);

        message = waitForMessage(queues[0]);
        assertEquals(message, StatusMessages.OK); //Should join lobby correctly

        Thread finalT = t;
        await().until(() -> !finalT.isAlive()); //Thread should terminate here

        //Check if maintains playerCount
        assertEquals(3, server.getLobby().getLobbyMaxPlayerCount());

        //Check if the only player is present and with the right connection
        Map<String, Connection> connectedUsers = server.getLobby().getConnectedUsers();
        assertEquals(1, connectedUsers.size());
        assertTrue(connectedUsers.containsKey("Boris"));
        assertSame(connectedUsers.get("Boris"), connections[0]);

        //Second player joins
        t = new Thread( () -> {
            server.registerNickname("Rene Ferretti", connections[1]);
            server.getLobby().joinLobby("Rene Ferretti", connections[1]);
        });

        t.start();

        message = waitForMessage(queues[1]);
        assertEquals(message, StatusMessages.OK); //Here it should not receive the CONTINUE

        Thread finalT1 = t;
        await().until(() -> !finalT1.isAlive());

        //Check if maintains playerCount
        assertEquals(3, server.getLobby().getLobbyMaxPlayerCount());

        //Check if all users are present
        connectedUsers = server.getLobby().getConnectedUsers();
        assertEquals(2, connectedUsers.size());
        assertTrue(connectedUsers.containsKey("Boris"));
        assertSame(connectedUsers.get("Boris"), connections[0]);
        assertTrue(connectedUsers.containsKey("Rene Ferretti"));
        assertSame(connectedUsers.get("Rene Ferretti"), connections[1]);

        //Third player joins
        t = new Thread(() -> {
            server.registerNickname("Stanis", connections[2]);
            server.getLobby().joinLobby("Stanis", connections[2]);
        });

        t.start();

        message = waitForMessage(queues[2]);
        assertEquals(message, StatusMessages.OK);

        Thread finalT2 = t;
        await().until(() -> !finalT2.isAlive());

        assertEquals(1, server.getOngoingMatches().size()); //There should be one match
        assertEquals(0, server.getLobby().getLobbyMaxPlayerCount()); //The lobby should be reset

        //Check match
        Match match = server.getOngoingMatches().get(0);
    }
}

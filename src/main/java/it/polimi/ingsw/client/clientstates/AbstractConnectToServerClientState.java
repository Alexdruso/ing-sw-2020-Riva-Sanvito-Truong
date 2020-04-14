package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.Ui;
import it.polimi.ingsw.utils.networking.Connection;

import java.io.IOException;

public abstract class AbstractConnectToServerClientState extends AbstractClientState {
    protected String host;
    protected int port;

    public AbstractConnectToServerClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {
        triggerRender();
    }


    @Override
    public void notifyUiInteraction() {
        try {
            Connection connection = new Connection(host, port);
            System.out.println("connected");
            //cli.readString("aaa");
//            AbstractClientState asc = new ClientStateSetNickname();
//            connection.addObserver(asc);
//            connection.send(new ClientSetNicknameMessage("nick123"));
//            cliUtils.readString("aaa");
            connection.close();
        } catch (IOException e) {
//            cliUtils.error("Impossibile stabilire una connessione con il sevrer specificato. Dettagli:");
//            e.printStackTrace();
            client.getUi().notifyError("Impossibile stabilire una connessione con il sevrer specificato.", e);
        }
    }


}


/*
        CliUtils cliUtils = new CliUtils();

        String host = "127.0.0.1"; //cli.readString("Indirizzo del server:");
        int port = 1337; //cli.readInt("Porta del server:", 7268);

        try {
            Connection connection = new Connection(host, port);
            System.out.println("connected");
            //cli.readString("aaa");
            AbstractClientState asc = new ClientStateSetNickname();
            connection.addObserver(asc);
            connection.send(new ClientSetNicknameMessage("nick123"));
            cliUtils.readString("aaa");
            connection.close();
        } catch (IOException e) {
            cliUtils.error("Impossibile stabilire una connessione con il sevrer specificato. Dettagli:");
            e.printStackTrace();
        }
 */
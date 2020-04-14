package it.polimi.ingsw.client.clientstates;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.Ui;

public abstract class ConnectToServerClientState extends AbstractClientState {
    public ConnectToServerClientState(Client client) {
        super(client);
    }

    @Override
    public void setup() {

    }

    @Override
    public void render(Ui ui) {

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
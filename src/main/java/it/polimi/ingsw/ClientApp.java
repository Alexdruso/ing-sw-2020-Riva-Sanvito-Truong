package it.polimi.ingsw;


/*
Messaggi dal Game (tutti) da broadcastare a tutti con chi è il vero target (gli altri aggiornano)
Interfaccia comune per tutti i tipi di dati scambiati con la Connection
Parametri nei messaggi di stato (?) per ack+seleziona_players_count -> NO, vedi nuovo UML (splittato ack + richiesta)
retry? -> message ID nella queue


send:
- GET
-- lobby
- POST
-- nickname
-- players_count
-- moveAction
-- buildAction
- PUT
-- gods
-- god
- NOTIFY
-- match_started
-- available_gods
-- player_god
-- set_worker
-- set_tower
-- win [async]
-- lose [async]
-- start_turn

receive:
- ok
- continue
-- lobby
- nak
 */


import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.ui.cli.Cli;

public class ClientApp {
    public static void main(String[] args) {
        Client client = new Client(new Cli());
        client.run();
//            System.out.println(ansi().cursor(0, 0).eraseScreen());
//            for (int i = 0; i < 5; i++) {
//                System.out.println(ansi()
//                        .bgGreen().a("     ")
//                        .reset().a(" ")
//                        .bg(CYAN).a("     ")
//                        .reset().a(" ")
//                        .bg(BLUE).a("     ")
//                        .reset().a(" ")
//                        .bg(MAGENTA).a("     ")
//                        .reset().a(" ")
//                        .bg(RED).a("   / ")
//                        .reset().a(" ")
//                        .reset());
//                System.out.println(ansi()
//                        .bgGreen().a("  A  ")
//                        .reset().a(" ")
//                        .bg(CYAN).a("  a  ")
//                        .reset().a(" ")
//                        .bg(BLUE).a("  B  ")
//                        .reset().a(" ")
//                        .bg(MAGENTA).a("  b  ")
//                        .reset().a(" ")
//                        .bg(RED).a("  /  ")
//                        .reset());
//                System.out.println(ansi()
//                        .bgGreen().a("     ")
//                        .reset().a(" ")
//                        .bg(CYAN).a("     ")
//                        .reset().a(" ")
//                        .bg(BLUE).a("     ")
//                        .reset().a(" ")
//                        .bg(MAGENTA).a("     ")
//                        .reset().a(" ")
//                        .bg(RED).a(" /   ")
//                        .reset());
//                System.out.println("");
//            }
//
//            String test = scanner.nextLine();
//            System.out.println(ansi().cursor(0, 0).a(test));
//        CliUtils cliUtils = new CliUtils();
//
//        String host = "127.0.0.1"; //cli.readString("Indirizzo del server:");
//        int port = 1337; //cli.readInt("Porta del server:", 7268);
//
//        try {
//            Connection connection = new Connection(host, port);
//            System.out.println("connected");
//            //cli.readString("aaa");
//            AbstractClientState asc = new AbstractSetNicknameClientState();
//            connection.addObserver(asc);
//            connection.send(new ClientSetNicknameMessage("nick123"));
//            cliUtils.readString("aaa");
//            connection.close();
//        } catch (IOException e) {
//            cliUtils.error("Impossibile stabilire una connessione con il sevrer specificato. Dettagli:");
//            e.printStackTrace();
//        }

        //AnsiConsole.systemUninstall();
    }
}

package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.UI;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Represents the CLI.
 */
public class CLI extends UI {
    private Scanner in;
    private PrintWriter out;

    @Override
    public void init() {
        AnsiConsole.systemInstall();

        in = new Scanner(System.in);
        out = new PrintWriter(System.out, true);

        clear();
    }

    @Override
    public AbstractClientState getClientState(ClientState clientState, Client client) {
        switch (clientState) {
            case CONNECT_TO_SERVER:
                return new ConnectToServerCLIClientState(client);
            case DISCONNECT:
                return new DisconnectCLIClientState(client);
            case JOIN_LOBBY:
                return new JoinLobbyCLIClientState(client);
            case SET_NICKNAME:
                return new SetNicknameCLIClientState(client);
            case SET_PLAYERS_COUNT:
                return new SetPlayersCountCLIClientState(client);
            case SHOW_GAME_PASSIVE:
                return new ShowGamePassiveCLIClientState(client);
            case WAIT_PLAYERS:
                return new WaitPlayersCLIClientState(client);
            default:
                throw new IllegalStateException();

        }
    }

    @Override
    public void notifyError(String message) {
        error(message);
    }

    /**
     * Clears the screen.
     */
    void clear() {
        out.println(ansi().cursor(0, 0).eraseScreen());
    }

    /**
     * Formats a string and converts it to an Ansi object.
     *
     * @param format the format string
     * @param args   the values to be substituted in the format string
     * @return the formatted string as an Ansi object
     */
    private Ansi af(String format, Object... args) {
        return ansi().a(String.format(format, args));
    }

    /**
     * Prints a string. The buffer is flushed at each call, so that the provided string gets displayed for sure.
     *
     * @param s the string to print
     */
    private void print(Ansi s) {
        out.print(s);
        out.flush();
    }

    /**
     * Prints a string, adding a newline at the end.
     *
     * @param s the String to print
     */
    void println(String s) {
        println(ansi().a(s));
    }

    /**
     * Prints an int, adding a newline at the end.
     *
     * @param i the int to print
     */
    void println(int i) {
        println(ansi().a(i));
    }

    private void println(Ansi s) {
        out.println(s);
    }

    /**
     * Displays an error message.
     *
     * @param s the error message
     */
    void error(String s) {
        println(ansi().render("@|bold,red Errore:|@ %s", s));
    }

    /**
     * Reads a string from the CLI.
     *
     * @param prompt the prompt to show when asking for input
     * @return the string read from the CLI
     */
    String readString(String prompt) {
        return readString(prompt, "", 15);
    }

    /**
     * Reads a string from the CLI.
     *
     * @param prompt the prompt to show when asking for input
     * @param def    the default value, suggested to the user
     * @return the string read from the CLI
     */
    String readString(String prompt, String def) {
        return readString(prompt, def, def.length() + 2);
    }

    /**
     * Reads a string from the CLI.
     *
     * @param prompt                the prompt to show when asking for input
     * @param def                   the default value, suggested to the user
     * @param expected_input_length the expected input length
     * @return the string read from the CLI
     */
    String readString(String prompt, String def, int expected_input_length) {
        printReadPrompt(prompt, def, expected_input_length);
        return in.nextLine();
    }

    /**
     * Reads an int from the CLI.
     *
     * @param prompt the prompt to show when asking for input
     * @return the int read from the CLI
     */
    int readInt(String prompt) {
        return readInt(prompt, null, 6);
    }

    /**
     * Reads an int from the CLI.
     *
     * @param prompt the prompt to show when asking for input
     * @param def    the default value, suggested to the user
     * @return the int read from the CLI
     */
    int readInt(String prompt, Integer def) {
        return readInt(prompt, def, (int) (Math.ceil(Math.log10(def)) + 2));
    }

    /**
     * Reads an int from the CLI.
     *
     * @param prompt                the prompt to show when asking for input
     * @param def                   the default value, suggested to the user
     * @param expected_input_length the expected input length
     * @return the int read from the CLI
     */
    int readInt(String prompt, Integer def, int expected_input_length) {
        printReadPrompt(prompt, def != null ? Integer.toString(def) : "", expected_input_length);
        String line = in.nextLine();
        try {
            return Integer.parseInt(line);
        }
        catch (NumberFormatException e) {
            error(String.format("%s non e' un intero", line));
            return readInt(prompt, def, expected_input_length);
        }
    }

    /**
     * Displays the input prompt when asking an input from the user.
     *
     * @param prompt                the prompt to show when asking for input
     * @param def                   the default value, suggested to the user
     * @param expected_input_length the expected input length
     */
    private void printReadPrompt(String prompt, String def, int expected_input_length) {
        String underscores = "_".repeat(expected_input_length);
        String defText = def.length() > 0 ? String.format(" (default: %s)", def) : "";
        print(af("%s%s %s", prompt, defText, underscores).cursorLeft(expected_input_length));
    }
}

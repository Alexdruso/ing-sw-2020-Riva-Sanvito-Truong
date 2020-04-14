package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.ui.Ui;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.PrintWriter;
import java.util.InputMismatchException;
import java.util.Scanner;

import static org.fusesource.jansi.Ansi.ansi;

public class Cli extends Ui {
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
                return new ConnectToServerCliClientState(client);
            default:
                throw new IllegalStateException();
//            case SET_NICKNAME:
//                return new SetN
        }
    }

    @Override
    public void notifyError(String message) {
        error(message);
    }

    public void clear() {
        out.println(ansi().cursor(0, 0).eraseScreen());
    }

    private Ansi af(String format, Object... args) {
        return ansi().a(String.format(format, args));
    }

    private void print(Ansi s) {
        out.print(s);
        out.flush();
    }

    public void println(String s) {
        println(ansi().a(s));
    }

    public void println(int i) {
        println(ansi().a(i));
    }

    private void println(Ansi s) {
        out.println(s);
    }

    public void error(String s) {
        println(ansi().render("@|bold,red Errore:|@ %s", s));
    }

    public String readString(String prompt) {
        return readString(prompt, "", 15);
    }

    public String readString(String prompt, String def) {
        return readString(prompt, def, def.length() + 2);
    }

    public String readString(String prompt, String def, int expected_input_length) {
        printReadPrompt(prompt, def, expected_input_length);
        return in.nextLine();
    }

    public int readInt(String prompt) {
        return readInt(prompt, null, 6);
    }

    public int readInt(String prompt, Integer def) {
        return readInt(prompt, def, (int) (Math.ceil(Math.log10(def)) + 2));
    }

    public int readInt(String prompt, Integer def, int expected_input_length) {
        printReadPrompt(prompt, def != null ? Integer.toString(def) : "", expected_input_length);
        try {
            return in.nextInt();
        }
        catch (InputMismatchException e) {
            error(String.format("%s non e' un intero", in.nextLine()));
            return readInt(prompt, def, expected_input_length);
        }
    }

    private void printReadPrompt(String prompt, String def, int expected_input_length) {
        String underscores = "_".repeat(expected_input_length);
        String defText = def.length() > 0 ? String.format(" (default: %s)", def) : "";
        print(af("%s%s %s", prompt, defText, underscores).cursorLeft(expected_input_length));
    }
}

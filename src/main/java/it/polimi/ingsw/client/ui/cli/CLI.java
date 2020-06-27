package it.polimi.ingsw.client.ui.cli;

import it.polimi.ingsw.client.Client;
import it.polimi.ingsw.client.clientstates.AbstractClientState;
import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.client.clientstates.ClientState;
import it.polimi.ingsw.client.clientstates.ClientTurnState;
import it.polimi.ingsw.client.reducedmodel.*;
import it.polimi.ingsw.client.ui.UI;
import it.polimi.ingsw.utils.i18n.I18n;
import it.polimi.ingsw.utils.i18n.I18nKey;
import it.polimi.ingsw.utils.networking.transmittables.ReducedGod;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static org.fusesource.jansi.Ansi.ansi;

/**
 * Represents the CLI.
 */
public class CLI implements UI {
    private static final Logger LOGGER = Logger.getLogger(CLI.class.getName());
    public static final String CLI_INPUT_FILE_ENV_VAR_NAME = "CLI_INPUT_FILE";
    public static final String CLI_LOG_INPUTS_FOLDER_ENV_VAR_NAME = "CLI_LOG_INPUTS_FOLDER";
    private static final String PLATFORM_DEPENDENT_NEWLINE = String.format("%n");

    private Scanner in;
    private PrintWriter out;
    private final List<PrintWriter> inLoggers = new ArrayList<>();
    private boolean usingInputFile = false;
    private static final Ansi.Color[] levelsBgColors = new Ansi.Color[]{
            Ansi.Color.GREEN,   // level 0
            Ansi.Color.YELLOW,  // level 1
            Ansi.Color.YELLOW,  // level 2
            Ansi.Color.YELLOW,  // level 3
            Ansi.Color.RED      // dome
    };
    private static final String BOARD_COLUMNS_FORMAT_STRING = "    %c    ";
    private static final String BOARD_ROWS_FORMAT_STRING = "     %n     %n  %d  %n     %n     ";
    private static final String EMPTY_CELL_STRING = "   ";
    private static final String DOME_CELL_STRING = "\u2591\u2591\u2591";
    private static final String[] levelFormatString = new String[]{
            "         %n" +
                    "         %n" +
                    "   %s   %n" +
                    "         %n" +
                    "         ",
            "         %n" +
                    "         %n" +
                    "   %s   %n" +
                    "         %n" +
                    "         ",
            "\u250c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510%n" +
                    "\u2502       \u2502%n" +
                    "\u2502  %s  \u2502%n" +
                    "\u2502       \u2502%n" +
                    "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518",
            "\u250c\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2510%n" +
                    "\u2502 \u250c\u2500\u2500\u2500\u2510 \u2502%n" +
                    "\u2502 \u2502%s\u2502 \u2502%n" +
                    "\u2502 \u2514\u2500\u2500\u2500\u2518 \u2502%n" +
                    "\u2514\u2500\u2500\u2500\u2500\u2500\u2500\u2500\u2518",
    };
    private static final String[][] workersStrings = new String[][]{
            {" A ", " a "},
            {" B ", " b "},
            {" C ", " c "},
    };

    @Override
    public void init(Runnable onExit) {
        Runtime.getRuntime().addShutdownHook(new Thread(onExit));
        AnsiConsole.systemInstall();

        try {
            String inFilename = System.getenv(CLI_INPUT_FILE_ENV_VAR_NAME);
            in = new Scanner(new FileInputStream(inFilename));
            LOGGER.log(Level.FINE, () -> String.format("Using %s for feeding stdin", inFilename));
            usingInputFile = true;
        }
        catch (NullPointerException | FileNotFoundException e) {
            in = new Scanner(System.in);
            LOGGER.log(Level.FINER, "Using real stdin", e);
        }

        try {
            String inLoggerFolder = System.getenv(CLI_LOG_INPUTS_FOLDER_ENV_VAR_NAME);
            if (inLoggerFolder != null) {
                inLoggers.add(new PrintWriter(new FileOutputStream(String.format("%s%d.txt", inLoggerFolder, System.currentTimeMillis()))));
                inLoggers.add(new PrintWriter(new FileOutputStream(String.format("%slatest.txt", inLoggerFolder))));
                LOGGER.log(Level.FINE, () -> String.format("Logging inputs in folder %s", inLoggerFolder));
            }
            else {
                LOGGER.log(Level.FINER, "Skipping log inputs to file");
            }
        }
        catch (FileNotFoundException e) {
            LOGGER.log(Level.FINER, "Unable to log inputs to file", e);
        }

        out = new PrintWriter(System.out, true);

        clear();
    }

    @Override
    public AbstractClientState getClientState(ClientState clientState, Client client) {
        return switch (clientState) {
            case ASK_GOD_FROM_LIST -> new AskGodFromListCLIClientState(client);
            case ASK_GODS_FROM_LIST -> new AskGodsFromListCLIClientState(client);
            case ASK_START_PLAYER -> new AskStartPlayerCLIClientState(client);
            case CONNECT_TO_SERVER -> new ConnectToServerCLIClientState(client);
            case DISCONNECT -> new DisconnectCLIClientState(client);
            case JOIN_LOBBY -> new JoinLobbyCLIClientState(client);
            case IN_GAME -> new InGameCLIClientState(client);
            case LOSE_GAME -> new LoseGameCLIClientState(client);
            case SET_NICKNAME -> new SetNicknameCLIClientState(client);
            case SET_PLAYERS_COUNT -> new SetPlayersCountCLIClientState(client);
            case WAIT_PLAYERS -> new WaitPlayersCLIClientState(client);
            case WIN_GAME -> new WinGameCLIClientState(client);
            case WELCOME_SCREEN -> new WelcomeScreenCLIClientState(client);
        };
    }

    @Override
    public AbstractClientTurnState getClientTurnState(ClientTurnState clientTurnState, Client client) {
        try {
            return switch (clientTurnState) {
                case ASK_WORKER_POSITION -> new AskWorkerPositionCLIClientTurnState(client, (InGameCLIClientState) client.getCurrentState());
                case BUILD -> new BuildCLIClientTurnState(client, (InGameCLIClientState) client.getCurrentState());
                case MOVE -> new MoveCLIClientTurnState(client, (InGameCLIClientState) client.getCurrentState());
            };
        }
        catch (ClassCastException e) {
            // We can't have a ClientTurnState if we aren't within InGame ClientState
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
     * Prints a string at the given row and column.
     *
     * @param s      the String to print
     * @param row    the row on which to start printing the string
     * @param column the column on which to start printing the string
     */
    void println(String s, int row, int column) {
        println(ansi().a(s), row, column);
    }

    /**
     * Prints a string with custom formatting.
     *
     * @param s       the String to print
     * @param options the options to use to format the string
     * @see org.fusesource.jansi.AnsiRenderer.Code
     */
    void println(String s, String options) {
        println(ansi().render(String.format("@|%s %%s|@", options), s));
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

    private void println(Ansi s, int row, int column) {
        println(ansi().cursor(row, column).a(s.toString().replaceAll(PLATFORM_DEPENDENT_NEWLINE, ansi().a(PLATFORM_DEPENDENT_NEWLINE).cursorRight(column - 1).toString())));
    }

    /**
     * Displays an error message.
     *
     * @param s the error message
     */
    void error(String s) {
        println(ansi().render("@|bold,red %s:|@ %s", I18n.string(I18nKey.ERROR), s));
        pause();
        println(ansi().cursorUpLine().eraseLine().cursorUpLine().eraseLine().cursorUpLine().eraseLine().cursorUpLine());
    }

    /**
     * Waits until the user presses 'Return'.
     */
    void pause() {
        readString(I18n.string(I18nKey.PRESS_RETURN_TO_CONTINUE), null, 0);
    }

    void moveUpAndClearLine() {
        println(ansi().cursorUpLine().eraseLine().cursorUpLine());
    }

    /**
     * Reads a string from the CLI.
     *
     * @param prompt the prompt to show when asking for input
     * @return the string read from the CLI
     */
    String readString(String prompt) {
        return readString(prompt, null, 15);
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
     * @param prompt              the prompt to show when asking for input
     * @param def                 the default value, suggested to the user
     * @param expectedInputLength the expected input length
     * @return the string read from the CLI
     */
    String readString(String prompt, String def, int expectedInputLength) {
        printReadPrompt(prompt, def, expectedInputLength);
        String line = getLine();
        if (def != null && line.equals("")) {
            return def;
        }
        return line;
    }

    /**
     * Reads an int from the CLI.
     *
     * @param prompt the prompt to show when asking for input
     * @return the int read from the CLI
     */
    int readInt(String prompt) {
        return readInt(prompt, null, 3);
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
     * @param prompt              the prompt to show when asking for input
     * @param def                 the default value, suggested to the user
     * @param expectedInputLength the expected input length
     * @return the int read from the CLI
     */
    int readInt(String prompt, Integer def, int expectedInputLength) {
        printReadPrompt(prompt, def != null ? Integer.toString(def) : null, expectedInputLength);
        String line = getLine();
        try {
            return Integer.parseInt(line);
        }
        catch (NumberFormatException e) {
            if (def != null && line.equals("")) {
                return def;
            }
            error(String.format("%s %s", line, I18n.string(I18nKey.IS_NOT_AN_INTEGER)));
            return readInt(prompt, def, expectedInputLength);
        }
    }

    boolean readYesNo(String prompt) {
        String yes = I18n.string(I18nKey.YES);
        String no = I18n.string(I18nKey.NO);
        while (true) {
            String choice = readString(String.format("%s [%s/%s]", prompt, yes, no), null, 3);

            if (choice.equalsIgnoreCase(yes) || (choice.length() == 1 && choice.substring(0, 1).equalsIgnoreCase(yes.substring(0, 1)))) {
                return true;
            }
            if (choice.equalsIgnoreCase(no) || (choice.length() == 1 && choice.substring(0, 1).equalsIgnoreCase(no.substring(0, 1)))) {
                return false;
            }

            error(I18n.string(I18nKey.CHOOSE_BETWEEN_YES_OR_NO));
        }
    }

    /**
     * Reads the coordinate of a cell from the CLI and returns the corresponding ReducedCell.
     *
     * @param board  the board to which the cells belong
     * @param prompt the prompt to show when asking for input
     * @return the selected ReducedCell
     */
    ReducedCell readCell(ReducedBoard board, String prompt) {
        return readCell(board, prompt, false);
    }

    ReducedCell readCell(ReducedBoard board, String prompt, boolean allowSkip) {
        final String errorInvalidCoordinates = String.format("%s (%s C2)", I18n.string(I18nKey.INSERT_A_VALID_COORDINATE), I18n.string(I18nKey.E_G));
        ReducedCell res = null;
        while (res == null) {
            String choice = readString(prompt, null, 3);
            if (allowSkip && choice.equalsIgnoreCase("x")) {
                return null;
            }
            if (choice.length() != 2) {
                error(errorInvalidCoordinates);
                continue;
            }
            char choiceCol = choice.toUpperCase().charAt(0);
            char choiceRow = choice.charAt(1);
            int col = choiceCol - 'A';
            int row = choiceRow - '1';
            if (col < 0 || row < 0 || col >= board.getDimension() || row >= board.getDimension()) {
                error(errorInvalidCoordinates);
            }
            else {
                res = board.getCell(col, row);
            }
        }
        return res;
    }

    /**
     * Reopens stdin and initializes in to a scanner bound to it.
     */
    private void reopenStdin() {
        in = new Scanner(System.in);
        usingInputFile = false;
    }

    /**
     * Reads a line from the input. If an input file was specified for input, the file is used
     * as input source until its end is reached, then the input is moved to stdin.
     * If reading from file, the special value %TIMESTAMP% is replaced with the current timestamp.
     *
     * @return the read line
     */
    private String getLine() {
        try {
            return getLineFromOpenStream();
        }
        catch (NoSuchElementException e) {
            reopenStdin();
            return getLine();
        }
    }

    /**
     * Reads a line from the currently open stream.
     * If reading from file, the special value %TIMESTAMP% is replaced with the current timestamp.
     *
     * @return the read line
     * @throws NoSuchElementException if the currently open stream has ended
     */
    private String getLineFromOpenStream() {
        String s;
        try {
            s = in.nextLine();
        }
        catch (IndexOutOfBoundsException ex) {
            // Sometimes, if the input does not contain only ASCII-printable, Java throws an IndexOutOfBoundsException.
            reopenStdin();
            return getLine();
        }
        if (usingInputFile) {
            println("");
            if (s.equalsIgnoreCase("%TIMESTAMP%")) {
                s = String.valueOf(System.currentTimeMillis());
            }
        }
        for (PrintWriter inLogger : inLoggers) {
            inLogger.println(s);
            inLogger.flush();
        }
        return s;
    }

    /**
     * Displays the input prompt when asking an input from the user.
     *
     * @param prompt              the prompt to show when asking for input
     * @param def                 the default value, suggested to the user
     * @param expectedInputLength the expected input length
     */
    private void printReadPrompt(String prompt, String def, int expectedInputLength) {
        String underscores = "_".repeat(expectedInputLength);
        String defText = def != null ? String.format(" (%s: %s)", I18n.string(I18nKey.DEFAULT), def) : "";
        print(af("%s%s %s", prompt, defText, underscores).cursorLeft(expectedInputLength));
    }

    void printPlayersOfGame(ReducedGame game) {
        StringBuilder res = new StringBuilder();
        res.append(ansi().a(String.format("%s:%n%n", I18n.string(I18nKey.PLAYERS))));
        for (ReducedPlayer player : game.getPlayersList()) {
            Ansi resPlayer = ansi();
            if (player.isLocalPlayer()) {
                resPlayer = resPlayer.bold();
            }
            ReducedTurn turn = game.getTurn();
            if (turn != null && turn.getPlayer().equals(player)) {
                resPlayer = resPlayer.fgBrightCyan().a("\u25cf ");
            }
            else {
                resPlayer = resPlayer.a("  ");
            }
            resPlayer = resPlayer.a(String.format(
                    "%s%n    %s: %s%n    %s: %s%n",
                    player.getNickname(),
                    I18n.string(I18nKey.GOD),
                    I18n.string(I18nKey.valueOf(String.format("%s_NAME", player.getGod().getName().toUpperCase()))),
                    I18n.string(I18nKey.WORKERS),
                    Arrays.stream(workersStrings[player.getPlayerIndex()]).map(String::strip).collect(Collectors.joining(", ")))
            ).reset();
            res.append(resPlayer);
        }
        println(ansi().a(res.toString()).reset(), 5, 55);
    }

    /**
     * Draw board.
     *
     * @param board the board
     */
    void drawBoard(ReducedBoard board) {
        int dimension = board.getDimension();
        StringBuilder boardStr = new StringBuilder((dimension+1)*(dimension+1)*9*5);
        boardStr.append(PLATFORM_DEPENDENT_NEWLINE);
        boardStr.append("     ");
        for (int i = 0; i < dimension; i++) {
            boardStr.append(String.format(BOARD_COLUMNS_FORMAT_STRING, (char) (i + 65)));
        }
        boardStr.append(PLATFORM_DEPENDENT_NEWLINE);
        for (int y = 0; y < dimension; y++) {
            Ansi[][] rowAnsi = new Ansi[dimension+1][];
            String[] rowHeaders = String.format(BOARD_ROWS_FORMAT_STRING, y + 1).split(PLATFORM_DEPENDENT_NEWLINE);
            rowAnsi[0] = new Ansi[rowHeaders.length];
            for (int i = 0; i < rowHeaders.length; i++) {
                rowAnsi[0][i] = ansi().a(rowHeaders[i]);
            }
            for (int x = 0; x < dimension; x++) {
                rowAnsi[x+1] = getCellAnsi(board.getCell(x, y));
            }
            for (int i = 0; i < rowAnsi[0].length; i++) {
                for (int j = 0; j < dimension + 1; j++) {
                    boardStr.append(rowAnsi[j][i]);
                }
                boardStr.append(PLATFORM_DEPENDENT_NEWLINE);
            }

        }

        print(ansi().cursor(0, 0).a(boardStr.toString()));
    }

    void drawLegend() {
        StringBuilder res = new StringBuilder();
        res.append(ansi().a(String.format("%s:%n%n", I18n.string(I18nKey.LEGEND))));
        Ansi[][] cellLevels = new Ansi[5][];
        for (int i = 0; i <= 3; i++) {
            cellLevels[i] = getCellAnsi(false, i, ansi().a("   "), false);
        }
        cellLevels[4] = getCellAnsi(true, 0, ansi().a("   "), false);
        for (int i = 0; i < cellLevels[0].length; i++) {
            for (int j = 0; j <= 4; j++) {
                res.append(cellLevels[j][i]);
                if (j < 4) {
                    res.append(" ");
                }
            }
            res.append(PLATFORM_DEPENDENT_NEWLINE);
        }
        res.append(String.format("%-9s %-9s %-9s %-9s %s%n", I18n.string(I18nKey.GROUND), I18n.string(I18nKey.TOWER), I18n.string(I18nKey.TOWER), I18n.string(I18nKey.TOWER), I18n.string(I18nKey.DOME)));
        res.append("          ");
        for (int i = 1; i <= 3; i++) {
            res.append(String.format("%-10s", String.format("(%s: %d)", I18n.string(I18nKey.HEIGHT_ABBR), i)));
        }

        println(ansi().a(res.toString()).reset(), 18, 55);
    }

    private Ansi[] getCellAnsi(ReducedCell cell) {
        return getCellAnsi(cell.hasDome(), cell.getTowerHeight(), getCellWorkerChar(cell), cell.isHighlighted());
    }

    private Ansi[] getCellAnsi(boolean hasDome, int towerHeight, Ansi cellWorkerChar, boolean isHighlighted) {
        Ansi[] ret;
        Ansi.Color bg;
        Ansi.Color fg = Ansi.Color.DEFAULT;
        String[] retStr;

        if (hasDome) {
            bg = levelsBgColors[4];
            retStr = String.format(levelFormatString[towerHeight], DOME_CELL_STRING).split(PLATFORM_DEPENDENT_NEWLINE);
        }
        else {
            bg = levelsBgColors[towerHeight];
            retStr = String.format(levelFormatString[towerHeight], cellWorkerChar).split(PLATFORM_DEPENDENT_NEWLINE);
        }

        ret = new Ansi[retStr.length];
        for (int i = 0; i < retStr.length; i++) {
            ret[i] = ansi().reset().fg(fg);
            if (isHighlighted) {
                ret[i] = ret[i].bgBright(bg);
            }
            else {
                ret[i] = ret[i].bg(bg);
            }
            ret[i] = ret[i].a(retStr[i]).reset();
        }
        return ret;
    }

    private Ansi getCellWorkerChar(ReducedCell cell) {
        Ansi ret = ansi();
        Optional<ReducedWorker> maybeWorker = cell.getWorker();
        if (maybeWorker.isPresent()) {
            ReducedWorker worker = maybeWorker.get();
            ret = ret.a(workersStrings[worker.getPlayer().getPlayerIndex()][worker.getWorkerID().getWorkerIDIndex()]);
            return ret;
        }
        else {
            return ret.a(EMPTY_CELL_STRING);
        }
    }

    void moveCursorToStatusPosition() {
        println("", 28, 0);
    }

    String getGodNameAndSubtitle(ReducedGod god) {
        return String.format(
                "%s: %s",
                I18n.string(I18nKey.valueOf(String.format("%s_NAME", god.getName().toUpperCase()))),
                I18n.string(I18nKey.valueOf(String.format("%s_SUBTITLE", god.getName().toUpperCase())))
        );
    }

    boolean printGodCardConfirmationScreen(ReducedGod god) {
        clear();
        println(getGodNameAndSubtitle(god));
        println(I18n.string(I18nKey.valueOf(String.format("%s_DESCRIPTION", god.getName().toUpperCase()))));
        println("");
        return readYesNo(I18n.string(I18nKey.DO_YOU_WANT_TO_CONFIRM_THE_CHOICE_OF_THIS_GOD));
    }
}

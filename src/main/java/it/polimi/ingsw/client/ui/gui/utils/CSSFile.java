package it.polimi.ingsw.client.ui.gui.utils;

/**
 * This enum contains references to all CSS files used in the project
 */
public enum CSSFile {
    /**
     * Represents the CSS for the board and in game graphics
     */
    BOARD(CSSFile.class.getResource("/css/board.css").toExternalForm()),
    /**
     * Represents the common CSS for all the scenes
     */
    COMMON(CSSFile.class.getResource("/css/common.css").toExternalForm()),
    /**
     * Represents the CSS for the credits screen
     */
    CREDITS(CSSFile.class.getResource("/css/credits.css").toExternalForm()),
    /**
     * Represents the CSS for the Gods selection screens
     */
    GOD_SELECTION(CSSFile.class.getResource("/css/god-selection.css").toExternalForm()),
    /**
     * Represents the common CSS for all text
     */
    TEXT(CSSFile.class.getResource("/css/text.css").toExternalForm()),
    /**
     * Represents the CSS for the main screen
     */
    MAIN_MENU(CSSFile.class.getResource("/css/main-menu.css").toExternalForm());

    /**
     * The CCS form that can be loaded as CSS stylesheet
     */
    public final String cssForm;

    /**
     * The enum constructor
     * @param cssForm the CSS form
     */
    CSSFile(String cssForm){
        this.cssForm = cssForm;
    }
}

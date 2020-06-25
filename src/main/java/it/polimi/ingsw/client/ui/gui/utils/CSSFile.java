package it.polimi.ingsw.client.ui.gui.utils;

/**
 * This enum contains references to all CSS files used in the project
 */
public enum CSSFile {
    BOARD(CSSFile.class.getResource("/css/board.css").toExternalForm()),
    COMMON(CSSFile.class.getResource("/css/common.css").toExternalForm()),
    CREDITS(CSSFile.class.getResource("/css/credits.css").toExternalForm()),
    GOD_SELECTION(CSSFile.class.getResource("/css/god-selection.css").toExternalForm()),
    TEXT(CSSFile.class.getResource("/css/text.css").toExternalForm()),
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

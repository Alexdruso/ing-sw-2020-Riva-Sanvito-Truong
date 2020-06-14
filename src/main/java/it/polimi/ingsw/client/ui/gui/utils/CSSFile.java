package it.polimi.ingsw.client.ui.gui.utils;

public enum CSSFile {
    BOARD(CSSFile.class.getResource("/css/board.css").toExternalForm()),
    COMMON(CSSFile.class.getResource("/css/common.css").toExternalForm()),
    CREDITS(CSSFile.class.getResource("/css/credits.css").toExternalForm()),
    GOD_SELECTION(CSSFile.class.getResource("/css/god-selection.css").toExternalForm()),
    TEXT(CSSFile.class.getResource("/css/text.css").toExternalForm()),
    MAIN_MENU(CSSFile.class.getResource("/css/main-menu.css").toExternalForm());

    public final String cssForm;

    CSSFile(String cssForm){
        this.cssForm = cssForm;
    }
}

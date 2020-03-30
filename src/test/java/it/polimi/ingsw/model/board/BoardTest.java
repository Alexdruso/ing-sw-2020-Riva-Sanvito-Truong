package it.polimi.ingsw.model.board;

import it.polimi.ingsw.parsing.ConfigParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    ConfigParser cfg = ConfigParser.getInstance();
    Board board = new Board();

    @Test
    public void TestBoardMeasurements(){
        assertEquals(board.getDimension(), Integer.parseInt(cfg.getProperty("boardSize")));
    }
}

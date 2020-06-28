package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.server.model.board.Board;
import it.polimi.ingsw.server.model.board.Cell;
import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReducedBoardTest {
    private final ReducedBoard board = new ReducedBoard();

    @Test
    void testBoardMeasurements(){
        assertEquals(5, board.getDimension());
    }

    @Test
    void cellListIsValid(){
        Board board = new Board();
        List<Cell> cellList = board.getCellsList();
        Set<Cell> cellSet = new HashSet<Cell>(cellList);
        assertEquals(cellList.size(), Math.pow(5, 2));
        assertEquals(cellList.size(), cellSet.size(), "There should be no duplicate Cells");
    }

    @Test
    void testGetTargets() {
        boolean[][] mockTargetCells = new boolean[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                mockTargetCells[i][j] = i == 2 && j == 3;
            }
        }
        ReducedTargetCells targetCells = new ReducedTargetCells(mockTargetCells);
        List<ReducedCell> result = board.getTargets(targetCells);

        assertEquals(1, result.size());
        assertEquals(board.getCell(2, 3), result.get(0));
    }

}
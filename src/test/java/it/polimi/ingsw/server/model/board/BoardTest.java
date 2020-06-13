package it.polimi.ingsw.server.model.board;

import it.polimi.ingsw.utils.config.ConfigParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    Board board = new Board();

    @Test
    public void TestBoardMeasurements(){
        assertEquals(5, board.getDimension());
    }

    @Test
    public void cellListIsValid(){
        Board board = new Board();
        List<Cell> cellList = board.getCellsList();
        Set<Cell> cellSet = new HashSet<Cell>(cellList);
        assertEquals(cellList.size(), Math.pow(5, 2));
        assertEquals(cellList.size(), cellSet.size(), "There should be no duplicate Cells");
    }

    @ParameterizedTest
    @MethodSource("getPointAndDirection")
    public void testFromBaseCellAndDirection(Cell baseCell, Direction direction, Optional<Cell> expectedCell){
        Board board = new Board();
        Optional<Cell> resultCell = board.fromBaseCellAndDirection(baseCell, direction);
        if(expectedCell.isPresent()){
            assertTrue(resultCell.isPresent());
            assertEquals(resultCell.get().getX(), expectedCell.get().getX());
            assertEquals(resultCell.get().getY(), expectedCell.get().getY());
        } else {
            assertFalse(resultCell.isPresent());
        }
    }

    public static Stream<Arguments> getPointAndDirection(){
        int[][][] coordArray = new int[][][] {
                {{3, 4}, {2, 1}, {1, 1}},
                {{4, 1}, {2, 0}, {4, 2}},
                {{2, 3}, {3, 2}, {4, 2}},
                {{0, 0}, {4, 2}, {3, 3}},
                {{3, 4}, {3, 1}, {1, 0}},
                {{3, 2}, {1, 1}, {4, 1}},
                {{2, 2}, {1, 1}, {1, 0}},
                {{3, 0}, {3, 4}, {1, 0}},
                {{1, 4}, {7, 11}, {8, 14}},
                {{12, 2}, {14, 0}, {14, 5}},
                {{5, 11}, {10, 1}, {5, 8}},
                {{6, 7}, {4, 11}, {1, 8}},
                {{11, 7}, {3, 5}, {2, 12}},
                {{4, 13}, {8, 3}, {13, 2}},
                {{0, 7}, {1, 15}, {10, 1}},
                {{1027, 382}, {1180, 24}, {782, 745}},
                {{1428, 406}, {1798, 248}, {224, 596}},
                {{888, 1457}, {233, 736}, {797, 313}},
                {{404, 1337}, {1765, 1020}, {918, 596}},
                {{0, 0}, {191, 375}, {0, 0}},
                {{472, 8}, {35, 4}, {0, 0}},
                {{0, 0}, {0, 0}, {797, 313}},
        };

        List<Arguments> args = new ArrayList<Arguments>();
        final int BOARD_SIZE = 5;

        for (int[][] pp: coordArray){
            Cell firstCell = new Cell(pp[1][0], pp[1][1]);
            Cell secondCell = new Cell(pp[2][0], pp[2][1]);
            Cell baseCell = new Cell(pp[0][0], pp[0][1]);
            Optional<Cell> expectedCell;
            Direction direction = new Direction(firstCell, secondCell);
            int expectedX = pp[0][0] + direction.dx;
            int expectedY = pp[0][1] + direction.dy;
            if (expectedX < 0 || expectedX > BOARD_SIZE || expectedY < 0 || expectedY > BOARD_SIZE){
                expectedCell = Optional.empty();
            } else {
                expectedCell = Optional.of(new Cell(expectedX, expectedY));
            }
            args.add(Arguments.of(baseCell, direction, expectedCell));
        }
        return args.stream();
    }

}

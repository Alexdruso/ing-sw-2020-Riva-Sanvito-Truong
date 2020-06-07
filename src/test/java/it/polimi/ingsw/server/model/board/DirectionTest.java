package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DirectionTest {

    @ParameterizedTest
    @MethodSource("getPointPairs")
    public void testDirection(Cell firstCell, Cell secondCell, int dxExpect, int dyExpect){
        Direction direction = new Direction(firstCell, secondCell);
        assertEquals(direction.dx, dxExpect);
        assertEquals(direction.dy, dyExpect);
    }

    public static Stream<Arguments> getPointPairs(){ //Since board size is configurable, this should work with any possible board size
        int[][][] coordArray = new int[][][] {
                {{2, 1}, {5, 1}},
                {{2, 0}, {4, 2}},
                {{5, 2}, {4, 2}},
                {{4, 2}, {3, 3}},
                {{3, 1}, {1, 0}},
                {{1, 1}, {4, 1}},
                {{1, 1}, {1, 0}},
                {{3, 4}, {1, 0}},
                {{7, 11}, {8, 14}},
                {{14, 0}, {14, 5}},
                {{10, 1}, {5, 8}},
                {{4, 11}, {1, 8}},
                {{3, 5}, {2, 12}},
                {{8, 3}, {13, 2}},
                {{1, 15}, {10, 1}},
                {{1180, 24}, {782, 745}},
                {{1798, 248}, {224, 596}},
                {{233, 736}, {797, 313}},
                {{1765, 1020}, {918, 596}},
                {{191, 375}, {0, 0}},
                {{35, 4}, {0, 0}},
                {{0, 0}, {797, 313}},
        };

        List<Arguments> args = new ArrayList<Arguments>();

        for (int[][] pp: coordArray){
            Cell firstCell = new Cell(pp[0][0], pp[0][1]);
            Cell secondCell = new Cell(pp[1][0], pp[1][1]);
            int dxExpect = pp[1][0] - pp[0][0];
            int dyExpect = pp[1][1] - pp[0][1];
            args.add(Arguments.of(firstCell, secondCell, dxExpect, dyExpect));
        }

        return args.stream();
    }
}

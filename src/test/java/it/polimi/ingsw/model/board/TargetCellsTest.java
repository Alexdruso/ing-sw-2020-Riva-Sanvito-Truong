package it.polimi.ingsw.model.board;


import it.polimi.ingsw.parsing.ConfigParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TargetCellsTest {

    private static final int BOARD_SIZE = Integer.parseInt(ConfigParser.getInstance().getProperty("boardSize"));

    public void targetInitShouldBeEmpty(){
        TargetCells target = new TargetCells();
        assertTrue(target.isEmpty());
        for(int i = 0; i < BOARD_SIZE; i++){
           for(int j = 0; j < BOARD_SIZE; j++){
               assertFalse(target.getPosition(i, j));
           }
        }
    }

    @ParameterizedTest
    @MethodSource("getCoordPairs") //Somehow fully qualified name does not work here, will investigate later
    public void setUnsetSelectPosition(int x, int y) {
        Board board = new Board();
        TargetCells target = new TargetCells();
        target.setPosition(x, y, true);
        List<Cell> targeted = board.getTargets(target);
        assertEquals(targeted.size(), 1, "Only a single cell should be targeted");
        assertSame(targeted.get(0), board.getCell(x, y));
        target.setPosition(x, y, false);
        targeted = board.getTargets(target);
        assertEquals(targeted.size(), 0);
    }

    @ParameterizedTest
    @MethodSource("getCoordRange")
    public void setUnsetColumn(int x){
        Board board = new Board();
        TargetCells target = new TargetCells();
        target.setColumn(x, true);
        List<Cell> targeted = board.getTargets(target);
        assertEquals(targeted.size(), BOARD_SIZE);
        for(int i = 0; i < BOARD_SIZE; i++){
            assertTrue(targeted.contains(board.getCell(x, i)));
        }
        target.setColumn(x, false);
        targeted = board.getTargets(target);
        assertEquals(targeted.size(), 0);
    }

    @ParameterizedTest
    @MethodSource("getCoordRange")
    public void setUnsetRow(int y){
        Board board = new Board();
        TargetCells target = new TargetCells();
        target.setRow(y, true);
        List<Cell> targeted = board.getTargets(target);
        assertEquals(targeted.size(), BOARD_SIZE);
        for(int i = 0; i < BOARD_SIZE; i++){
            assertTrue(targeted.contains(board.getCell(i, y)));
        }
        target.setRow(y, false);
        targeted = board.getTargets(target);
        assertEquals(targeted.size(), 0);
    }

    @Test
    public void fromMalformedMatrix(){
        boolean[][] matrix1 = new boolean[][]
                {{true, false, false, true, false},
                        {true, false, true, true, true},
                        {true, true, true, true, true},
                        {false, true, false, true, false}};
        boolean[][] matrix2 = new boolean[][]
                {{false, false, true, true},
                        {false, false, true, false},
                        {true, false, false, true},
                        {false, true, true, true},
                        {false, false, true, true}};
        boolean[][] matrix3 = new boolean[][]
                {{true, true, false, true, true},
                        {false, true, false, true, false},
                        {false, true, false, true, true}};
        boolean[][] matrix4 = new boolean[][]
                {{true, false, true, false, true}};

        List<boolean[][]> matrices = Arrays.asList(matrix1, matrix2, matrix3, matrix4);
        for (boolean[][] mtx: matrices){
            assertThrows(IllegalArgumentException.class, () -> TargetCells.fromMatrix(mtx));
        }
    }


    /*
    @ParameterizedTest
    @MethodSource("getValidMatrices")
    public void fromValidMatrices(boolean[][] mtx){
       TargetCells target = TargetCells.fromMatrix(mtx);
       for(int i = 0; i < BOARD_SIZE; i++){
           for(int j = 0; j < BOARD_SIZE; j++){
               assertEquals(target.getPosition(j, i), mtx[i][j]);
           }
       }
    }
     */

    public static ArrayList<Arguments> getValidMatrices(){
        ArrayList<Arguments> matrices = new ArrayList<Arguments>();
        boolean[][] matrix1 = new boolean[][]
                {{true, false, false, true, false},
                        {true, false, true, true, true},
                        {false, false, false, false, false},
                        {true, true, true, true, true},
                        {false, true, false, true, false}};
        boolean[][] matrix2 = new boolean[][]
                {{false, false, true, true,true},
                        {false, false, true, false, true},
                        {true, false, false, true, false},
                        {false, true, true, true, true},
                        {false, false, true, true, false}};
        boolean[][] matrix3 = new boolean[][]
                {{true, true, false, true, true},
                        {false, false, true, true, false},
                        {true, false, true, false, true},
                        {false, true, false, true, false},
                        {false, true, false, true, true}};
        boolean[][] matrix4 = new boolean[][]
                {{true, false, true, false, true},
                        {false, true, false, true, false},
                        {true, false, true, false, true},
                        {false, true, false, true, false},
                        {true, false, true, false, true}};

        matrices.add(Arguments.of(matrix1));
        matrices.add(Arguments.of(matrix2));
        matrices.add(Arguments.of(matrix3));
        matrices.add(Arguments.of(matrix4));

        //return matrices.stream();
        return matrices;

        /*
        return Arrays.asList(
                Arguments.of(matrix1),
                Arguments.of(matrix2),
                Arguments.of(matrix3),
                Arguments.of(matrix4)
        ).stream();
         */
    }

    public static Stream<Arguments> getInvalidMatrices(){
        ArrayList<Arguments> matrices = new ArrayList<Arguments>();
        boolean[][] matrix1 = new boolean[][]
                {{true, false, false, true, false},
                        {true, false, true, true, true},
                        {true, true, true, true, true},
                        {false, true, false, true, false}};
        boolean[][] matrix2 = new boolean[][]
                {{false, false, true, true},
                        {false, false, true, false},
                        {true, false, false, true},
                        {false, true, true, true},
                        {false, false, true, true}};
        boolean[][] matrix3 = new boolean[][]
                {{true, true, false, true, true},
                        {false, true, false, true, false},
                        {false, true, false, true, true}};
        boolean[][] matrix4 = new boolean[][]
                {{true, false, true, false, true}};

        return Arrays.asList(
                Arguments.of(matrix1),
                Arguments.of(matrix2),
                Arguments.of(matrix3),
                Arguments.of(matrix4)
        ).stream();
    }

    public static Stream<Arguments> getCoordRange(){
        ArrayList<Arguments> coordRange = new ArrayList<Arguments>();
        for(int i = 0; i < BOARD_SIZE; i++){
            coordRange.add(Arguments.of(i));
        }
        return coordRange.stream();
    }


    public static Stream<Arguments> getCoordPairs(){
        ArrayList<Arguments> coords = new ArrayList<Arguments>();
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                coords.add(Arguments.of(i, j));
            }
        }
        return coords.stream();
    }
}

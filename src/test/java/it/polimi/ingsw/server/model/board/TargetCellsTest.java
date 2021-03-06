package it.polimi.ingsw.server.model.board;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class TargetCellsTest {

    private static final int BOARD_SIZE = 5;

    @Test
    public void targetInitShouldBeEmpty(){
        TargetCells target = new TargetCells();
        assertTrue(target.isEmpty());
        for(int i = 0; i < BOARD_SIZE; i++){
           for(int j = 0; j < BOARD_SIZE; j++){
               assertFalse(target.getPosition(i, j));
           }
        }
    }

    @Test
    public void setUnsetAllBoard(){
        Board board = new Board();
        TargetCells target = new TargetCells();
        target.setAllTargets(true);
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                assertTrue(target.getPosition(i,j));
            }
        }
        target.setAllTargets(false);
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                assertFalse(target.getPosition(i,j));
            }
        }
    }

    @ParameterizedTest
    @MethodSource("getCoordPairs") //Somehow fully qualified name does not work here, will investigate later
    public void setUnsetSelectPosition(int x, int y) {
        Board board = new Board();
        TargetCells target = new TargetCells();
        target.setPosition(x, y, true);
        assertTrue(target.getPosition(x, y));
        List<Cell> targeted = board.getTargets(target);
        assertEquals(1, targeted.size(), "Only a single cell should be targeted");
        assertSame(targeted.get(0), board.getCell(x, y));
        assertTrue(target.getPosition(targeted.get(0)));
        target.setPosition(x, y, false);
        targeted = board.getTargets(target);
        assertEquals(0, targeted.size());
    }

    @ParameterizedTest
    @MethodSource("getCoordRange")
    public void setUnsetColumn(int x){
        Board board = new Board();
        TargetCells target = new TargetCells();
        target.setColumn(x, true);
        List<Cell> targeted = board.getTargets(target);
        assertEquals(BOARD_SIZE, targeted.size());
        for(int i = 0; i < BOARD_SIZE; i++){
            assertTrue(targeted.contains(board.getCell(x, i)));
        }
        target.setColumn(x, false);
        targeted = board.getTargets(target);
        assertEquals(0, targeted.size());
    }

    @ParameterizedTest
    @MethodSource("getCoordRange")
    public void setUnsetRow(int y){
        Board board = new Board();
        TargetCells target = new TargetCells();
        target.setRow(y, true);
        List<Cell> targeted = board.getTargets(target);
        assertEquals(BOARD_SIZE, targeted.size());
        for(int i = 0; i < BOARD_SIZE; i++){
            assertTrue(targeted.contains(board.getCell(i, y)));
        }
        target.setRow(y, false);
        targeted = board.getTargets(target);
        assertEquals(0, targeted.size());
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


    public void fromValidMatrices(boolean[][] mtx){
       TargetCells target = TargetCells.fromMatrix(mtx);
       TargetCells target2 = new TargetCells();
       for(int i = 0; i < BOARD_SIZE; i++){
           for(int j = 0; j < BOARD_SIZE; j++){
               assertEquals(target.getPosition(i, j), mtx[i][j]);
               if (mtx[i][j]){
                   target2.setPosition(i, j, true);
               }
           }
       }
       assertTrue(target.equals(target2));
    }

    @Test
    public void testTargetCellsUnion(){
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
        boolean[][] result = new boolean[][]
                {{true, false, true, true, true},
                        {true, false, true, true, true},
                        {true, false, false, true, false},
                        {true, true, true, true, true},
                        {false, true, true, true, false}};

        TargetCells target1 = TargetCells.fromMatrix(matrix1);
        TargetCells target2 = TargetCells.fromMatrix(matrix2);
        target1.union(target2);
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++) {
                assertEquals(target1.getPosition(i, j), result[i][j]);
            }
        }

    }

    @Test
    public void testTargetCellsIntersection(){
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
        boolean[][] result = new boolean[][]
                {{false, false, false, true, false},
                        {false, false, true, false, true},
                        {false, false, false, false, false},
                        {false, true, true, true, true},
                        {false, false, false, true, false}};

        TargetCells target1 = TargetCells.fromMatrix(matrix1);
        TargetCells target2 = TargetCells.fromMatrix(matrix2);
        target1.intersect(target2);
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++) {
                assertEquals(target1.getPosition(i, j), result[i][j]);
            }
        }
    }

    @Test
    public void testTargetCellsInvert(){
        boolean[][] matrix1 = new boolean[][]
                {{true, false, false, true, false},
                        {true, false, true, true, true},
                        {false, false, false, false, false},
                        {true, true, true, true, true},
                        {false, true, false, true, false}};
        boolean[][] result = new boolean[][]
                {{false, true, true, false, true},
                        {false, true, false, false, false},
                        {true, true, true , true, true},
                        {false, false, false, false, false},
                        {true, false, true, false, true}};

        TargetCells target1 = TargetCells.fromMatrix(matrix1);
        target1.invert();
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++) {
                assertEquals(target1.getPosition(i, j), result[i][j]);
            }
        }
    }

    @Test
    public void testFromCellAndRadiusNegativeException(){
         Cell base = new Cell(2, 2);
         int radius = -1;
         assertThrows(IllegalArgumentException.class, () -> TargetCells.fromCellAndRadius(base, radius));
    }

    @Test
    public void testSetEdges(){
        boolean[][] result = new boolean[][]
                {{true, true, true, true, true},
                        {true, false, false, false, true},
                        {true, false, false, false, true},
                        {true, false, false, false, true},
                        {true, true, true, true, true}};

        TargetCells target = new TargetCells();

        target.setEdges(true);

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                assertEquals(target.getPosition(i, j), result[i][j]);
            }
        }

        target.setEdges(false);

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                assertFalse(target.getPosition(i, j));
            }
        }
    }

    @Test
    public void testFromCellAndRadius2(){
        Cell base = new Cell(0, 0);
        int radius = 2;
        boolean[][] result = new boolean[][]
                {{false, false, true, false, false},
                        {false, false, true, false, false},
                        {true, true, true, false, false},
                        {false, false, false, false, false},
                        {false, false, false, false, false}};

        TargetCells target = TargetCells.fromCellAndRadius(base, radius);

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                assertEquals(target.getPosition(i, j), result[i][j]);
            }
        }

    }

    @Test
    public void testFromCellAndRadius1(){
        Cell base = new Cell(3, 2);
        int radius = 1;
        boolean[][] result = new boolean[][]
                {{false, false, false, false, false},
                        {false, false, false, false, false},
                        {false, true, true, true, false},
                        {false, true, false, true, false},
                        {false, true, true, true, false}};

        TargetCells target = TargetCells.fromCellAndRadius(base, radius);

        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
                assertEquals(target.getPosition(i, j), result[i][j]);
            }
        }
    }

    @Test
    public void testSuiteWithValidMatrices(){
        ArrayList<boolean[][]> matrices = new ArrayList<boolean[][]>();
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
        boolean[][] matrix5 = new boolean[][]
                {{false, false, false, false, false},
                        {false, false, false, false, false},
                        {false, false, false, false, false},
                        {false, false, false, false, false},
                        {false, false, false, false, false}};
        boolean[][] matrix6 = new boolean[][]
                {{true, true, true, true, true},
                        {true, true, true, true, true},
                        {true, true, true, true, true},
                        {true, true, true, true, true},
                        {true, true, true, true, true}};

        matrices.add(matrix1);
        matrices.add(matrix2);
        matrices.add(matrix3);
        matrices.add(matrix4);
        matrices.add(matrix5);
        matrices.add(matrix6);

        for(boolean[][] mtx: matrices){
            fromValidMatrices(mtx);
            assertEquals(TargetCells.fromMatrix(mtx).isEmpty(), isMatrixEmpty(mtx));
        }
    }

    public boolean isMatrixEmpty(boolean[][] mtx){
        for(int i = 0; i < BOARD_SIZE; i++){
            for(int j = 0; j < BOARD_SIZE; j++){
               if(mtx[i][j]){
                   return false;
               }
            }
        }
        return true;
    }

    @Test
    public void testEquals(){
        TargetCells t1 = new TargetCells();
        TargetCells t2 = t1;
        TargetCells t3 = new TargetCells().setAllTargets(true);
        TargetCells t4 = new TargetCells();
        Object o = new Object();
        assertTrue(t1.equals(t1)); //Reflexivity
        assertTrue(t2.equals(t1));
        assertTrue(t1.equals(t2));
        assertFalse(t1.equals(t3));
        assertFalse(t3.equals(t1));
        assertTrue(t1.equals(t4));
        assertTrue(t2.equals(t4));
        assertFalse(t1.equals(o));
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

package it.polimi.ingsw.model.board;

import it.polimi.ingsw.config.ConfigParser;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.model.workers.WorkerID;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    private final Cell cell = new Cell(0, 0);
    private static final int BOARD_SIZE = ConfigParser.getInstance().getIntProperty("boardSize");

    @ParameterizedTest
    @EnumSource(WorkerID.class)
    public void workerSetUnset(WorkerID workerID){
        Player player = new Player("testPlayer");
        Worker worker = new Worker(player, workerID);
        cell.setWorker(worker);
        assertSame(cell.getWorker().get(), worker);
        cell.setNoWorker();
        assertFalse(cell.getWorker().isPresent());
    }

    @ParameterizedTest
    @MethodSource("getCoordPairs")
    public void checkCoords(int x, int y){
        Cell cell = new Cell(x, y);
        assertEquals(cell.getX(), x);
        assertEquals(cell.getY(), y);
    }

    @ParameterizedTest
    @MethodSource("getCellWithTowerByHeight")
    public void cellTowerTest(Cell cell1, Cell cell2, int expectedDiff){
        assertEquals(cell1.getHeightDifference(cell2), expectedDiff);
    }

    public static Stream<Arguments> getCellWithTowerByHeight(){
        int[] cellHeights1 = {0, 1, 2, 3, 4};
        int[] cellHeights2 = {0, 1, 2, 3, 4};

        List<Arguments> args = new ArrayList<Arguments>();

        for(int ch1: cellHeights1){
            for(int ch2: cellHeights2){
                Cell cell1 = new Cell(0, 0);
                Cell cell2 = new Cell(1, 1);

                int expectedHeight = (ch2 == 4 ? ch2-1 : ch2) - (ch1 == 4 ? ch1-1 : ch1);

                for(int i = 0; i < Math.min(ch1, 3); i++){
                    cell1.getTower().placeComponent(Component.BLOCK);
                }
                if(ch1 == 4){
                    cell1.getTower().placeComponent(Component.DOME);
                }

                for(int i = 0; i < Math.min(ch2, 3); i++){
                    cell2.getTower().placeComponent(Component.BLOCK);
                }
                if(ch1 == 4){
                    cell2.getTower().placeComponent(Component.DOME);
                }

                args.add(Arguments.of(cell1, cell2, expectedHeight));
            }
        }
        return args.stream();
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

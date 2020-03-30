package it.polimi.ingsw.model.board;

import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.workers.Worker;
import it.polimi.ingsw.model.workers.WorkerID;
import it.polimi.ingsw.parsing.ConfigParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;

import javax.swing.text.StyledEditorKit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {
    private final Cell cell = new Cell(0, 0);
    private static final int BOARD_SIZE = Integer.parseInt(ConfigParser.getInstance().getProperty("boardSize"));

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
        assertEquals(cell.x, x);
        assertEquals(cell.y, y);
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

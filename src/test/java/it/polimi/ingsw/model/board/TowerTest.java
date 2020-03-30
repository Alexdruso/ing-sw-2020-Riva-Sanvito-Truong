package it.polimi.ingsw.model.board;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

public class TowerTest {

    @Test
    public void towerInit(){
        Tower tower = new Tower();
        assertEquals(tower.getCurrentLevel(), 0);
    }

    @ParameterizedTest
    @CsvSource(value = {"0","1","2","3"})
    public void buildDomeAtHeight(int height){
       Tower tower = new Tower();
       for(int i = 0; i < height; i++) {
           tower.placeComponent(Component.BLOCK);
       }
       tower.placeComponent(Component.DOME);
       assertEquals(tower.getCurrentLevel(), height);
       assertTrue(tower.isComplete());
    }

    @ParameterizedTest
    @CsvSource(value = {"0","1","2","3"})
    public void buildAboveDome(int height){
        Tower tower = new Tower();
        for(int i = 0; i < height; i++) {
            tower.placeComponent(Component.BLOCK);
        }
        tower.placeComponent(Component.DOME);
        assertThrows(RuntimeException.class, () -> tower.placeComponent(Component.BLOCK));
    }
}

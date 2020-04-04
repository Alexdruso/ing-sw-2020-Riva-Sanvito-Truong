package it.polimi.ingsw.model.board;

import it.polimi.ingsw.parsing.ConfigParser;
import org.junit.jupiter.params.provider.Arguments;

import java.util.ArrayList;
import java.util.stream.Stream;

public class BoardTestingUtils {
    private static final int BOARD_SIZE = Integer.parseInt(ConfigParser.getInstance().getProperty("boardSize"));

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
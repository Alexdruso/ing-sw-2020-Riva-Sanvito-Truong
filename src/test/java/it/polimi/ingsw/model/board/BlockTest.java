package it.polimi.ingsw.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BlockTest {
   private final Block block = new Block();
   @Test
   public void blockIsTargetable(){
      assertEquals(block.isTargetable(), true);
   }
}

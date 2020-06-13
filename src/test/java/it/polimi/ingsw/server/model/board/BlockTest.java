package it.polimi.ingsw.server.model.board;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class BlockTest {
   private final Buildable block = Component.BLOCK.getInstance();
   @Test
   public void blockIsTargetable(){
      assertTrue(block.isTargetable());
   }
}

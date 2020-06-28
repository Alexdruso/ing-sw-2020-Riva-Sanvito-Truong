package it.polimi.ingsw.client.reducedmodel;

import it.polimi.ingsw.client.clientstates.AbstractClientTurnState;
import it.polimi.ingsw.utils.networking.transmittables.ReducedTargetCells;
import it.polimi.ingsw.utils.networking.transmittables.ReducedWorkerID;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

class ReducedTurnTest {
    private final ReducedPlayer mockPlayer = mock(ReducedPlayer.class);
    private final AbstractClientTurnState mockTurnState = mock(AbstractClientTurnState.class);

    @Test
    void testClientTurnState() {
        ReducedTurn turn = new ReducedTurn(mockPlayer, mockTurnState);
        assertEquals(mockPlayer, turn.getPlayer());
        assertEquals(mockTurnState, turn.getTurnState());
    }

    @Test
    void testMove() {
        List<ReducedWorkerID> mockWorkerIDs = new ArrayList<>();
        mockWorkerIDs.add(ReducedWorkerID.WORKER1);
        Map<ReducedWorkerID, ReducedTargetCells> mockWalkableCells = new EnumMap<>(ReducedWorkerID.class);
        mockWalkableCells.put(ReducedWorkerID.WORKER1, mock(ReducedTargetCells.class));
        ReducedTurn turn = new ReducedTurn(mockPlayer, mockTurnState, mockWorkerIDs, mockWalkableCells, true);
        assertEquals(mockPlayer, turn.getPlayer());
        assertEquals(mockTurnState, turn.getTurnState());
        assertEquals(mockWorkerIDs, turn.getAllowedWorkers());
        assertEquals(mockWalkableCells.get(ReducedWorkerID.WORKER1), turn.getWorkerWalkableCells(ReducedWorkerID.WORKER1));
        assertTrue(turn.isSkippable());
    }


    @Test
    void testBuild() {
        List<ReducedWorkerID> mockWorkerIDs = new ArrayList<>();
        mockWorkerIDs.add(ReducedWorkerID.WORKER1);
        mockWorkerIDs.add(ReducedWorkerID.WORKER2);
        Map<ReducedWorkerID, ReducedTargetCells> mockBlockBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        Map<ReducedWorkerID, ReducedTargetCells> mockDomeBuildableCells = new EnumMap<>(ReducedWorkerID.class);
        mockBlockBuildableCells.put(ReducedWorkerID.WORKER1, mock(ReducedTargetCells.class));
        mockDomeBuildableCells.put(ReducedWorkerID.WORKER2, mock(ReducedTargetCells.class));
        ReducedTurn turn = new ReducedTurn(mockPlayer, mockTurnState, mockWorkerIDs, mockBlockBuildableCells, mockDomeBuildableCells, true);
        assertEquals(mockPlayer, turn.getPlayer());
        assertEquals(mockTurnState, turn.getTurnState());
        assertEquals(mockWorkerIDs, turn.getAllowedWorkers());
        assertEquals(mockBlockBuildableCells.get(ReducedWorkerID.WORKER1), turn.getWorkerBlockBuildableCells(ReducedWorkerID.WORKER1));
        assertEquals(mockDomeBuildableCells.get(ReducedWorkerID.WORKER2), turn.getWorkerDomeBuildableCells(ReducedWorkerID.WORKER2));
        assertTrue(turn.isSkippable());
    }}
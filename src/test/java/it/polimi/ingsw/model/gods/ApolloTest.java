package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.board.Board;
import it.polimi.ingsw.model.board.Cell;
import it.polimi.ingsw.model.board.TargetCells;
import it.polimi.ingsw.model.board.Tower;
import it.polimi.ingsw.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;

class ApolloTest {
    private final Apollo apollo = new Apollo();
    private final Game mockedGame = mock(Game.class);
    private final Board mockedBoard = mock(Board.class);
    private final Player mockedPlayer = mock(Player.class);
    private final Player mockedOpponent1 = mock(Player.class);
    private final TurnEventsManager turnEventsManager = new TurnEventsManager(mockedPlayer);
    private final Turn mockedTurn = mock(Turn.class);
    private final Worker mockedWorker = mock(Worker.class);
    private final Cell mockedWorkerCell = new Cell(0, 0);
    private Map<Player, List<Worker>> mockedWorkers;
    private Cell[][] mockedBoardCells;
    private Map<Worker, TargetCells> mockedWalkableTargets;

    @BeforeEach
    void beforeEachTest() {
        mockedWalkableTargets = new HashMap<>();
        mockedWorkers = new HashMap<>();

        when(mockedGame.getBoard()).thenReturn(mockedBoard);
        when(mockedBoard.getDimension()).thenCallRealMethod();
        when(mockedBoard.getTargets(any(TargetCells.class))).thenAnswer(mockedCall -> {
            TargetCells target = mockedCall.getArgument(0);
            List<Cell> targetedCells = new ArrayList<Cell>();
            for(int i = 0; i < mockedBoard.getDimension(); i++) {
                for (int j = 0; j < mockedBoard.getDimension(); j++) {
                    if (target.getPosition(j, i)) {
                        targetedCells.add(mockedBoardCells[j][i]);
                    }
                }
            }
            return targetedCells;
        });
        when(mockedPlayer.getGod()).thenReturn(apollo);
        when(mockedPlayer.getOwnWorkers()).thenAnswer(mockedCall -> mockedWorkers.get(mockedPlayer).toArray(new Worker[mockedWorkers.get(mockedPlayer).size()]));
        when(mockedTurn.getPlayer()).thenReturn(mockedPlayer);
        when(mockedTurn.getGame()).thenReturn(mockedGame);
        when(mockedTurn.getWorkerWalkableCells(any(Worker.class))).thenAnswer(mockedCall -> {
           Worker worker = mockedCall.getArgument(0);
           return mockedWalkableTargets.get(worker);
        });
        when(mockedWorker.getCell()).thenReturn(mockedWorkerCell);
        when(mockedWorker.getPlayer()).thenReturn(mockedPlayer);

        mockedBoardCells = new Cell[mockedBoard.getDimension()][mockedBoard.getDimension()];
        for(int x = 0; x < mockedBoard.getDimension(); x++){
            for(int y = 0; y < mockedBoard.getDimension(); y++ ){
                mockedBoardCells[x][y] = makeMockCell();
            }
        }
    }

    Tower makeMockTower(boolean isComplete, int height) {
        Tower tower = mock(Tower.class);
        lenient().when(tower.isComplete()).thenReturn(isComplete);
        lenient().when(tower.getCurrentLevel()).thenReturn(height);
        return tower;
    }

    Worker makeMockWorker(Cell cell, Player player) {
        if (!mockedWorkers.containsKey(player)) {
            mockedWorkers.put(player, new ArrayList<>());
        }
        Worker worker = mock(Worker.class);
        lenient().when(worker.getCell()).thenReturn(cell);
        lenient().when(worker.getPlayer()).thenReturn(player);
        mockedWorkers.get(player).add(worker);
        mockedWalkableTargets.put(worker, mock(TargetCells.class));
        return worker;
    }

    Cell makeMockCell() {
        return makeMockCell(null, false, 0);
    }

    Cell makeMockCell(Player player, boolean towerComplete, int towerHeight) {
        Cell cell = mock(Cell.class);
        Tower mockTower = makeMockTower(towerComplete, towerHeight);
        Worker mockWorker = player != null ? makeMockWorker(cell, player) : null;
        lenient().when(cell.getTower()).thenReturn(mockTower);
        lenient().when(cell.getWorker()).thenReturn(player != null ? Optional.of(mockWorker) : Optional.empty());
        return cell;
    }

    @Test
    void allowMoveToOpponentSameLevel() {
        mockedBoardCells[0][0] = makeMockCell(mockedPlayer, false, 1);
        mockedBoardCells[1][1] = makeMockCell(mockedOpponent1, false, 1);

        turnEventsManager.processBeforeMovementEvents(mockedTurn);

        verify(mockedWalkableTargets.get(mockedWorkers.get(mockedPlayer).get(0))).setPosition(mockedBoardCells[1][1], true);
    }


    @Test
    void doNotAllowMoveToOpponentTooHigh() {
        mockedBoardCells[0][0] = makeMockCell(mockedPlayer, false, 1);
        mockedBoardCells[1][1] = makeMockCell(mockedOpponent1, false, 3);

        turnEventsManager.processBeforeMovementEvents(mockedTurn);

        verify(mockedWalkableTargets.get(mockedWorkers.get(mockedPlayer).get(0)), never()).setPosition(mockedBoardCells[1][1], true);
    }
}

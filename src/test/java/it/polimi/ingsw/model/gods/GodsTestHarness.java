package it.polimi.ingsw.model.gods;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.Turn;
import it.polimi.ingsw.model.actions.BuildAction;
import it.polimi.ingsw.model.actions.MoveAction;
import it.polimi.ingsw.model.board.*;
import it.polimi.ingsw.model.turnevents.TurnEventsManager;
import it.polimi.ingsw.model.workers.Worker;
import org.junit.jupiter.api.BeforeEach;

import java.util.*;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.lenient;

public class GodsTestHarness {
//    private final God god;
    private final Game mockedGame = mock(Game.class);
    private final Board mockedBoard = mock(Board.class);
    private final TurnEventsManager turnEventsManager = new TurnEventsManager(MockedPlayer.OWNER.player);
    private Turn mockedTurn;
    private Map<Player, List<Worker>> mockedWorkers;
    private Cell[][] mockedBoardCells;
    private Map<Turn, Map<Worker, TargetCells>> mockedWalkableTargets;
    private Map<Turn, Map<Worker, TargetCells>> mockedBlockBuildableTargets;
    private Map<Turn, Map<Worker, TargetCells>> mockedDomeBuildableTargets;
    private Map<MockedPlayer, God> mockedPlayersGods;
    private Map<Turn, List<MoveAction>> mockedMoveActions;
    private Map<Turn, List<BuildAction>> mockedBuildActions;
    private List<Turn> mockedLastRoundTurnsList;

    public enum MockedPlayer {
        OWNER(mock(Player.class)),
        OPPONENT1(mock(Player.class)),
        OPPONENT2(mock(Player.class)),
        ;

        Player player;
        MockedPlayer(Player mockedPlayer) {
            this.player = mockedPlayer;
        }
    }

    GodsTestHarness(God god) {
        this(MockedPlayer.OWNER, god);
    }

    GodsTestHarness(MockedPlayer godPlayer, God god) {
        mockedWalkableTargets = new HashMap<>();
        mockedBlockBuildableTargets = new HashMap<>();
        mockedDomeBuildableTargets = new HashMap<>();
        mockedWorkers = new HashMap<>();
        mockedPlayersGods = new HashMap<>();
        mockedMoveActions = new HashMap<>();
        mockedBuildActions = new HashMap<>();
        mockedLastRoundTurnsList = new LinkedList<>();

        mockedTurn = makeMockTurn();

        mockedWorkers.put(godPlayer.player, new ArrayList<>());
        mockedPlayersGods.put(godPlayer, god);

        if (!godPlayer.equals(MockedPlayer.OWNER)) {
            mockedWorkers.put(MockedPlayer.OWNER.player, new ArrayList<>());
            mockedPlayersGods.put(MockedPlayer.OWNER, new EmptyGod());
            turnEventsManager.addTurnEventsFromOpponents(godPlayer.player, god.getOpponentsTurnEvents());
        }

        when(mockedGame.getBoard()).thenReturn(mockedBoard);
        when(mockedGame.getLastRoundTurnsList()).thenReturn(mockedLastRoundTurnsList);
        when(mockedGame.getPlayersList()).thenAnswer(mockedCall -> {
            ArrayList<Player> retVal = new ArrayList<>();
            for (MockedPlayer mockedPlayer : MockedPlayer.values()) {
                retVal.add(mockedPlayer.player);
            }
            return retVal;
        });
//        when(mockedGame.getPlayersList()).thenReturn(Arrays.stream(MockedPlayer.values()).reduce(
//                new ArrayList<Player>(),
//                (retVal, mockedPlayer) -> {retVal.add(mockedPlayer); return retVal;}
//                ));
        when(mockedBoard.getDimension()).thenCallRealMethod();
        when(mockedBoard.getCell(any(int.class), any(int.class))).thenAnswer(mockedCall -> {
            int x = mockedCall.getArgument(0);
            int y = mockedCall.getArgument(1);
            return mockedBoardCells[x][y];
        });
        when(mockedBoard.fromBaseCellAndDirection(any(Cell.class), any(Direction.class))).thenCallRealMethod();
        when(mockedBoard.getCellsList()).thenAnswer(mockedCall -> Arrays.stream(mockedBoardCells).flatMap(Arrays::stream).collect(Collectors.toList()));
        when(mockedBoard.getTargets(any(TargetCells.class))).thenAnswer(mockedCall -> {
            TargetCells target = mockedCall.getArgument(0);
            List<Cell> targetedCells = new ArrayList<Cell>();
            for(int i = 0; i < mockedBoard.getDimension(); i++) {
                for (int j = 0; j < mockedBoard.getDimension(); j++) {
                    if (target.getPosition(i, j)) {
                        targetedCells.add(mockedBoardCells[i][j]);
                    }
                }
            }
            return targetedCells;
        });
        for (MockedPlayer mockedPlayer : MockedPlayer.values()) {
            when(mockedPlayer.player.getGod()).thenAnswer(mockedCall -> mockedPlayersGods.get(mockedPlayer) != null ? mockedPlayersGods.get(mockedPlayer) : new EmptyGod());
        }
//        when(MockedPlayer.OWNER.player.getGod()).thenReturn(this.god);
        when(MockedPlayer.OWNER.player.getOwnWorkers()).thenAnswer(mockedCall -> mockedWorkers.get(MockedPlayer.OWNER.player).toArray(Worker[]::new));

        mockedBoardCells = new Cell[mockedBoard.getDimension()][mockedBoard.getDimension()];
        for(int x = 0; x < mockedBoard.getDimension(); x++){
            for(int y = 0; y < mockedBoard.getDimension(); y++ ){
                mockedBoardCells[x][y] = makeMockCell(x, y);
            }
        }
    }

    Turn makeMockTurn() {
        return makeMockTurn(MockedPlayer.OWNER);
    }

    Turn makeMockTurn(MockedPlayer mockedPlayer) {
        Turn turn = mock(Turn.class);
        mockedWalkableTargets.put(turn, new HashMap<>());
        mockedBlockBuildableTargets.put(turn, new HashMap<>());
        mockedDomeBuildableTargets.put(turn, new HashMap<>());
        mockedMoveActions.put(turn, new LinkedList<>());
        mockedBuildActions.put(turn, new LinkedList<>());
        when(turn.getPlayer()).thenReturn(mockedPlayer.player);
        when(turn.getMoves()).thenReturn(mockedMoveActions.get(turn));
        when(turn.getBuilds()).thenReturn(mockedBuildActions.get(turn));
        when(turn.getGame()).thenReturn(mockedGame);
        when(turn.getWorkerWalkableCells(any(Worker.class))).thenAnswer(mockedCall -> {
            Worker worker = mockedCall.getArgument(0);
            return mockedWalkableTargets.get(turn).get(worker);
        });
        when(turn.getWorkerBlockBuildableCells(any(Worker.class))).thenAnswer(mockedCall -> {
            Worker worker = mockedCall.getArgument(0);
            return mockedBlockBuildableTargets.get(turn).get(worker);
        });
        when(turn.getWorkerDomeBuildableCells(any(Worker.class))).thenAnswer(mockedCall -> {
            Worker worker = mockedCall.getArgument(0);
            return mockedDomeBuildableTargets.get(turn).get(worker);
        });

        return turn;
    }

    public Game getGame() {
        return mockedGame;
    }

    public Turn getTurn() {
        return mockedTurn;
    }

    public TurnEventsManager getTurnEventsManager() {
        return turnEventsManager;
    }

    public Worker getWorker(int workerNumber) {
        return getWorker(MockedPlayer.OWNER.player, workerNumber);
    }

    public Worker getWorker(Player player, int workerNumber) {
        return mockedWorkers.get(player).get(workerNumber);
    }

    public TargetCells getWalkableTargetCells(int workerNumber) {
        return getWalkableTargetCells(mockedTurn, MockedPlayer.OWNER.player, workerNumber);
    }

    public TargetCells getWalkableTargetCells(Player player, int workerNumber) {
        return getWalkableTargetCells(mockedTurn, player, workerNumber);
    }

    public TargetCells getWalkableTargetCells(Turn turn, Player player, int workerNumber) {
        return mockedWalkableTargets.get(turn).get(getWorker(player, workerNumber));
    }

    public TargetCells getBlockBuildableTargetCells(int workerNumber) {
        return getBlockBuildableTargetCells(mockedTurn, MockedPlayer.OWNER.player, workerNumber);
    }

    public TargetCells getBlockBuildableTargetCells(Player player, int workerNumber) {
        return getBlockBuildableTargetCells(mockedTurn, player, workerNumber);
    }

    public TargetCells getBlockBuildableTargetCells(Turn turn, Player player, int workerNumber) {
        return mockedBlockBuildableTargets.get(turn).get(getWorker(player, workerNumber));
    }

    public TargetCells getDomeBuildableTargetCells(int workerNumber) {
        return getDomeBuildableTargetCells(mockedTurn, MockedPlayer.OWNER.player, workerNumber);
    }

    public TargetCells getDomeBuildableTargetCells(Player player, int workerNumber) {
        return getDomeBuildableTargetCells(mockedTurn, player, workerNumber);
    }

    public TargetCells getDomeBuildableTargetCells(Turn turn, Player player, int workerNumber) {
        return mockedDomeBuildableTargets.get(turn).get(getWorker(player, workerNumber));
    }

    Tower makeMockTower(boolean isComplete, int height) {
        Tower tower = mock(Tower.class);
        lenient().when(tower.isComplete()).thenReturn(isComplete);
        lenient().when(tower.getCurrentLevel()).thenReturn(height);
        return tower;
    }

    Worker makeMockWorker(Cell cell, Player player) {
        return makeMockWorker(mockedTurn, cell, player);
    }

    Worker makeMockWorker(Turn turn, Cell cell, Player player) {
        if (!mockedWorkers.containsKey(player)) {
            mockedWorkers.put(player, new ArrayList<>());
        }
        Worker worker = mock(Worker.class);
        lenient().when(worker.getCell()).thenReturn(cell);
        lenient().when(worker.getPlayer()).thenReturn(player);
        mockedWorkers.get(player).add(worker);

        return worker;
    }

    void computeWalkableTargets() {
        List<Turn> turns = new ArrayList<>(mockedLastRoundTurnsList);
        turns.add(mockedTurn);
        for (Turn turn : turns) {
            computeWalkableTargets(turn);
        }
    }

    void computeWalkableTargets(Turn turn) {
        mockedWorkers.values().stream().flatMap(List::stream).forEach(worker -> {
            computeWalkableTargets(turn, worker);
        });
    }

    void computeWalkableTargets(Turn turn, Worker worker) {
        TargetCells walkableCellsRadius = TargetCells.fromCellAndRadius(worker.getCell(), 1);
        mockedBoard.getTargets(walkableCellsRadius)
                .stream()
                .filter(lambdaCell -> lambdaCell.getTower().isComplete() || lambdaCell.getWorker().isPresent())
                .forEach(lambdaCell -> walkableCellsRadius.setPosition(lambdaCell,false));
        mockedWalkableTargets.get(turn).put(worker, walkableCellsRadius);
    }

    void computeBlockBuildableTargets(Turn turn) {
        mockedWorkers.values().stream().flatMap(List::stream).forEach(worker -> {
            computeBlockBuildableTargets(turn, worker);
        });
    }

    void computeBlockBuildableTargets(Turn turn, Worker worker) {
        TargetCells blockBuildableCellRadius = TargetCells.fromCellAndRadius(worker.getCell(), 1);
        mockedBoard.getTargets(blockBuildableCellRadius)
                .stream()
                .filter(lambdaCell -> lambdaCell.getTower().isComplete() || lambdaCell.getWorker().isPresent() || lambdaCell.getTower().getCurrentLevel() == 3)
                .forEach(lambdaCell -> blockBuildableCellRadius.setPosition(lambdaCell,false));
        mockedBlockBuildableTargets.get(turn).put(worker, blockBuildableCellRadius);
    }

    void computeDomeBuildableTargets(Turn turn) {
        mockedWorkers.values().stream().flatMap(List::stream).forEach(worker -> {
            computeDomeBuildableTargets(turn, worker);
        });
    }

    void computeDomeBuildableTargets(Turn turn, Worker worker) {
        TargetCells domeBuildableCellRadius = TargetCells.fromCellAndRadius(worker.getCell(), 1);
        mockedBoard.getTargets(domeBuildableCellRadius)
                .stream()
                .filter(lambdaCell -> lambdaCell.getTower().isComplete() || lambdaCell.getWorker().isPresent() || lambdaCell.getTower().getCurrentLevel() < 3)
                .forEach(lambdaCell -> domeBuildableCellRadius.setPosition(lambdaCell,false));
        mockedDomeBuildableTargets.get(turn).put(worker, domeBuildableCellRadius);
    }

    Cell makeMockCell(int x, int y) {
        return makeMockCell(null, false, 0, x, y);
    }

    Cell makeMockCell(Player player, boolean towerComplete, int towerHeight, int x, int y) {
        Cell cell = mock(Cell.class);
        Tower mockTower = makeMockTower(towerComplete, towerHeight);
        Worker mockWorker = player != null ? makeMockWorker(cell, player) : null;
        lenient().when(cell.getX()).thenReturn(x);
        lenient().when(cell.getY()).thenReturn(y);
        lenient().when(cell.getTower()).thenReturn(mockTower);
        lenient().when(cell.getWorker()).thenReturn(player != null ? Optional.of(mockWorker) : Optional.empty());
//        computeWalkableTargets();
        return cell;
    }

    void setCell(int x, int y, boolean towerComplete, int towerHeight) {
        setCell(x, y, null, towerComplete, towerHeight);
    }

    void setCell(int x, int y, Player player, boolean towerComplete, int towerHeight) {
        mockedBoardCells[x][y] = makeMockCell(player, towerComplete, towerHeight, x, y);
    }

    Cell getCell(int x, int y) {
        return mockedBoardCells[x][y];
    }

    void addMoveAction(int sourceX, int sourceY, int targetX, int targetY, int sourceLevel, int targetLevel, int workerNumber) {
        addMoveAction(mockedTurn, sourceX, sourceY, targetX, targetY, sourceLevel, targetLevel, workerNumber);
    }

    void addMoveAction(Turn turn, int sourceX, int sourceY, int targetX, int targetY, int sourceLevel, int targetLevel, int workerNumber) {
        mockedMoveActions.get(turn).add(new MoveAction(
                mockedBoardCells[sourceX][sourceY],
                mockedBoardCells[targetX][targetY],
                sourceLevel,
                targetLevel,
                mockedWorkers.get(turn.getPlayer()).get(workerNumber)
        ));
    }

    void addBuildAction(int targetX, int targetY, Buildable component, int targetLevel, int workerNumber) {
        addBuildAction(mockedTurn, targetX, targetY, component, targetLevel, workerNumber);
    }

    void addBuildAction(Turn turn, int targetX, int targetY, Buildable component, int targetLevel, int workerNumber) {
        mockedBuildActions.get(turn).add(new BuildAction(
                mockedBoardCells[targetX][targetY],
                component,
                targetLevel,
                mockedWorkers.get(turn.getPlayer()).get(workerNumber)
        ));
    }

    Turn addPreviousRoundTurn(MockedPlayer mockedPlayer) {
        Turn turn = makeMockTurn(mockedPlayer);
        mockedLastRoundTurnsList.add(turn);
        return turn;
    }

    void commitState() {
        computeWalkableTargets(mockedTurn);
        computeBlockBuildableTargets(mockedTurn);
        computeDomeBuildableTargets(mockedTurn);
    }
}

class EmptyGod extends AbstractGod{
    @Override
    public String getName() {
        return null;
    }
}
package MPQSimulator.Core;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.inject.Inject;
import com.google.inject.Provider;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Core.GameBoardMatches.GameBoardMatchesFactory;


public class GameEngineImpl implements GameEngine{
  // SHould be private, for testing.
  public GameBoardImpl board;
  
  public static final String NUM_BOARD_ROWS_STRING = "numBoardRows";
  public static final String NUM_BOARD_COLS_STRING = "numBoardCols";
  public static final int NUM_BOARD_ROWS = 8;
  public static final int NUM_BOARD_COLS = 8;
  public static final int NUM_TILES_ON_BOARD = NUM_BOARD_COLS * NUM_BOARD_ROWS;
  private final Provider<GameEngineMoveResults> engineMoveResultsProvider;
  private final GameBoardMatchesFactory gameBoardMatchesFactory;
  
  @Inject
  public GameEngineImpl(GameBoardImpl b, Provider<GameEngineMoveResults> engineMoveResultsProvider,
      GameBoardMatchesFactory gameBoardMatchesFactory) {
    this(b, true, engineMoveResultsProvider, gameBoardMatchesFactory);
  }
  
  // Used for debugging, relies on the initial board not being stabilized.
  public GameEngineImpl(GameBoardImpl b, boolean stabilizeBoard, 
      Provider<GameEngineMoveResults> engineMoveResultsProvider, GameBoardMatchesFactory gameBoardMatchesFactory) {
	  this.board = b;
	  this.engineMoveResultsProvider = engineMoveResultsProvider;
	  this.gameBoardMatchesFactory = gameBoardMatchesFactory;
	  if (stabilizeBoard) {
	    stabilizeBoard();
	  }
  }
  
  public GameEngineMoveResults useAbilityAndStabilizeBoard(Ability ability) {
      useAbility(ability);
      return stabilizeBoard();
  }
  
  
  public GameEngineMoveResults stabilizeBoard() {
    
    GameEngineMoveResults overallResults = engineMoveResultsProvider.get();
    
    GameEngineMoveResults currentMoveResults = resolveCurrentBoard();
    while (!currentMoveResults.empty()) {
      overallResults.add(currentMoveResults);
      currentMoveResults = resolveCurrentBoard();
    }
    
    return overallResults;
  }
  
  // Finds and destroys all tiles involved in match 3s+ on the current board.
  public GameEngineMoveResults resolveCurrentBoard() {
    GameEngineMoveResults engineResults = engineMoveResultsProvider.get();
    GameBoardMatches matches = gameBoardMatchesFactory.create(board);
    
    Set<Tile> tilesToDestroy = matches.getAllMatchedTiles();
    
    Set<Integer> rowsToDestroy = matches.getHorizontalMatchFours();
    for (Integer row : rowsToDestroy) {
      tilesToDestroy.addAll(board.getTilesInRow(row));
    }
    Set<Integer> colsToDestroy = matches.getVerticalMatchFours();
    for (Integer col : colsToDestroy) {
      tilesToDestroy.addAll(board.getTilesInCol(col));
    }
    
    engineResults.addDestroyedTiles(tilesToDestroy);
    board.destroyTiles(tilesToDestroy);
    return engineResults;
  }
  
  private void useAbility(Ability ability) {
    List<AbilityComponent> components = ability.getComponents();
    Iterator<AbilityComponent> it = components.iterator();
    while (it.hasNext()) {
      AbilityComponent component = it.next();
      component.process(board);
    }
  }
  
}

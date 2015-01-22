package MPQSimulator.Core;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;


public class GameEngine {
  // SHould be private, for testing.
  public GameBoardImpl board;
  
  public static final String NUM_BOARD_ROWS_STRING = "numBoardRows";
  public static final String NUM_BOARD_COLS_STRING = "numBoardCols";
  public static final int NUM_BOARD_ROWS = 8;
  public static final int NUM_BOARD_COLS = 8;
  public static final int NUM_TILES_ON_BOARD = NUM_BOARD_COLS * NUM_BOARD_ROWS;
  // This is pretty messy, maybe think of another way to deal with TileColors.
  
  public GameEngine() {
    this.board = new GameBoardImpl(NUM_BOARD_ROWS, NUM_BOARD_COLS);
    stabilizeBoard();
  }
  
  // Used for debugging, relies on the initial board not being stabilized.
  public GameEngine(GameBoardImpl b) {
	  this.board = b;
  }
  
  public GameEngineMoveResults useAbilityAndStabilizeBoard(Ability ability) {
      useAbility(ability);
      return stabilizeBoard();
  }
  
  
  public GameEngineMoveResults stabilizeBoard() {
    
    GameEngineMoveResults overallResults = new GameEngineMoveResults();
    
    GameEngineMoveResults currentMoveResults = resolveCurrentBoard();
    while (!currentMoveResults.empty()) {
      /*int totalTilesDestroyed = 0;
      for (TileColor color : currentMoveResults.getTilesDestroyedCount().keySet()) {
        totalTilesDestroyed += currentMoveResults.getTilesDestroyedCount().get(color);
      }
      
      if (totalTilesDestroyed > 20) {
        System.out.println("wat");
      }
      System.out.println("Total tiles destroyed: " + totalTilesDestroyed);*/
      overallResults.add(currentMoveResults);
      currentMoveResults = resolveCurrentBoard();
    }
    
    return overallResults;
  }
  
  // Finds and destroys all tiles involved in match 3s+ on the current board.
  public GameEngineMoveResults resolveCurrentBoard() {
    GameEngineMoveResults engineResults = new GameEngineMoveResults();
    GameBoardMatches matches = new GameBoardMatches(board);
    
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

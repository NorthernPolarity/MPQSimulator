package MPQSimulator.Core;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Sets;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.AbilityComponent.TileLocation;
import MPQSimulator.Abilities.ChangeTileColorAbilityComponent;
import MPQSimulator.Abilities.DestroySpecificTilesAbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Core.GameBoardMoveResults.MatchedTileBlob;
import MPQSimulator.Core.Tile.TileColor;


public class GameEngine {
  private GameBoard board;
  
  public static final int NUM_BOARD_ROWS = 8;
  public static final int NUM_BOARD_COLS = 8;
  public static final int NUM_TILES_ON_BOARD = NUM_BOARD_COLS * NUM_BOARD_ROWS;
  // This is pretty messy, maybe think of another way to deal with TileColors.
  
  public GameEngine() {
    this.board = new GameBoard(NUM_BOARD_ROWS, NUM_BOARD_COLS);
    stabilizeBoard();
  }
  
  // Used for debugging, relies on the initial board not being stabilized.
  public GameEngine(GameBoard b) {
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
    
    GameBoardMoveResults results = board.findMatchesOnBoard();
    Set<Tile> tilesToDestroy = results.getDestroyedTileSet();
    
    List<MatchedTileBlob> blobs = results.findTileBlobs();
    for (MatchedTileBlob blob : blobs) {
      Set<Integer> rowsToDestroy = blob.getHorizontalMatchFours();
      for (Integer row : rowsToDestroy) {
        tilesToDestroy.addAll(board.getTilesInRow(row));
      }
      Set<Integer> colsToDestroy = blob.getVerticalMatchFours();
      for (Integer col : colsToDestroy) {
        tilesToDestroy.addAll(board.getTilesInCol(col));
      }
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
      Set<Tile> tilesToDestroy = component.process(board);
      board.destroyTiles(tilesToDestroy); 
    }
  }
  
}

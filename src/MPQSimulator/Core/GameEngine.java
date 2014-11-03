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

import com.google.common.collect.ImmutableMap;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.AbilityComponent.TileLocation;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Core.GameBoardMoveResults.MatchedTileBlob;
import MPQSimulator.Core.Tile.TileColor;


public class GameEngine {
  private GameBoard board;
  
  private static final int NUM_BOARD_ROWS = 8;
  private static final int NUM_BOARD_COLS = 8;
  // This is pretty messy, maybe think of another way to deal with TileColors.
  private static final Map<AbilityComponent.TileColor, TileColor> tileColorTotileColorMap = 
      ImmutableMap.<AbilityComponent.TileColor, TileColor>builder()
          .put(AbilityComponent.TileColor.BLACK, TileColor.BLACK)
          .put(AbilityComponent.TileColor.BLUE, TileColor.BLUE)
          .put(AbilityComponent.TileColor.RED, TileColor.RED)
          .put(AbilityComponent.TileColor.GREEN, TileColor.GREEN)
          .put(AbilityComponent.TileColor.YELLOW, TileColor.YELLOW)
          .put(AbilityComponent.TileColor.PURPLE, TileColor.PURPLE)
          .put(AbilityComponent.TileColor.TEAMUP, TileColor.TEAMUP)
          .build();
  
  public GameEngine() {
      this.board = new GameBoard(NUM_BOARD_ROWS, NUM_BOARD_COLS);
      stabilizeBoard();
  }
  
  public GameEngineMoveResults useAbilityAndStabilizeBoard(Ability ability) {
      useAbility(ability);
      return stabilizeBoard();
  }
  
  
  public GameEngineMoveResults stabilizeBoard() {
    
    GameEngineMoveResults overallResults = new GameEngineMoveResults();
    
    GameEngineMoveResults currentMoveResults = resolveCurrentBoard();
    while (!currentMoveResults.empty()) {
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
      if (component instanceof SwapTileAbilityComponent) {
        processSwapTileAbility((SwapTileAbilityComponent) component);
      }
    }
  }
  
  // Logic for processing abilities involving swapping tiles.
  private void processSwapTileAbility(SwapTileAbilityComponent component) {
    
    if (component.tileALocation != TileLocation.RANDOM 
        || component.tileBLocation != TileLocation.RANDOM) {
      throw new IllegalArgumentException();
    }
    
    Set<Tile> tileSetA = component.tileAColor == AbilityComponent.TileColor.ANY
        ? board.getAllTiles() : board.getTiles(tileColorTotileColorMap.get(component.tileAColor));
    Set<Tile> tileSetB = component.tileBColor == AbilityComponent.TileColor.ANY
        ? board.getAllTiles() : board.getTiles(tileColorTotileColorMap.get(component.tileBColor));
    
    List<Tile> randomizedTileListA = new ArrayList<Tile>(tileSetA);
    Collections.shuffle(randomizedTileListA);
    List<Tile> randomizedTileListB = new ArrayList<Tile>(tileSetB);
    Collections.shuffle(randomizedTileListB);
    
    Set<Tile> alreadyShuffledTiles = new HashSet<>();
    Iterator<Tile> aIt = randomizedTileListA.iterator();
    Iterator<Tile> bIt = randomizedTileListB.iterator();
    int tilePairsSwapped = 0;
    Tile tileA = null;
    Tile tileB = null;
    while ( (component.tilePairsToSwap > tilePairsSwapped)
        && aIt.hasNext() && bIt.hasNext()) {
      
      // Search through the lists until we find a tile A and B that hasn't already been shuffled.
      if (tileA == null) {
        Tile next = aIt.next();
        if (!alreadyShuffledTiles.contains(next)) {
          tileA = next;
        }
      }
      
      if (tileB == null) {
        Tile next = bIt.next();
        if (!alreadyShuffledTiles.contains(next)) {
          tileB = next;
        }
      }
      
      if (tileA != null && tileB != null) {
        board.swapTiles(tileA, tileB);
        alreadyShuffledTiles.add(tileA);
        alreadyShuffledTiles.add(tileB);
        tilePairsSwapped++;
        tileA = null;
        tileB = null;
      }
    }
  }
  
}

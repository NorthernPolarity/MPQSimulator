package MPQSimulator.Core;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import MPQSimulator.Core.GameBoardMatches.SingleMatch;
import MPQSimulator.Core.Tile.TileColor;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;

public interface GameBoard {

  public void resetGameBoard();
  
  public Tile[][] getBoardState();

  // Returns all of the tiles of the given color currently on the board.
  public Set<Tile> getTiles(TileColor color);
  
  // Returns all of the tiles of the given color currently on the board.
  public Set<Tile> getTiles(Iterable<TileColor> colors);

  public Set<Tile> getTilesOfColors(TileColor ... colors);

  // Returns all of the tiles of the given color currently on the board.
  public Set<Tile> getAllTiles();
  
  // Returns all the tiles in the given row.
  public List<Tile> getTilesInRow(int row);
  
  public List<Tile> getTilesInCol(int col);
  
  public Tile getTile(int row, int col);
  
  public TileColor getTileColor(int row, int col);
  
  /*
   * locations is a list of size TILES_PER_ROW where each set in the list
   * represents a row in the array [locations.get(0) is row 0, and so on.
   * and each set contains the cols of the tiles in that row to be destroyed.
   */ 
  public void destroyTiles(Set<Tile> tiles);
  
  // Changes the new tiles to a new color chosen uniformly at random.
  public void changeTileColor(Set<Tile> tiles, List<TileColor> newColors);
  
  // Swaps tiles a and b on the board.
  public void swapTiles(Tile a, Tile b);
  

  public int[] getDimensions();
  
  public int getTilesPerCol();
  
  public int getTilesPerRow();
  
}

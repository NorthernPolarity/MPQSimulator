package MPQSimulator.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.google.common.base.Preconditions;

import MPQSimulator.Abilities.AbilityComponent.TileLocation;
import MPQSimulator.Core.Tile.TileColor;

public class SwapTileAbilityComponent implements AbilityComponent {

  public final int tilePairsToSwap;
  public final List<TileColor> tileAColors;
  public final TileLocation tileALocation;
  public final int tileARow;
  public final int tileACol;
  public final int tileBRow;
  public final int tileBCol;
  public final List<TileColor> tileBColors;
  public final TileLocation tileBLocation;  
  
  // Swaps n random tiles.
  public SwapTileAbilityComponent(int tilesToSwap) {
    this(tilesToSwap, ALL_COLORS_NO_TEAMUPS_LIST, ALL_COLORS_NO_TEAMUPS_LIST);
  }
  
  // Swaps n A tiles of color A with B tiles of color B.
  public SwapTileAbilityComponent(int tilesToSwap, TileColor tileAColor, TileColor tileBColor) {
    this(tilesToSwap, Arrays.asList(tileAColor), Arrays.asList(tileBColor));
  }
  
  // Swaps n A tiles of color A with B tiles of color B.
  public SwapTileAbilityComponent(int tilesToSwap, 
      List<TileColor> tileAColors, List<TileColor> tileBColors) {
    this(tilesToSwap, tileAColors, TileLocation.RANDOM, -1, -1, 
        tileBColors, TileLocation.RANDOM, -1, -1);
  }
  
  // Swaps the tile in location (row, col) with a random tile of tile B's color.
  public SwapTileAbilityComponent(int tileARow, int tileACol, TileColor tileBColor) {
    this(1, null, TileLocation.FIXED, tileARow, tileACol, Arrays.asList(tileBColor), TileLocation.RANDOM,
        -1, -1);
    
  }
  
  public SwapTileAbilityComponent(int tilesToSwap, List<TileColor> tileAColors, TileLocation tileALocation,
      int tileARow, int tileACol, List<TileColor> tileBColors, TileLocation tileBLocation, int tileBRow, 
      int tileBCol) {
    
    if (tileALocation == TileLocation.RANDOM) {
      Preconditions.checkArgument(tileARow == -1 && tileACol == -1);
    }

    if (tileBLocation == TileLocation.RANDOM) {
      Preconditions.checkArgument(tileBRow == -1 && tileBCol == -1);
    }    
    
    this.tilePairsToSwap = tilesToSwap;
    this.tileAColors = tileAColors;
    this.tileALocation = tileALocation;    
    this.tileARow = tileARow;
    this.tileACol = tileACol;
    this.tileBColors = tileBColors;
    this.tileBRow = tileBRow;
    this.tileBCol = tileBCol;
    this.tileBLocation = tileBLocation;  
  }
}

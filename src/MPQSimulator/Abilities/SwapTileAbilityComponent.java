package MPQSimulator.Abilities;

import com.google.common.base.Preconditions;

import MPQSimulator.Abilities.AbilityComponent.TileColor;
import MPQSimulator.Abilities.AbilityComponent.TileLocation;

public class SwapTileAbilityComponent implements AbilityComponent {

  public final int tilePairsToSwap;
  public final TileColor tileAColor;
  public final TileLocation tileALocation;
  public final int tileARow;
  public final int tileACol;
  public final int tileBRow;
  public final int tileBCol;
  public final TileColor tileBColor;
  public final TileLocation tileBLocation;  
  
  // Swaps n random tiles.
  public SwapTileAbilityComponent(int tilesToSwap) {
    this(tilesToSwap, TileColor.ANY, TileLocation.RANDOM, -1, -1, TileColor.ANY,
        TileLocation.RANDOM, -1, -1);
  }
  
  // Swaps n A tiles of color A with B tiles of color B.
  public SwapTileAbilityComponent(int tilesToSwap, TileColor tileAColor, TileColor tileBColor) {
    this(tilesToSwap, tileAColor, TileLocation.RANDOM, -1, -1, tileBColor,
        TileLocation.RANDOM, -1, -1);
  }
  
  public SwapTileAbilityComponent(int tilesToSwap, TileColor tileAColor, TileLocation tileALocation,
      int tileARow, int tileACol, TileColor tileBColor, TileLocation tileBLocation, int tileBRow, 
      int tileBCol) {
    
    if (tileALocation == TileLocation.RANDOM) {
      Preconditions.checkArgument(tileARow == -1 && tileACol == -1);
    }

    if (tileBLocation == TileLocation.RANDOM) {
      Preconditions.checkArgument(tileBRow == -1 && tileBCol == -1);
    }    
    
    this.tilePairsToSwap = tilesToSwap;
    this.tileAColor = tileAColor;
    this.tileALocation = tileALocation;    
    this.tileARow = tileARow;
    this.tileACol = tileACol;
    this.tileBColor = tileBColor;
    this.tileBRow = tileBRow;
    this.tileBCol = tileBCol;
    this.tileBLocation = tileBLocation;  
  }
}
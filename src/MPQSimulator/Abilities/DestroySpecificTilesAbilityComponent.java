
package MPQSimulator.Abilities;

import java.util.Arrays;
import java.util.List;

import MPQSimulator.Core.Tile.TileColor;

public class DestroySpecificTilesAbilityComponent implements AbilityComponent {

  public boolean[][] killPattern = { 
		  { true, false, true },
		  { false, true, false },
		  { true, false, true }
  };
  
  public int numTilesToDestroy = 0;
    
  public DestroySpecificTilesAbilityComponent(boolean[][] pattern) {
	  this.killPattern = pattern;
	  for( boolean[] row : pattern ) {
		  for( boolean col : row ) {
			  if( col ) {
				  numTilesToDestroy += 1;
			  }
		  }
	  }
  }
  
  // for an row X col block of destruction
  public DestroySpecificTilesAbilityComponent(int numRows, int numCols) {
	  this.killPattern = new boolean[numRows][numCols];
	  for( boolean[] row: this.killPattern ) {
		  Arrays.fill(row, true);
	  }
	  this.numTilesToDestroy = numRows * numCols;
  }
}


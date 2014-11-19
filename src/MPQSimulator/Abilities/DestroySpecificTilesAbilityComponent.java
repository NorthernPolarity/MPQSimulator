
package MPQSimulator.Abilities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.Tile;
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
  
//Processes abilities involving destroying specific patterns of tiles.
 public Set<Tile> process(GameBoard board) {    
   boolean[][] pattern = this.killPattern;
   int height = pattern.length;
   int width = pattern[0].length;
   for(boolean[] row : pattern) {
   	Preconditions.checkArgument(row.length == width);
   }
   int boardWidth = board.getDimensions()[0];
	Preconditions.checkArgument(boardWidth >= width);
   int boardHeight = board.getDimensions()[1];
	Preconditions.checkArgument(boardHeight >= height);
   
   int startingRow = (int)Math.round(Math.random() * (boardHeight - height));
   int startingCol = (int)Math.round(Math.random() * (boardWidth - width));
   
   Set<Tile> tilesToDestroy = new HashSet<>();
   
   for( int i = 0; i < height; i++) {
   	boolean[] row = pattern[i];
   	for( int j = 0; j < width; j++ ) {
   		if( row[j] ) {
   			tilesToDestroy.add(board.getTile(startingRow + i, startingCol + j));
   		}
   	}
   }
   return tilesToDestroy;
 }
}


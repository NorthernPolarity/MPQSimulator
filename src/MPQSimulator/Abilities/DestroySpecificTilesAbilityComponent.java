
package MPQSimulator.Abilities;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import MPQSimulator.Core.GameBoardImpl;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.RandomCaller;
import MPQSimulator.Core.Tile.TileColor;

public class DestroySpecificTilesAbilityComponent implements AbilityComponent {

  public final boolean[][] killPattern;
  
  public final int numTilesToDestroy;
  
  // Whether or not the pattern can be centered on an edge tile, resulting in not all of the pattern
  // being on the bard.
  public final boolean edgesTargetable;
  
  private final RandomCaller randomCaller;
    
  public DestroySpecificTilesAbilityComponent(boolean[][] pattern, boolean edgesTargetable,
      RandomCaller randomCaller) {
	  this.killPattern = pattern;
	  int numTilesToDestroy = 0;
	  for( boolean[] row : pattern ) {
		  for( boolean col : row ) {
			  if ( col ) {
			    numTilesToDestroy++;
			  }
		  }
	  }
	 
	  this.numTilesToDestroy = numTilesToDestroy;
	  this.edgesTargetable = edgesTargetable;
	  this.randomCaller = randomCaller;
  }
  
  // for an row X col block of destruction
  public DestroySpecificTilesAbilityComponent(int numRows, int numCols, boolean edgesTargetable,
      RandomCaller randomCaller) {
	  this(DestroySpecificTilesAbilityComponent.makeRectangularPattern(numRows, numCols),
	      edgesTargetable, randomCaller);
  }
  
  private static boolean[][] makeRectangularPattern(int numRows, int numCols) {
    boolean[][] killPattern = new boolean[numRows][numCols];
    for( boolean[] row: killPattern ) {
        Arrays.fill(row, true);
    }
    return killPattern;
  }
  
//Processes abilities involving destroying specific patterns of tiles.
 public void process(GameBoardImpl board) {    
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
     
     int rowCenter = height / 2;
     int colCenter = width / 2;
     
     int startingRow = edgesTargetable
         ? (int) ((randomCaller.random() * boardHeight) - rowCenter)
             : (int) Math.round(randomCaller.random() * (boardHeight - height));
         
     int startingCol = edgesTargetable
         ? (int) ((randomCaller.random() * boardWidth) - colCenter)
             : (int)Math.round(randomCaller.random() * (boardWidth - width));
         
     Set<Tile> tilesToDestroy = new HashSet<>();
     
     for( int i = 0; i < height; i++) {
     	boolean[] row = pattern[i];
     	for( int j = 0; j < width; j++ ) {
     	  int curRow = startingRow + i;
     	  int curCol = startingCol + j;
     		if (curRow >= 0 && curRow < boardHeight
     		    && curCol >= 0 && curCol < boardWidth 
     		    && row[j] ) {
     			tilesToDestroy.add(board.getTile(curRow, curCol));
     		}
     	}
     }
     
     board.destroyTiles(tilesToDestroy);
 }
 
}


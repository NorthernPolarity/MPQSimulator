package MPQSimulator.Core;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import MPQSimulator.Core.Tile.TileColor;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import com.google.inject.name.Named;

/*
 * Performs only the bare necessities for a match-3 game: Supports storing the state for an NxM board, 
 * finding all matches currently on the board, destroying a given set of tiles, and swapping
 * two tiles on the board.
 */
public class GameBoardImpl implements GameBoard {
    private final int tilesPerRow;
    private final int tilesPerCol;

    private Tile[][] gameBoard; //Represents the current state of the board
 
    
    // tracks stats of things done to the game board, like swaps and destroy
    // does not include tile matches or other game logic - see GameBoardMatches instead
    public class GameBoardStats {
    	public GameBoardStats(int countTileSwaps, int countTilesDestroyed) {
			super();
			this.countSwaps = countTileSwaps;
			this.countDestroyed = countTilesDestroyed;
		}
    	
    	public GameBoardStats() {
    		this(0,0);
    	}
    	
		public int countSwaps;
    	public int countDestroyed;
    	public int countColorChange;
    	
		public int getCountColorChange() {
			return countColorChange;
		}

		public void incCountColorChange(int countColorChange) {
			this.countColorChange += countColorChange;
		}

		public int getCountTileSwaps() {
			return countSwaps;
		}
		public void incCountTileSwaps(int countTileSwaps) {
			this.countSwaps += countTileSwaps;
		}
		public int getCountTilesDestroyed() {
			return countDestroyed;
		}
		public void incCountTilesDestroyed(int countTilesDestroyed) {
			this.countDestroyed += countTilesDestroyed;
		}

    };
    
    public GameBoardStats stats = new GameBoardStats();
    
    @Override
    public String toString() {
      String s = "";
      for (int i = 0; i < tilesPerRow; i++) {
        for (int j = 0; j < tilesPerCol; j++) {
          char tileLetter = gameBoard[i][j].getColor() != TileColor.BLUE ? gameBoard[i][j].getColor().toString().charAt(0) : 'U';
          s +=  tileLetter + " ";
        }
        s += "\n";
      }
      return s;
    }
    
    @Inject
    public GameBoardImpl(
        @Named(GameEngineImpl.NUM_BOARD_ROWS_STRING) int tilesPerRow, 
        @Named(GameEngineImpl.NUM_BOARD_COLS_STRING) int tilesPerCol) {
      this.tilesPerRow = tilesPerRow;
      this.tilesPerCol = tilesPerCol;
      gameBoard = new Tile[tilesPerRow][tilesPerCol];
      resetGameBoard();
    }
    
    public GameBoardImpl(int tilesPerRow, int tilesPerCol, Tile[][] initialBoard) {
      Preconditions.checkArgument(initialBoard.length == tilesPerRow);
      for (Tile[] col : initialBoard) {
        Preconditions.checkArgument(col.length == tilesPerCol);
      }
      
      // Check that all tiles have valid state
      for (int i = 0; i < tilesPerRow; i++) {
        for (int j = 0; j < tilesPerCol; j++) {
          Tile t = initialBoard[i][j];
          Preconditions.checkArgument(t.getRow() == i);
          Preconditions.checkArgument(t.getCol() == j);
        }
      }
      
      this.tilesPerRow = tilesPerRow;
      this.tilesPerCol = tilesPerCol;
      
      gameBoard = new Tile[tilesPerRow][tilesPerCol];
      // Copy the tiles over.
      for (int i = 0; i < tilesPerRow; i++) {
        for (int j = 0; j < tilesPerCol; j++) {
          gameBoard[i][j] = new Tile(initialBoard[i][j]);
        }
      }
    }
    
    public void resetGameBoard() {
    // Initialize tiles randomly.
      for(int i = 0; i < tilesPerRow; i++){
          for(int j = 0; j < tilesPerCol; j++){
              gameBoard[i][j] = new Tile(i, j);
          }
      }
    }
    
    public Tile[][] getBoardState() {
        return gameBoard;
    }
    
    // Returns all of the tiles of the given color currently on the board.
    public Set<Tile> getTiles(TileColor color) {
      Set<Tile> tiles = new HashSet<>();
      for (Tile[] col : gameBoard) {
        for (Tile t : col) {
          if (t.getColor() == color) {
            tiles.add(t);
          }
        }
      }
      return tiles;
    }
    
    
    // Returns all of the tiles of the given color currently on the board.
    public Set<Tile> getTiles(Iterable<TileColor> colors) {
      Set<Tile> tiles = new HashSet<>();
      for (Tile[] col : gameBoard) {
        for (Tile t : col) {
          for (TileColor color : colors) {
            if (t.getColor() == color) {
              tiles.add(t);
            }
          }
        }
      }
      return tiles;
    }

    public Set<Tile> getTilesOfColors(TileColor ... colors) {
    	return getTiles(Arrays.asList(colors));
    }

    // Returns all of the tiles of the given color currently on the board.
    public Set<Tile> getAllTiles() {
      Set<Tile> tiles = new HashSet<>();
      for (Tile[] col : gameBoard) {
        for (Tile t : col) {
            tiles.add(t);
        }
      }
      return tiles;
    }
    
    // Returns all the tiles in the given row.
    public List<Tile> getTilesInRow(int row) {
      List<Tile> tiles = Lists.newArrayList(gameBoard[row]);
      return tiles;
//      for (int col = 0; col < tilesPerRow; col++) {
//        tiles.add(gameBoard[row][col]);
//      }
//      return tiles;
    }
    
    public List<Tile> getTilesInCol(int col) {
      List<Tile> tiles = Lists.newArrayList();
      for (int row = 0; row < tilesPerCol; row++) {
        tiles.add(gameBoard[row][col]);
      }
      return tiles;
    }
    
    public Tile getTile(int row, int col) {
      return gameBoard[row][col];
    }
    
    public TileColor getTileColor(int row, int col) {
    	return getTile(row, col).getColor();
    }

    private Set<Tile> resolveColumn(int colIndex, List<Tile> existingCol, Set<Tile> destroyedTiles) {
    	Set<Tile> result = new HashSet<Tile>();
    	Tile[] colAsArray = existingCol.toArray(new Tile[existingCol.size()]);
    	for(Tile t : destroyedTiles) {
    		assert(t.getCol() == colIndex);
    		colAsArray[t.getRow()] = null;
    	}
    	Arrays.sort(colAsArray, new Comparator<Tile>() {
            @Override
            public int compare(Tile o1, Tile o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return -1;
                }
                if (o2 == null) {
                    return 1;
                }
                return o1.compareTo(o2);
            }});
    	
    	for( int i = 0; i < colAsArray.length; i++ ) {
    		if( colAsArray[i] == null ) {
    			colAsArray[i] = new Tile(i, colIndex);
    		} else {
    			colAsArray[i].changeLocation(i, colIndex);
    		}
    		result.add(colAsArray[i]);
    	}
    	return result;
    }
    
    private void replaceTile(Tile t) {
    	gameBoard[t.getRow()][t.getCol()] = t;
    }
    
    private void destroyTiles(GameBoardMoveResults results) {
      if (results.getTilesPerRow() != tilesPerRow){
          System.out.println("Error in destroy Tiles!");
      }      
      
      stats.incCountTilesDestroyed(results.size());
      
      //For each col
      for (int currentCol = 0; currentCol < tilesPerRow; currentCol++){
        Set<Tile> destroyedTilesByCol = results.getTilesByCol(currentCol);
        if( destroyedTilesByCol.size() == 0 ) {
        	continue;
        }
        for (Tile t : destroyedTilesByCol) {
          Preconditions.checkArgument(gameBoard[t.getRow()][t.getCol()].equals(t));
        }
        
        List<Tile> currentColTiles = getTilesInCol(currentCol);
        Set<Tile> newCol = resolveColumn(currentCol, currentColTiles, destroyedTilesByCol);
        
        for( Tile t : newCol ) {
        	replaceTile(t);
        }
        
      }     
    }
    
    /*
     * locations is a list of size TILES_PER_ROW where each set in the list
     * represents a row in the array [locations.get(0) is row 0, and so on.
     * and each set contains the cols of the tiles in that row to be destroyed.
     */ 
    public void destroyTiles(Set<Tile> tiles) {
      GameBoardMoveResults results = new GameBoardMoveResults(tilesPerRow, tilesPerCol);
      results.addTiles(tiles);
      this.destroyTiles(results);
    }
    
    // Changes the new tiles to a new color chosen uniformly at random.
    public void changeTileColor(Set<Tile> tiles, List<TileColor> newColors) {
      for (Tile t : tiles) {
        int rand = (int)(Math.random() * newColors.size());
        t.setColor(newColors.get(rand));
      }
      stats.incCountColorChange(tiles.size());
    }
    
    // Swaps tiles a and b on the board.
    public void swapTiles(Tile a, Tile b) {
      Preconditions.checkArgument(gameBoard[a.getRow()][a.getCol()] == a);
      Preconditions.checkArgument(gameBoard[b.getRow()][b.getCol()] == b);
      
      gameBoard[a.getRow()][a.getCol()] = b;
      gameBoard[b.getRow()][b.getCol()] = a;
      
      int aRow = a.getRow();
      int aCol = a.getCol();
      a.changeLocation(b.getRow(), b.getCol());
      b.changeLocation(aRow, aCol);
      
      stats.incCountTileSwaps(2);
    }
    

	public int[] getDimensions() {
		int[] d = new int[2];
		d[0] = this.getTilesPerRow();
		d[1] = this.getTilesPerCol();
		return d;
	}
    
	public int getTilesPerCol() {
		return this.tilesPerCol;
	}
	public int getTilesPerRow() {
		return this.tilesPerRow;
	}
}
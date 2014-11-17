package MPQSimulator.Core;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.TreeSet;

import MPQSimulator.Core.Tile.TileColor;

import com.google.common.base.Preconditions;

public class GameBoardMoveResults {
    private final int tilesPerRow;
    private final int tilesPerCol;
    //Where the destroyed tiles were located on the board. Each set corresponds to a row,
    //the Integers in the set correspond to the columns that were destroyed.
    private Set<Tile> destroyedTileSet; 
    // The destroyed tiles grouped by the column that they're in: (0,1) (0,2) in the same set.
    private List<Set<Tile>> destroyedTileSetByCol;
    // Each element corresponds to whether or not the tile in that position was destroyed
    // Used for finding blobs.
    private Tile[][] destroyedTiles;
    
    public GameBoardMoveResults(int tilesPerRow, int tilesPerCol){
        
      this.tilesPerRow = tilesPerRow;
      this.tilesPerCol = tilesPerCol;
      this.destroyedTileSet = new HashSet<Tile>();
      this.destroyedTileSetByCol = new ArrayList<Set<Tile>>();
      for (int i = 0; i < tilesPerRow; i++) {
        destroyedTileSetByCol.add(new HashSet<Tile>());
      }
      
      this.destroyedTiles = new Tile[tilesPerRow][tilesPerCol];
    }
    
    public int size() {
    	return destroyedTileSet.size();
    }
    
    public void addTile(Tile tile){
      this.destroyedTileSet.add(tile);
      this.destroyedTileSetByCol.get(tile.getRow()).add(tile);
      destroyedTiles[tile.getRow()][tile.getCol()] = tile;
    }
    
    public void addTile(Set<Tile> tiles){
      for (Tile t: tiles) {
          this.addTile(t);
      }
    }
    
    public void add(GameBoardMoveResults results){
      Set<Tile> tilesDestroyedLocations = results.getDestroyedTileSet();
        
      for(int i = 0; i < tilesPerRow; i++){
          destroyedTileSetByCol.get(i).addAll(results.destroyedTileSetByCol.get(i));
      }
      destroyedTileSet.addAll(tilesDestroyedLocations);
      
      // Merge the truth map together.
      for (int i = 0; i < tilesPerRow; i++) {
        for (int j = 0; j < tilesPerCol; j++) {
          if (results.destroyedTiles[i][j] != null) {
            destroyedTiles[i][j] = results.destroyedTiles[i][j];
          }
        }
      }
    }
    
    public Set<Tile> getDestroyedTileSet(){
        return this.destroyedTileSet;
    }
    
    public Set<Tile> getTilesByCol(int col){
        return this.destroyedTileSetByCol.get(col);
    }
    
    public int getTilesPerRow(){
        return tilesPerRow;
    }
    
    public boolean empty(){
        return this.destroyedTileSet.size() == 0;
    }
    
    public String toString() {
        String s = "Tiles for MoveResults: \n";

        for (Tile t : destroyedTileSet) {
            s += t.toString() + "\n";
        }
        s += "End Tiles for Move Results \n";
        
        return s;
    }
    
    // Returns a list of all of the tile blobs in these MoveResults.
    public List<MatchedTileBlob> findTileBlobs() {
      // The search algorithm modifies this array, so make a copy of it.
      Tile[][] destroyedTilesCopy = new Tile[tilesPerRow][tilesPerCol];
      for (int i = 0; i < tilesPerRow; i++) {
        for (int j = 0; j < tilesPerRow; j++) {
          destroyedTilesCopy[i][j] = destroyedTiles[i][j];
        }
      }
      
      // Find the beginning of each blob, and recursively find all tiles belonging to that blob.
      // The recursion will automatically set the tiles to null as it adds it to a blob, so we
      // can linearly scan through the entire array without fearing duplicate tiles added to blobs.
      List<MatchedTileBlob> blobs = new ArrayList<>();
      for (int i = 0; i < tilesPerRow; i++) {
        for (int j = 0; j < tilesPerCol; j++) {
          if (destroyedTilesCopy[i][j] != null) {
            MatchedTileBlob blob = new MatchedTileBlob(destroyedTilesCopy[i][j].getColor());
            findTileBlobsHelper(destroyedTilesCopy, i, j, blob);
            blobs.add(blob);
          }
        }
      }
      
      return blobs;
    }
    
    // Searches all adjacent tiles from row, col outwards to add to the current blob, and 
    // marks them as searched.
    private void findTileBlobsHelper(Tile[][] destroyedTiles, int row, int col,
        MatchedTileBlob blob) {
      // Overstepped our bounds, return.
      if (row >= tilesPerRow || col >= tilesPerCol
          || row < 0 || col < 0) {
        return;
      }
      // If this is a destroyed tile that hasn't been marked, mark it, add it to the blob,
      // and keep on searching all directions.
      if (destroyedTiles[row][col] != null 
          && destroyedTiles[row][col].getColor() == blob.getColor()) {
        blob.addTile(destroyedTiles[row][col]);
        destroyedTiles[row][col] = null;
        findTileBlobsHelper(destroyedTiles, row - 1, col, blob);
        findTileBlobsHelper(destroyedTiles, row, col - 1, blob);
        findTileBlobsHelper(destroyedTiles, row + 1, col, blob);
        findTileBlobsHelper(destroyedTiles, row, col + 1, blob);
      }
      // Otherwise stop searching and do nothing.
    }
    
    // A blob is a set of adjacent tiles.
    public static class MatchedTileBlob {
      private List<TreeSet<Tile>> tilesByCol;
      private List<TreeSet<Tile>> tilesByRow;
      private TileColor color;
      
      public MatchedTileBlob(TileColor color) {
        tilesByCol = new ArrayList<>();
        tilesByRow = new ArrayList<>();
        this.color = color;
      }
      
      // Adds a tile to the blob. Throws an exception if the tile is not adjacent 
      // to an existing tile in the blob.
      public void addTile(Tile t) throws IllegalArgumentException {
        Preconditions.checkArgument(t.getColor() == color);
        addToRowsList(t);
        addToColsList(t);
      }
      
      public TileColor getColor() {
        return color;
      }
      
      private void addToRowsList(Tile t) throws IllegalArgumentException {
        // If the tile isn't adjacent to any tiles in the list (and the lists aren't empty).
        if (!adjacent(t) && !tilesByRow.isEmpty()) {
          throw new IllegalArgumentException();
        }
        // Find the correct row to add the tile at
        for (Set<Tile> s : tilesByRow) {
          // Make sure the tile wasn't already added to the list.
          Preconditions.checkArgument(!s.contains(t));
          Preconditions.checkState(!s.isEmpty());
          // Check the row of the first element of the set to see if its the right row.
          if (t.getRow() != s.iterator().next().getRow()) {
            continue;
          }
          
          s.add(t);
          return;
        }
        
        // Getting to this point means that the list doesn't have the tile's row.
        TreeSet<Tile> s = new TreeSet<>();
        s.add(t);
        tilesByRow.add(s);
      }
      
      private void addToColsList(Tile t) {
        // If the tile isn't adjacent to any tiles in the list (and the lists aren't empty).
        if (!adjacent(t) && !tilesByCol.isEmpty()) {
          throw new IllegalArgumentException();
        }
        // Find the correct row to add the tile at
        for (Set<Tile> s : tilesByCol) {
          // Make sure the tile wasn't already added to the list.
          Preconditions.checkArgument(!s.contains(t));
          Preconditions.checkState(!s.isEmpty());
          // Check the col of the first element of the set to see if its the right cp;.
          if (t.getCol() != s.iterator().next().getCol()) {
            continue;
          }
          
          s.add(t);
          return;
        }
        
        // Getting to this point means that the list doesn't have the tile's row.
        TreeSet<Tile> s = new TreeSet<>();
        s.add(t);
        tilesByCol.add(s);
      }
      
      // Returns if tile t is adjacent to any of the tiles in the current blob
      private boolean adjacent(Tile t) {
        for (Set<Tile> s : tilesByCol) {
          for (Tile tile : s) {
            if (t.adjacent(tile)) {
              return true;
            }
          }
        }
        return false;
      }
      
      // Returns empty list if there aren't any.
      public Set<Integer> getHorizontalMatchFours() {
        Set<Integer> matchFourRows = new HashSet<>();
        
          for (Set<Tile> s : tilesByCol) {
            // Skip rows that don't have 4 elements.
            if (s.size() < 4) {
              continue;
            }
            
            if (s.isEmpty()) {
              throw new IllegalArgumentException();
            }
            
            Iterator<Tile> it = s.iterator();
            // This works because TreeSets are sorted.
            Tile prevTile = it.next();
            int adjacentTilesCount = 1;
            while (it.hasNext() && adjacentTilesCount < 4) {
              Tile currentTile = it.next();
              if (prevTile.horizontallyAdjacent(currentTile)) {
                adjacentTilesCount++;
              } else {
                adjacentTilesCount = 1;
              }
              
              prevTile = currentTile;
            }
            
            if (adjacentTilesCount >= 4) {
              matchFourRows.add(prevTile.getCol());
            }
          }
          
          return matchFourRows;
      }
      
      // Returns empty list if there aren't any.
      public Set<Integer> getVerticalMatchFours() {
        Set<Integer> matchFourCols = new HashSet<>();
        
        for (Set<Tile> s : tilesByRow) {
          // Skip rows that don't have 4 elements.
          if (s.size() < 4) {
            continue;
          }
          
          if (s.isEmpty()) {
            throw new IllegalArgumentException();
          }
          
          Iterator<Tile> it = s.iterator();
          // This works because TreeSets are sorted.
          Tile prevTile = it.next();
          int adjacentTilesCount = 1;
          while (it.hasNext() && adjacentTilesCount < 4) {
            Tile currentTile = it.next();
            if (prevTile.verticallyAdjacent(currentTile)) {
              adjacentTilesCount++;
            } else {
              adjacentTilesCount = 1;
            }
            
            prevTile = currentTile;
          }
          
          if (adjacentTilesCount >= 4) {
            matchFourCols.add(prevTile.getRow());
          }
        }
        
        return matchFourCols;
      }
      
      // Returns the location of the tile that the critical tile should spawn at. 
      public Tile getCriticalTileLocation() {
        return null;
      }
    }
}
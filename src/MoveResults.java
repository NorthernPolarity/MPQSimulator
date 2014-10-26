import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class MoveResults {
    private int tilesPerRow;
    //Where the destroyed tiles were located on the board. Each set corresponds to a row,
    //the Integers in the set correspond to the columns that were destroyed.
    private Set<Tile> destroyedTileSet; 
    // The destroyed tiles grouped by columns.
    private List<Set<Tile>> destroyedTileSetByCol;
    // Each element corresponds to whether or not the tile in that position was destroyed.
    private boolean[][] destroyedTiles;
    
    public MoveResults(int tilesPerRow, int tilesPerCol){
        
      this.tilesPerRow = tilesPerRow;
      this.destroyedTileSet = new HashSet<Tile>();
      this.destroyedTileSetByCol = new ArrayList<Set<Tile>>();
      for (int i = 0; i < tilesPerRow; i++) {
        destroyedTileSetByCol.add(new HashSet<Tile>());
      }
      
      this.destroyedTiles = new boolean[tilesPerRow][tilesPerCol];
    }
    
    public void addTile(Tile tile){
      this.destroyedTileSet.add(tile);
      this.destroyedTileSetByCol.get(tile.getRow()).add(tile);
    }
    
    public void addTile(Set<Tile> tiles){
      for (Tile t: tiles) {
          this.addTile(t);
      }
    }
    
    // doesnt work if same tile is in both.
    public void add(MoveResults results){
      Set<Tile> tilesDestroyedLocations = results.getDestroyedTileSet();
        
      for(int i = 0; i < tilesPerRow; i++){
          destroyedTileSetByCol.get(i).addAll(results.destroyedTileSetByCol.get(i));
      }
      destroyedTileSet.addAll(tilesDestroyedLocations);
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
}
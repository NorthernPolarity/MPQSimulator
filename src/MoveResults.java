import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

public class MoveResults {
    private Map<TileColor, Integer> mTilesDestroyedCount; //Number of tiles destroyed per color.
    private int mTilesPerRow;
    //Where the destroyed tiles were located on the board. Each set corresponds to a row,
    //the Integers in the set correspond to the columns that were destroyed.
    private Set<Tile> mDestroyedTileSet; 
    private List<Set<Tile>> mDestroyedTileSetByCol; 
    
    public MoveResults(int gameTotalRows){
        this.mTilesDestroyedCount = new HashMap<TileColor,Integer>();
        this.mTilesPerRow = gameTotalRows;
        this.mDestroyedTileSet = new HashSet<Tile>();
        this.mDestroyedTileSetByCol = new ArrayList<Set<Tile>>();
        for(int i = 0; i < mTilesPerRow; i++){
            mDestroyedTileSetByCol.add(new HashSet<Tile>());
        }
        
    }
    
    public void addTile(Tile tile){
        this.mDestroyedTileSet.add(tile);
        this.mDestroyedTileSetByCol.get(tile.getRow()).add(tile);
        updateTilesDestroyedCount(tile.getColor(), 1);
    }
    
    public void addTile(Set<Tile> tiles){
        for(Tile t: tiles){
            this.addTile(t);
        }
    }
    
    // doesnr work if same tile is in both.
    public void add(MoveResults results){
        Map<TileColor,Integer> tilesDestroyedCount = results.getTilesDestroyedCount();
        Set<Tile> tilesDestroyedLocations = results.getDestroyedTileSet();
        
        for(TileColor color : tilesDestroyedCount.keySet()){
            updateTilesDestroyedCount(color, tilesDestroyedCount.get(color));
        }
          
        for(int i = 0; i < mTilesPerRow; i++){
            mDestroyedTileSetByCol.get(i).addAll(results.mDestroyedTileSetByCol.get(i));
        }
        mDestroyedTileSet.addAll(tilesDestroyedLocations);
    }
    
    private void updateTilesDestroyedCount(TileColor color, Integer tilesDestroyed){
        int totalTilesDestroyed = tilesDestroyed; 
        //If the color exists in the map, add those tiles destroyed to the total.
        if (mTilesDestroyedCount.containsKey(color)){
            totalTilesDestroyed+= mTilesDestroyedCount.get(color);
        }
        mTilesDestroyedCount.put(color, totalTilesDestroyed);
    }
    
    public Map<TileColor,Integer> getTilesDestroyedCount(){
        return new HashMap<TileColor,Integer>(mTilesDestroyedCount); //Defensive copy
    }
    
    public Set<Tile> getDestroyedTileSet(){
        return this.mDestroyedTileSet;
    }
    
    public Set<Tile> getTilesByCol(int col){
        return this.mDestroyedTileSetByCol.get(col);
    }
    
    public int getTilesPerRow(){
        return mTilesPerRow;
    }
    
    public boolean empty(){
        return this.mTilesDestroyedCount.size() == 0 
            || this.mDestroyedTileSet.size() == 0;
    }
    
    public String toString() {
        String s = "Tiles for MoveResults: \n";

        for (Tile t : mDestroyedTileSet) {
            s += t.toString() + "\n";
        }
        s += "End Tiles for Move Results \n";
        
        return s;

    }
    
}
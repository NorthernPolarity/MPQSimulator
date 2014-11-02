package MPQSimulator.Core;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class GameEngine {
  private GameBoard board;
  
  public GameEngine() {
      this.board = new GameBoard(8, 8);
    
  }
  
  
  private void resolveBoard() {
    MoveResults results = board.findMatchesOnBoard();
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    
  }
  
  /**
   
       private Map<TileColor, Integer> mTilesDestroyedCount; //Number of tiles destroyed per color.
       this.mTilesDestroyedCount = new HashMap<TileColor,Integer>();
       
       
       private void updateTilesDestroyedCount(TileColor color, Integer tilesDestroyed){
        int totalTilesDestroyed = tilesDestroyed; 
        //If the color exists in the map, add those tiles destroyed to the total.
        if (mTilesDestroyedCount.containsKey(color)){
            totalTilesDestroyed+= mTilesDestroyedCount.get(color);
        }
        mTilesDestroyedCount.put(color, totalTilesDestroyed);
        
       
    }
    
        Map<TileColor,Integer> tilesDestroyedCount = results.getTilesDestroyedCount();
            for(TileColor color : tilesDestroyedCount.keySet()){
            updateTilesDestroyedCount(color, tilesDestroyedCount.get(color));
        }
        
            public Map<TileColor,Integer> getTilesDestroyedCount(){
        return new HashMap<TileColor,Integer>(mTilesDestroyedCount); //Defensive copy
    }
   
   */
  
  
}

package MPQSimulator.Core;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;

import MPQSimulator.Core.Tile.TileColor;

public class GameEngineMoveResults {

  private Map<TileColor, Integer> mTilesDestroyedCount; //Number of tiles destroyed per color.
  
  GameEngineMoveResults() {
    this.mTilesDestroyedCount = new HashMap<TileColor,Integer>();
    for (TileColor color : TileColor.values()) {
      mTilesDestroyedCount.put(color, 0);
    }
  }
  
  public void add(GameEngineMoveResults results) {
    for (TileColor color : results.mTilesDestroyedCount.keySet()) {
      Integer count = mTilesDestroyedCount.get(color);
      count += results.mTilesDestroyedCount.get(color);
    }
  }
  
  public boolean empty() {
    Set<TileColor> colorSet = mTilesDestroyedCount.keySet();
    for (TileColor color : colorSet) {
      if (mTilesDestroyedCount.get(color) != 0) {
        return false;
      }
    }
    
    return true;
  }
  
  public void addDestroyedTiles(Set<Tile> tiles) {
    for (Tile t: tiles) {
      Integer count = mTilesDestroyedCount.get(t.getColor());
      Preconditions.checkNotNull(count);
      count++;
    }
  }
  
  public Map<TileColor,Integer> getTilesDestroyedCount() {
    return new HashMap<TileColor,Integer>(mTilesDestroyedCount); //Defensive copy
  }  
}

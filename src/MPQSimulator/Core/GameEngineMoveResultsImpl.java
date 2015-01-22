package MPQSimulator.Core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;

import MPQSimulator.Core.Tile.TileColor;

public class GameEngineMoveResultsImpl implements GameEngineMoveResults {

  private List<Map<TileColor, Integer>> tilesDestroyedCountList;
  
  @Inject
  public GameEngineMoveResultsImpl() {
    this.tilesDestroyedCountList = new ArrayList<>();
  }
  
  public void add(GameEngineMoveResults results) {
    tilesDestroyedCountList.addAll(results.getTilesDestroyedCountList());
  }
  
  public boolean empty() {
    return tilesDestroyedCountList.isEmpty();
  }
  
  private Map<TileColor,Integer> getNewTilesDestroyedMap() {
    Map<TileColor, Integer> map = new HashMap<>();
    for (TileColor color : TileColor.values()) {
      map.put(color, 0);
    }
    return map;
  }
  
  public void addDestroyedTiles(Set<Tile> tiles) {
    if (tiles.isEmpty()) {
      return;
    }
    Map<TileColor, Integer> tilesDestroyedCount = getNewTilesDestroyedMap();
    for (Tile t: tiles) {
      Integer count = tilesDestroyedCount.get(t.getColor());
      Preconditions.checkNotNull(count);
      tilesDestroyedCount.put(t.getColor(), count + 1);
    }
    tilesDestroyedCountList.add(tilesDestroyedCount);
  }
  
  public Map<TileColor,Integer> getTilesDestroyedCount() {
    Map<TileColor, Integer> totalTilesDestroyedCount = getNewTilesDestroyedMap();
    
    for (Map<TileColor,Integer> map : tilesDestroyedCountList) {
      for (TileColor color : TileColor.values()) {
        Integer count = map.get(color);
        totalTilesDestroyedCount.put(color, totalTilesDestroyedCount.get(color) + count);
      }
    }
    return totalTilesDestroyedCount; 
  }  
  
  public List<Map<TileColor, Integer>> getTilesDestroyedCountList() {
    return tilesDestroyedCountList;
  }
  
  public int getNumTilesDestroyed() {
    Map<TileColor, Integer> totalTilesDestroyedCount = getTilesDestroyedCount();
    int tilesDestroyed = 0;
    for (TileColor color : TileColor.values()) {
      tilesDestroyed += totalTilesDestroyedCount.get(color);
    }
    
      return tilesDestroyed;
  }
}

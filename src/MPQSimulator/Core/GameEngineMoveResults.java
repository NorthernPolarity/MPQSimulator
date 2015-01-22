package MPQSimulator.Core;

import java.util.List;
import java.util.Map;
import java.util.Set;

import MPQSimulator.Core.Tile.TileColor;

public interface GameEngineMoveResults {

  public void add(GameEngineMoveResults results);
  
  public boolean empty();
  
  public void addDestroyedTiles(Set<Tile> tiles);
  
  public Map<TileColor,Integer> getTilesDestroyedCount();
  
  public int getNumTilesDestroyed();
  
  public List<Map<TileColor, Integer>> getTilesDestroyedCountList();
}

package MPQSimulator.Core;

import java.util.List;
import java.util.Set;

import MPQSimulator.Core.GameBoardMoveResultsImpl.MatchedTileBlob;

public interface GameBoardMoveResults {

  public int size();

  public void addTile(Tile tile);
  
  public void addTiles(Iterable<Tile> tiles);
  
  public void add(GameBoardMoveResultsImpl results);
  
  public Set<Tile> getDestroyedTileSet();
  
  public Set<Tile> getTilesByCol(int col);
  
  public int getTilesPerRow();
  
  public boolean empty();
  
  // Returns a list of all of the tile blobs in these MoveResults.
  public List<MatchedTileBlob> findTileBlobs();
}

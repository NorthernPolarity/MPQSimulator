package MPQSimulator.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;

public class DestroyTileAbilityComponent implements AbilityComponent {

  public final int maxTilesToDestroy;
  public final List<TileColor> tileColorsToDestroy;
  public final static int DESTROY_ALL_TILES = -1;
  
  // Destroys N random tiles of any color.
  public DestroyTileAbilityComponent(int maxTilesToDestroy) {
    this(maxTilesToDestroy, AbilityComponent.ALL_COLORS_LIST);
  }
  
  // Destroys N random tiles of the given color.
  public DestroyTileAbilityComponent(int maxTilesToDestroy, TileColor color) {
    this(maxTilesToDestroy, Arrays.asList(color));
  }
  
  // Destroys N random tiles of the given colors.
  public DestroyTileAbilityComponent(int maxTilesToDestroy, List<TileColor> colors) {
    this.maxTilesToDestroy = maxTilesToDestroy;
    this.tileColorsToDestroy = colors;
  }
  
  // Processes abilities involving destroying tiles.
  public void process(GameBoard board) {
    Set<Tile> tileSet = board.getTiles(tileColorsToDestroy);
    List<Tile> randomizedTileList = new ArrayList<Tile>(tileSet);
    Collections.shuffle(randomizedTileList);
    int ttd;
    if( maxTilesToDestroy == DestroyTileAbilityComponent.DESTROY_ALL_TILES ) {
    	ttd = randomizedTileList.size();
    } else {
    	ttd = Math.min(randomizedTileList.size(), maxTilesToDestroy);
    }
    assert(ttd >= 0);
    assert(ttd <= randomizedTileList.size());
    List<Tile> tilesToDestroy = randomizedTileList.subList(
        0, ttd);
    
    Set<Tile> tileSetToDestroy = new HashSet<>(tilesToDestroy);
    board.destroyTiles(tileSetToDestroy);;
  }
}

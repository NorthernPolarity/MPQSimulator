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
  public Set<Tile> process(GameBoard board) {
    Set<Tile> tileSet = board.getTiles(tileColorsToDestroy);
    List<Tile> randomizedTileList = new ArrayList<Tile>(tileSet);
    Collections.shuffle(randomizedTileList);
    int ttd = maxTilesToDestroy;
    if( ttd == DestroyTileAbilityComponent.DESTROY_ALL_TILES ) {
    	ttd = randomizedTileList.size();
    }
    List<Tile> tilesToDestroy = randomizedTileList.subList(
        0, Math.min(randomizedTileList.size(), ttd));
    
    Set<Tile> tileSetToDestroy = new HashSet<>(tilesToDestroy);
    return tileSetToDestroy;
  }
}

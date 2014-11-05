package MPQSimulator.Abilities;

import java.util.Arrays;
import java.util.List;

import MPQSimulator.Core.Tile.TileColor;

public class DestroyTileAbilityComponent implements AbilityComponent {

  public final int maxTilesToDestroy;
  public final List<TileColor> tileColorsToDestroy;
  
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
}

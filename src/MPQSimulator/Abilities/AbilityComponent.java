package MPQSimulator.Abilities;

import java.util.Arrays;
import java.util.List;

import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;


public interface AbilityComponent {
  public static enum TileLocation {RANDOM, FIXED};
  public static List<TileColor> ALL_COLORS_LIST = 
      Arrays.asList(Arrays.copyOfRange(TileColor.values(), 0, Tile.NUM_COLORED_TILES));
}

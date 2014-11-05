package MPQSimulator.Abilities;

import java.util.Arrays;
import java.util.List;

import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;


public interface AbilityComponent {
  public static enum TileLocation {RANDOM, FIXED};
  // No team-ups
  public static List<TileColor> ALL_COLORS_NO_TEAMUPS_LIST = 
      Arrays.asList(Arrays.copyOfRange(TileColor.values(), 0, Tile.NUM_COLORED_TILES));
  public static List<TileColor> ALL_COLORS_LIST = Arrays.asList(TileColor.values());
  
}

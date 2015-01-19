package MPQSimulator.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;

import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;


public interface AbilityComponent {
  public static enum TileLocation {RANDOM, FIXED};
  // Commonly used TileColor lists.
  public static List<TileColor> ALL_COLORS_NO_TEAMUPS_LIST = 
      Arrays.asList(Arrays.copyOfRange(TileColor.values(), 0, Tile.NUM_COLORED_TILES));
  public static List<TileColor> ALL_COLORS_LIST = Arrays.asList(TileColor.values());
  
  // Helper function that enables the current constructor structure.
  public static List<TileColor> removeColor(List<TileColor> list, TileColor color) {
    return removeColors(list, Arrays.asList(color));
  }
  
  public static List<TileColor> removeColors(List<TileColor> list, List<TileColor> colors) {
    List<TileColor> newList = new ArrayList<>(list);
    for (TileColor color : colors) {
      Preconditions.checkArgument(newList.contains(color));
      newList.remove(color);
    }
    return newList;
  }
  
  // process this ability on the designated board
  public void process(GameBoard board);
  
}

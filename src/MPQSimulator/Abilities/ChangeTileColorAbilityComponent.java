package MPQSimulator.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MPQSimulator.Core.Tile.TileColor;

public class ChangeTileColorAbilityComponent implements AbilityComponent{
  public final int maxTilesToChange;
  public final List<TileColor> oldTileColors;
  public final List<TileColor> newTileColors;
  
  //Changes N tiles of the old colors chosen uniformly at random to a new color chosen randomly.
  public ChangeTileColorAbilityComponent(int maxTilesToChange, List<TileColor> oldTileColors,
      List<TileColor> newTileColors) {
    this.maxTilesToChange = maxTilesToChange;
    this.oldTileColors = oldTileColors;
    this.newTileColors = newTileColors;
  }
  
  // Changes n random COLORED (no Teamups) tiles that aren't the new tile color to the new tile color.
  public ChangeTileColorAbilityComponent(int maxTilesToChange, TileColor newTileColor) {
    // Remove the new tile color from the list of old tile colors.
    this(maxTilesToChange, 
        AbilityComponent.removeColor(AbilityComponent.ALL_COLORS_NO_TEAMUPS_LIST, newTileColor), 
        Arrays.asList(newTileColor));
  }
  
  
}

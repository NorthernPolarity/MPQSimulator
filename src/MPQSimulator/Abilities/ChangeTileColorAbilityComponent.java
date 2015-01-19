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
  
  // Changes n random tiles of the old color to the new tile color.
  public ChangeTileColorAbilityComponent(int maxTilesToChange, TileColor oldTileColor, TileColor newTileColor) {
    // Remove the new tile color from the list of old tile colors.
    this(maxTilesToChange, Arrays.asList(oldTileColor), Arrays.asList(newTileColor));
  }
  
  public void process(GameBoard board) {
    Set<Tile> tileSet = board.getTiles(this.oldTileColors);
    List<Tile> randomizedTileList = new ArrayList<Tile>(tileSet);
    Collections.shuffle(randomizedTileList);
    
    List<Tile> tilesToChangeColor = randomizedTileList.subList(0, Math.min(randomizedTileList.size(), this.maxTilesToChange));
    board.changeTileColor(new HashSet<Tile>(tilesToChangeColor), this.newTileColors);
  }
}

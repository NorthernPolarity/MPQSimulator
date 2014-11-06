package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.ChangeTileColorAbilityComponent;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;

public class Mystique extends MPQCharacter {

  @Override
  protected List<Ability> initAbility1() {
    // TODO Auto-generated method stub
    return null;
  }

  // 
  @Override
  protected List<Ability> initAbility2() {
    int[] tilesChangedByLevel = {5, 6, 7, 8, 10};
    List<TileColor> oldTileColors = Arrays.asList(TileColor.RED, TileColor.GREEN, TileColor.YELLOW);
    List<TileColor> newTileColors = Arrays.asList(TileColor.BLACK, TileColor.PURPLE);

    List<Ability> abilityList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      AbilityComponent changeTiles = new ChangeTileColorAbilityComponent(tilesChangedByLevel[i], 
          oldTileColors, newTileColors);
      Ability ability = new Ability();
      ability.addComponent(changeTiles);
      abilityList.add(ability);
    }
    return abilityList;

  }

  @Override
  protected List<Ability> initAbility3() {
    // TODO Auto-generated method stub
    return null;
  }

}

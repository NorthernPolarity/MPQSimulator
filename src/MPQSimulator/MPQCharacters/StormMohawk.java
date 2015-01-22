package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class StormMohawk extends MPQCharacter{

  // Lightning Storm
  @Override
  protected List<AbilityImpl> initAbility1() {
    int[] tilesDestroyedByLevel = {8, 9, 10, 12, 14};
    List<AbilityImpl> abilityList = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      AbilityComponent destroyTiles = new DestroyTileAbilityComponent
          (tilesDestroyedByLevel[i], AbilityComponent.ALL_COLORS_LIST);
      AbilityImpl ability = new AbilityImpl();
      ability.addComponent(destroyTiles);
      abilityList.add(ability);
    }
    return abilityList;
  }

  // Mistress of the elements
  @Override
  protected List<AbilityImpl> initAbility2() {
    int[] tilesDestroyedByLevel = {5, 5, 5, 5, 7};
    List<AbilityImpl> abilityList = new ArrayList<>();
    
    for (int i = 0; i < 5; i++) {
      AbilityComponent destroyTiles = new DestroyTileAbilityComponent
          (tilesDestroyedByLevel[i], TileColor.TEAMUP);
      AbilityImpl ability = new AbilityImpl();
      ability.addComponent(destroyTiles);
      abilityList.add(ability);
    }
    return abilityList;
  }

  // 
  @Override
  protected List<AbilityImpl> initAbility3() {
    return null;
  }
  
}

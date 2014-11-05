package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class StormMohawk extends MPQCharacter{

  // Lightning Storm
  @Override
  protected List<Ability> initAbility1() {
    int[] tilesDestroyedByLevel = {8, 9, 10, 12, 14};
    List<Ability> abilityList = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      AbilityComponent destroyTiles = new DestroyTileAbilityComponent
          (tilesDestroyedByLevel[i], AbilityComponent.ALL_COLORS_LIST);
      Ability ability = new Ability();
      ability.addComponent(destroyTiles);
      abilityList.add(ability);
    }
    return abilityList;
  }

  // Mistress of the elements
  @Override
  protected List<Ability> initAbility2() {
    int[] tilesDestroyedByLevel = {5, 5, 5, 5, 7};
    List<Ability> abilityList = new ArrayList<>();
    
    for (int i = 0; i < 5; i++) {
      AbilityComponent destroyTiles = new DestroyTileAbilityComponent
          (tilesDestroyedByLevel[i], TileColor.TEAMUP);
      Ability ability = new Ability();
      ability.addComponent(destroyTiles);
      abilityList.add(ability);
    }
    return abilityList;
  }

  // 
  @Override
  protected List<Ability> initAbility3() {
    return null;
  }
  
}

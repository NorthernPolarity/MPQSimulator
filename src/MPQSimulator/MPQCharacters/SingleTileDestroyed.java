package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class SingleTileDestroyed extends MPQCharacter {

  @Override
  protected List<Ability> initAbility1() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<Ability> initAbility2() {
    List<Ability> abilityList = new ArrayList<>();
    
    for (int i = 0; i < 5; i++) {
      AbilityComponent destroyTiles = new DestroyTileAbilityComponent
          (1, TileColor.GREEN);
      Ability ability = new Ability();
      ability.addComponent(destroyTiles);
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

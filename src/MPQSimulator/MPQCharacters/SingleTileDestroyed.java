package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class SingleTileDestroyed extends MPQCharacter {

  @Override
  protected List<AbilityImpl> initAbility1() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<AbilityImpl> initAbility2() {
    List<AbilityImpl> abilityList = new ArrayList<>();
    
    for (int i = 0; i < 5; i++) {
      AbilityComponent destroyTiles = new DestroyTileAbilityComponent
          (1, TileColor.GREEN);
      AbilityImpl ability = new AbilityImpl();
      ability.addComponent(destroyTiles);
      abilityList.add(ability);
    }
    return abilityList;
  }

  @Override
  protected List<AbilityImpl> initAbility3() {
    // TODO Auto-generated method stub
    return null;
  }

}

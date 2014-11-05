package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class TorchClassic extends MPQCharacter {

  @Override
  protected List<Ability> initAbility1() {
    int[] tilesDestroyedByLevel = {2, 2, 2, 2, 2};
    List<Ability> abilityList = new ArrayList<>();
    
    for (int i = 0; i < 5; i++) {
      AbilityComponent destroyTiles = new DestroyTileAbilityComponent
          (tilesDestroyedByLevel[i], TileColor.RED);
      Ability ability = new Ability();
      ability.addComponent(destroyTiles);
      abilityList.add(ability);
    }
    return abilityList;
  }

  @Override
  protected List<Ability> initAbility2() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<Ability> initAbility3() {
    // TODO Auto-generated method stub
    return null;
  }

}

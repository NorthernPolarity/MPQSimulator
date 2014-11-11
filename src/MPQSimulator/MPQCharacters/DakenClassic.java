package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.ChangeTileColorAbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class DakenClassic extends MPQCharacter {

  @Override
  protected List<Ability> initAbility1() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<Ability> initAbility2() {
    // TODO Auto-generated method stub
    return null;
  }

  // Chemical Reaction
  @Override
  protected List<Ability> initAbility3() {
    List<Ability> abilityList = new ArrayList<>();
    AbilityComponent changeTiles = new ChangeTileColorAbilityComponent(2, TileColor.BLUE, TileColor.GREEN);
    Ability ability = new Ability();
    ability.addComponent(changeTiles);
    
    for (int i = 0; i < 5; i++) {
      abilityList.add(ability);
    }
    return abilityList;
  }

}

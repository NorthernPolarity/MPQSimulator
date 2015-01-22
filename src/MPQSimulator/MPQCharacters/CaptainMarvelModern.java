package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.ChangeTileColorAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class CaptainMarvelModern extends MPQCharacter {

  @Override
  protected List<AbilityImpl> initAbility1() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<AbilityImpl> initAbility2() {
    // TODO Auto-generated method stub
    return null;
  }

  // Strategic Command
  @Override
  protected List<AbilityImpl> initAbility3() {
    int[] tilesChangedByLevel = {5, 6, 7, 8, 9};
    // TODO Auto-generated method stub
    List<AbilityImpl> abilityList = new ArrayList<>();
    
    for (int i = 0; i < 5; i++) {

      AbilityComponent changeTiles = 
          new ChangeTileColorAbilityComponent(tilesChangedByLevel[i], TileColor.TEAMUP);
      AbilityImpl ability = new AbilityImpl();
      ability.addComponent(changeTiles);
      abilityList.add(ability);
    }
    return abilityList;
  }

}

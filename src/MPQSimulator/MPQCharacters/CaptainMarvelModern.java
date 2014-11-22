package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.ChangeTileColorAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class CaptainMarvelModern extends MPQCharacter {

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

  // Strategic Command
  @Override
  protected List<Ability> initAbility3() {
    int[] tilesChangedByLevel = {5, 6, 7, 8, 9};
    // TODO Auto-generated method stub
    List<Ability> abilityList = new ArrayList<>();
    
    for (int i = 0; i < 5; i++) {

      AbilityComponent changeTiles = 
          new ChangeTileColorAbilityComponent(tilesChangedByLevel[i], TileColor.TEAMUP);
      Ability ability = new Ability();
      ability.addComponent(changeTiles);
      abilityList.add(ability);
    }
    return abilityList;
  }

}

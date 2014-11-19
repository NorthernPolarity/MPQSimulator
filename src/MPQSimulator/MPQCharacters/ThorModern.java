package MPQSimulator.MPQCharacters;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.ChangeTileColorAbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class ThorModern extends MPQCharacter {


  // Mjolnirs might
  @Override
  protected List<Ability> initAbility1() {
	AbilityComponent changeTiles = new ChangeTileColorAbilityComponent(3, TileColor.YELLOW);
    return buildRepeatAbilityList(changeTiles);
  }

  // Thunder strike
  @Override
  protected List<Ability> initAbility2() {
    int[] tilesChangedByLevel = {4, 5, 6, 7, 9};
    // TODO Auto-generated method stub
    List<Ability> abilityList = new ArrayList<>();
    
    for (int i = 0; i < 5; i++) {

      AbilityComponent changeTiles = new ChangeTileColorAbilityComponent(tilesChangedByLevel[i], TileColor.GREEN);
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

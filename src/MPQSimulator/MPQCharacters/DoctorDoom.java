package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.ChangeTileColorAbilityComponent;
import MPQSimulator.Core.GameEngine;
import MPQSimulator.Core.Tile.TileColor;

public class DoctorDoom extends MPQCharacter {

  // Technopathic Strike
  protected List<Ability> initAbility1() {
    int[] tilesChangedByLevel = {6, 7, 8, 9, GameEngine.NUM_TILES_ON_BOARD};
    List<Ability> abilityList = new ArrayList<>();
    
    for (int i = 0; i < 5; i++) {

      AbilityComponent changeTiles = new ChangeTileColorAbilityComponent(
          tilesChangedByLevel[i], TileColor.BLUE, TileColor.BLACK);
      Ability ability = new Ability();
      ability.addComponent(changeTiles);
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

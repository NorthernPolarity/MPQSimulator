package MPQSimulator.Characters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Abilities.AbilityComponent.TileColor;

public class LokiDarkReign extends Character{

  @Override
  protected List<Ability> initAbility1() {
    
    return null;
  }

  // Illusions
  @Override
  protected List<Ability> initAbility2() {
    List<Ability> abilityList = new ArrayList<>();
    int[] tilesSwappedByLevel = {14, 18, 22, 26, 32};
    // Just swap tiles around.
    for (int i : tilesSwappedByLevel) {
      AbilityComponent swapTiles = new SwapTileAbilityComponent(i);
      
      Ability ability = new Ability();
      ability.addComponent(swapTiles);
      abilityList.add(ability);
    }
    
    return abilityList;
  }

  @Override
  protected List<Ability> initAbility3() {
    return null;
  }  
  
}

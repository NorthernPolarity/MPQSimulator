package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;

public class LokiDarkReign extends MPQCharacter{

  @Override
  protected List<AbilityImpl> initAbility1() {
    
    return null;
  }

  // Illusions
  @Override
  protected List<AbilityImpl> initAbility2() {
    List<AbilityImpl> abilityList = new ArrayList<>();
    int[] tilesSwappedByLevel = {14, 18, 22, 26, 32};
    // Just swap tiles around.
    for (int i : tilesSwappedByLevel) {
      AbilityComponent swapTiles = new SwapTileAbilityComponent(
          i, AbilityComponent.ALL_COLORS_NO_TEAMUPS_LIST, AbilityComponent.ALL_COLORS_NO_TEAMUPS_LIST);
      
      AbilityImpl ability = new AbilityImpl();
      ability.addComponent(swapTiles);
      abilityList.add(ability);
    }
    
    return abilityList;
  }

  @Override
  protected List<AbilityImpl> initAbility3() {
    return null;
  }  
  
}

package MPQSimulator.Characters;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;

public class LokiDarkReign extends Character{

  @Override
  protected Ability getAbility1() {
    
    return null;
  }

  // Illusions
  @Override
  protected Ability getAbility2() {
    Ability ability = new Ability();
    AbilityComponent swapTiles = new SwapTileAbilityComponent(36);
    ability.addComponent(swapTiles);
    
    return ability;
  }

  @Override
  protected Ability getAbility3() {
    return null;
  }  
  
}

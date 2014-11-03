package Characters;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Abilities.AbilityComponent.TileColor;

public class MagnetoClassic extends Character {

  @Override
  protected Ability getAbility1() {
    
    return null;
  }

  @Override
  protected Ability getAbility2() {
    // TODO Auto-generated method stub
    return null;
  }

  // Magnetized Projectiles
  @Override
  protected Ability getAbility3() {
    Ability ability = new Ability();
    AbilityComponent swapTiles = new SwapTileAbilityComponent(7, TileColor.RED, TileColor.BLUE);
    ability.addComponent(swapTiles);
    
    return ability;
  }

}

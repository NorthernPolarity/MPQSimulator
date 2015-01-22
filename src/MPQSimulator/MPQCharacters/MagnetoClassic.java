package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Core.GameEngine;
import MPQSimulator.Core.Tile.TileColor;

public class MagnetoClassic extends MPQCharacter {

  @Override
  protected List<AbilityImpl> initAbility1() {
    
    return null;
  }

  // Polarizing Force
  @Override
  protected List<AbilityImpl> initAbility2() {
    List<AbilityImpl> abilityList = new ArrayList<>();
    AbilityComponent destroyTiles = new DestroyTileAbilityComponent
        (DestroyTileAbilityComponent.DESTROY_ALL_TILES, TileColor.TEAMUP);
    AbilityImpl ability = new AbilityImpl();
    ability.addComponent(destroyTiles);
    for (int i = 0; i < 5; i++) {
      abilityList.add(ability);
    }
    return abilityList;
  }

  // Magnetized Projectiles
  @Override
  protected List<AbilityImpl> initAbility3() {
    List<AbilityImpl> abilityList = new ArrayList<>();
    int[] tilesSwappedByLevel = {5, 5, 6, 6, 7};
    // Just swap tiles around.
    for (int i : tilesSwappedByLevel) {
      AbilityComponent swapTiles = new SwapTileAbilityComponent(i, TileColor.RED, TileColor.BLUE);
      AbilityImpl ability = new AbilityImpl();
      ability.addComponent(swapTiles);
      abilityList.add(ability);
    }
    
    return abilityList;
  }

}

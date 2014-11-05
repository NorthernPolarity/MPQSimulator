package MPQSimulator.Characters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Core.GameEngine;
import MPQSimulator.Core.Tile.TileColor;

public class MagnetoClassic extends Character {

  @Override
  protected List<Ability> initAbility1() {
    
    return null;
  }

  // Polarizing Force
  @Override
  protected List<Ability> initAbility2() {
    List<Ability> abilityList = new ArrayList<>();
    AbilityComponent destroyTiles = new DestroyTileAbilityComponent
        (GameEngine.NUM_TILES_ON_BOARD, TileColor.TEAMUP);
    Ability ability = new Ability();
    ability.addComponent(destroyTiles);
    for (int i = 0; i < 5; i++) {
      abilityList.add(ability);
    }
    return abilityList;
  }

  // Magnetized Projectiles
  @Override
  protected List<Ability> initAbility3() {
    List<Ability> abilityList = new ArrayList<>();
    int[] tilesSwappedByLevel = {5, 5, 6, 6, 7};
    // Just swap tiles around.
    for (int i : tilesSwappedByLevel) {
      AbilityComponent swapTiles = new SwapTileAbilityComponent(i, TileColor.RED, TileColor.BLUE);
      Ability ability = new Ability();
      ability.addComponent(swapTiles);
      abilityList.add(ability);
    }
    
    return abilityList;
  }

}

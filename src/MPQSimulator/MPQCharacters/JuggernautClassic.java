package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class JuggernautClassic extends MPQCharacter {

  @Override
  protected List<Ability> initAbility1() {
    // TODO Auto-generated method stub
    return null;
  }

  // Unstoppable Crash.
  @Override
  protected List<Ability> initAbility2() {
	  AbilityComponent destroyTiles = new DestroyTileAbilityComponent(16, AbilityComponent.ALL_COLORS_LIST);
      return MPQCharacter.buildRepeatAbilityList(destroyTiles);
  }

  @Override
  protected List<Ability> initAbility3() {
    // TODO Auto-generated method stub
    return null;
  }

}

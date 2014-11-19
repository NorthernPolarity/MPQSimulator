package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class TorchClassic extends MPQCharacter {

  @Override
  protected List<Ability> initAbility1() {
      AbilityComponent destroyTiles = new DestroyTileAbilityComponent
              (2, TileColor.RED);
	  return this.buildRepeatAbilityList(destroyTiles);
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

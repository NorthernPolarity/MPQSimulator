package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class TorchClassic extends MPQCharacter {

  @Override
  protected List<AbilityImpl> initAbility1() {
      AbilityComponent destroyTiles = new DestroyTileAbilityComponent
              (2, TileColor.RED);
	  return MPQCharacter.buildRepeatAbilityList(destroyTiles);
  }

  @Override
  protected List<AbilityImpl> initAbility2() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<AbilityImpl> initAbility3() {
    // TODO Auto-generated method stub
    return null;
  }

}

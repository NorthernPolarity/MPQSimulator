package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.ChangeTileColorAbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.Tile.TileColor;

public class DakenClassic extends MPQCharacter {

  @Override
  protected List<AbilityImpl> initAbility1() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<AbilityImpl> initAbility2() {
    // TODO Auto-generated method stub
    return null;
  }

  // Chemical Reaction
  @Override
  protected List<AbilityImpl> initAbility3() {
    AbilityComponent changeTiles = new ChangeTileColorAbilityComponent(2, TileColor.BLUE, TileColor.GREEN);
    return MPQCharacter.buildRepeatAbilityList(changeTiles);
  }

}

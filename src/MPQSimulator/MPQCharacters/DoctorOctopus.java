package MPQSimulator.MPQCharacters;

import java.util.ArrayList;
import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;

public class DoctorOctopus extends MPQCharacter {

  // Manipulation
  @Override
  protected List<AbilityImpl> initAbility1() {

    AbilityComponent swapTiles = new SwapTileAbilityComponent(
        8, AbilityComponent.ALL_COLORS_NO_TEAMUPS_LIST, AbilityComponent.ALL_COLORS_NO_TEAMUPS_LIST);

    
    return MPQCharacter.buildRepeatAbilityList(swapTiles);
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

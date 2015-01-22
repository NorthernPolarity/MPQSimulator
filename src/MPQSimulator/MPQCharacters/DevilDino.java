package MPQSimulator.MPQCharacters;

import java.util.Arrays;
import java.util.List;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Core.GameEngine;
import MPQSimulator.Core.Tile.TileColor;

public class DevilDino extends MPQCharacter {

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

  // Prehistoric Arms
  @Override
  protected List<AbilityImpl> initAbility3() {
	  
	  // TODO: I am not sure these coordinates are correct
    AbilityComponent black = 
        new SwapTileAbilityComponent(0, 0, TileColor.BLACK);
    AbilityComponent red = 
        new SwapTileAbilityComponent(1, 1, TileColor.RED);
    AbilityComponent green = 
        new SwapTileAbilityComponent(2, 1, TileColor.GREEN);
    AbilityComponent yellow = 
        new SwapTileAbilityComponent(0, 2, TileColor.YELLOW);
    AbilityComponent purple = 
        new SwapTileAbilityComponent(1, 2, TileColor.PURPLE);
    AbilityComponent blue = 
        new SwapTileAbilityComponent(2, 2, TileColor.BLUE);
    
    AbilityImpl level1 = new AbilityImpl();
    level1.addComponent(black);
    AbilityImpl level2 = new AbilityImpl(level1);
    level2.addComponent(red);
    AbilityImpl level3 = new AbilityImpl(level2);
    level3.addComponent(green);
    AbilityImpl level4 = new AbilityImpl(level3);
    level4.addComponent(yellow);
    AbilityImpl level5 = new AbilityImpl(level4);
    level5.addComponent(purple);
    level5.addComponent(blue);
    
    List<AbilityImpl> allLevels = Arrays.asList(level1, level2, level3, level4, level5);

    return allLevels;
  }

}

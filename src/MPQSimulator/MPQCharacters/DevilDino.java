package MPQSimulator.MPQCharacters;

import java.util.Arrays;
import java.util.List;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Core.GameEngine;
import MPQSimulator.Core.Tile.TileColor;

public class DevilDino extends MPQCharacter {

  @Override
  protected List<Ability> initAbility1() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  protected List<Ability> initAbility2() {
    // TODO Auto-generated method stub
    return null;
  }

  // Prehistoric Arms
  @Override
  protected List<Ability> initAbility3() {    
    AbilityComponent black = 
        new SwapTileAbilityComponent(0, GameEngine.NUM_BOARD_COLS - 1, TileColor.BLACK);
    AbilityComponent red = 
        new SwapTileAbilityComponent(1, GameEngine.NUM_BOARD_COLS - 1, TileColor.RED);
    AbilityComponent green = 
        new SwapTileAbilityComponent(2, GameEngine.NUM_BOARD_COLS - 1, TileColor.GREEN);
    AbilityComponent yellow = 
        new SwapTileAbilityComponent(0, GameEngine.NUM_BOARD_COLS - 2, TileColor.YELLOW);
    AbilityComponent purple = 
        new SwapTileAbilityComponent(1, GameEngine.NUM_BOARD_COLS - 2, TileColor.PURPLE);
    AbilityComponent blue = 
        new SwapTileAbilityComponent(2, GameEngine.NUM_BOARD_COLS - 2, TileColor.BLUE);
    
    Ability level1 = new Ability();
    level1.addComponent(black);
    Ability level2 = new Ability(level1);
    level2.addComponent(red);
    Ability level3 = new Ability(level2);
    level3.addComponent(green);
    Ability level4 = new Ability(level3);
    level4.addComponent(yellow);
    Ability level5 = new Ability(level4);
    level5.addComponent(purple);
    level5.addComponent(blue);
    
    List<Ability> allLevels = Arrays.asList(level1, level2, level3, level4, level5);

    return allLevels;
  }

}

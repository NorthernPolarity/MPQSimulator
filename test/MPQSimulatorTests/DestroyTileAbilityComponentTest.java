package MPQSimulatorTests;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.GameEngine;
import MPQSimulator.Core.GameEngineMoveResults;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.FixedSequenceRandomImpl;
import MPQSimulator.Core.Tile.TileColor;
import MPQSimulator.MPQCharacters.MagnetoClassic;
import MPQSimulator.MPQCharacters.StormMohawk;
import MPQSimulator.MPQCharacters.TorchClassic;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;

public class DestroyTileAbilityComponentTest {

  // Torch Fireball
  @Test
  public void testDestroyTwoRedTiles() {
    Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.YELLOW, TileColor.GREEN);
    String bstr = "Y P B Y T \n" +
                  "U G U U Y \n" +
                  "P R T P R \n" +
                  "R G T U P \n" +
                  "T R Y Y T \n";
    GameBoard board = GameBoardTest.createBoardFromString(bstr);
    GameEngine engine = new GameEngine(board);
    
    Ability destroyTiles = new Ability(new DestroyTileAbilityComponent(2, TileColor.RED));

    assertEquals(4, board.getTiles(TileColor.RED).size());
    GameEngineMoveResults cascades = engine.useAbilityAndStabilizeBoard(destroyTiles);
    assertEquals(2, board.getTiles(TileColor.RED).size());
    assertEquals(0, cascades.getNumTilesDestroyed());

    assertEquals(2, board.stats.getCountTilesDestroyed());
  }
  
  // Mohawk Mistress
  @Test
  public void destroyFiveTeamUpTiles() {
    Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.YELLOW, TileColor.RED, TileColor.GREEN);
    String bstr = "Y P B Y T \n" +
                  "U G U U Y \n" +
                  "T R T P R \n" +
                  "R G T U P \n" +
                  "T R Y Y T \n";
    GameBoard board = GameBoardTest.createBoardFromString(bstr);
    GameEngine engine = new GameEngine(board);
    
    Ability mistress = new Ability(new DestroyTileAbilityComponent(5, TileColor.TEAMUP));

    assertEquals(6, board.getTiles(TileColor.TEAMUP).size());
    engine.useAbilityAndStabilizeBoard(mistress);           
    assertEquals(1, board.getTiles(TileColor.TEAMUP).size());
  }
  
  // C. Mag's polarizing force.
  @Test
  public void testDestroyAllTeamUpTiles() {
      for( AbilityLevel al : AbilityLevel.values() ) {
          Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.YELLOW);
          String bstr = "T T T T T \n" +
                        "T T T T T \n" +
                        "T T T T T \n" +
                        "T T T T T \n" +
                        "T T T R T \n";
          GameBoard board = GameBoardTest.createBoardFromString(bstr);
          GameEngine engine = new GameEngine(board);
          
          Ability polar = new Ability(new DestroyTileAbilityComponent
              (DestroyTileAbilityComponent.DESTROY_ALL_TILES, TileColor.TEAMUP));

          System.out.println("WAT: " + board.getTiles(TileColor.TEAMUP).size());
          assertEquals(24, board.getTiles(TileColor.TEAMUP).size());
          engine.useAbilityAndStabilizeBoard(polar);          
          assertEquals(0, board.getTiles(TileColor.TEAMUP).size());
      }
  }
  
}

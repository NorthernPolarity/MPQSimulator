package MPQSimulatorTests;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.DestroySpecificTilesAbilityComponent;
import MPQSimulator.Abilities.DestroyTileAbilityComponent;
import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.GameEngine;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.FixedSequenceRandomImpl;
import MPQSimulator.Core.Tile.TileColor;
import MPQSimulator.MPQCharacters.Punisher;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;


public class DestroySpecificTilesAbilityComponentTest {

  @Test
  public void testDestroy3x3EdgeTopLeftCorner() {
    Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.GREEN, TileColor.TEAMUP);

    String bstr = "R P B \n" +
                  "Y T P \n" +
                  "P R R \n";

    GameBoard board = GameBoardTest.createBoardFromString(bstr);
    GameEngine engine = new GameEngine(board);
    assertEquals(board.toString(), bstr); // board is unchanged
    
    Ability judgement = new Ability(new DestroySpecificTilesAbilityComponent(3, 3, true, new FixedSequenceRandomImpl(3, 0, 0)));
    // destroys whole board
    engine.useAbilityAndStabilizeBoard(judgement);
    String bstrAfter = "B G B \n" +
                       "U T P \n" +
                       "P R R \n";

    assertEquals(bstrAfter, board.toString()); 
    assertEquals(4, board.stats.getCountTilesDestroyed());
  }
  
  @Test
  public void testDestroy3x3EdgeRightMiddleTile() {
    Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.GREEN, TileColor.TEAMUP);

    String bstr = "R P B \n" +
                  "Y T P \n" +
                  "P R R \n";

    GameBoard board = GameBoardTest.createBoardFromString(bstr);
    GameEngine engine = new GameEngine(board);
    assertEquals(board.toString(), bstr); // board is unchanged
    
    Ability judgement = new Ability(new DestroySpecificTilesAbilityComponent(3, 3, true, new FixedSequenceRandomImpl(3, 1, 2)));
    // destroys whole board
    engine.useAbilityAndStabilizeBoard(judgement);
    String bstrAfter = "R B T \n" +
                       "Y U B \n" +
                       "P G U \n";

    assertEquals(bstrAfter, board.toString()); 
    assertEquals(6, board.stats.getCountTilesDestroyed());
  }
  
  @Test
  public void testDestroy3x3All() {
    Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.GREEN, TileColor.TEAMUP);

    String bstr = "R P B \n" +
                  "Y T P \n" +
                  "P R R \n";

    GameBoard board = GameBoardTest.createBoardFromString(bstr);
    GameEngine engine = new GameEngine(board);
    assertEquals(board.toString(), bstr); // board is unchanged
    
    Ability judgement = new Ability(new DestroySpecificTilesAbilityComponent(3, 3, true, new FixedSequenceRandomImpl(3, 1, 1)));
    // destroys whole board
    engine.useAbilityAndStabilizeBoard(judgement);
    String bstrAfter = "B T G \n" +
                       "U B T \n" +
                       "G U B \n";

    assertEquals(bstrAfter, board.toString()); 
    assertEquals(9, board.stats.getCountTilesDestroyed());
  }
  
  @Test 
  public void testDestroyXPatternAll() {
    Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.GREEN, TileColor.TEAMUP);

    String bstr = "R P B \n" +
                  "Y T P \n" +
                  "P R R \n";

    GameBoard board = GameBoardTest.createBoardFromString(bstr);
    GameEngine engine = new GameEngine(board);
    assertEquals(board.toString(), bstr); // board is unchanged
    
    boolean[][] pattern3x3 = { 
        { true, false, true },
        { false, true, false },
        { true, false, true }
    };
    
    Ability xforce = new Ability(new DestroySpecificTilesAbilityComponent(pattern3x3, true, new FixedSequenceRandomImpl(3, 1, 1)));
    // destroys whole board
    engine.useAbilityAndStabilizeBoard(xforce);
    String bstrAfter = "B G T \n" +
                       "U P B \n" +
                       "Y R P \n";

    assertEquals(bstrAfter, board.toString()); 
    assertEquals(5, board.stats.getCountTilesDestroyed());
  }
}

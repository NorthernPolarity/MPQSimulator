package MPQSimulatorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Abilities.DestroySpecificTilesAbilityComponent;
import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.GameEngine;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.FixedSequenceRandomImpl;
import MPQSimulator.Core.Tile.TileColor;

public class GameEngineTest {

	@Test
	public void testEngineBasics() {
		String bstr = "R G B \n" +
				      "Y T P \n" +
				      "G R R \n";
		GameBoard board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngine engine = new GameEngine(board);
		assertEquals(board.toString(), bstr); // board is unchanged
		
		engine.stabilizeBoard();
		assertEquals(board.toString(), bstr); // board is unchanged

		engine.resolveCurrentBoard();
		assertEquals(board.toString(), bstr); // board is unchanged

	}

	@Test
	public void testEngineOneMatch() {
		Tile.defaultRandomCaller = new FixedSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE);
		String bstr = "R G B \n" +
				      "Y T P \n" +
				      "R R R \n";
		GameBoard board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngine engine = new GameEngine(board);

		String bstr2 = "B U B \n" +
		               "R G B \n" +
				       "Y T P \n";
		
		assertEquals(bstr2, board.toString()); // board is stabilized
		
		engine.stabilizeBoard();
		assertEquals(board.toString(), bstr2); // board is unchanged

		engine.resolveCurrentBoard();
		assertEquals(board.toString(), bstr2); // board is unchanged

	}

	@Test
	public void testEngineTwoMatches() {
		Tile.defaultRandomCaller = new FixedSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE , TileColor.YELLOW);
		String bstr = "R T Y \n" +
				      "G G G \n" +
				      "R R R \n";
		GameBoard board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngine engine = new GameEngine(board);

		String bstr2 = "B Y U \n" +
		               "U B Y \n" +
				       "R T Y \n";
		
		assertEquals(bstr2, board.toString()); // board is stabilized
		
		engine.stabilizeBoard();
		assertEquals(board.toString(), bstr2); // board is unchanged

		engine.resolveCurrentBoard();
		assertEquals(board.toString(), bstr2); // board is unchanged

	}
	
	@Test
	public void testSwapComponentProcessNull() {
		String bstr = "B Y U \n" +
		              "U B Y \n" +
				      "R T Y \n";
		GameBoard board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngine engine = new GameEngine(board);
		AbilityComponent black = 
				new SwapTileAbilityComponent(0, 0, TileColor.BLACK);
		
		Ability level1 = new Ability();
		level1.addComponent(black);
		engine.useAbilityAndStabilizeBoard(level1);
		
		assertEquals(board.toString(), bstr); // board is unchanged, upper-left is already black

	}

	@Test
	public void testSwapComponentProcess() {
		String bstr = "Y Y U \n" +
		              "U B Y \n" +
				      "R T Y \n";
		GameBoard board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngine engine = new GameEngine(board);
		AbilityComponent black = 
				new SwapTileAbilityComponent(0, 0, TileColor.BLACK);
		
		Ability level1 = new Ability();
		level1.addComponent(black);
		
		assertEquals(TileColor.YELLOW, board.getTile(0, 0).getColor()); 

		engine.useAbilityAndStabilizeBoard(level1);
		
		assertEquals(TileColor.BLACK, board.getTile(0, 0).getColor()); 

	}
	
	@Test
	public void testXForcePattern() {
		Tile.defaultRandomCaller = new FixedSequenceRandomImpl(TileColor.PURPLE, TileColor.TEAMUP);

		String bstr = "Y Y U \n" +
		              "U B Y \n" +
				      "R Y Y \n";
		GameBoard board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngine engine = new GameEngine(board);
		boolean[][] pattern = {
				{ true, false, true },
				{ false, true, false },
				{ true, false, true }
			};
		DestroySpecificTilesAbilityComponent xf = new DestroySpecificTilesAbilityComponent( pattern );
		assertEquals(5, xf.numTilesToDestroy);
		
		Ability level1 = new Ability();
		level1.addComponent(xf);
		
		assertEquals(TileColor.YELLOW, board.getTile(0, 1).getColor()); 
		assertEquals(TileColor.BLUE, board.getTile(1, 0).getColor()); 

		engine.useAbilityAndStabilizeBoard(level1);
				
		assertEquals(TileColor.YELLOW, board.getTile(1, 1).getColor()); 
		assertEquals(TileColor.BLUE, board.getTile(2, 0).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(2, 1).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(2, 2).getColor()); 


	}
	
	
}

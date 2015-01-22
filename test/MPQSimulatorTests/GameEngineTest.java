package MPQSimulatorTests;

import static org.junit.Assert.*;

import org.junit.Test;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Abilities.DestroySpecificTilesAbilityComponent;
import MPQSimulator.Core.GameBoardImpl;
import MPQSimulator.Core.GameEngineImpl;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.FixedSequenceRandomImpl;
import MPQSimulator.Core.Tile.TileColor;

public class GameEngineTest {

	@Test
	public void testEngineBasics() {
		String bstr = "R G B \n" +
				      "Y T P \n" +
				      "G R R \n";
		GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngineImpl engine = new GameEngineImpl(board, false);
		assertEquals(board.toString(), bstr); // board is unchanged
		
		engine.stabilizeBoard();
		assertEquals(board.toString(), bstr); // board is unchanged

		engine.resolveCurrentBoard();
		assertEquals(board.toString(), bstr); // board is unchanged

	}

	@Test
	public void testEngineOneMatch() {
		Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE);
		String bstr = "R G B \n" +
				      "Y T P \n" +
				      "R R R \n";
		GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngineImpl engine = new GameEngineImpl(board, false);

		String bstr2 = "B U B \n" +
		               "R G B \n" +
				       "Y T P \n";
		
		engine.stabilizeBoard();
		assertEquals(bstr2, board.toString()); // board is stabilized
		
		engine.stabilizeBoard();
		assertEquals(board.toString(), bstr2); // board is unchanged

		engine.resolveCurrentBoard();
		assertEquals(board.toString(), bstr2); // board is unchanged
		
		assertEquals(3, board.stats.getCountTilesDestroyed());
	}

	@Test
	public void testEngineTwoMatches() {
		Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE , TileColor.YELLOW);
		String bstr = "R T Y \n" +
				      "G G G \n" +
				      "R R R \n";
		GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngineImpl engine = new GameEngineImpl(board, false);

		String bstr2 = "B Y U \n" +
		               "U B Y \n" +
				       "R T Y \n";
		engine.stabilizeBoard();
		assertEquals(bstr2, board.toString()); // board is stabilized
		
		engine.stabilizeBoard();
		assertEquals(board.toString(), bstr2); // board is unchanged

		engine.resolveCurrentBoard();
		assertEquals(board.toString(), bstr2); // board is unchanged
		
		assertEquals(6, board.stats.getCountTilesDestroyed());

	}
	
	@Test
	public void testSwapComponentProcessNull() {
		String bstr = "B Y U \n" +
		              "U B Y \n" +
				      "R T Y \n";
		GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngineImpl engine = new GameEngineImpl(board, false);
		AbilityComponent black = 
				new SwapTileAbilityComponent(0, 0, TileColor.BLACK);
		
		AbilityImpl level1 = new AbilityImpl();
		level1.addComponent(black);
		engine.useAbilityAndStabilizeBoard(level1);
		
		assertEquals(board.toString(), bstr); // board is unchanged, upper-left is already black

		
	}

	@Test
	public void testSwapComponentProcess() {
		String bstr = "Y Y U \n" +
		              "U B Y \n" +
				      "R T Y \n";
		GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngineImpl engine = new GameEngineImpl(board, false);
		AbilityComponent black = 
				new SwapTileAbilityComponent(0, 0, TileColor.BLACK);
		
		AbilityImpl level1 = new AbilityImpl();
		level1.addComponent(black);
		
		assertEquals(TileColor.YELLOW, board.getTile(0, 0).getColor()); 

		engine.useAbilityAndStabilizeBoard(level1);
		
		assertEquals(TileColor.BLACK, board.getTile(0, 0).getColor()); 

		assertEquals(0, board.stats.getCountTilesDestroyed(), 0);
		assertEquals(2, board.stats.getCountTileSwaps());


	}
	
	@Test
	public void testXForcePattern() {
		Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.PURPLE, TileColor.TEAMUP);

		String bstr = "Y Y U \n" +
		              "U B Y \n" +
				      "R Y Y \n";
		GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngineImpl engine = new GameEngineImpl(board, false);
		boolean[][] pattern = {
				{ true, false, true },
				{ false, true, false },
				{ true, false, true }
			};
		DestroySpecificTilesAbilityComponent xf = new DestroySpecificTilesAbilityComponent( pattern, false, new Tile.RandomCallerImpl());
		assertEquals(5, xf.numTilesToDestroy);
		
		AbilityImpl level1 = new AbilityImpl();
		level1.addComponent(xf);
		
		assertEquals(TileColor.YELLOW, board.getTile(0, 1).getColor()); 
		assertEquals(TileColor.BLUE, board.getTile(1, 0).getColor()); 

		engine.useAbilityAndStabilizeBoard(level1);
				
		assertEquals(TileColor.YELLOW, board.getTile(1, 1).getColor()); 
		assertEquals(TileColor.BLUE, board.getTile(2, 0).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(2, 1).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(2, 2).getColor()); 


	}

	@Test
	public void testBlockPattern2x3() {
		Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.PURPLE, TileColor.TEAMUP, TileColor.RED);

		String bstr = "B Y Y \n" +
		              "U B U \n" +
				      "B Y Y \n";
		GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngineImpl engine = new GameEngineImpl(board, false);
		DestroySpecificTilesAbilityComponent xf = new DestroySpecificTilesAbilityComponent(2,3, false, new Tile.RandomCallerImpl());
		assertEquals(6, xf.numTilesToDestroy);
		
		AbilityImpl level1 = new AbilityImpl();
		level1.addComponent(xf);
		
		assertEquals(TileColor.BLACK, board.getTile(0, 0).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(0, 1).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(0, 2).getColor()); 
		assertEquals(TileColor.BLACK, board.getTile(2, 0).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(2, 1).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(2, 2).getColor()); 

		engine.useAbilityAndStabilizeBoard(level1);
		System.out.println(board);
				
		// bottom row is BYY no matter how the random numbers work out
		assertEquals(TileColor.BLACK, board.getTile(2, 0).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(2, 1).getColor()); 
		assertEquals(TileColor.YELLOW, board.getTile(2, 2).getColor()); 


	}

	
}

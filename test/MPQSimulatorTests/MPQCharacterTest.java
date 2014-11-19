package MPQSimulatorTests;

import static org.junit.Assert.*;

import java.util.List;

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
import MPQSimulator.MPQCharacters.DakenClassic;
import MPQSimulator.MPQCharacters.JuggernautClassic;
import MPQSimulator.MPQCharacters.LokiDarkReign;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;
import MPQSimulator.MPQCharacters.ThorModern;
import MPQSimulator.MPQCharacters.WolverineXforce;


public class MPQCharacterTest {

	@Test
	public void testThorModern() {
		Tile.defaultRandomCaller = new FixedSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.GREEN, TileColor.TEAMUP);

		String bstr = "R P B \n" +
				      "Y T P \n" +
				      "P R R \n";
		GameBoard board = GameBoardTest.createBoardFromString(bstr);
		
		GameEngine engine = new GameEngine(board);
		assertEquals(board.toString(), bstr); // board is unchanged
		
		ThorModern thor = new ThorModern();
		Ability yellow = thor.getAbility2(AbilityLevel.FIVE);
		List<AbilityComponent> yellowComponents = yellow.getComponents();
		assertEquals(yellowComponents.size(), 1);
		
		// turns whole board GREEN, leading to new tiles dropping
		engine.useAbilityAndStabilizeBoard(yellow);
		String bstrAfter = "B T U \n" +
			               "U B G \n" +
			               "G T T \n";

		assertEquals(bstrAfter, board.toString()); 
		
	}
	
	@Test
	public void testDakenClassic() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			String bstr = "R P B \n" +
					      "U T U \n" +
					      "P R R \n";
			GameBoard board = GameBoardTest.createBoardFromString(bstr);
			
			GameEngine engine = new GameEngine(board);
			assertEquals(board.toString(), bstr); // board is unchanged
			
			DakenClassic daken = new DakenClassic();
			Ability twogreens = daken.getAbility3(al);
			List<AbilityComponent> yellowComponents = twogreens.getComponents();
			assertEquals(yellowComponents.size(), 1);
			
			// turns two tiles GREEN
			engine.useAbilityAndStabilizeBoard(twogreens);
			String bstrAfter = "R P B \n" +
				               "G T G \n" +
				               "P R R \n";
	
			assertEquals(bstrAfter, board.toString());
		}
		
	}

	@Test
	public void testJuggs() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			Tile.defaultRandomCaller = new FixedSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.GREEN, TileColor.TEAMUP);
			String bstr = "R P B \n" +
					      "U T U \n" +
					      "P R R \n";
			GameBoard board = GameBoardTest.createBoardFromString(bstr);
			
			GameEngine engine = new GameEngine(board);
			assertEquals(board.toString(), bstr); // board is unchanged
			
			JuggernautClassic juggs = new JuggernautClassic();
			Ability crash = juggs.getAbility2(al);
			
			// destroys the whole board
			engine.useAbilityAndStabilizeBoard(crash);
			String bstrAfter = "B T G \n" +
		                       "U B T \n" +
		                       "G U B \n";
	
			assertEquals(bstrAfter, board.toString());
		}
		
	}


	@Test
	public void testLoki() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			String bstr = "Y P B \n" +
					      "U T U \n" +
					      "P R R \n";
			GameBoard boardBefore = GameBoardTest.createBoardFromString(bstr);
			GameBoard board = GameBoardTest.createBoardFromString(bstr);
			
			GameEngine engine = new GameEngine(board);
			assertEquals(board.toString(), bstr); // board is unchanged
			
			LokiDarkReign loki = new LokiDarkReign();
			Ability illusions = loki.getAbility2(al);

			// there are not three of any color so should be no matches possible
			engine.useAbilityAndStabilizeBoard(illusions);
			for(TileColor c : TileColor.values()) {
				assertEquals(boardBefore.getTiles(c).size(), board.getTiles(c).size());
			}
		}
		
	}


	@Test
	public void testXForce() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			Tile.defaultRandomCaller = new FixedSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.TEAMUP);
			String bstr = "Y P B Y T \n" +
					      "U G U U Y \n" +
					      "P R R P R \n" +
					      "R P B U P \n" +
					      "U B P Y T \n";
			GameBoard board = GameBoardTest.createBoardFromString(bstr);
			
			GameEngine engine = new GameEngine(board);
			assertEquals(board.toString(), bstr); // board is unchanged
			
			WolverineXforce xf = new WolverineXforce();
			Ability rage = xf.getAbility1(al);
			Ability surgical = xf.getAbility2(al);

			// destroy just the one GREEN tile
			assertEquals(board.getTiles(TileColor.GREEN).size(), 1);
			engine.useAbilityAndStabilizeBoard(surgical);
			assertEquals(board.getTiles(TileColor.GREEN).size(), 0);
			
			engine.useAbilityAndStabilizeBoard(rage);
			
			// TODO: not sure what to assert here to make sure it worked properly
			assertEquals(board.getTiles(TileColor.GREEN).size(), 0);


		}
		
	}


}


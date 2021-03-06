package MPQSimulatorTests;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Abilities.AbilityComponent;
import MPQSimulator.Abilities.SwapTileAbilityComponent;
import MPQSimulator.Abilities.DestroySpecificTilesAbilityComponent;
import MPQSimulator.Core.GameBoardImpl;
import MPQSimulator.Core.GameBoardMoveResultsImpl;
import MPQSimulator.Core.GameEngineImpl;
import MPQSimulator.Core.GameEngineMoveResults;
import MPQSimulator.Core.GameEngineMoveResultsImpl;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.FixedSequenceRandomImpl;
import MPQSimulator.Core.Tile.TileColor;
import MPQSimulator.MPQCharacters.DakenClassic;
import MPQSimulator.MPQCharacters.DevilDino;
import MPQSimulator.MPQCharacters.JuggernautClassic;
import MPQSimulator.MPQCharacters.LokiDarkReign;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;
import MPQSimulator.MPQCharacters.MagnetoClassic;
import MPQSimulator.MPQCharacters.Mystique;
import MPQSimulator.MPQCharacters.Punisher;
import MPQSimulator.MPQCharacters.StormMohawk;
import MPQSimulator.MPQCharacters.ThorModern;
import MPQSimulator.MPQCharacters.TorchClassic;
import MPQSimulator.MPQCharacters.WolverineXforce;


public class MPQCharacterTest {

    @Test
    public void testThorModern() {
        Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.GREEN, TileColor.TEAMUP);
  
        String bstr = "R P B \n" +
                      "Y T P \n" +
                      "P R R \n";
        GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
        
        GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
        assertEquals(board.toString(), bstr); // board is unchanged
        
        ThorModern thor = new ThorModern();
        AbilityImpl yellow = thor.getAbility2(AbilityLevel.FIVE);
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
			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
			
			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			DakenClassic daken = new DakenClassic();
			AbilityImpl twogreens = daken.getAbility3(al);
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
			Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.GREEN, TileColor.TEAMUP);
			String bstr = "R P B \n" +
					      "U T U \n" +
					      "P R R \n";
			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
			
			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			JuggernautClassic juggs = new JuggernautClassic();
			AbilityImpl crash = juggs.getAbility2(al);
			
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
			GameBoardImpl boardBefore = GameBoardTest.createBoardFromString(bstr);
			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
			
			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			LokiDarkReign loki = new LokiDarkReign();
			AbilityImpl illusions = loki.getAbility2(al);

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
			Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.TEAMUP);
			String bstr = "Y P B Y T \n" +
					      "U G U U Y \n" +
					      "P R R P R \n" +
					      "R P B U P \n" +
					      "U B P Y T \n";
			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
			
			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			WolverineXforce xf = new WolverineXforce();
			AbilityImpl rage = xf.getAbility1(al);
			AbilityImpl surgical = xf.getAbility2(al);

			// destroy just the one GREEN tile
			assertEquals(board.getTiles(TileColor.GREEN).size(), 1);
			engine.useAbilityAndStabilizeBoard(surgical);
			assertEquals(board.getTiles(TileColor.GREEN).size(), 0);
			
			engine.useAbilityAndStabilizeBoard(rage);
			
			// TODO: not sure what to assert here to make sure it worked properly
			assertEquals(board.getTiles(TileColor.GREEN).size(), 0);


		}
		
	}


	@Test
	public void testDino() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			String bstr = "Y P B \n" +
				          "U T U \n" +
				          "P R G \n";
			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
			
			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			DevilDino dino = new DevilDino();
			AbilityImpl swap = dino.getAbility3(al);

			assertEquals(board.getTile(0,0).getColor(), TileColor.YELLOW);
			engine.useAbilityAndStabilizeBoard(swap);
			assertEquals(board.getTile(0,0).getColor(), TileColor.BLACK);

			if(al.ordinal() > AbilityLevel.ONE.ordinal()) {
				assertEquals(TileColor.RED, board.getTile(1,1).getColor());
			}

			if(al.ordinal() > AbilityLevel.TWO.ordinal()) {
				assertEquals( TileColor.GREEN, board.getTile(2,1).getColor());
			}

			if(al.ordinal() > AbilityLevel.THREE.ordinal()) {
				assertEquals( TileColor.YELLOW, board.getTile(0,2).getColor());
			}

			if(al.ordinal() > AbilityLevel.FOUR.ordinal()) {
				assertEquals( TileColor.PURPLE, board.getTile(1,2).getColor());
				assertEquals( TileColor.BLUE, board.getTile(2,2).getColor());
			}



		}
		
	}
	

	@Test
	public void testMagnetoClassic() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.YELLOW);
			String bstr = "Y P B Y T \n" +
					      "U G U U Y \n" +
					      "P R R P R \n" +
					      "R P B U P \n" +
					      "U R Y Y T \n";
			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
			assertEquals( TileColor.BLUE, board.getTile(1,0).getColor());

			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			MagnetoClassic mags = new MagnetoClassic();
			AbilityImpl polar = mags.getAbility2(al);
			AbilityImpl projectiles = mags.getAbility3(al);

			assertEquals(2, board.getTiles(TileColor.TEAMUP).size());
			engine.useAbilityAndStabilizeBoard(polar);			
			assertEquals(0, board.getTiles(TileColor.TEAMUP).size());

			assertEquals(5, board.getTiles(TileColor.BLUE).size());
			assertEquals(5, board.getTiles(TileColor.RED).size());

			engine.useAbilityAndStabilizeBoard(projectiles);

			// TODO: not sure what else to assert here
			assertEquals( TileColor.RED, board.getTile(1,0).getColor());
			assertEquals(5, board.getTiles(TileColor.BLUE).size());
			assertEquals(5, board.getTiles(TileColor.RED).size());

			assertEquals( TileColor.YELLOW, board.getTile(0,0).getColor());


		}
		
	}
	
	@Test
	public void testMystique() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLUE, TileColor.YELLOW);
			String bstr = "Y R G Y T \n" +
					      "U G U U Y \n" +
					      "G R R G R \n" +
					      "G T G U R \n" +
					      "U G Y Y T \n";
			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);

			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			Mystique mystique = new Mystique();
			AbilityImpl changeColors = mystique.getAbility2(al);

			assertEquals(0, board.getTiles(TileColor.BLACK).size());
			assertEquals(0, board.getTiles(TileColor.PURPLE).size());
			engine.useAbilityAndStabilizeBoard(changeColors);			
			assertNotEquals(0, board.getTilesOfColors(TileColor.BLACK, TileColor.PURPLE).size());
			assertEquals(3, board.getTiles(TileColor.TEAMUP).size());

			// TODO: assert something more interesting here

		}
		
	}
	


	@Test
	public void testStormMohawk() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.YELLOW, TileColor.RED, TileColor.GREEN);
			String bstr = "Y P B Y T \n" +
					      "U G U U Y \n" +
					      "P R T P R \n" +
					      "R G T U P \n" +
					      "T R Y Y T \n";
			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			StormMohawk mohawk = new StormMohawk();
			AbilityImpl lightning = mohawk.getAbility1(al);
			AbilityImpl mistress = mohawk.getAbility2(al);

			assertEquals(5, board.getTiles(TileColor.TEAMUP).size());
			engine.useAbilityAndStabilizeBoard(mistress);			
			assertEquals(0, board.getTiles(TileColor.TEAMUP).size());

			engine.useAbilityAndStabilizeBoard(lightning);

			// TODO: not sure what else to assert here

		}
		
	}
	

	@Test
	public void testTorchClassic() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.YELLOW, TileColor.GREEN);
			String bstr = "Y P B Y T \n" +
					      "U G U U Y \n" +
					      "P R T P R \n" +
					      "R G T U P \n" +
					      "T R Y Y T \n";
			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			TorchClassic torch = new TorchClassic();
			AbilityImpl fireball = torch.getAbility1(al);

			assertEquals(4, board.getTiles(TileColor.RED).size());
			GameEngineMoveResults cascades = engine.useAbilityAndStabilizeBoard(fireball);
			assertEquals(2, board.getTiles(TileColor.RED).size());
			assertEquals(0, cascades.getNumTilesDestroyed());

			assertEquals(2, board.stats.getCountTilesDestroyed());

		}
		
	}
	
	// Should be broken: we need to move characters into ability component tests.
	@Test
	public void testPunisher() {
		for( AbilityLevel al : AbilityLevel.values() ) {
			Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE, TileColor.GREEN, TileColor.TEAMUP);

			String bstr = "R P B \n" +
					      "Y T P \n" +
					      "P R R \n";

			GameBoardImpl board = GameBoardTest.createBoardFromString(bstr);
			GameEngineImpl engine = new GameEngineImpl(board, false, new TestUtilities.GameEngineMoveResultsProvider(), new TestUtilities.GameBoardMatchesFactoryImpl());
			assertEquals(board.toString(), bstr); // board is unchanged
			
			Punisher punisher = new Punisher();
			AbilityImpl judgement = punisher.getAbility2(al);
			// destroys whole board
			engine.useAbilityAndStabilizeBoard(judgement);
			String bstrAfter = "B T G \n" +
				               "U B T \n" +
				               "G U B \n";

			assertEquals(bstrAfter, board.toString()); 
			assertEquals(9, board.stats.getCountTilesDestroyed());
		}
	}
	

	
}


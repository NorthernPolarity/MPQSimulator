package MPQSimulatorTests;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Set;

import org.junit.Test;

import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.GameBoardMatches;
import MPQSimulator.Core.GameBoardMoveResults;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.FixedSequenceRandomImpl;
import MPQSimulator.Core.Tile.TileColor;

import com.google.common.collect.Lists;

public class GameBoardMatchesTest {

	  @Test 
	  public void testVerticalDrop() {
		  Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE);

		  String test = 
				    "Y Y B G\n"
				  + "T R G T\n"
				  + "T R G T\n"
				  + "R R P Y";
		GameBoard board = GameBoardTest.createBoardFromString(test);
		
	    List<Tile> expectedCol1 = Lists.newArrayList();
	    expectedCol1.add(new Tile(0, 0, TileColor.YELLOW));
	    expectedCol1.add(new Tile(1, 0, TileColor.TEAMUP));
	    expectedCol1.add(new Tile(2, 0, TileColor.TEAMUP));
	    expectedCol1.add(new Tile(3, 0, TileColor.RED));
	    assertEquals(expectedCol1, board.getTilesInCol(0));

	    List<Tile> expectedCol2 = Lists.newArrayList();
	    expectedCol2.add(new Tile(0, 1, TileColor.YELLOW));
	    expectedCol2.add(new Tile(1, 1, TileColor.RED));
	    expectedCol2.add(new Tile(2, 1, TileColor.RED));
	    expectedCol2.add(new Tile(3, 1, TileColor.RED));
	    assertEquals(expectedCol2, board.getTilesInCol(1));

	    GameBoardMatches policy = new GameBoardMatches(board);
	    GameBoardMoveResults results = policy.findMatchesOnBoard();
	    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
	    board.destroyTiles(destroyedTileSet);
	    
	    List<Tile> expectedRow4After = Lists.newArrayList();
	    expectedRow4After.add(new Tile(3, 0, TileColor.RED));
	    expectedRow4After.add(new Tile(3, 1, TileColor.YELLOW));
	    expectedRow4After.add(new Tile(3, 2, TileColor.PURPLE));
	    expectedRow4After.add(new Tile(3, 3, TileColor.YELLOW));
	    assertEquals(expectedRow4After, board.getTilesInRow(3));

	    List<Tile> expectedCol2After = Lists.newArrayList();
	    expectedCol2After.add(new Tile(0, 1, TileColor.BLACK));
	    expectedCol2After.add(new Tile(1, 1, TileColor.BLUE));
	    expectedCol2After.add(new Tile(2, 1, TileColor.BLACK));
	    expectedCol2After.add(new Tile(3, 1, TileColor.YELLOW));
	    assertEquals(expectedCol2After, board.getTilesInCol(1));

	}
}

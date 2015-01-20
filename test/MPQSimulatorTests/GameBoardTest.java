package MPQSimulatorTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.GameBoardMatches;
import MPQSimulator.Core.GameBoardMoveResults;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;
import MPQSimulator.Core.Tile.FixedSequenceRandomImpl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

public class GameBoardTest {
  private static Map<String, TileColor> fileCharToTileColor = ImmutableMap.<String, TileColor>builder()
      .put("B", TileColor.BLACK)
      .put("U", TileColor.BLUE)
      .put("R", TileColor.RED)
      .put("G", TileColor.GREEN)
      .put("Y", TileColor.YELLOW)
      .put("P", TileColor.PURPLE)
      .put("T", TileColor.TEAMUP)
      .build();
  
  @Test 
  public void testTestFile() throws IOException {
    GameBoard board = createBoardFromFile("test/MPQSimulatorTests/res/test.txt");
    GameBoardMatches matches = new GameBoardMatches(board);
    Set<Tile> destroyedTileSet = matches.getAllMatchedTiles();
    printDestroyedTiles(destroyedTileSet, board.getDimensions());
  }
  

  @Test 
  public void testBoardConstruction() {
	  String test = 
			    "R G B \n"
			  + "B R G \n"
			  + "G B R \n";
	GameBoard board = createBoardFromString(test);
	assertEquals(board.getTileColor(0, 0), TileColor.RED);
	assertEquals(board.getTileColor(0, 1), TileColor.GREEN);
	assertEquals(board.getTileColor(0, 2), TileColor.BLACK);
	assertEquals(board.getTileColor(1, 0), TileColor.BLACK);
	assertEquals(board.getTileColor(1, 1), TileColor.RED);
	assertEquals(board.getTileColor(1, 2), TileColor.GREEN);
	assertEquals(board.getTileColor(2, 0), TileColor.GREEN);
	assertEquals(board.getTileColor(2, 1), TileColor.BLACK);
	assertEquals(board.getTileColor(2, 2), TileColor.RED);
	assertEquals(board.toString(), test);
  }

  @Test 
  public void testGetTilesOfSingleColorNotInBoardReturnsEmpty() {
    String test = 
			    "R T G\n"
			  + "Y G T\n"
			  + "G T G";
	GameBoard board = createBoardFromString(test);
	Set<Tile> expected = new HashSet<>();
    assertEquals(board.getTiles(TileColor.BLUE), expected);
  }
  
  @Test 
  public void testGetTilesOfSingleColorInBoardReturnsSingleTile() {
    String test = 
                "R T G\n"
              + "Y G T\n"
              + "G T G";
    GameBoard board = createBoardFromString(test);
    Set<Tile> expected = new HashSet<>();
    expected.add(new Tile(0,0,TileColor.RED));
    assertEquals(board.getTiles(TileColor.RED), expected);
  }
  
  @Test 
  public void testGetTilesOfSingleColorInBoardReturnsThoseTiles() {
    String test = 
        "R T G\n"
      + "Y G T\n"
      + "G T R";
    GameBoard board = createBoardFromString(test);
    Set<Tile> expected = new HashSet<>();
    expected.add(new Tile(0,0,TileColor.RED));
    expected.add(new Tile(2,2,TileColor.RED));
    assertEquals(board.getTiles(TileColor.RED), expected);
  }
  
  @Test 
  public void testGetTilesOfMultipleColorsInBoardReturnsThoseTiles() {
    String test = 
        "R T G\n"
      + "Y G T\n"
      + "G U R";
    GameBoard board = createBoardFromString(test);
    Set<Tile> expected = new HashSet<>();
    expected.add(new Tile(0,0,TileColor.RED));
    expected.add(new Tile(2,2,TileColor.RED));
    expected.add(new Tile(2,1,TileColor.BLUE));
    assertEquals(board.getTiles(ImmutableList.of(TileColor.RED, TileColor.BLUE)), expected);
  }
  
  @Test 
  public void testChangeColorOfSingleTileToSingleColor() {
	  String test = 
			    "U T G\n"
			  + "Y G T\n"
			  + "G T G";
	GameBoard board = createBoardFromString(test);
	Set<Tile> expected = new HashSet<>();
    assertEquals(board.getTiles(TileColor.RED), expected);

    // no red, yet
    ImmutableList<TileColor> newColors = ImmutableList.of(TileColor.RED);
    board.changeTileColor(board.getTiles(TileColor.BLUE), newColors);
    // change one blue to red
    
    expected.add(new Tile(0,0,TileColor.RED));
    assertEquals(board.getTiles(TileColor.RED), expected);
  }
  
  @Test 
  public void testChangeColorOfMultipleTilesToSingleColor() {
    String test = 
                "U T G\n"
              + "Y U T\n"
              + "G T G";
    GameBoard board = createBoardFromString(test);
    Set<Tile> expected = new HashSet<>();
    assertEquals(board.getTiles(TileColor.RED), expected);

    // no red, yet
    ImmutableList<TileColor> newColors = ImmutableList.of(TileColor.RED);
    board.changeTileColor(board.getTiles(TileColor.BLUE), newColors);
    // change one blue to red
    
    expected.add(new Tile(0,0,TileColor.RED));
    expected.add(new Tile(1,1,TileColor.RED));
    assertEquals(board.getTiles(TileColor.RED), expected);
  }
  
  @Test
  public void testSwapTwoTiles() {
    String test = 
        "U T G\n"
      + "Y U T\n"
      + "G T G";
    GameBoard board = createBoardFromString(test);
    Tile a = board.getTile(0, 0);
    Tile b = board.getTile(2, 2);
    board.swapTiles(a, b);
    
    assertEquals(a, board.getTile(2,2));
    assertEquals(b, board.getTile(0,0));
    
  }
  
 // #TODO: test changing to a set of colors
 // TODO: test the destroy tile function.

  public static GameBoard createBoardFromString(String inputString) {
  	ImmutableList<String> rows = ImmutableList.copyOf(inputString.split("\n"));	  
      GameBoard board = createBoardFromStringRows(rows);
  	return board;
  }

  
  private static void printDestroyedTiles(Set<Tile> destroyedTiles, int[] dimensions) {
	  assertEquals(dimensions[0], dimensions[1]);
    boolean[][] destroyedTileMap = new boolean[dimensions[0]][dimensions[1]];
    for (Tile t : destroyedTiles) {
      destroyedTileMap[t.getRow()][t.getCol()] = true;
    }
    
    for (int i = 0; i < dimensions[0]; i++) {
      for (int j = 0; j < dimensions[1]; j++) {
        if (destroyedTileMap[i][j]) {
          System.out.print("X ");
        } else {
          System.out.print("O ");
        }
        
      }
      System.out.println();
    }
  }
  // Assumes that the game board is N by N.
  private static GameBoard createBoardFromFile(String filePath) throws IOException {
    CharSource fileSource = Files.asCharSource(new File(filePath), Charset.defaultCharset());
    ImmutableList<String> rows = fileSource.readLines();
    return createBoardFromStringRows(rows);
  }

  private static GameBoard createBoardFromStringRows(ImmutableList<String> rows) {
	Tile[][] initialBoard = new Tile[rows.size()][rows.size()];
    
    for (int i = 0; i < rows.size(); i++) {
      String[] tileColors = rows.get(i).split(" ");
      
      // Make sure that all the columns are the same size.
      assert (tileColors.length == rows.size());
      
      for (int j = 0; j < tileColors.length; j++) {
        
        TileColor tileColor = fileCharToTileColor.get(tileColors[j]);
        initialBoard[i][j] = new Tile(i, j, tileColor);
      }
    }
    return new GameBoard(rows.size(), rows.size(), initialBoard);
}
}

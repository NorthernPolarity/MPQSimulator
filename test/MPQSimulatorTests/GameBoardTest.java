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
import MPQSimulator.Core.GameBoardMoveResults;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;

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
  public void testSingleHorizontalMatch() throws IOException {
    GameBoard board = createBoardFromFile("test/MPQSimulatorTests/res/OneHorizontalMatch3Board3x3.txt");
    GameBoardMoveResults results = board.findMatchesOnBoard();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(2, 0, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 1, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 2, TileColor.RED));
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assertTrue(expectedDestroyedTileSet.equals(destroyedTileSet));
    board.destroyTiles(destroyedTileSet);
    
  }
  
  @Test
  public void testSingleHorizontalFourInRowMatch() throws IOException {
    GameBoard board = createBoardFromFile("test/MPQSimulatorTests/res/FourInARowBoard5x5.txt");
    GameBoardMoveResults results = board.findMatchesOnBoard();
    
    assertEquals(board.getDimensions()[0], 5);
    assertEquals(board.getDimensions()[1], 5);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(2, 0, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 1, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 2, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 3, TileColor.RED));
//    expectedDestroyedTileSet.add(new Tile(2, 4, TileColor.RED));
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    board.destroyTiles(destroyedTileSet);
    printDestroyedTiles(destroyedTileSet, board.getDimensions());
    
  }
  
  @Test 
  public void testTestFile() throws IOException {
    GameBoard board = createBoardFromFile("test/MPQSimulatorTests/res/test.txt");
    GameBoardMoveResults results = board.findMatchesOnBoard();
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
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
  public void testNoMatches() {
	  String test = 
			    "R G B\n"
			  + "B R G\n"
			  + "G B R";
	GameBoard board = createBoardFromString(test);
    GameBoardMoveResults results = board.findMatchesOnBoard();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    assertTrue(results.empty());

  }
  
  @Test 
  public void testVerticalMatch() {
	  String test = 
			    "R G B\n"
			  + "R R G\n"
			  + "R B R";
	GameBoard board = createBoardFromString(test);
    GameBoardMoveResults results = board.findVerticalMatches();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(0, 0, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(1, 0, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 0, TileColor.RED));
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    board.destroyTiles(destroyedTileSet);
    printDestroyedTiles(destroyedTileSet, board.getDimensions());
    
  }

  @Test 
  public void testHorizontalMatch() {
	  String test = 
			    "T T T\n"
			  + "Y R G\n"
			  + "T B R";
	GameBoard board = createBoardFromString(test);
    GameBoardMoveResults results = board.findHorizontalMatches();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(0, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    board.destroyTiles(destroyedTileSet);
    printDestroyedTiles(destroyedTileSet, board.getDimensions());
    
    
  }
  
  @Test 
  public void testDoubleMatchLPattern() {
	  String test = 
			    "T T T\n"
			  + "T R G\n"
			  + "T B R";
	GameBoard board = createBoardFromString(test);
    GameBoardMoveResults results = board.findHorizontalMatches();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(0, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    

    GameBoardMoveResults results2 = board.findVerticalMatches();
    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    expectedDestroyedTileSet2.add(new Tile(0, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 0, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet2 = results2.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet2, destroyedTileSet2);
    
    results = board.findMatchesOnBoard();
    Set<Tile> destroyedTileSet3 = results.getDestroyedTileSet();
    printDestroyedTiles(destroyedTileSet3, board.getDimensions());
    assertEquals(destroyedTileSet3.size(), 5);
  }
  
  @Test 
  public void testDoubleMatchCrossPattern() {
	  String test = 
			    "G T G\n"
			  + "T T T\n"
			  + "B T R";
	GameBoard board = createBoardFromString(test);
    GameBoardMoveResults results = board.findHorizontalMatches();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(1, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(1, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(1, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    

    GameBoardMoveResults results2 = board.findVerticalMatches();
    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    expectedDestroyedTileSet2.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 1, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet2 = results2.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet2, destroyedTileSet2);
    
    results = board.findMatchesOnBoard();
    Set<Tile> destroyedTileSet3 = results.getDestroyedTileSet();
    printDestroyedTiles(destroyedTileSet3, board.getDimensions());
    assertEquals(destroyedTileSet3.size(), 5);
  }
  
  @Test 
  public void testDoubleMatchSixTotal() {
	  String test = 
			    "G T T\n"
			  + "Y T T\n"
			  + "B T T";
	GameBoard board = createBoardFromString(test);
    GameBoardMoveResults results = board.findHorizontalMatches();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    

    GameBoardMoveResults results2 = board.findVerticalMatches();
    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    expectedDestroyedTileSet2.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(0, 2, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 2, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet2 = results2.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet2, destroyedTileSet2);
    
    results = board.findMatchesOnBoard();
    Set<Tile> destroyedTileSet3 = results.getDestroyedTileSet();
    printDestroyedTiles(destroyedTileSet3, board.getDimensions());
    assertEquals(destroyedTileSet3.size(), 6);
  }

  @Test 
  public void testDoubleMatchSevenTotal() {
	  String test = 
			    "G T T\n"
			  + "Y T T\n"
			  + "T T T";
	GameBoard board = createBoardFromString(test);
    GameBoardMoveResults results = board.findHorizontalMatches();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(2, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(2, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(2, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    

    GameBoardMoveResults results2 = board.findVerticalMatches();
    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    expectedDestroyedTileSet2.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(0, 2, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 2, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet2 = results2.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet2, destroyedTileSet2);
    
    results = board.findMatchesOnBoard();
    Set<Tile> destroyedTileSet3 = results.getDestroyedTileSet();
    printDestroyedTiles(destroyedTileSet3, board.getDimensions());
    assertEquals(destroyedTileSet3.size(), 7);
  }
  
  @Test 
  public void testXPatternNoMatch() {
	  String test = 
			    "G T G\n"
			  + "Y G T\n"
			  + "G T G";
	GameBoard board = createBoardFromString(test);
    GameBoardMoveResults results = board.findHorizontalMatches();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    

    GameBoardMoveResults results2 = board.findVerticalMatches();
    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    Set<Tile> destroyedTileSet2 = results2.getDestroyedTileSet();
    assertEquals(expectedDestroyedTileSet2, destroyedTileSet2);
    
    results = board.findMatchesOnBoard();
    Set<Tile> destroyedTileSet3 = results.getDestroyedTileSet();
    printDestroyedTiles(destroyedTileSet3, board.getDimensions());
    assertEquals(destroyedTileSet3.size(), 0);
  }

  @Test 
  public void testColorMethods() {
	  String test = 
			    "R T G\n"
			  + "Y G T\n"
			  + "G T G";
	GameBoard board = createBoardFromString(test);
	Set<Tile> expected = new HashSet<>();
    assertEquals(board.getTiles(TileColor.BLUE), expected);
    expected.add(new Tile(0,0,TileColor.RED));
    assertEquals(board.getTiles(TileColor.RED), expected);

    ImmutableList<TileColor> colors = ImmutableList.of(TileColor.RED, TileColor.BLUE);
    assertEquals(board.getTiles(colors), expected);
  }
  
  @Test 
  public void testChangeColor() {
	  String test = 
			    "U T G\n"
			  + "Y G T\n"
			  + "G T G";
	GameBoard board = createBoardFromString(test);
	Set<Tile> expected = new HashSet<>();
    assertEquals(board.getTiles(TileColor.RED), expected);

    // no red, yet
    ImmutableList<TileColor> newColors = ImmutableList.of(TileColor.RED, TileColor.RED);
    board.changeTileColor(board.getTiles(TileColor.BLUE), newColors);
    // change one blue to red
    
    expected.add(new Tile(0,0,TileColor.RED));
    assertEquals(board.getTiles(TileColor.RED), expected);

    // only one red or blue now
    ImmutableList<TileColor> colors = ImmutableList.of(TileColor.RED, TileColor.BLUE);
    assertEquals(board.getTiles(colors), expected);
  }
  
  @Test 
  public void testHorizontalDrop() {
	  String test = 
			    "Y G B\n"
			  + "T R G\n"
			  + "R R R";
	GameBoard board = createBoardFromString(test);
	
    List<Tile> expectedRow1 = Lists.newArrayList();
    expectedRow1.add(new Tile(0, 0, TileColor.YELLOW));
    expectedRow1.add(new Tile(0, 1, TileColor.GREEN));
    expectedRow1.add(new Tile(0, 2, TileColor.BLACK));
    assertEquals(expectedRow1, board.getTilesInRow(0));

    List<Tile> expectedRow2 = Lists.newArrayList();
    expectedRow2.add(new Tile(1, 0, TileColor.TEAMUP));
    expectedRow2.add(new Tile(1, 1, TileColor.RED));
    expectedRow2.add(new Tile(1, 2, TileColor.GREEN));
    assertEquals(expectedRow2, board.getTilesInRow(1));

    List<Tile> expectedRow3 = Lists.newArrayList();
    expectedRow3.add(new Tile(2, 0, TileColor.RED));
    expectedRow3.add(new Tile(2, 1, TileColor.RED));
    expectedRow3.add(new Tile(2, 2, TileColor.RED));
    assertEquals(expectedRow3, board.getTilesInRow(2));

    GameBoardMoveResults results = board.findMatchesOnBoard();
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    board.destroyTiles(destroyedTileSet);
    
    List<Tile> expectedRow2After = Lists.newArrayList();
    expectedRow2After.add(new Tile(2, 0, TileColor.TEAMUP));
    expectedRow2After.add(new Tile(2, 1, TileColor.RED));
    expectedRow2After.add(new Tile(2, 2, TileColor.GREEN));
    System.out.println(board);
    assertEquals(expectedRow2After, board.getTilesInRow(2));
  
}
  
  @Test 
  public void testHorizontalDrop2() {
	  String test = 
			    "Y G B\n"
			  + "R R R\n"
			  + "T R G";
	GameBoard board = createBoardFromString(test);
	
    List<Tile> expectedRow1 = Lists.newArrayList();
    expectedRow1.add(new Tile(0, 0, TileColor.YELLOW));
    expectedRow1.add(new Tile(0, 1, TileColor.GREEN));
    expectedRow1.add(new Tile(0, 2, TileColor.BLACK));
    assertEquals(expectedRow1, board.getTilesInRow(0));

    List<Tile> expectedRow2 = Lists.newArrayList();
    expectedRow2.add(new Tile(1, 0, TileColor.RED));
    expectedRow2.add(new Tile(1, 1, TileColor.RED));
    expectedRow2.add(new Tile(1, 2, TileColor.RED));
    assertEquals(expectedRow2, board.getTilesInRow(1));

    List<Tile> expectedRow3 = Lists.newArrayList();
    expectedRow3.add(new Tile(2, 0, TileColor.TEAMUP));
    expectedRow3.add(new Tile(2, 1, TileColor.RED));
    expectedRow3.add(new Tile(2, 2, TileColor.GREEN));
    assertEquals(expectedRow3, board.getTilesInRow(2));

    GameBoardMoveResults results = board.findMatchesOnBoard();
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    board.destroyTiles(destroyedTileSet);
    
    List<Tile> expectedRow2After = Lists.newArrayList();
    expectedRow2After.add(new Tile(1, 0, TileColor.YELLOW));
    expectedRow2After.add(new Tile(1, 1, TileColor.GREEN));
    expectedRow2After.add(new Tile(1, 2, TileColor.BLACK));
    System.out.println(board);
    assertEquals(expectedRow2After, board.getTilesInRow(1));
  
}
  

  

private GameBoard createBoardFromString(String inputString) {
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

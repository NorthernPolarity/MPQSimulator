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

import MPQSimulator.Core.GameBoardImpl;
import MPQSimulator.Core.GameBoardMatchesImpl;
import MPQSimulator.Core.GameBoardMoveResultsImpl;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.GameBoardMoveResultsImpl.MatchedTileBlob;
import MPQSimulator.Core.Tile.TileColor;
import MPQSimulator.Core.Tile.FixedSequenceRandomImpl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.io.CharSource;
import com.google.common.io.Files;

public class GameBoardMatchesTest {
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
    GameBoardImpl board = createBoardFromFile("test/MPQSimulatorTests/res/OneHorizontalMatch3Board3x3.txt");
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(2, 0, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 1, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 2, TileColor.RED));
    Set<Tile> destroyedTileSet = matches.getAllMatchedTiles();
    assertTrue(expectedDestroyedTileSet.equals(destroyedTileSet));
    board.destroyTiles(destroyedTileSet);
    
  }
  
  @Test
  public void testSingleHorizontalFourInRowMatch() throws IOException {
    GameBoardImpl board = createBoardFromFile("test/MPQSimulatorTests/res/FourInARowBoard5x5.txt");
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    
    assertEquals(board.getDimensions()[0], 5);
    assertEquals(board.getDimensions()[1], 5);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(2, 0, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 1, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 2, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 3, TileColor.RED));
    Set<Tile> destroyedTileSet = matches.getAllMatchedTiles();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    board.destroyTiles(destroyedTileSet);
    printDestroyedTiles(destroyedTileSet, board.getDimensions());
    
  }
  
  @Test 
  public void testTestFile() throws IOException {
    GameBoardImpl board = createBoardFromFile("test/MPQSimulatorTests/res/test.txt");
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> destroyedTileSet = matches.getAllMatchedTiles();
    printDestroyedTiles(destroyedTileSet, board.getDimensions());
  }
  

  @Test 
  public void testBoardConstruction() {
      String test = 
                "R G B \n"
              + "B R G \n"
              + "G B R \n";
    GameBoardImpl board = createBoardFromString(test);
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
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    assertTrue(matches.getAllMatchedTiles().isEmpty());

  }
  
  @Test 
  public void testVerticalMatch() {
      String test = 
                "R G B\n"
              + "R R G\n"
              + "R B R";
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(0, 0, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(1, 0, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(2, 0, TileColor.RED));
    Set<Tile> destroyedTileSet = matches.getVerticalMatchedTiles();
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
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(0, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet = matches.getHorizontalMatchedTiles();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    board.destroyTiles(destroyedTileSet);
    printDestroyedTiles(destroyedTileSet, board.getDimensions());
    
    
  }
  
  @Test 
  public void testHorizontalMatch4() {
      String test = 
                "T T T T G\n"
              + "Y R G G R\n"
              + "Y R G G B\n"
              + "T B R Y U\n"
              + "T B R Y U";
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(0, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 2, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 3, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet = matches.getHorizontalMatchedTiles();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    board.destroyTiles(destroyedTileSet);
    printDestroyedTiles(destroyedTileSet, board.getDimensions());
  }
  
  

  @Test 
  public void testVerticalMatch4() {
      String test = 
                "T P B U G\n"
              + "T R G G R\n"
              + "T R G G B\n"
              + "T B R Y U\n"
              + "G B R Y U";
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(0, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(1, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(2, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(3, 0, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet = matches.getVerticalMatchedTiles();
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
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(0, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(0, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet = matches.getHorizontalMatchedTiles();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    

    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    expectedDestroyedTileSet2.add(new Tile(0, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 0, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet2 = matches.getVerticalMatchedTiles();
    assertEquals(expectedDestroyedTileSet2, destroyedTileSet2);
    
    Set<Tile> destroyedTileSet3 = matches.getAllMatchedTiles();
    printDestroyedTiles(destroyedTileSet3, board.getDimensions());
    assertEquals(destroyedTileSet3.size(), 5);
    Set<Tile> expectedDestroyedTileSet3 = new HashSet<Tile>();
    expectedDestroyedTileSet3.add(new Tile(0, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet3.add(new Tile(1, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet3.add(new Tile(2, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet3.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet3.add(new Tile(0, 2, TileColor.TEAMUP));
    assertEquals(expectedDestroyedTileSet3, destroyedTileSet3);

  }
  
  @Test 
  public void testDoubleMatchCrossPattern() {
      String test = 
                "G T G\n"
              + "T T T\n"
              + "B T R";
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(1, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(1, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(1, 2, TileColor.TEAMUP));
    assertEquals(expectedDestroyedTileSet, matches.getHorizontalMatchedTiles());
    

    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    expectedDestroyedTileSet2.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 1, TileColor.TEAMUP));
    assertEquals(expectedDestroyedTileSet2, matches.getVerticalMatchedTiles());
    
    Set<Tile> destroyedTileSet3 = matches.getAllMatchedTiles();
    assertEquals(destroyedTileSet3.size(), 5);
  }
  
  @Test 
  public void testDoubleMatchSixTotal() {
      String test = 
                "G T T\n"
              + "Y T T\n"
              + "B T T";
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    Set<Tile> destroyedTileSet = matches.getHorizontalMatchedTiles();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    

    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    expectedDestroyedTileSet2.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(0, 2, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 2, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet2 = matches.getVerticalMatchedTiles();
    assertEquals(expectedDestroyedTileSet2, destroyedTileSet2);
    
    Set<Tile> destroyedTileSet3 = matches.getAllMatchedTiles();
    printDestroyedTiles(destroyedTileSet3, board.getDimensions());
    assertEquals(destroyedTileSet3.size(), 6);
  }

  @Test 
  public void testDoubleMatchSevenTotal() {
      String test = 
                "G T T\n"
              + "Y T T\n"
              + "T T T";
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(2, 0, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(2, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet.add(new Tile(2, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet = matches.getHorizontalMatchedTiles();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    
    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    expectedDestroyedTileSet2.add(new Tile(0, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 1, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(0, 2, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(1, 2, TileColor.TEAMUP));
    expectedDestroyedTileSet2.add(new Tile(2, 2, TileColor.TEAMUP));
    Set<Tile> destroyedTileSet2 = matches.getVerticalMatchedTiles();
    assertEquals(expectedDestroyedTileSet2, destroyedTileSet2);
    
    Set<Tile> destroyedTileSet3 = matches.getAllMatchedTiles();
    printDestroyedTiles(destroyedTileSet3, board.getDimensions());
    assertEquals(destroyedTileSet3.size(), 7);
  }
  
  @Test 
  public void testXPatternNoMatch() {
      String test = 
                "G T G\n"
              + "Y G T\n"
              + "G T G";
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    Set<Tile> destroyedTileSet = matches.getHorizontalMatchedTiles();
    assertEquals(expectedDestroyedTileSet, destroyedTileSet);
    

    Set<Tile> expectedDestroyedTileSet2 = new HashSet<Tile>();
    Set<Tile> destroyedTileSet2 = matches.getVerticalMatchedTiles();
    assertEquals(expectedDestroyedTileSet2, destroyedTileSet2);
    
    
    Set<Tile> destroyedTileSet3 = matches.getAllMatchedTiles();
    printDestroyedTiles(destroyedTileSet3, board.getDimensions());
    assertEquals(destroyedTileSet3.size(), 0);
  }
  
  @Test 
  public void testHorizontalDrop() {
      String test = 
                "Y G B\n"
              + "T R G\n"
              + "R R R";
    GameBoardImpl board = createBoardFromString(test);
    
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

    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> destroyedTileSet = matches.getAllMatchedTiles();
    board.destroyTiles(destroyedTileSet);
    
    List<Tile> expectedRow2After = Lists.newArrayList();
    expectedRow2After.add(new Tile(2, 0, TileColor.TEAMUP));
    expectedRow2After.add(new Tile(2, 1, TileColor.RED));
    expectedRow2After.add(new Tile(2, 2, TileColor.GREEN));
    assertEquals(expectedRow2After, board.getTilesInRow(2));
  
}
  
  @Test 
  public void testVerticalDrop() {
      Tile.defaultRandomCaller = new Tile.FixedTileColorSequenceRandomImpl(TileColor.BLACK, TileColor.BLUE);

      String test = 
                "Y Y B G\n"
              + "T R G T\n"
              + "T R G T\n"
              + "R R P Y";
    GameBoardImpl board = GameBoardTest.createBoardFromString(test);
    
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

    GameBoardMatchesImpl policy = new GameBoardMatchesImpl(board);
    GameBoardMoveResultsImpl results = policy.findMatchesOnBoard();
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
  
  @Test 
  public void testHorizontalDrop2() {
      String test = 
                "Y G B\n"
              + "R R R\n"
              + "T R G";
    GameBoardImpl board = createBoardFromString(test);
    
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

    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    Set<Tile> destroyedTileSet = matches.getAllMatchedTiles();
    board.destroyTiles(destroyedTileSet);
    
    List<Tile> expectedRow2After = Lists.newArrayList();
    expectedRow2After.add(new Tile(1, 0, TileColor.YELLOW));
    expectedRow2After.add(new Tile(1, 1, TileColor.GREEN));
    expectedRow2After.add(new Tile(1, 2, TileColor.BLACK));
    System.out.println(board);
    assertEquals(expectedRow2After, board.getTilesInRow(1));
  
}
  
  @Test
  public void testSingleHorizontalMatch4() throws IOException {
    String test = 
        "R R R R\n"
      + "T U G T\n"
      + "T R G T\n"
      + "R R P Y";
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    
    // Single horizontal match 4 in row 0.
    Set<Integer> actualRows = matches.getHorizontalMatchFours();
    
    Set<Integer> expectedRows = new HashSet<>();
    expectedRows.add(0);
    assertTrue(expectedRows.equals(actualRows));
    
    //assertNull(blob.getCriticalTileLocation());
    
    // No vertical match-4s.
    assertEquals(0, matches.getVerticalMatchFours().size());
  }  
  
  @Test
  public void testSingleVerticalMatch4() throws IOException {
    String test = 
        "G R U R\n"
      + "T R G T\n"
      + "T R G T\n"
      + "R R P Y";
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    
    // Single vertical match 4 in row 1.
    Set<Integer> actualRows = matches.getVerticalMatchFours();
    
    Set<Integer> expectedRows = new HashSet<>();
    expectedRows.add(1);
    assertTrue(expectedRows.equals(actualRows));
    
    //assertNull(blob.getCriticalTileLocation());
    
    // No vertical match-4s.
    assertEquals(0, matches.getHorizontalMatchFours().size());
  }
  
  @Test
  public void testTwoMatchFours() throws IOException {
    String test = 
        "Y R R R R\n"
      + "R Y R R U\n"
      + "Y R R R T\n"
      + "R R R Y U\n"
      + "U Y G U Y";
    
    GameBoardImpl board = createBoardFromString(test);
    GameBoardMatchesImpl matches = new GameBoardMatchesImpl(board);
    
    // Expect a column and row at 0,0.
    Set<Integer> expectedCols = new HashSet<>();
    expectedCols.add(2);
    
    Set<Integer> expectedRows = new HashSet<>();
    expectedRows.add(0);

    Set<Integer> actualCols = matches.getVerticalMatchFours();
    assertTrue(expectedCols.equals(actualCols));
    Set<Integer> actualRows = matches.getHorizontalMatchFours();
    assertTrue(expectedRows.equals(actualRows));
    
    // Crit tile at the intersection of the two.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(0, critTile.getCol());*/
  } 
  
public static GameBoardImpl createBoardFromString(String inputString) {
    ImmutableList<String> rows = ImmutableList.copyOf(inputString.split("\n"));   
    GameBoardImpl board = createBoardFromStringRows(rows);
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
  private static GameBoardImpl createBoardFromFile(String filePath) throws IOException {
    CharSource fileSource = Files.asCharSource(new File(filePath), Charset.defaultCharset());
    ImmutableList<String> rows = fileSource.readLines();
    return createBoardFromStringRows(rows);
  }

  private static GameBoardImpl createBoardFromStringRows(ImmutableList<String> rows) {
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
    return new GameBoardImpl(rows.size(), rows.size(), initialBoard, new TestUtilities.GameBoardMoveResultsProvider());
}

}

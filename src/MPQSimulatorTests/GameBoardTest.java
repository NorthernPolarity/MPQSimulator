package MPQSimulatorTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.CharSource;
import com.google.common.io.Files;
import com.google.common.math.IntMath;

import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.GameBoardMoveResults;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;
import MPQSimulator.ThirdParty.StdIn;

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
    GameBoard board = createBoardFromFile("src/MPQSimulatorTests/res/OneHorizontalMatch3Board3x3.txt");
    GameBoardMoveResults results = board.findMatchesOnBoard();
    Set<Tile> expectedDestroyedTileSet = new HashSet<Tile>();
    expectedDestroyedTileSet.add(new Tile(0, 0, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(0, 1, TileColor.RED));
    expectedDestroyedTileSet.add(new Tile(0, 2, TileColor.RED));
    Set<Tile> destroyedTileSet = results.getDestroyedTileSet();
    assert(expectedDestroyedTileSet.equals(destroyedTileSet));
  }
  
  
  // Assumes that the game board is N by N.
  private static GameBoard createBoardFromFile(String filePath) throws IOException {
    CharSource fileSource = Files.asCharSource(new File(filePath), Charset.defaultCharset());
    ImmutableList<String> rows = fileSource.readLines();
    Tile[][] initialBoard = new Tile[rows.size()][rows.size()];
    
    for (int i = 0; i < rows.size(); i++) {
      String[] tileColors = rows.get(i).split(" ");
      
      // Make sure that all the columns are the same size.
      assert (tileColors.length == rows.size());
      
      for (int j = 0; j < tileColors.length; j++) {
        
        TileColor tileColor = fileCharToTileColor.get(tileColors[j]);
        initialBoard[i][rows.size() - 1 - j] = new Tile(i, rows.size() - 1 - j, tileColor);
      }
    }
    return new GameBoard(rows.size(), rows.size(), initialBoard);
  }
}

package MPQSimulatorTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

import MPQSimulator.Core.GameBoardMoveResultsImpl;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.GameBoardMoveResultsImpl.MatchedTileBlob;
import MPQSimulator.Core.Tile.TileColor;


public class MoveResultsTest {
  
  @Test
  public void testSingleHorizontalMatch4Blob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    
    // Single horizontal match 4 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    //assertNull(blob.getCriticalTileLocation());
  }
  
  @Test
  public void testSingleVerticalMatch4Blob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(0, 3, TileColor.RED));
    
    // Single vertical match 4 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    //assertNull(blob.getCriticalTileLocation());
    
  }
  
  @Test
  public void testSingleHorizontalMatch5Blob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    results.addTile(new Tile(4, 0, TileColor.RED));
    
    // Single horizontal match 5 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(2, critTile.getRow());
    assertEquals(0, critTile.getCol());*/
  }
  
  @Test
  public void testSingleVerticalMatch5Blob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(0, 3, TileColor.RED));
    results.addTile(new Tile(0, 4, TileColor.RED));
    
    // Single vertical match 5 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
    
  }
  
  @Test
  public void testSingleHorizontalMatch6Blob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    results.addTile(new Tile(4, 0, TileColor.RED));
    results.addTile(new Tile(5, 0, TileColor.RED));
    
    // Single horizontal match 6 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(2, critTile.getRow());
    assertEquals(0, critTile.getCol());*/
    
  }
  
  @Test
  public void testSingleVerticalMatch6Blob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(0, 3, TileColor.RED));
    results.addTile(new Tile(0, 4, TileColor.RED));
    results.addTile(new Tile(0, 5, TileColor.RED));
    
    // Single vertical match 5 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
    
  }
  
  @Test
  public void test3x3LBlob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    // Crit tile at the intersection of the two.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(0, critTile.getCol());*/
  } 
  
  @Test
  public void test4x4LBlob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(0, 3, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    // Crit tile at the intersection of the two.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(0, critTile.getCol());*/
  } 
  
  @Test
  public void test3x2RectangleShapedBlob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(1, 1, TileColor.RED));
    results.addTile(new Tile(1, 2, TileColor.RED));
    
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    // Crit tile at the top left corner.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
  } 
  
  @Test
  public void test4x4LargeHollowSquareShapedBlob() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(0, 3, TileColor.RED));
    results.addTile(new Tile(1, 3, TileColor.RED));
    results.addTile(new Tile(2, 3, TileColor.RED));
    results.addTile(new Tile(3, 3, TileColor.RED));
    results.addTile(new Tile(3, 2, TileColor.RED));
    results.addTile(new Tile(3, 1, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());

    // Crit tile at the top left corner.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
  } 
  
  @Test
  public void testBlobWith4TilesInRowsAndColsWithoutAnyActualMatch4() throws IOException {
    GameBoardMoveResultsImpl results = new GameBoardMoveResultsImpl(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(1, 2, TileColor.RED));
    results.addTile(new Tile(1, 3, TileColor.RED));
    results.addTile(new Tile(1, 4, TileColor.RED));
    results.addTile(new Tile(0, 4, TileColor.RED));
    results.addTile(new Tile(0, 5, TileColor.RED));
    results.addTile(new Tile(0, 6, TileColor.RED));
    
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    // Crit tile at the top left corner.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(1, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
  } 
  
}

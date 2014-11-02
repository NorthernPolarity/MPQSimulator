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

import MPQSimulator.Core.MoveResults;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.MoveResults.MatchedTileBlob;
import MPQSimulator.Core.Tile.TileColor;


public class MoveResultsTest {
  
  @Test
  public void testSingleHorizontalMatch4Blob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    
    // Single horizontal match 4 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    MatchedTileBlob blob = blobList.get(0);
    Set<Integer> expectedRows = new HashSet<>();
    expectedRows.add(0);
    Set<Integer> actualRows = blob.getHorizontalMatchFours();
    assertTrue(expectedRows.equals(actualRows));
    
    //assertNull(blob.getCriticalTileLocation());
    
    // No vertical match-4s.
    assertEquals(0, blob.getVerticalMatchFours().size());
  }
  
  @Test
  public void testHorizontalMatch4BlobNotImmediate() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    
    // Single horizontal match 4 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    MatchedTileBlob blob = blobList.get(0);
    Set<Integer> expectedRows = new HashSet<>();
    expectedRows.add(0);
    Set<Integer> actualRows = blob.getHorizontalMatchFours();
    assertTrue(expectedRows.equals(actualRows));
    
    //assertNull(blob.getCriticalTileLocation());
    
    // No vertical match-4s.
    assertEquals(0, blob.getVerticalMatchFours().size());
  }
  
  @Test
  public void testSingleVerticalMatch4Blob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(0, 3, TileColor.RED));
    
    // Single vertical match 4 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    MatchedTileBlob blob = blobList.get(0);
    Set<Integer> expectedCols = new HashSet<>();
    expectedCols.add(0);
    Set<Integer> actualCols = blob.getVerticalMatchFours();
    assertTrue(expectedCols.equals(actualCols));
    
    //assertNull(blob.getCriticalTileLocation());
    
    // No horizontal match-4s.
    assertEquals(0, blob.getHorizontalMatchFours().size());
  }
  
  @Test
  public void testSingleHorizontalMatch5Blob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    results.addTile(new Tile(4, 0, TileColor.RED));
    
    // Single horizontal match 5 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    MatchedTileBlob blob = blobList.get(0);
    Set<Integer> expectedRows = new HashSet<>();
    expectedRows.add(0);
    Set<Integer> actualRows = blob.getHorizontalMatchFours();
    assertTrue(expectedRows.equals(actualRows));
    
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(2, critTile.getRow());
    assertEquals(0, critTile.getCol());*/
    
    // No vertical match-5s.
    assertEquals(0, blob.getVerticalMatchFours().size());
  }
  
  @Test
  public void testSingleVerticalMatch5Blob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(0, 3, TileColor.RED));
    results.addTile(new Tile(0, 4, TileColor.RED));
    
    // Single vertical match 5 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    MatchedTileBlob blob = blobList.get(0);
    Set<Integer> expectedCols = new HashSet<>();
    expectedCols.add(0);
    Set<Integer> actualCols = blob.getVerticalMatchFours();
    assertTrue(expectedCols.equals(actualCols));
    
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
    
    // No horizontal match-5s.
    assertEquals(0, blob.getHorizontalMatchFours().size());
  }
  
  @Test
  public void testSingleHorizontalMatch6Blob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    results.addTile(new Tile(4, 0, TileColor.RED));
    results.addTile(new Tile(5, 0, TileColor.RED));
    
    // Single horizontal match 6 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    MatchedTileBlob blob = blobList.get(0);
    Set<Integer> expectedRows = new HashSet<>();
    expectedRows.add(0);
    Set<Integer> actualRows = blob.getHorizontalMatchFours();
    assertTrue(expectedRows.equals(actualRows));
    
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(2, critTile.getRow());
    assertEquals(0, critTile.getCol());*/
    
    // No vertical match-6s.
    assertEquals(0, blob.getVerticalMatchFours().size());
  }
  
  @Test
  public void testSingleVerticalMatch6Blob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(0, 3, TileColor.RED));
    results.addTile(new Tile(0, 4, TileColor.RED));
    results.addTile(new Tile(0, 5, TileColor.RED));
    
    // Single vertical match 5 in row 0.
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    MatchedTileBlob blob = blobList.get(0);
    Set<Integer> expectedCols = new HashSet<>();
    expectedCols.add(0);
    Set<Integer> actualCols = blob.getVerticalMatchFours();
    assertTrue(expectedCols.equals(actualCols));
    
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
    
    // No horizontal match-5s.
    assertEquals(0, blob.getHorizontalMatchFours().size());
  }
  
  @Test
  public void test3x3LBlob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    MatchedTileBlob blob = blobList.get(0);
    
    // No horizontal match-4s.
    assertEquals(0, blob.getHorizontalMatchFours().size());
    assertEquals(0, blob.getVerticalMatchFours().size());
    
    // Crit tile at the intersection of the two.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(0, critTile.getCol());*/
  } 
  
  @Test
  public void test4x4LBlob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(0, 3, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(2, 0, TileColor.RED));
    results.addTile(new Tile(3, 0, TileColor.RED));
    
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    // Expect a column and row at 0,0.
    MatchedTileBlob blob = blobList.get(0);
    Set<Integer> expectedColsAndRows = new HashSet<>();
    expectedColsAndRows.add(0);
    Set<Integer> actualCols = blob.getVerticalMatchFours();
    assertTrue(expectedColsAndRows.equals(actualCols));
    Set<Integer> actualRows = blob.getHorizontalMatchFours();
    assertTrue(expectedColsAndRows.equals(actualRows));
    
    // Crit tile at the intersection of the two.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(0, critTile.getCol());*/
  } 
  
  @Test
  public void test3x2RectangleShapedBlob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
    results.addTile(new Tile(0, 0, TileColor.RED));
    results.addTile(new Tile(0, 1, TileColor.RED));
    results.addTile(new Tile(0, 2, TileColor.RED));
    results.addTile(new Tile(1, 0, TileColor.RED));
    results.addTile(new Tile(1, 1, TileColor.RED));
    results.addTile(new Tile(1, 2, TileColor.RED));
    
    List<MatchedTileBlob> blobList = results.findTileBlobs();
    assertEquals(1, blobList.size());
    
    // Expect a column and row at 0,0.
    MatchedTileBlob blob = blobList.get(0);
    // No horizontal match-4s.
    assertEquals(0, blob.getHorizontalMatchFours().size());
    assertEquals(0, blob.getVerticalMatchFours().size());
    
    // Crit tile at the top left corner.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
  } 
  
  @Test
  public void test4x4LargeHollowSquareShapedBlob() throws IOException {
    MoveResults results = new MoveResults(7, 7);
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
    
    // Expect a column and row at 0,0.
    MatchedTileBlob blob = blobList.get(0);
    Set<Integer> expectedColsAndRows = new HashSet<>();
    expectedColsAndRows.add(0);
    expectedColsAndRows.add(3);
    Set<Integer> actualCols = blob.getVerticalMatchFours();
    assertTrue(expectedColsAndRows.equals(actualCols));
    Set<Integer> actualRows = blob.getHorizontalMatchFours();
    assertTrue(expectedColsAndRows.equals(actualRows));
    
    // Crit tile at the top left corner.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(0, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
  } 
  
  @Test
  public void testBlobWith4TilesInRowsAndColsWithoutAnyActualMatch4() throws IOException {
    MoveResults results = new MoveResults(7, 7);
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
    
    // Expect a single blob
    MatchedTileBlob blob = blobList.get(0);
    
    // No horizontal match-4s.
    assertEquals(0, blob.getHorizontalMatchFours().size());
    assertEquals(0, blob.getVerticalMatchFours().size());
    
    // Crit tile at the top left corner.
    /*Tile critTile = blob.getCriticalTileLocation();
    assertEquals(1, critTile.getRow());
    assertEquals(2, critTile.getCol());*/
  } 
  
}

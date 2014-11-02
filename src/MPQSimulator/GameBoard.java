package MPQSimulator;
import java.util.Arrays;
import java.util.Set;

import com.google.common.base.Preconditions;

public class GameBoard {
    private final int tilesPerRow;
    private final int tilesPerCol;

    private Tile[][] gameBoard; //Represents the current state of the board
 
    public GameBoard(int tilesPerRow, int tilesPerCol) {
      this.tilesPerRow = tilesPerRow;
      this.tilesPerCol = tilesPerCol;
      gameBoard = new Tile[tilesPerRow][tilesPerCol];
      resetGameBoard();
    }
    
    public GameBoard(int tilesPerRow, int tilesPerCol, Tile[][] initialBoard) {
      Preconditions.checkArgument(initialBoard.length == tilesPerRow);
      for (Tile[] col : initialBoard) {
        Preconditions.checkArgument(col.length == tilesPerCol);
      }
      
      // Check that all tiles have valid state
      for (int i = 0; i < tilesPerRow; i++) {
        for (int j = 0; j < tilesPerCol; j++) {
          Tile t = initialBoard[i][j];
          Preconditions.checkArgument(t.getRow() == i);
          Preconditions.checkArgument(t.getCol() == j);
        }
      }
      
      this.tilesPerRow = tilesPerRow;
      this.tilesPerCol = tilesPerCol;
      
      gameBoard = new Tile[tilesPerRow][tilesPerCol];
      // Copy the tiles over.
      for (int i = 0; i < tilesPerRow; i++) {
        for (int j = 0; j < tilesPerCol; j++) {
          gameBoard[i][j] = new Tile(initialBoard[i][j]);
        }
      }
    }
    
    public void resetGameBoard() {
    //Initialize tiles randomly.
      for(int i = 0; i < tilesPerRow; i++){
          for(int j = 0; j < tilesPerCol; j++){
              gameBoard[i][j] = new Tile(i, j);
          }
      }
    }
    
    public Tile[][] getBoardState() {
        return gameBoard;
    }
    
    /*
     * Checks the current state of the board, and returns the set of matches involved in a match 3.
     */
    public MoveResults findMatchesOnBoard() {
        
        MoveResults colMoveResults = this.findVerticalMatches();
        MoveResults rowMoveResults = this.findHorizontalMatches();
        
        //Remove any matches from the board, update the board accordingly.
        MoveResults currentMoveResults = colMoveResults;
        colMoveResults.add(rowMoveResults);
        this.destroyTiles(currentMoveResults);
        
        return currentMoveResults;
    }
    
    
    private void destroyTiles(MoveResults results) {
      if (results.getTilesPerRow() != tilesPerRow){
          System.out.println("Error in destroy Tiles!");
      }
      
      //For each col
      for (int i = 0; i < tilesPerRow; i++){
        Set<Tile> tileSet = results.getTilesByCol(i);
        Tile[] tilesToDestroy = tileSet.toArray(new Tile[tileSet.size()]);

        Arrays.sort(tilesToDestroy);
        
        int numTilesToDestroy = tilesToDestroy.length;
        if (numTilesToDestroy == 0){
          continue;
        }
        
       

        //The current row that is empty and needs a new tile.
        int emptyRowIndex = tilesToDestroy[0].getCol();
        //The row of the tile that we are considering moving to the empty row.
        int tileToCopyIndex = emptyRowIndex + 1;
        
        int tilesToDestroyIndex = 1;
        
        //While there are still existing tiles on the board that we need to make "fall"
        while(tileToCopyIndex < tilesPerCol){
          //If the current tile in the old board has been destroyed by a match, skip it.
          if (tilesToDestroyIndex < tilesToDestroy.length
               && tilesToDestroy[tilesToDestroyIndex].getCol() == tileToCopyIndex){
            tileToCopyIndex++;
            tilesToDestroyIndex++;
              
          } else { //Otherwise move the bottom-most not destroyed tile to the current free location.
            gameBoard[i][emptyRowIndex] = new Tile(gameBoard[i][tileToCopyIndex]);
            gameBoard[i][emptyRowIndex].changeLocation(i, emptyRowIndex);
            emptyRowIndex++;
            tileToCopyIndex++;
          }
        }
        
        //Fill the remaining empty rows with "new" blocks.
        for (int j = emptyRowIndex; j < tilesPerCol; j++){
            gameBoard[i][j] = new Tile(i, j);
        }
      }
      
    }
    
    /*
     * locations is a list of size TILES_PER_ROW where each set in the list
     * represents a row in the array [locations.get(0) is row 0, and so on.
     * and each set contains the cols of the tiles in that row to be destroyed.
     */ 
    public void destroyTiles(Set<Tile> tiles) {
        MoveResults results = new MoveResults(tilesPerRow, tilesPerCol);
        results.addTile(tiles);
        this.destroyTiles(results);
    }
    
    // Finds and returns all tiles that are part of a vertical match 3.
    private MoveResults findVerticalMatches() {
        MoveResults results = new MoveResults(tilesPerRow, tilesPerCol);
        //For each column...
        for (int i = 0; i < tilesPerCol; i++) {
            // Go down each column looking for vertical match 3's
          int j = 0;
          while (j < tilesPerRow - 2){
            TileColor currentColor = gameBoard[i][j].getColor();
            
            //If there is at least a match 3
            if (currentColor.equals(gameBoard[i][j+1].getColor())
                && currentColor.equals(gameBoard[i][j+2].getColor())){
              
              results.addTile(gameBoard[i][j]);
              results.addTile(gameBoard[i][j+1]);
              results.addTile(gameBoard[i][j+2]);
              
              //Check for match 4's+ starting at the tile after the match 3
              int k = j + 3;
              while (k < tilesPerRow) {
                  //Stop searching once we see a different colored tile
                  if (!currentColor.equals(gameBoard[i][k].getColor())) {
                      break;
                  }
                  results.addTile(this.gameBoard[i][k]);
                  k++;
              }
              
              j = k;
            } else {
              j++;
            }
          }
        }
        return results;
    }
    
    // Finds and returns all tiles that are part of a horizontal match 3.
    private MoveResults findHorizontalMatches() {
      MoveResults results = new MoveResults(tilesPerRow, tilesPerCol);
      // For each row...
      for (int j = 0; j < tilesPerRow; j++) {
        // Go across each row looking for horizontal match-3s.
        int i = 0;
        while (i < tilesPerCol - 2) {
          TileColor currentColor = gameBoard[i][j].getColor();
          
          //If there is at least a match 3
          if (currentColor.equals(gameBoard[i + 1][j].getColor())
              && currentColor.equals(gameBoard[i + 2][j].getColor())) {
            results.addTile(gameBoard[i][j]);
            results.addTile(gameBoard[i + 1][j]);
            results.addTile(gameBoard[i + 2][j]);
            
            //Check for match 4's+ starting at the tile after the match 3
            int k = i + 3;
            while (k < tilesPerCol) {
                if (!currentColor.equals(gameBoard[k][j].getColor())) {
                    break;
                } 
                results.addTile(gameBoard[k][j]);
                k++;
            }
            
            i = k;
          } else {
              i++;
          }
        }
      }
      return results;
    }
    
}
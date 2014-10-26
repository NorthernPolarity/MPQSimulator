import java.util.Arrays;
import java.util.Set;


public class GameBoard {
    private static final int TILES_PER_ROW = 7;
    private static final int TILES_PER_COL = 7;

    private Tile[][] mGameBoard; //Represents the current state of the board
 
    public GameBoard(){
        mGameBoard = new Tile[TILES_PER_ROW][TILES_PER_COL];
        resetGameBoard();
    }
    
    public void resetGameBoard() {
    //Initialize tiles randomly.
      for(int i = 0; i < TILES_PER_ROW; i++){
          for(int j = 0; j < TILES_PER_COL; j++){
              mGameBoard[i][j] = new Tile(i, j);
          }
      }
    }
    
    public Tile[][] getBoardState() {
        return mGameBoard;
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
      if (results.getTilesPerRow() != GameBoard.TILES_PER_ROW){
          System.out.println("Error in destroy Tiles!");
      }
      
      //For each col
      for (int i = 0; i < GameBoard.TILES_PER_ROW; i++){
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
        while(tileToCopyIndex < TILES_PER_COL){
          //If the current tile in the old board has been destroyed by a match, skip it.
          if (tilesToDestroyIndex < tilesToDestroy.length
               && tilesToDestroy[tilesToDestroyIndex].getCol() == tileToCopyIndex){
            tileToCopyIndex++;
            tilesToDestroyIndex++;
              
          } else {//Otherwise move the bottom-most not destroyed tile to the current free location.
            mGameBoard[i][emptyRowIndex] = new Tile(mGameBoard[i][tileToCopyIndex]);
            mGameBoard[i][emptyRowIndex].changeLocation(i, emptyRowIndex);
            emptyRowIndex++;
            tileToCopyIndex++;
          }
        }
        
        //Fill the remaining empty rows with "new" blocks.
        for (int j = emptyRowIndex; j < TILES_PER_COL; j++){
            mGameBoard[i][j] = new Tile(i, j);
        }
      }
      
    }
    
    /*
     * locations is a list of size TILES_PER_ROW where each set in the list
     * represents a row in the array [locations.get(0) is row 0, and so on.
     * and each set contains the cols of the tiles in that row to be destroyed.
     */ 
    public void destroyTiles(Set<Tile> tiles) {
        MoveResults results = new MoveResults(GameBoard.TILES_PER_ROW, GameBoard.TILES_PER_COL);
        results.addTile(tiles);
        this.destroyTiles(results);
    }
    
    // Finds and returns all tiles that are part of a vertical match 3.
    private MoveResults findVerticalMatches() {
        MoveResults results = new MoveResults(GameBoard.TILES_PER_ROW, GameBoard.TILES_PER_COL);
        //For each column...
        for (int i = 0; i < TILES_PER_COL; i++) {
            // Go down each column looking for vertical match 3's
          int j = 0;
          while (j < TILES_PER_ROW - 2){
            TileColor currentColor = mGameBoard[i][j].getColor();
            
            //If there is at least a match 3
            if (currentColor.equals(mGameBoard[i][j+1].getColor())
                && currentColor.equals(mGameBoard[i][j+2].getColor())){
              
              results.addTile(mGameBoard[i][j]);
              results.addTile(mGameBoard[i][j+1]);
              results.addTile(mGameBoard[i][j+2]);
              
              //Check for match 4's+ starting at the tile after the match 3
              int k = j + 3;
              while (k < TILES_PER_ROW) {
                  //Stop searching once we see a different colored tile
                  if (!currentColor.equals(mGameBoard[i][k].getColor())) {
                      break;
                  }
                  results.addTile(this.mGameBoard[i][k]);
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
      MoveResults results = new MoveResults(GameBoard.TILES_PER_ROW, GameBoard.TILES_PER_COL);
      // For each row...
      for (int j = 0; j < TILES_PER_ROW; j++) {
        // Go across each row looking for horizontal match-3s.
        int i = 0;
        while (i < TILES_PER_COL - 2) {
          TileColor currentColor = mGameBoard[i][j].getColor();
          
          //If there is at least a match 3
          if (currentColor.equals(mGameBoard[i + 1][j].getColor())
              && currentColor.equals(mGameBoard[i + 2][j].getColor())) {
            results.addTile(mGameBoard[i][j]);
            results.addTile(mGameBoard[i + 1][j]);
            results.addTile(mGameBoard[i + 2][j]);
            
            //Check for match 4's+ starting at the tile after the match 3
            int k = i + 3;
            while (k < TILES_PER_COL) {
                if (!currentColor.equals(mGameBoard[k][j].getColor())) {
                    break;
                } 
                results.addTile(mGameBoard[k][j]);
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
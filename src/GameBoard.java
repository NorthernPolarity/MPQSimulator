import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class GameBoard {
	private static final int TILES_PER_ROW = 7;
	private static final int TILES_PER_COL = 7;

	private Tile[][] mGameBoard; //Represents the current state of the board
	
	public GameBoard(){
		mGameBoard = new Tile[TILES_PER_ROW][TILES_PER_COL];
		//Initialize tiles randomly.
		for(int i = 0; i < TILES_PER_ROW; i++){
			for(int j = 0; j < TILES_PER_COL; j++){
				mGameBoard[i][j] = new Tile(j, j);
			}
		}
	}
	
	public void resetGameBoard(){
		for(int i = 0; i < TILES_PER_ROW; i++){
			for(int j = 0; j < TILES_PER_COL; j++){
				mGameBoard[i][j].randomizeColor();
			}
		}		
	}
	
	/*
	 * Checks the current state of the board, and makes matches / drops new tiles
	 * until there are no more existing matches in the board.
	 */
	private MoveResults stabilizeGameBoard(){
		MoveResults overallResults = new MoveResults(GameBoard.TILES_PER_ROW);
		
		
		MoveResults colMoveResults = this.findMatchesInCols();
		MoveResults rowMoveResults = this.findMatchesInRows();
		MoveResults currentMoveResults = null;
		
		//Remove any matches from the board, update the board accordingly, and repeat
		//until there are no more matches.
		while(!colMoveResults.empty() || !rowMoveResults.empty()){
			currentMoveResults = colMoveResults;
			currentMoveResults.add(rowMoveResults);
			this.destroyTiles(currentMoveResults);
			
			overallResults.add(currentMoveResults);
		}
		
		return currentMoveResults;
	}
	

	private void destroyTiles(MoveResults results){
		if(results.getTilesPerRow() != GameBoard.TILES_PER_ROW){
			System.out.println("Error in destroy Tiles!");
		}
		
		//For each col
		for(int i = 0; i < GameBoard.TILES_PER_ROW; i++){
			Tile[] tilesToDestroy = (Tile[])(results.getTilesByCol(i).toArray());
			Arrays.sort(tilesToDestroy);
			
			int numTilesToDestroy = tilesToDestroy.length;
			if(numTilesToDestroy == 0){
				continue;
			}
			
			int nextTileToBeDestroyedIndex = tilesToDestroy.length - 1;
			//The row of the tile that we are considering moving to the empty row.
			int tileToCopyIndex = tilesToDestroy.length - 2;
			//The current row that is empty and needs a new tile.
			int emptyRowIndex = tilesToDestroy[nextTileToBeDestroyedIndex].getRow();
			nextTileToBeDestroyedIndex--;
			
			//While there are still existing tiles on the board that we need to make "fall"
			while(tileToCopyIndex >= 0){
				//If the current tile in the old board has been destroyed by a match, skip it.
				if(nextTileToBeDestroyedIndex >=0
						&& tilesToDestroy[nextTileToBeDestroyedIndex].getRow() == tileToCopyIndex){
					tileToCopyIndex--;
					nextTileToBeDestroyedIndex--;
					
				} else {//Otherwise move the bottom-most not destroyed tile to the current free location.
					mGameBoard[i][emptyRowIndex] = mGameBoard[i][tileToCopyIndex];
					mGameBoard[i][emptyRowIndex].changeLocation(i, emptyRowIndex);
					emptyRowIndex--;
					tileToCopyIndex--;
				}
			}
			
			//Fill the remaining empty rows with random blocks.
			for(int j = 0; j < emptyRowIndex; j++){
				mGameBoard[i][j].randomizeColor();
			}
		}
		
	}

	/*
	 * locations is a list of size TILES_PER_ROW where each set in the list
	 * represents a row in the array [locations.get(0) is row 0, and so on.
	 * and each set contains the cols of the tiles in that row to be destroyed.
	 */	
	public void destroyTiles(Set<Tile> tiles){
		MoveResults results = new MoveResults(GameBoard.TILES_PER_ROW);
		results.addTile(tiles);
		this.destroyTiles(results);
	}
	
	private MoveResults findMatchesInRows(){
		MoveResults results = new MoveResults(GameBoard.TILES_PER_ROW);
		//Fix the row,
		for(int i = 0; i < TILES_PER_ROW; i++){
			int j = 0;
			while(j < TILES_PER_COL-3){
				TileColor currentColor = mGameBoard[i][j].getColor();
				
				//If there is at least a match 3
				if(currentColor.equals(mGameBoard[i][j+1].getColor())
						&& currentColor.equals(mGameBoard[i][j+2].getColor())){
					
					Integer tilesMatched = 3; 
					results.addTile(mGameBoard[i][j]);
					results.addTile(mGameBoard[i][j+1]);
					results.addTile(mGameBoard[i][j+2]);
					
					//Check for match 4's+ starting at the tile after the match 3
					int k = j+3;
					while(k < TILES_PER_COL-3){
						if(currentColor.equals(mGameBoard[i][k].getColor())){
							tilesMatched++;
						} else { //Stop searching once we see a different colored tiled
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
	
	private MoveResults findMatchesInCols(){
		MoveResults results = new MoveResults(GameBoard.TILES_PER_ROW);
		//Check rows for matches
		for(int j = 0; j < TILES_PER_COL-3; j++){
			int i = 0;
			while(i < TILES_PER_COL-3){
				TileColor currentColor = mGameBoard[i][j].getColor();
				
				//If there is at least a match 3
				if(currentColor.equals(mGameBoard[i+1][j].getColor())
						&& currentColor.equals(mGameBoard[i+2][j].getColor())){
					Integer tilesMatched = 3; 
					results.addTile(mGameBoard[i][j]);
					results.addTile(mGameBoard[i+1][j]);
					results.addTile(mGameBoard[i+2][j]);
					
					//Check for match 4's+ starting at the tile after the match 3
					int k = i+3;
					while(k < TILES_PER_COL-3){
						if(currentColor.equals(mGameBoard[k][j].getColor())){
							tilesMatched++;
						} else { //Stop searching once we see a different colored tiled
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

package MPQSimulator.Abilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;

import MPQSimulator.Core.GameBoardImpl;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;

public class SwapTileAbilityComponent implements AbilityComponent {

	public final int tilePairsToSwap;
	public final List<TileColor> tileAColors;
	public final TileLocation tileALocation;
	public final int tileARow;
	public final int tileACol;
	public final int tileBRow;
	public final int tileBCol;
	public final List<TileColor> tileBColors;
	public final TileLocation tileBLocation;  

	// Swaps n random tiles.
	public SwapTileAbilityComponent(int tilesToSwap) {
		this(tilesToSwap, ALL_COLORS_NO_TEAMUPS_LIST, ALL_COLORS_NO_TEAMUPS_LIST);
	}

	// Swaps n A tiles of color A with B tiles of color B.
	public SwapTileAbilityComponent(int tilesToSwap, TileColor tileAColor, TileColor tileBColor) {
		this(tilesToSwap, Arrays.asList(tileAColor), Arrays.asList(tileBColor));
	}

	// Swaps n A tiles of color A with B tiles of color B.
	public SwapTileAbilityComponent(int tilesToSwap, 
			List<TileColor> tileAColors, List<TileColor> tileBColors) {
		this(tilesToSwap, tileAColors, TileLocation.RANDOM, -1, -1, 
				tileBColors, TileLocation.RANDOM, -1, -1);
	}

	// Swaps the tile in location (row, col) with a random tile of tile B's color.
	public SwapTileAbilityComponent(int tileARow, int tileACol, TileColor tileBColor) {
		this(1, null, TileLocation.FIXED, tileARow, tileACol, Arrays.asList(tileBColor), TileLocation.RANDOM,
				-1, -1);

	}

	public SwapTileAbilityComponent(int tilesToSwap, List<TileColor> tileAColors, TileLocation tileALocation,
			int tileARow, int tileACol, List<TileColor> tileBColors, TileLocation tileBLocation, int tileBRow, 
			int tileBCol) {

		if (tileALocation == TileLocation.RANDOM) {
			Preconditions.checkArgument(tileARow == -1 && tileACol == -1);
		}

		if (tileBLocation == TileLocation.RANDOM) {
			Preconditions.checkArgument(tileBRow == -1 && tileBCol == -1);
		}    

		this.tilePairsToSwap = tilesToSwap;
		this.tileAColors = tileAColors;
		this.tileALocation = tileALocation;    
		this.tileARow = tileARow;
		this.tileACol = tileACol;
		this.tileBColors = tileBColors;
		this.tileBRow = tileBRow;
		this.tileBCol = tileBCol;
		this.tileBLocation = tileBLocation;  
	}

	public void process(GameBoardImpl board) {
		if (this.tileBLocation != TileLocation.RANDOM) {
			throw new IllegalArgumentException();
		}

		Set<Tile> tileSetA;
		if( this.tileALocation == TileLocation.RANDOM ) {
			tileSetA = board.getTiles(this.tileAColors);
		} else {
			tileSetA = Sets.newHashSet(board.getTile(this.tileARow, this.tileACol));
		}

		Set<Tile> tileSetB = board.getTiles(this.tileBColors);

		List<Tile> randomizedTileListA = new ArrayList<Tile>(tileSetA);
		Collections.shuffle(randomizedTileListA);
		List<Tile> randomizedTileListB = new ArrayList<Tile>(tileSetB);
		Collections.shuffle(randomizedTileListB);

		Set<Tile> alreadyShuffledTiles = new HashSet<>();
		Iterator<Tile> aIt = randomizedTileListA.iterator();
		Iterator<Tile> bIt = randomizedTileListB.iterator();
		int tilePairsSwapped = 0;
		Tile tileA = null;
		Tile tileB = null;
		while ( (this.tilePairsToSwap > tilePairsSwapped)
				&& aIt.hasNext() && bIt.hasNext()) {

			// Search through the lists until we find a tile A and B that hasn't already been shuffled.
			if (tileA == null) {
				Tile next = aIt.next();
				if (!alreadyShuffledTiles.contains(next)) {
					tileA = next;
				} 
			}

			if (tileB == null) {
				Tile next = bIt.next();
				if (!alreadyShuffledTiles.contains(next)) {
					tileB = next;
				}
			}

			// If it's the same tile, and tileA is fixed, keep on looking through tileB.
			if (tileA == tileB && this.tileALocation == TileLocation.FIXED
					&& this.tileBLocation == TileLocation.RANDOM) {
				tileB = null;
			}

			if (tileA != null && tileB != null) {
				board.swapTiles(tileA, tileB);
				alreadyShuffledTiles.add(tileA);
				alreadyShuffledTiles.add(tileB);
				tilePairsSwapped++;
				tileA = null;
				tileB = null;
			}
		}
	}

}

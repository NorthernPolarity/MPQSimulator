package MPQSimulator.Core;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;

import MPQSimulator.Core.Tile.TileColor;
import MPQSimulator.Core.Tile.TileLocation;

// find the various kinds of matches on a GameBoard (3-in-a-row, 4-in-a-row, etc)
public class GameBoardMatchesImpl implements GameBoardMatches {

	public abstract class Match implements Iterable<Tile> {
		Set<Tile> matchTiles;

		private Match(Set<Tile> m) {
			matchTiles = m;
		}

		private Match() {
			this(new HashSet<Tile>());
		}

		public void add(Tile t) {
			matchTiles.add(t);
		}

		@Override
		public Iterator<Tile> iterator() {
			return matchTiles.iterator();
		}
		
		public Set<Tile> getTiles() {
		  return new HashSet<Tile>(matchTiles);
		}
		
		public int size() {
		  return matchTiles.size();
		}
	}

	// simple N-in-a-row match object
	public class SingleMatch extends Match {	

		SingleMatch(Set<Tile> m) {
			super(m);
			assert(length() >= 3);
		}

		SingleMatch() {
			super();
		}

		public int length() {
			return matchTiles.size();
		}

		public boolean doesIntersect(SingleMatch m) {
			return intersects(m) != null;
		}

		public Tile intersects(SingleMatch m) {
			assert(m != this);
			for( Tile t : m ) {
				if( matchTiles.contains(t) ) {
					return t;
				}
			}
			return null;
		}



		public boolean isAdjacent(SingleMatch m) {
			return adjacent(m) != null;
		}

		public TileLocation adjacent(SingleMatch m) {
			assert(m!=this);
			Set<TileLocation> tls = m.tileLocations();
			tls.retainAll(adjacentLocations()) ;

			if(tls.size() > 0) {
				return tls.iterator().next();
			} else {
				return null;
			}
		}


		private Set<TileLocation> tileLocations() {
			Set<TileLocation> tls = new HashSet<TileLocation>();
			for(Tile t : matchTiles) {
				tls.add(t.getLocation());
			}
			return tls;
		}

		private Set<TileLocation> adjacentLocations() {
			Set<TileLocation> tls = new HashSet<TileLocation>();
			for(Tile t : matchTiles) {
				int col = t.getCol();
				int row = t.getRow();

				if(row > 0) {
					tls.add(new TileLocation(row - 1, col));
				}
				if(col > 0) {
					tls.add(new TileLocation(row, col - 1));
				}
				tls.add(new TileLocation(row+1, col));
				tls.add(new TileLocation(row, col+1));
			}
			return tls;
		}

	}

	public class CritMatch extends Match {
		TileLocation critLocation;

		CritMatch(SingleMatch vertical, SingleMatch horizontal) {
			super();
			assert((vertical.doesIntersect(horizontal)) || vertical.isAdjacent(horizontal));
			matchTiles.addAll(vertical.matchTiles);
			matchTiles.addAll(horizontal.matchTiles);

			calculateCritLocation(vertical, horizontal);
		}

		private void calculateCritLocation(SingleMatch vertical, SingleMatch horizontal) {
			Tile intersectionPoint = vertical.intersects(horizontal);
			if( intersectionPoint != null ) {
				critLocation = new TileLocation(intersectionPoint.getLocation());
			} else {
				TileLocation adjacentPoint = vertical.adjacent(horizontal);
				assert(adjacentPoint != null );
				critLocation = adjacentPoint;
			}
		}
	}

	private GameBoard board;
	private final Set<SingleMatch> horizontalMatches;
	private final Set<SingleMatch> verticalMatches;
	private final Set<Tile> horizontalMatchedTiles;
    private final Set<Tile> verticalMatchedTiles;
    private final Set<Tile> allMatchedTiles;
    private final Tile[][] matchedTiles;
	
    @Inject
	public GameBoardMatchesImpl(@Assisted GameBoard b) {
		board = b;
		horizontalMatches = findHorizontalMatches();
		verticalMatches = findVerticalMatches();
		
		horizontalMatchedTiles = new HashSet<>();
      	for (SingleMatch m : horizontalMatches) {
      	  horizontalMatchedTiles.addAll(m.getTiles());
      	}
        verticalMatchedTiles = new HashSet<>();
        for (SingleMatch m : verticalMatches) {
          verticalMatchedTiles.addAll(m.getTiles());
        }	
        
        allMatchedTiles = new HashSet<>(horizontalMatchedTiles);
        allMatchedTiles.addAll(verticalMatchedTiles);
        
        matchedTiles = new Tile[board.getTilesPerRow()][board.getTilesPerCol()];
        for (Tile t : allMatchedTiles) {
          matchedTiles[t.getRow()][t.getCol()] = t;
        }
	}

	public Set<SingleMatch> getHorizontalMatches() {
	  return horizontalMatches;
	}
	
	public Set<SingleMatch> getVerticalMatches() {
	  return verticalMatches;
	}
	
    public Set<Tile> getHorizontalMatchedTiles() {
	  return horizontalMatchedTiles;
	}
	    
	public Set<Tile> getVerticalMatchedTiles() {
	  return verticalMatchedTiles;
	}
	
	public Set<Tile> getAllMatchedTiles() {
	  return allMatchedTiles;
	}
	
	public Set<Integer> getHorizontalMatchFours() {
	  Set<Integer> s = new HashSet<>();
      for (SingleMatch m : horizontalMatches) {
        if (m.size() >= 4) {
          s.add(m.iterator().next().getRow());
        }
      }
      return s;
	}
	
	public Set<Integer> getVerticalMatchFours() {
	   Set<Integer> s = new HashSet<>();
	      for (SingleMatch m : verticalMatches) {
	        if (m.size() >= 4) {
	          s.add(m.iterator().next().getCol());
	        }
	      }
	      return s;
	}
	
	// Finds and returns all tiles that are part of a horizontal match 3.
	private Set<SingleMatch> findHorizontalMatches() {
		Set<SingleMatch> results = new HashSet<SingleMatch>();
		//For each row...
		for (int i = 0; i < board.getTilesPerCol(); i++) {
			results.addAll(findHorizontalMatchForRow(i));
		}
		return results;
	}

	// Wrapper function for finding horizontal matches for a single row.
	private Set<SingleMatch> findHorizontalMatchForRow(int row) {
		List<Tile> thisRow = board.getTilesInRow(row);		
		return findMatchInList(thisRow);
	}
	

	// Finds and returns all tiles that are part of a vertical match 3.
    private Set<SingleMatch> findVerticalMatches() {
        Set<SingleMatch> results = new HashSet<SingleMatch>();
        // For each column...
        for (int j = 0; j < board.getTilesPerRow(); j++) {
            results.addAll(findVerticalMatchesForColumn(j));
        }
        return results;
    }

    // Wrapper function for finding all matches for a single column.
    private Set<SingleMatch> findVerticalMatchesForColumn(int col) {
        List<Tile> thisRow = board.getTilesInCol(col);
        return findMatchInList(thisRow);
    }

	// Returns all matches found in the list.
	private Set<SingleMatch> findMatchInList(List<Tile> source) {
		Set<SingleMatch> results = new HashSet<SingleMatch>();
		assert(source.size() > 2);
		// Go down each column looking for vertical match 3's
		int j = 0;
		while ( j < source.size() - 2 ) {
		    // Look at the current element and build a match of it and all adjacent elements
		    // of the same color.
			TileColor currentColor = source.get(j).getColor();
			SingleMatch currentMatch = new SingleMatch();
			for(Tile thisTile : source.subList(j, source.size())) {
				if( thisTile.getColor() == currentColor ) {
					currentMatch.add(thisTile);
				} else {
					break;
				}
			}
			
			// If the match that was built has more than 3 elements, we have a match-3+!
			if ( currentMatch.length() >= 3 ) {
				results.add(currentMatch);
			} 
			
			j += currentMatch.length();
		}
		return results;
	}

	// Find all matches on the current board.
	public GameBoardMoveResultsImpl findMatchesOnBoard() {

		Set<SingleMatch> moveResults = this.findVerticalMatches();
		moveResults.addAll(this.findHorizontalMatches());

		//Remove any matches from the board, update the board accordingly.
		GameBoardMoveResultsImpl currentMoveResults = 
		    new GameBoardMoveResultsImpl(board.getTilesPerRow(), board.getTilesPerCol());
		for( SingleMatch r : moveResults ) {
			currentMoveResults.addTiles(r.matchTiles);
		}
		return currentMoveResults;
	}
	

}
	
	
	
	
	
	
	
	
	
	
	
	
	
	



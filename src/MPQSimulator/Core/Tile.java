package MPQSimulator.Core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;

public class Tile implements Comparable<Tile> {
    public static enum TileColor {
      // "Normal tiles"
      BLACK, BLUE, RED, GREEN, YELLOW, PURPLE, TEAMUP,
    }
    // Array index of the last colored tile.
    public static int NUM_COLORED_TILES = 6;
    public static int NUM_NORMAL_TILES = 7;
    
    public static class TileLocation implements Comparable<TileLocation> {
      public int row;
      public int col;
      public TileLocation(int row, int col) {
        this.row = row;
        this.col = col;
      }
      
      public TileLocation(TileLocation that) {
        this.row = that.row;
        this.col = that.col;
      }
      
      @Override
      public int compareTo(TileLocation that)  {
          if( that == null) {
              return 0;
          }
          if (this.col > that.col) { return 1; }
          else if (this.col < that.col) { return -1; }
          else {
              
              if (this.row > that.row) { return 1; }
              else if (this.row < that.row) { return -1; }
              else {
                  return 0;
              }
          }
      }
      
      @Override
      public boolean equals(Object obj) {
        if (obj instanceof TileLocation) {
          TileLocation that = (TileLocation) obj;
           return this.compareTo(that) == 0;
        }
        return false;
      }
    }
  
    private TileColor tileColor;
    private TileLocation location;
    
    public Tile(Tile that) {
      this(that.location.row, that.location.col, that.tileColor);
    }
    
    public Tile(int row, int col){
        this(row, col, Tile.getRandomColor());
    }
    
    public Tile(int row, int col, TileColor color) {
      this.location = new TileLocation(row, col);

      Preconditions.checkNotNull(color);
      this.tileColor = color;
    }
    
    // destroyTiles depends on this implementation.
    @Override
    public int compareTo(Tile that) {
    	return location.compareTo(that.location);
    }
    
    @Override
    public int hashCode() {
      return Integer.valueOf(location.row).hashCode() ^ Integer.valueOf(location.col).hashCode() ^ tileColor.hashCode();
    }
    
    
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Tile) {
        Tile that = (Tile) obj;
         if (this.location.equals(that.location)
            && tileColor == that.tileColor) {
           return true;
         }
      }
      return false;
    }
    
    public TileColor getColor(){
        return this.tileColor;
    }
    
    public TileLocation getLocation() {
      return this.location;
    }
    
    public int getRow(){
        return this.location.row;
    }
    
    public int getCol(){
        return this.location.col;
    }
    
    public void setColor(TileColor color){
        Preconditions.checkArgument(color != tileColor);
        this.tileColor = color;
    }
    
    private void setRow(int row){
        this.location.row = row;
    }
    
    private void setCol(int col){
        this.location.col = col;
    }
    
    public void changeLocation(int row, int col){
        //Preconditions.checkArgument(!(row == this.row && col == this.col));
        this.setRow(row);
        this.setCol(col);
    }
    
    public void randomizeColor(){
        tileColor = Tile.getRandomColor();
    }
    
    // Returns if this tile is adjacent (north south east west) to tile t.
    public boolean adjacent(Tile t) {
      int manhattenDistance = Math.abs(t.location.col - this.location.col) 
          + Math.abs(t.location.row - this.location.row);
      return manhattenDistance == 1;
    }
    
    // Returns if this tile is horizontally adjacent to tile t (west / east)
    public boolean horizontallyAdjacent(Tile t) {
      return (this.location.col == t.location.col) 
          && (Math.abs(t.location.row - this.location.row) == 1);
    }
    
    // Returns if this tile is vertically adjacent to tile t (north / south)
    public boolean verticallyAdjacent(Tile t) {
      return (this.location.row == t.location.row) 
          && (Math.abs(t.location.col - this.location.col) == 1);
    }
    
    public interface RandomTileLocation {
        TileLocation nextLocation();
    }
    
    // Returns any potential sequence of random tile locations.
    public static class PureRandomTileLocationImpl {
        private int rows;
        private int cols;
        public PureRandomTileLocationImpl(int rows, int cols) {
          this.rows = rows;
          this.cols = cols;
        }
        public TileLocation nextLocation() {
          return new TileLocation((int)(Math.random() * rows), (int)(Math.random() * cols));
        }
    }
    
    //TODO: Unit tests for these?
    public static class NonRepeatingRandomTileLocationImpl {
      private List<TileLocation> list;
      private Iterator<TileLocation> it;

      public NonRepeatingRandomTileLocationImpl(int rows, int cols) {
        this(rows, cols, new ArrayList<TileLocation>());
      }
      
      public NonRepeatingRandomTileLocationImpl(int rows, int cols, 
          List<TileLocation> locationsToExclude) {
        this.list = new ArrayList<>();
        
        for (int i = 0; i < rows; i++) {
          for (int j = 0; j < cols; j++) {
              TileLocation location = new TileLocation(i, j);
              if(!locationsToExclude.contains(location)) {
                list.add(location);
              }
          }
        }
        
        Preconditions.checkArgument(!list.isEmpty());
        Collections.shuffle(list);
        it = list.iterator();
      }
      
      public TileLocation nextLocation() {
        // Reset iterator and list if end of list has been reached.
        if (!it.hasNext()) {
          Collections.shuffle(list);
          it = list.iterator();
        }
        
        return it.next();
      }
  }
    
    public interface RandomCaller {
    	double random();
    }
    
    public static class RandomCallerImpl implements RandomCaller {
    	public double random() {
    		return Math.random();
    	}
    }
    
    // for testing purposes only
    public static class FixedSequenceRandomImpl implements Tile.RandomCaller {
        private int counter = 0;
        private Integer[] sequence;
        private int maxInt;
        
        public FixedSequenceRandomImpl(int maxInt, Integer ... s) {
            sequence = s;
            this.maxInt = maxInt;
        }
        
        @Override
        public double random() {
          Integer i = sequence[counter];
          double result = (double) i / maxInt;
          counter = (counter + 1) % sequence.length;
          return result;
        }
        
    }
    
    // for testing purposes only
	public static class FixedTileColorSequenceRandomImpl implements Tile.RandomCaller {
		private int counter = 0;
		private TileColor[] sequence;
		
		public FixedTileColorSequenceRandomImpl(TileColor ... s) {
			sequence = s;
		}
		
		@Override
		public double random() {
			for(int i = 0; i < TileColor.values().length; i++) {
				if( sequence[counter] == TileColor.values()[i]) {
					double result = (double) i /  TileColor.values().length;
					counter = (counter + 1) % sequence.length;
					return result;
				}
			}
			assert(false);
			return 0;
		}
		
	}

	public static RandomCaller defaultRandomCaller = new Tile.RandomCallerImpl();
    
    public static TileColor getRandomColor() {
    	return getRandomColor(defaultRandomCaller);
    }
    
    public static TileColor getRandomColor(RandomCaller mathSource) {
      TileColor[] tileColorValues = TileColor.values();
      int randomizedIndex = (int)(mathSource.random() * NUM_NORMAL_TILES) % NUM_NORMAL_TILES;
      return tileColorValues[randomizedIndex];
    }
    
    @Override
    public String toString() {
        return "Tile (" + location.row + ", " + location.col + ") has color:" + tileColor;
    }
}
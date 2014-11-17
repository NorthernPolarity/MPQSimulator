package MPQSimulator.Core;

import java.util.Comparator;

import com.google.common.base.Preconditions;

public class Tile implements Comparable<Tile> {
    public static enum TileColor {
      // "Normal tiles"
      BLACK, BLUE, RED, GREEN, YELLOW, PURPLE, TEAMUP,
    }
    // Array index of the last colored tile.
    public static int NUM_COLORED_TILES = 6;
    public static int NUM_NORMAL_TILES = 7;
    
  
    private TileColor tileColor;
    private int row;
    private int col;
    
    public Tile(Tile that) {
      this(that.row, that.col, that.tileColor);
    }
    
    public Tile(int row, int col){
        this(row, col, Tile.getRandomColor());
    }
    
    public Tile(int row, int col, TileColor color) {
      this.row = row;
      this.col = col;
      Preconditions.checkNotNull(color);
      this.tileColor = color;
    }
    
    // destroyTiles depends on this implementation.
    @Override
    public int compareTo(Tile that) {
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
    public int hashCode() {
      return Integer.valueOf(row).hashCode() ^ Integer.valueOf(col).hashCode() ^ tileColor.hashCode();
    }
    
    
    @Override
    public boolean equals(Object obj) {
      if (obj instanceof Tile) {
        Tile that = (Tile) obj;
         if (row == that.row 
            && col == that.col
            && tileColor == that.tileColor) {
           return true;
         }
      }
      return false;
    }
    
    public TileColor getColor(){
        return this.tileColor;
    }
    
    public int getRow(){
        return this.row;
    }
    
    public int getCol(){
        return this.col;
    }
    
    public void setColor(TileColor color){
        Preconditions.checkArgument(color != tileColor);
        this.tileColor = color;
    }
    
    private void setRow(int row){
        this.row = row;
    }
    
    private void setCol(int col){
        this.col = col;
    }
    
    public void changeLocation(int row, int col){
        Preconditions.checkArgument(!(row == this.row && col == this.col));
        this.setRow(row);
        this.setCol(col);
    }
    
    public void randomizeColor(){
        tileColor = Tile.getRandomColor();
    }
    
    // Returns if this tile is adjacent (north south east west) to tile t.
    public boolean adjacent(Tile t) {
      int manhattenDistance = Math.abs(t.col - this.col) + Math.abs(t.row - this.row);
      return manhattenDistance == 1;
    }
    
    // Returns if this tile is horizontally adjacent to tile t (west / east)
    public boolean horizontallyAdjacent(Tile t) {
      return (this.col == t.col) && (Math.abs(t.row - this.row) == 1);
    }
    
    // Returns if this tile is vertically adjacent to tile t (north / south)
    public boolean verticallyAdjacent(Tile t) {
      return (this.row == t.row) && (Math.abs(t.col - this.col) == 1);
    }
    
    public interface RandomCaller {
    	double random();
    }
    
    static class RandomCallerImpl implements RandomCaller {
    	public double random() {
    		return Math.random();
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
        return "Tile (" + row + ", " + col + ") has color:" + tileColor;
    }
}
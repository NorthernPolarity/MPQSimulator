
public class Tile {
	private TileColor mColor;
	private int mRow;
	private int mCol;
	
	public Tile(int row, int col){
		this.randomizeColor();
		this.mRow = row;
		this.mCol = col;
	}
	
	public int compareTo(Tile that){
		if(this.mRow > that.mRow){ return 1; }
		else if(this.mRow < that.mRow){ return -1; }
		else {
			if(this.mCol > that.mCol){ return 1; }
			else if(this.mCol < that.mCol){ return -1; }
			else {
				return 0;
			}
		}
	}
	
	public TileColor getColor(){
		return this.mColor;
	}
	
	public int getRow(){
		return this.mRow;
	}
	
	public int getCol(){
		return this.mCol;
	}
	
	public void setColor(TileColor color){
		this.mColor = color;
	}
	
	public void setRow(int row){
		this.mRow = row;
	}
	
	public void setCol(int col){
		this.mCol = col;
	}
	
	public void changeLocation(int row, int col){
		this.setRow(row);
		this.setCol(col);
	}
	
	public void randomizeColor(){
		TileColor[] tileColorValues = TileColor.values();
		int randomizedIndex = (int)(Math.random() * TileColor.values().length) 
				% TileColor.values().length;
		
		mColor = tileColorValues[randomizedIndex];
	}
}

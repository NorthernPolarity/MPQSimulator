
public class Tile implements Comparable{
    private TileColor tileColor;
    private int row;
    private int col;
    
    public Tile(Tile that) {
        this.tileColor = that.tileColor;
        this.row = that.row;
        this.col = that.col;
    }
    
    public Tile(int row, int col){
        this.randomizeColor();
        this.row = row;
        this.col = col;
    }
    
    @Override
    public int compareTo(Object t) {
        Tile that = (Tile) t;
        if(this.col > that.col){ return 1; }
        else if(this.col < that.col){ return -1; }
        else {
            
            if (this.row > that.row){ return 1; }
            else if (this.row < that.row){ return -1; }
            else {
                return 0;
            }
        }
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
        this.tileColor = color;
    }
    
    public void setRow(int row){
        this.row = row;
    }
    
    public void setCol(int col){
        this.col = col;
    }
    
    public void changeLocation(int row, int col){
        this.setRow(row);
        this.setCol(col);
    }
    
    public void randomizeColor(){
        TileColor[] tileColorValues = TileColor.values();
        int randomizedIndex = (int)(Math.random() * TileColor.values().length) 
            % TileColor.values().length;
        
        tileColor = tileColorValues[randomizedIndex];
    }
    @Override
    public String toString() {
        return "Tile (" + row + ", " + col + ") has color:" + tileColor;
    }
}
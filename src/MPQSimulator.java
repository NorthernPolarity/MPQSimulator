import java.awt.Color;
public class MPQSimulator {
    public static final double X_SCALING = 1;
    public static final double Y_SCALING = 1;
 
    private static void drawGameBoard(GameBoard board) {
        Tile[][] tiles = board.getBoardState();
        for (int x = 0; x < tiles.length; x++) {
          Tile[] col = tiles[x]; 
          for (int y = 0; y < col.length; y++) {
            Tile curTile = col[y];
            StdDraw.setPenColor(MPQSimulator.tileColorToColor(curTile.getColor()));
            
            double xHalfLength = (0.5 / tiles.length) * X_SCALING;
            double yHalfLength = (0.5 / tiles.length) * X_SCALING;
            double xCenter = xHalfLength + ((double) x / tiles.length) * X_SCALING;
            double yCenter = yHalfLength + ((double) y / tiles.length) * Y_SCALING;
            StdDraw.filledRectangle(xCenter, yCenter, xHalfLength, yHalfLength);
          }
        }
    }
    
    private static final Color tileColorToColor(TileColor color) {
        Color c;
        switch (color) {
          case BLACK:
              c = Color.BLACK;
              break;
          case BLUE:
              c = Color.BLUE;
              break;
          case RED:
              c = Color.RED;
              break;
          case GREEN:
              c = Color.GREEN;
              break;
          case YELLOW:
              c = Color.YELLOW;
              break;
          case PURPLE:
              c = Color.MAGENTA;
              break;
          case ENVIRONMENTAL:
              c = Color.WHITE;
              break;
          default:
              c = Color.PINK;
        }
        return c;
    }
    
    
    public static void main(String[] args) {
      StdDraw.setXscale(0, MPQSimulator.X_SCALING);
      StdDraw.setYscale(0, MPQSimulator.Y_SCALING);
      GameBoard board = new GameBoard();
      drawGameBoard(board);
      
      while (true) {
          if (StdDraw.mousePressed()) {
              board.findMatchesOnBoard();
              drawGameBoard(board);
          }
      }
    }
    
}
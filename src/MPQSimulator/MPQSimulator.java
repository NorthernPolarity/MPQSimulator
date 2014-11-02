package MPQSimulator;
import java.awt.Color;
import java.util.Map;

import com.google.common.collect.ImmutableMap;
public class MPQSimulator {
    public static final double X_SCALING = 1;
    public static final double Y_SCALING = 1;
    private static final Map<TileColor, Color> tileColorToColorMap = 
        ImmutableMap.<TileColor, Color>builder()
            .put(TileColor.BLACK, Color.BLACK)
            .put(TileColor.BLUE, Color.BLUE)
            .put(TileColor.RED, Color.RED)
            .put(TileColor.GREEN, Color.GREEN)
            .put(TileColor.YELLOW, Color.YELLOW)
            .put(TileColor.PURPLE, Color.MAGENTA)
            .put(TileColor.TEAMUP, Color.WHITE)
            .build();
 
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
        return tileColorToColorMap.get(color);
    }
    
    
    public static void main(String[] args) {
      StdDraw.setXscale(0, MPQSimulator.X_SCALING);
      StdDraw.setYscale(0, MPQSimulator.Y_SCALING);
      GameBoard board = new GameBoard(8, 8);
      drawGameBoard(board);
      
      while (true) {
          if (StdDraw.mousePressed()) {
              board.findMatchesOnBoard();
              drawGameBoard(board);
          }
      }
    }
    
}
package MPQSimulator.Graphics;
import java.awt.Color;
import java.util.Map;

import MPQSimulator.Core.GameBoardImpl;
import MPQSimulator.Core.Tile;
import MPQSimulator.Core.Tile.TileColor;
import MPQSimulator.ThirdParty.StdDraw;

import com.google.common.collect.ImmutableMap;
public class MPQSimulatorGraphics {
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
    
    public MPQSimulatorGraphics() {
      StdDraw.setXscale(0, MPQSimulatorGraphics.X_SCALING);
      StdDraw.setYscale(0, MPQSimulatorGraphics.Y_SCALING);
    }
 
    private static void drawGameBoard(GameBoardImpl board) {
        Tile[][] tiles = board.getBoardState();
        for (int x = 0; x < tiles.length; x++) {
          Tile[] col = tiles[x]; 
          for (int y = 0; y < col.length; y++) {
            Tile curTile = col[y];
            StdDraw.setPenColor(MPQSimulatorGraphics.tileColorToColor(curTile.getColor()));
            
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
      GameBoardImpl board = new GameBoardImpl(8, 8);
      drawGameBoard(board);
      
      while (true) {
          if (StdDraw.mousePressed()) {
              board.swapTiles(board.getBoardState()[1][2], board.getBoardState()[7][0]);
              //board.findMatchesOnBoard();
              drawGameBoard(board);
          }
      }
    }
    
}
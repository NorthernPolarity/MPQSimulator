package MPQSimulator.Core;

import MPQSimulator.Core.Tile.TileColor;

public class GameBoardSimulation {
  GameEngine engine;

  public GameBoardSimulation() {
    engine = new GameEngine();
  }
  
  public int getNumTiles(TileColor color) {
    return engine.board.getTiles(color).size();
  }
  
}

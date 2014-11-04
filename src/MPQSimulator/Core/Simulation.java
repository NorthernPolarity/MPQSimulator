package MPQSimulator.Core;

import java.util.Map;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Core.Tile.TileColor;

public class Simulation {

  private static final int NUM_ITERATIONS = 1;
  private final GameEngineMoveResults overallResults;

  public Simulation (Ability ability) {
    overallResults = new GameEngineMoveResults();
    
    for (int i = 0; i < NUM_ITERATIONS; i++) {
      GameEngine engine = new GameEngine();
      
      overallResults.add(engine.useAbilityAndStabilizeBoard(ability));
    }
  }
  
  public void printResults() {
    int totalTilesDestroyed = 0;
    Map<TileColor, Integer> tilesDestroyedMap = overallResults.getTilesDestroyedCount();
    
    for (TileColor color : tilesDestroyedMap.keySet()) {
      totalTilesDestroyed += tilesDestroyedMap.get(color);
    }
    
    //System.out.println("Total tiles destroyed: " + totalTilesDestroyed);
   // System.out.println("Average tiles destroyed: " + (double) totalTilesDestroyed / NUM_ITERATIONS);
  }
  
}

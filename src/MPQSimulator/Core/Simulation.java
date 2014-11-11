package MPQSimulator.Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Core.Tile.TileColor;

public class Simulation {

  private static final int NUM_ITERATIONS = 500000;
  private final List<GameEngineMoveResults> overallResults;

  public Simulation (Ability ability) {
    overallResults = new ArrayList<>();
    
    for (int i = 0; i < NUM_ITERATIONS; i++) {
      GameEngine engine = new GameEngine();
      
      overallResults.add(engine.useAbilityAndStabilizeBoard(ability));
    }
  }
  
  private List<Integer> getTilesDestroyedByRun(List<GameEngineMoveResults> resultsList) {
    List<Integer> list = new ArrayList<>();
    for (GameEngineMoveResults results : resultsList) {
      list.add(results.getNumTilesDestroyed());
    }
    return list;
  }
  
  private int getTotalTilesDestroyed(List<GameEngineMoveResults> resultsList) {
    int i = 0;
    for (GameEngineMoveResults results : resultsList) {
      i += results.getNumTilesDestroyed();
    }
    return i;
  }
  
  private Map<TileColor, Integer> getTotalTilesDestroyedByColor(List<GameEngineMoveResults> resultsList) {
    GameEngineMoveResults overallResults = new GameEngineMoveResults();
    for (GameEngineMoveResults results : resultsList) {
      overallResults.add(results);
    }
    return overallResults.getTilesDestroyedCount();
  }
  
  public void printResults() {
    //System.out.println("Tiles Destroyed by run:");

    int whiffs = 0;
    List<Integer> destroyedTilesList = getTilesDestroyedByRun(overallResults);
    for (Integer i : destroyedTilesList) {
      if (i == 0) {
        whiffs++;
      }
      //System.out.print(i + ", ");
    }
    //System.out.println();
    
    int totalTilesDestroyed = getTotalTilesDestroyed(overallResults);

    System.out.println("Probability of a cascade occurring: " + (1 - ((double) whiffs / NUM_ITERATIONS)));
    //System.out.println("Total tiles destroyed: " + totalTilesDestroyed);
    System.out.println("Average tiles destroyed: " + (double) totalTilesDestroyed / NUM_ITERATIONS);
    Map<TileColor, Integer> colorMap = getTotalTilesDestroyedByColor(overallResults);
    System.out.println("Average Tiles destroyed by color:");
    for (TileColor c : colorMap.keySet()) {
      System.out.println(c + ": " + ((double)colorMap.get(c) / NUM_ITERATIONS) + " tiles destroyed");
    }
  }
  
}

package MPQSimulator.Core;

import MPQSimulator.Abilities.Ability;

public interface Simulation {
  public void printResults();
  
  public interface SimulationFactory {
    Simulation create(Ability ability);
    Simulation create(Ability[] abilities);
  }
}

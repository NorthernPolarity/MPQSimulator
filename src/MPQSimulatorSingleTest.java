import MPQSimulator.Core.Simulation;
import MPQSimulator.MPQCharacters.MPQCharacter;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;
import MPQSimulator.MPQCharacters.SingleTileDestroyed;


public class MPQSimulatorSingleTest {

  public static void main(String[] args) {
    MPQCharacter torch = new SingleTileDestroyed();
    Simulation sim = new Simulation(torch.getAbility2(AbilityLevel.ONE));
    sim.printResults();
    
   /*MPQCharacter thor = new DevilDino();
    for (int i = 0; i < 5; i++) {
      Simulation sim = new Simulation(thor.getAbility3(AbilityLevel.values()[i]));
      sim.printResults();
    }*/
  }
}

import MPQSimulator.Core.Simulation;
import MPQSimulator.MPQCharacters.DakenClassic;
import MPQSimulator.MPQCharacters.DevilDino;
import MPQSimulator.MPQCharacters.JuggernautClassic;
import MPQSimulator.MPQCharacters.MPQCharacter;
import MPQSimulator.MPQCharacters.Mystique;
import MPQSimulator.MPQCharacters.SingleTileDestroyed;
import MPQSimulator.MPQCharacters.ThorModern;
import MPQSimulator.MPQCharacters.TorchClassic;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;


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

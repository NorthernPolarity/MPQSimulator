import MPQSimulator.Core.Simulation;
import MPQSimulator.MPQCharacters.MPQCharacter;
import MPQSimulator.MPQCharacters.TorchClassic;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;


public class MPQSimulatorSingleTest {

  public static void main(String[] args) {
    MPQCharacter torch = new TorchClassic();
    Simulation sim = new Simulation(torch.getAbility1(AbilityLevel.ONE));
    sim.printResults();
  }
}

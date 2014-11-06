import MPQSimulator.Core.Simulation;
import MPQSimulator.MPQCharacters.MPQCharacter;
import MPQSimulator.MPQCharacters.Mystique;
import MPQSimulator.MPQCharacters.ThorModern;
import MPQSimulator.MPQCharacters.TorchClassic;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;


public class MPQSimulatorSingleTest {

  public static void main(String[] args) {
    MPQCharacter torch = new ThorModern();
    Simulation sim = new Simulation(torch.getAbility1(AbilityLevel.ONE));
    sim.printResults();
    
   /* MPQCharacter thor = new ThorModern();
    for (int i = 0; i < 5; i++) {
      Simulation sim = new Simulation(thor.getAbility2(AbilityLevel.values()[i]));
      sim.printResults();
    }*/
  }
}

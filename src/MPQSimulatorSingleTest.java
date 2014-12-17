import MPQSimulator.Abilities.Ability;
import MPQSimulator.Core.Simulation;
import MPQSimulator.MPQCharacters.DevilDino;
import MPQSimulator.MPQCharacters.LokiDarkReign;
import MPQSimulator.MPQCharacters.MPQCharacter;
import MPQSimulator.MPQCharacters.MagnetoClassic;
import MPQSimulator.MPQCharacters.Punisher;
import MPQSimulator.MPQCharacters.WolverineXforce;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;
import MPQSimulator.MPQCharacters.SingleTileDestroyed;


public class MPQSimulatorSingleTest {

  public static void main(String[] args) {
    /*MPQCharacter torch = new Punisher();
    Simulation sim = new Simulation(torch.getAbility2(AbilityLevel.ONE));
    sim.printResults();*/
    
    MPQCharacter thor = new WolverineXforce();
    for (int i = 0; i < 5; i++) {
      Simulation sim = new Simulation(thor.getAbility1(AbilityLevel.values()[i]));
      sim.printResults();
    }
    
    /*MPQCharacter thor = new LokiDarkReign();
    MPQCharacter mags = new MagnetoClassic();
    Ability polarizing = mags.getAbility2(AbilityLevel.ONE);
    for (int i = 0; i < 5; i++) {
      Ability illusions = thor.getAbility2(AbilityLevel.values()[i]);
      Simulation sim = new Simulation(polarizing, illusions);
      sim.printResults();
    } */
  }
}

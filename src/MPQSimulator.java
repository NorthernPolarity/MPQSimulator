import MPQSimulator.Characters.Character;
import MPQSimulator.Characters.Character.AbilityLevel;
import MPQSimulator.Characters.LokiDarkReign;
import MPQSimulator.Characters.MagnetoClassic;
import MPQSimulator.Core.Simulation;


public class MPQSimulator {

  public static void main(String[] args) {
    Character cmags = new MagnetoClassic();
    Character loki = new LokiDarkReign();
    for (int i = 0; i < 5; i++) {
   // Simulation sim = new Simulation(cmags.getAbility3(AbilityLevel.values()[i]));
      Simulation sim = new Simulation(loki.getAbility2(AbilityLevel.values()[i]));
    sim.printResults();
    }
  }

}

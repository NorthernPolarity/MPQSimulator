import MPQSimulator.Characters.Character;
import MPQSimulator.Characters.LokiDarkReign;
import MPQSimulator.Characters.MagnetoClassic;
import MPQSimulator.Core.Simulation;


public class MPQSimulator {

  public static void main(String[] args) {
    Character cmags = new MagnetoClassic();
    Character loki = new LokiDarkReign();
    Simulation sim = new Simulation(loki.ability2);
    sim.printResults();
  }

}

import MPQSimulator.Characters.Character;
import MPQSimulator.Characters.MagnetoClassic;
import MPQSimulator.Core.Simulation;


public class MPQSimulator {

  public static void main(String[] args) {
    Character cmags = new MagnetoClassic();
    Simulation sim = new Simulation(cmags.ability3);
    sim.printResults();
  }

}

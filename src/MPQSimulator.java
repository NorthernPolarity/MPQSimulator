import MPQSimulator.Core.Simulation;
import MPQSimulator.MPQCharacters.LokiDarkReign;
import MPQSimulator.MPQCharacters.MPQCharacter;
import MPQSimulator.MPQCharacters.MagnetoClassic;
import MPQSimulator.MPQCharacters.StormMohawk;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;


public class MPQSimulator {

  public static void main(String[] args) {
    MPQCharacter cmags = new MagnetoClassic();
    MPQCharacter loki = new LokiDarkReign();
    MPQCharacter storm = new StormMohawk();
    System.out.println("Mohawk Lightning Storm:");
    for (int i = 0; i < 5; i++) {
      Simulation sim = new Simulation(storm.getAbility1(AbilityLevel.values()[i]));
      sim.printResults();
    }
    
    System.out.println("Mohawk Mistress:");
    for (int i = 0; i < 5; i++) {
      Simulation sim = new Simulation(storm.getAbility2(AbilityLevel.values()[i]));
      sim.printResults();
    }
    
    System.out.println("C Mags Blue:");
    Simulation sim = new Simulation(cmags.getAbility3(AbilityLevel.ONE));
    sim.printResults();
    sim = new Simulation(cmags.getAbility3(AbilityLevel.THREE));
    sim.printResults();
    sim = new Simulation(cmags.getAbility3(AbilityLevel.FIVE));
    sim.printResults();
    
    /*System.out.println("C Mags Red:");
    sim = new Simulation(cmags.getAbility2(AbilityLevel.ONE));
    sim.printResults();
    
    System.out.println("Loki:");
    for (int i = 0; i < 5; i++) {
      sim = new Simulation(loki.getAbility2(AbilityLevel.values()[i]));
      sim.printResults();
    }*/
  }

}

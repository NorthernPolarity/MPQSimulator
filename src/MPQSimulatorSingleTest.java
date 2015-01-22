import com.google.inject.Guice;
import com.google.inject.Injector;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Core.MainModule;
import MPQSimulator.Core.Simulation;
import MPQSimulator.Core.Simulation.SimulationFactory;
import MPQSimulator.Core.SimulationImpl;
import MPQSimulator.Core.Tile.TileColor;
import MPQSimulator.MPQCharacters.DevilDino;
import MPQSimulator.MPQCharacters.DoctorDoom;
import MPQSimulator.MPQCharacters.LokiDarkReign;
import MPQSimulator.MPQCharacters.MPQCharacter;
import MPQSimulator.MPQCharacters.MagnetoClassic;
import MPQSimulator.MPQCharacters.Punisher;
import MPQSimulator.MPQCharacters.StormMohawk;
import MPQSimulator.MPQCharacters.WolverineXforce;
import MPQSimulator.MPQCharacters.MPQCharacter.AbilityLevel;
import MPQSimulator.MPQCharacters.SingleTileDestroyed;


public class MPQSimulatorSingleTest {

  public static void main(String[] args) {
    Injector injector = Guice.createInjector(new MainModule());
    SimulationFactory simFactory = injector.getInstance(SimulationFactory.class);
    

    
    /*MPQCharacter torch = new Punisher();
    Simulation sim = new Simulation(torch.getAbility2(AbilityLevel.ONE));
    sim.printResults();*/
    
    
    MPQCharacter thor = new StormMohawk();
    for (int i = 0; i < 5; i++) {
      Ability[] abilities = {thor.getAbility2(AbilityLevel.values()[1]), thor.getAbility1(AbilityLevel.values()[i])};
      Simulation sim = simFactory.create(abilities);
      //Simulation sim = simFactory.create(thor.getAbility2(AbilityLevel.values()[1]), thor.getAbility1(AbilityLevel.values()[i]));
      sim.printResults();
    }
    
    /*int count = 0;
    for (int i = 0; i < 100000; i++) {
    
      GameBoardSimulation sim = new GameBoardSimulation();
      if (sim.getNumTiles(TileColor.RED) >= 11) {
        count++;
      }
    }
    
    System.out.println("Ratio: " + count/100000.0);*/
    
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

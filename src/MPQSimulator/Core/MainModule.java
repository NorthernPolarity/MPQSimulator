package MPQSimulator.Core;

import MPQSimulator.Abilities.Ability;
import MPQSimulator.Abilities.AbilityImpl;
import MPQSimulator.Core.Simulation.SimulationFactory;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.name.Names;

public class MainModule extends AbstractModule {

  @Override
  protected void configure() {

    
    bind(Ability.class).to(AbilityImpl.class);
    bind(GameBoard.class).to(GameBoardImpl.class);
   
    install(new FactoryModuleBuilder()
    .implement(Simulation.class, SimulationImpl.class)
    .build(SimulationFactory.class));

    
    bindConstant()
      .annotatedWith(Names.named(GameEngine.NUM_BOARD_ROWS_STRING)).to(GameEngine.NUM_BOARD_ROWS);
    bindConstant()
      .annotatedWith(Names.named(GameEngine.NUM_BOARD_COLS_STRING)).to(GameEngine.NUM_BOARD_COLS);
  }
}
package MPQSimulatorTests;

import MPQSimulator.Core.GameEngineMoveResults;
import MPQSimulator.Core.GameEngineMoveResultsImpl;

import com.google.inject.Provider;

public class TestUtilities {

  public static class GameEngineMoveResultsProvider implements Provider<GameEngineMoveResults> {
    @Override
    public GameEngineMoveResults get() {
      return new GameEngineMoveResultsImpl();
    }
  }
}

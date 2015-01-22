package MPQSimulatorTests;

import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.GameBoardMatches;
import MPQSimulator.Core.GameBoardMatches.GameBoardMatchesFactory;
import MPQSimulator.Core.GameBoardMatchesImpl;
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
  
  public static class GameBoardMatchesFactoryImpl implements GameBoardMatchesFactory {

    @Override
    public GameBoardMatches create(GameBoard board) {
      return new GameBoardMatchesImpl(board);
    }
    
  }
}

package MPQSimulatorTests;

import MPQSimulator.Core.GameBoard;
import MPQSimulator.Core.GameBoardMatches;
import MPQSimulator.Core.GameBoardMatches.GameBoardMatchesFactory;
import MPQSimulator.Core.GameBoardMatchesImpl;
import MPQSimulator.Core.GameBoardMoveResults;
import MPQSimulator.Core.GameBoardMoveResultsImpl;
import MPQSimulator.Core.GameEngineImpl;
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
  
  public static class GameBoardMoveResultsProvider implements Provider<GameBoardMoveResults> {
    @Override
    public GameBoardMoveResults get() {
      return new GameBoardMoveResultsImpl(GameEngineImpl.NUM_BOARD_ROWS, GameEngineImpl.NUM_BOARD_COLS);
    }
  }
  
  public static class GameBoardMatchesFactoryImpl implements GameBoardMatchesFactory {

    @Override
    public GameBoardMatches create(GameBoard board) {
      return new GameBoardMatchesImpl(board);
    }
    
  }
}

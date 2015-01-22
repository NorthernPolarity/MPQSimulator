package MPQSimulator.Core;

import MPQSimulator.Abilities.Ability;

public interface GameEngine {
  
  public GameEngineMoveResults useAbilityAndStabilizeBoard(Ability ability);

  public GameEngineMoveResults stabilizeBoard();
  
  // Finds and destroys all tiles involved in match 3s+ on the current board.
  public GameEngineMoveResults resolveCurrentBoard();
  
}

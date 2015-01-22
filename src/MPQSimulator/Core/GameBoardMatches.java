package MPQSimulator.Core;

import java.util.Set;

import MPQSimulator.Core.GameBoardMatchesImpl.SingleMatch;


public interface GameBoardMatches {

  public interface GameBoardMatchesFactory {
    public GameBoardMatches create(GameBoard board);
  }
  
  public Set<SingleMatch> getHorizontalMatches();
  
  public Set<SingleMatch> getVerticalMatches();
  
  public Set<Tile> getHorizontalMatchedTiles();
      
  public Set<Tile> getVerticalMatchedTiles();
  
  public Set<Tile> getAllMatchedTiles();
  
  public Set<Integer> getHorizontalMatchFours();
  
  public Set<Integer> getVerticalMatchFours();
}

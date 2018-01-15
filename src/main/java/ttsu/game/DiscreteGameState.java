package ttsu.game;

import ttsu.game.Exceptions.BadLogicException;
import ttsu.game.tictactoe.GameBoard;
import ttsu.game.tictactoe.TicTacToeGameState;

import java.util.HashSet;

public interface DiscreteGameState {
  HashSet<DiscreteGameState> availableStates();
  TicTacToeGameState.Player getCurrentPlayer();
  public GameBoard getGameBoard();
  public Block getLastMove();
  boolean hasWin(TicTacToeGameState.Player p);
  boolean isOver();
  public DiscreteGameState clone();
}

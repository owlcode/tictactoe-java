package ttsu.game;

import ttsu.game.tictactoe.GameBoard;
import ttsu.game.tictactoe.TicTacToeGameState;

import java.util.HashSet;
import java.util.List;

public interface DiscreteGameState {
  HashSet<DiscreteGameState> availableStates();
  TicTacToeGameState.Player getCurrentPlayer();
  public GameBoard getGameBoard();
  boolean hasWin(TicTacToeGameState.Player p);
  boolean isOver();
}

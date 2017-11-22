package ttsu.game.tictactoe;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import ttsu.game.Block;
import ttsu.game.DiscreteGameState;

/**
 * A {@link DiscreteGameState} representing the current state of a TicTacToe game.
 */
public class TicTacToeGameState implements DiscreteGameState {
    public static enum Player {
        O, X;
        public static Player opponentOf(Player player) {
            return player == X ? O : X;
        }
    }

    private final GameBoard board;
    private Player currentPlayer;
    private Block lastMove;

    public TicTacToeGameState() {
        board = new GameBoard();
        currentPlayer = Player.X;
    }

    /**
     * Creates a new instance of a TicTacToe game state with a given board layout and current player.
     *
     * @param board         the current board state
     * @param currentPlayer the current player whose turn it is to make the next move
     */
    public TicTacToeGameState(GameBoard board, Player currentPlayer) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        if (currentPlayer == null) {
            throw new IllegalArgumentException("currentPlayer cannot be null");
        }
        this.board = board;
        this.currentPlayer = currentPlayer;
    }

    /**
     * Creates a deep copy
     */
    public TicTacToeGameState(TicTacToeGameState other) {
        this.board = new GameBoard(other.board);
        this.currentPlayer = other.getCurrentPlayer();
        this.lastMove = other.lastMove;
    }

    @Override
    public List<DiscreteGameState> availableStates() {
        List<Block> availableMoves = board.getOpenPositions();

        List<DiscreteGameState> availableStates = new ArrayList<DiscreteGameState>(availableMoves.size());

        for (Block move : availableMoves) {
            TicTacToeGameState newState = new TicTacToeGameState(this);
            newState.play(move.a, move.b);
            newState.switchPlayer();
            availableStates.add(newState);
        }

        return availableStates;
    }

    /**
     * Gets the current player whose turn it is to make the next move.
     *
     * @return the {@link Player} who gets to make the next move
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Gets the last position that was played on the TicTacToe board.
     *
     * @return a {@link Block} on the TicTacToe board, or null if no moves were taken yet.
     */
    public Block getLastMove() {
        return lastMove;
    }

    public boolean hasWin(Player player) {
        return board.getOpenPositions().isEmpty() && (!currentPlayer.equals(player));
    }

    @Override
    public boolean isOver() {
        return hasWin(Player.O) || hasWin(Player.X) || board.getOpenPositions().isEmpty();
    }

    /**
     * Play a move in the given points of the TicTacToe board with the current player.
     *
     * @return <code>true</code> if this position was playable; <code>false</code> otherwise
     */
    private boolean play(Point a, Point b) {
        if (board.mark(new Block(a,b), currentPlayer)) {
            lastMove = new Block(a, b);
            return true;
        }
        return false;

    }

    public boolean play(Block b) {
        return play(b.a,b.b);
    }

    public GameBoard getGameBoard() {
        return board;
    }

    public void switchPlayer() {
        currentPlayer = Player.opponentOf(currentPlayer);
    }
}

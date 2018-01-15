package ttsu.game.tictactoe;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ttsu.game.*;

public class TicTacToeGameState implements DiscreteGameState {
    public static enum Player {
        O, X, N;

        public static Player opponentOf(Player player) {
            return player == X ? O : X;
        }
    }

    private final GameBoard board;
    private Player currentPlayer;
    private Block lastMove;
    List<Block> availableMoves;

    public TicTacToeGameState() throws IllegalArgumentException {
        board = new GameBoard(Main.DEFAULT_SIZE);
        availableMoves = board.getOpenPositions();
        currentPlayer = Player.X;
    }

    public TicTacToeGameState(int size, ArrayList<Point> excluded) throws TooLongException, IllegalArgumentException {
        board = new GameBoard(size, excluded);
        if(Communication.startTime > Main.TIME_LIMIT) {
            throw new TooLongException();
        }
        availableMoves = board.getOpenPositions();
        currentPlayer = Player.X;
    }

    public TicTacToeGameState(GameBoard board, Player currentPlayer) {
        validate(board, currentPlayer);
        this.board = board;
        availableMoves = board.getOpenPositions();
        this.currentPlayer = currentPlayer;
    }


    public HashSet<DiscreteGameState> availableStates() {
        List<Block> availableMoves = board.getOpenPositions();
        HashSet<DiscreteGameState> availableStates = new HashSet<DiscreteGameState>(availableMoves.size());

        for (Block move : availableMoves) {
            TicTacToeGameState newState = new TicTacToeGameState(this.board, this.currentPlayer);
            if(newState.play(move)) {
                newState.switchPlayer();
                availableStates.add(newState);
            }
        }

        return availableStates;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public Block getLastMove() {
        return lastMove;
    }

    public boolean hasWin(Player player) {
        List<Block> openPositions = board.getOpenPositions();
        if (openPositions.size() == 1) {
            return currentPlayer.equals(player);
        } else if (openPositions.size() == 2) {
            return openPositions.get(0).hasCommonPoint(openPositions.get(1));
        } else if (openPositions.size() == 0) {
            return getCurrentPlayer().equals(player);
        }

        return false;
    }

    public boolean isOver() {
        return hasWin(Player.O) || hasWin(Player.X);
    }

    private void validate(GameBoard board, Player currentPlayer) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
        if (currentPlayer == null) {
            throw new IllegalArgumentException("currentPlayer cannot be null");
        }
    }

    public boolean play(Block b) {
        if (board.mark(b, currentPlayer)) {
            lastMove = b;
            return true;
        }
        return false;

    }

    public GameBoard getGameBoard() {
        return board;
    }

    public void switchPlayer() {
        currentPlayer = Player.opponentOf(currentPlayer);
    }
}

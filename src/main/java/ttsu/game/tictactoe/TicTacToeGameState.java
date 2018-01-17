package ttsu.game.tictactoe;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import ttsu.game.*;
import ttsu.game.Exceptions.BadLogicException;
import ttsu.game.Exceptions.TooLongException;

public class TicTacToeGameState implements DiscreteGameState {
    public static enum Player {
        O, X, N;

        public static Player opponentOf(Player player) {
            return player == X ? O : X;
        }
    }

    private final GameBoard gameBoard;
    private Player currentPlayer;
    private Block lastMove;

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public TicTacToeGameState() throws IllegalArgumentException {
        gameBoard = new GameBoard();
        currentPlayer = Player.X;
    }

    public TicTacToeGameState(int size, ArrayList<Point> excluded) throws TooLongException, IllegalArgumentException {
        gameBoard = new GameBoard(size, excluded);
        if(Communication.startTime > Main.TIME_LIMIT) {
            throw new TooLongException();
        }
        currentPlayer = Player.X;
    }

    public TicTacToeGameState(GameBoard gameBoard, Player currentPlayer, Block lastMove) {
        validate(gameBoard, currentPlayer);
        this.gameBoard = gameBoard.clone();
        this.currentPlayer = currentPlayer;
        this.lastMove = lastMove;
    }

    public TicTacToeGameState clone() {
        return new TicTacToeGameState(this.gameBoard, this.currentPlayer, this.lastMove);
    }

    public HashSet<DiscreteGameState> availableStates() {
        List<Block> availableMoves = gameBoard.getOpenPositions();
        HashSet<DiscreteGameState> availableStates = new HashSet<DiscreteGameState>();

        for (Block move : availableMoves) {
            TicTacToeGameState newState = new TicTacToeGameState(this.gameBoard, this.currentPlayer, this.lastMove);
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
        List<Block> openPositions = gameBoard.getOpenPositions();
        if (openPositions.size() == 1) {
            return getCurrentPlayer().equals(player);
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

    private void validate(GameBoard gameBoard, Player currentPlayer) {
        if (gameBoard == null) {
            throw new IllegalArgumentException("gameBoard cannot be null");
        }
        if (currentPlayer == null) {
            throw new IllegalArgumentException("currentPlayer cannot be null");
        }
    }

    public boolean play(Block b) {
        if (currentPlayer == null) {
            throw new IllegalArgumentException("cannot mark null player");
        }

        if (gameBoard.mark(b)) {
            lastMove = b;
            return true;
        }

        return false;

    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }

    public void switchPlayer() {
        currentPlayer = Player.opponentOf(currentPlayer);
    }
}

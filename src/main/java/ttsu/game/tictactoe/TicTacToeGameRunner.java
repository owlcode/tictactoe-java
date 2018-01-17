package ttsu.game.tictactoe;

import java.io.PrintStream;

import ttsu.game.Block;
import ttsu.game.Exceptions.BadLogicException;
import ttsu.game.Main;
import ttsu.game.ai.GameIntelligenceAgent;
//import ttsu.game.ai.RandomAgent;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

public class TicTacToeGameRunner {
    private TicTacToeGameState game;
    private TicTacToeBoardPrinter boardPrinter;
    private GameIntelligenceAgent<TicTacToeGameState> propabilityAgent;
    private PrintStream printStream;

    public TicTacToeGameRunner(GameIntelligenceAgent<TicTacToeGameState> propabilityAgent,
                               PrintStream printStream) {
        this.game = new TicTacToeGameState();
        this.boardPrinter = new TicTacToeBoardPrinter(printStream);
        this.printStream = printStream;
        this.propabilityAgent = propabilityAgent;
    }

    TicTacToeGameState getGame() {
        return game;
    }

    public void run() throws BadLogicException {

        printStream.println("Game is starting - randomly by " + game.getCurrentPlayer());

        while (!game.isOver()) {
            movePropabilityComputer();
            movePropabilityComputer();
            boardPrinter.printGameBoard(game.getGameBoard());
        }

        printGameOver();
    }

    void movePropabilityComputer() {
        moveAny(propabilityAgent.evaluateNextState(game));
    }

    private void moveAny(TicTacToeGameState state) {
        if (state == null) {
            return;
        }
        Block nextMove = state.getLastMove();
        if(nextMove == null) {
            return;
        }
        game.play(nextMove);
        game.switchPlayer();
    }

    private void printGameOver() throws BadLogicException {
        if (game.hasWin(Player.X)) {
            printStream.println("Player X won.");
        } else if (game.hasWin(Player.O)) {
            printStream.println("Player O won.");
        }
    }
}

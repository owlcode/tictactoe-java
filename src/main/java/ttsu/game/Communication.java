package ttsu.game;

import ttsu.game.Exceptions.TooLongException;
import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.ai.PropabilityAgent;
import ttsu.game.tictactoe.TicTacToeBoardPrinter;
import ttsu.game.tictactoe.TicTacToeGameState;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.awt.*;
import java.util.ArrayList;

public class Communication {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static GameIntelligenceAgent<TicTacToeGameState> propabilityAgent = new PropabilityAgent<TicTacToeGameState>();

    public static long startTime;

    static void Communication() throws IOException, InterruptedException, TooLongException {
        TicTacToeGameState gameState = null;
        TicTacToeBoardPrinter boardPrinter = new TicTacToeBoardPrinter(System.out);
        String input;
        ArrayList<Point> excludedPoints;
        long startTime = System.nanoTime();

        input = br.readLine();

        Main.BOARD_SIZE = Parser.parseBoardSize(input);
        excludedPoints = Parser.parseExcludedPoints(input);
        gameState = new TicTacToeGameState(Main.BOARD_SIZE, excludedPoints);

        if (Main.DEBUG) {
            Out.std(Watcher.timePassedMs(startTime));
        }

        Out.std("OK\r\n");

        while (true) {
            input = br.readLine();

            if (input.equals("stop") || input.equals("STOP"))
                break;
            else if (input.equals("start") || input.equals("START")) {
                // Algorithm is O: O wants to win
                gameState.setCurrentPlayer(TicTacToeGameState.Player.O);
                gameState = propabilityAgent.evaluateNextState(gameState);
                Out.std(gameState.getLastMove().toString() + "\r\n");

            } else {
                Block opponentBlock = Parser.parseOpponentMove(input);

                if (!gameState.getGameBoard().validateBlock(opponentBlock)) {
                    continue;
                }
                gameState.play(opponentBlock);
                gameState.switchPlayer();

                gameState = propabilityAgent.evaluateNextState(gameState);
                Out.std(gameState.getLastMove().toString() + "\r\n");
            }
        }

        if (Main.DEBUG) {
            boardPrinter.printGameBoard(gameState.getGameBoard());
            Out.std("Time: " + Watcher.timePassedMs(startTime));
        }
    }
}


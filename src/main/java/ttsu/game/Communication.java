package ttsu.game;

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
    public static long startTime;

    public static void Communication() throws IOException, InterruptedException {
        TicTacToeGameState gameState = null;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        TicTacToeBoardPrinter boardPrinter = new TicTacToeBoardPrinter(System.out);
        GameIntelligenceAgent<TicTacToeGameState> propabilityAgent = new PropabilityAgent<TicTacToeGameState>();

        String input;
        int boardSize;
        ArrayList<Point> excludedPoints;

        input = br.readLine();

        long startTime = System.nanoTime();

        try {
            boardSize = Parser.parseBoardSize(input);
            excludedPoints = Parser.parseExcludedPoints(input);
            gameState = new TicTacToeGameState(boardSize, excludedPoints);
        } catch (TooLongException e) {
            Out.err("Za duża plansza");
        }

        if (Main.DEBUG) {
            Out.std(Watcher.timePassedMs(startTime));
        }

        Out.std("OK\r\n");

        while (true) {
            input = br.readLine();

            startTime = System.nanoTime();

            if (input.equals("stop") || input.equals("STOP"))
                break;
            else {
                if (input.equals("start") || input.equals("START")) {

                    gameState = propabilityAgent.evaluateNextState(gameState);
                    Out.std(gameState.getLastMove().toString() + "\r\n");

                } else {
                    Block opponentBlock = Parser.parseOpponentMove(input);

                    gameState.getGameBoard().validateBlock(opponentBlock);
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

}

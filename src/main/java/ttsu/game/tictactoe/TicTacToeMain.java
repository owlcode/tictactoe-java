package ttsu.game.tictactoe;

import ttsu.game.Block;
import ttsu.game.Main;
import ttsu.game.Out;
import ttsu.game.ai.GameIntelligenceAgent;
import ttsu.game.ai.PropabilityAgent;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class TicTacToeMain {
    public static HashMap<HashSet<Point>, List<Block>> cache;

    public static void main(String[] args) {
        GameIntelligenceAgent<TicTacToeGameState> propabilityAgent = new PropabilityAgent<TicTacToeGameState>();
        TicTacToeGameRunner game = new TicTacToeGameRunner(propabilityAgent, System.out);

        Main.deserialize();
        try {
            game.run();
        } catch (Exception e) {
            System.err.println(e);
        }

        Main.serialize();
    }

}

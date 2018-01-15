package ttsu.game.tictactoe;

import java.awt.*;
import java.util.*;
import java.util.List;

import ttsu.game.Main;
import ttsu.game.Block;
import ttsu.game.Out;
import ttsu.game.Watcher;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

public class GameBoard {
    private final HashSet<Point> board;

    GameBoard(int size) {
        board = new HashSet<Point>();
    }

    public GameBoard clone() {
        return new GameBoard(Main.BOARD_SIZE, new ArrayList<Point>(board));
    }

    GameBoard(int size, ArrayList<Point> excluded) {
        board = new HashSet<Point>();

        for (Point point : excluded) {
            board.add(point);
        }
    }

    GameBoard(GameBoard other) {
        board = new HashSet<Point>(other.board);
    }

    boolean mark(Block b) {
        validateBlock(b);
        board.add(b.a);
        board.add(b.b);
        return true;
    }


    boolean isEmpty(Point point) throws IllegalArgumentException {
        if (!this.isInsideBoard(point)) {
            throw new IllegalArgumentException();
        }
        return !board.contains(point);
    }

    List<Block> getOpenPositions() throws IllegalArgumentException {
        ArrayList<Block> blocks;

        if(this.board != null && Main.cache.containsKey(GameBoardKey())) {
            return Main.cache.get(GameBoardKey());
        }

        blocks = hardLifting();

        String key = GameBoardKey();
        Main.cache.put(key, blocks);
        return blocks;
    }

    public String GameBoardKey() {
        return this.board.toString() + Main.BOARD_SIZE;
    }

    @Override
    public String toString() {
        return board.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GameBoard)) {
            return false;
        }
        GameBoard other = (GameBoard) obj;
        other.board.equals(((GameBoard) obj).board);
        return true;
    }

    public boolean validateBlock(Block p) {
        for (Point point : board) {
            if (point.equals(p.a) || point.equals(p.b)) {
                return false;
            }
        }
        return p.isConsistent() && isEmpty(p.a) && isEmpty(p.b);
    }

    private boolean isInsideBoard(Point p) {
        return p.x >= 0 && p.x < Main.BOARD_SIZE && p.y >= 0 && p.y < Main.BOARD_SIZE;
    }

    private void validate(Player[][] board) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
    }

    private ArrayList<Point> neighbourPoints(Point point) {
        ArrayList<Point> tempHelper = new ArrayList<Point>();
        Point p1 = new Point(point.x + 1, point.y);
        Point p2 = new Point(point.x, point.y + 1);

        if (isInsideBoard(p1) && isEmpty(p1)) {
            tempHelper.add(p1);
        }

        if (isInsideBoard(p2) && isEmpty(p2)) {
            tempHelper.add(p2);
        }

        return tempHelper;
    }

    private ArrayList<Block> hardLifting() {
        long startTime = System.nanoTime();
        ArrayList<Block> blocks = new ArrayList<Block>();
        for (int row = 0; row < Main.BOARD_SIZE; row++) {
            for (int col = 0; col < Main.BOARD_SIZE; col++) {

                Point current = new Point(row, col);

                if (this.isEmpty(current)) {
                    ArrayList<Point> emptyRightAndBottomNeighbours = neighbourPoints(current);
                    for (Point p : emptyRightAndBottomNeighbours) {
                        blocks.add(new Block(current, p));
                        // No need for further validation cause current isEmpty & neighbourPoints()
                        // returns empty & inside board points, right & bottom of current
                    }
                }
            }
        }

//        if (Main.DEBUG) {
//            Out.std("getOpenPositions GameBoard.java: " + Watcher.timePassedMs(startTime) + "\r\n");
//        }

        return blocks;
    }
}

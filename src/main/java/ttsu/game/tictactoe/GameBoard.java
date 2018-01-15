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
    private final int size;

    public GameBoard(int size) {
        board = new HashSet<Point>();
        this.size = size;
    }

    public GameBoard(int size, ArrayList<Point> excluded) {
        board = new HashSet<Point>();
        this.size = size;

        for (Point point : excluded) {
            board.add(point);
        }
    }

    public int getSize() {
        return this.size;
    }

    public GameBoard(GameBoard other) {
        this.size = other.getSize();
        board = new HashSet<Point>(other.board);
    }

    public boolean mark(Block b, Player player) {
        validateBlock(b);
        return mark(b.a, b.b, player);
    }


    public boolean isEmpty(Point point) throws IllegalArgumentException {
        if (!this.isInsideBoard(point)) {
            throw new IllegalArgumentException();
        }
        return !board.contains(point);
    }

    public List<Block> getOpenPositions() throws IllegalArgumentException {
        ArrayList<Block> blocks = new ArrayList<Block>();

//        if(this.board != null && Main.cache.containsKey(this.board)) {
//            return Main.cache.get(this.board);
//        }

        blocks = hardLifting();

        Main.cache.put(this.board, blocks);
        return blocks;
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
        return p.isConsistent() && isEmpty(p.a) && isEmpty(p.b);
    }

    private boolean isInsideBoard(Point p) {
        return p.x >= 0 && p.x < this.size && p.y >= 0 && p.y < this.size;
    }

    private void validate(Player[][] board) {
        if (board == null) {
            throw new IllegalArgumentException("board cannot be null");
        }
    }

    private boolean mark(Point a, Point b, Player player) {
        validateBlock(new Block(a, b));

        if (player == null) {
            throw new IllegalArgumentException("cannot mark null player");
        }

        for (Point point : board) {
            if (point.equals(a) || point.equals(b)) {
                return false;
            }
        }

        board.add(a);
        board.add(b);
        return true;
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
        for (int row = 0; row < this.size; row++) {
            for (int col = 0; col < this.size; col++) {
                if (Watcher.timePassedMs(startTime) > Main.TIME_LIMIT && blocks.size() != 0) {
                    return blocks;
                }

                Point current = new Point(row, col);

                if (this.isEmpty(current)) {
                    ArrayList<Point> tempHelper = neighbourPoints(current);
                    for (Point p : tempHelper) {
                        if (validateBlock(new Block(current, p))) {
                            blocks.add(new Block(current, p));
                        }
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

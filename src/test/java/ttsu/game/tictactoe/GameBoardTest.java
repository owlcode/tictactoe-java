package ttsu.game.tictactoe;

import static org.fest.assertions.Assertions.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import ttsu.game.Block;
import ttsu.game.Main;
import ttsu.game.tictactoe.GameBoard;
import ttsu.game.tictactoe.TicTacToeGameState.Player;

import java.awt.*;
import java.util.ArrayList;


public class GameBoardTest {

    private GameBoard board;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void setup() {
        Main.BOARD_SIZE = 3;
        board = new GameBoard();
    }

    @Test
    public void ShouldCopy() {

    }

    @Test
    public void shouldBeGood() {
        // Arrange
        ArrayList<Point> except = new ArrayList<Point>();
        except.add(new Point(2,2));
        except.add(new Point(2,3));
        except.add(new Point(3,1));
        except.add(new Point(3,2));
        except.add(new Point(0,1));
        except.add(new Point(0,0));
        except.add(new Point(1,0));
        except.add(new Point(2,0));

        board = new GameBoard(4, except);

        // Act && Assert
        assertThat(board.getOpenPositions()).containsOnly(
                new Block(new Point(0,2), new Point(0,2))
//                new Block(new Point(0,2), new Point(1,2)),
//                new Block(new Point(1,0), new Point(2,0)),
//                new Block(new Point(2,0), new Point(2,1))
        );
    }

    @Test
    public void Should_Return_Without_Exception_Points() {
        // Arrange
        ArrayList<Point> except = new ArrayList<Point>();
        except.add(new Point(0,0));
        except.add(new Point(1,1));
        except.add(new Point(2,2));

        board = new GameBoard(3, except);

        // Act && Assert
        assertThat(board.getOpenPositions()).containsOnly(
                new Block(new Point(0,1), new Point(0,2)),
                new Block(new Point(0,2), new Point(1,2)),
                new Block(new Point(1,0), new Point(2,0)),
                new Block(new Point(2,0), new Point(2,1))
        );
    }

    @Test
    public void Should_Return_Without_Exception_Points_2() {
        // Arrange
        ArrayList<Point> except = new ArrayList<Point>();
        except.add(new Point(0,0));
        except.add(new Point(1,1));

        board = new GameBoard(3, except);

        // Act && Assert
        assertThat(board.getOpenPositions()).containsOnly(
                new Block(new Point(0,1), new Point(0,2)),
                new Block(new Point(0,2), new Point(1,2)),
                new Block(new Point(1,0), new Point(2,0)),
                new Block(new Point(2,0), new Point(2,1)),
                new Block(new Point(1,2), new Point(2,2)),
                new Block(new Point(2,1), new Point(2,2))
                );
    }

    // -- constructor

    @Test
    public void copyConstructor() {
        Point p1, p2, p3, p4;
        p1 = new Point(0, 0);
        p2 = new Point(0, 1);
        p3 = new Point(1, 0);
        p4 = new Point(1, 1);

        board.mark(new Block(p1, p2));
        GameBoard newBoard = new GameBoard(board);

        assertThat(newBoard.isEmpty(p1)).isFalse();
        assertThat(newBoard.isEmpty(p2)).isFalse();

        newBoard.mark(new Block(p3, p4));

        assertThat(newBoard.isEmpty(p3)).isFalse();
        assertThat(newBoard.isEmpty(p4)).isFalse();

    }

    // -- getMark
    @Test
    public void getMarkUnmarked() {
        assertThat(board.isEmpty(new Point(0, 0))).isTrue();
    }

    @Test
    public void getMarkOffBoard() {
        thrown.expect(IllegalArgumentException.class);
        board.isEmpty(new Point(3, 3));
    }

    @Test
    public void getMarkOffBoardNegative() {
        thrown.expect(IllegalArgumentException.class);
        board.isEmpty(new Point(-1, 0));
    }

    // -- mark

    @Test
    public void markOnBoard() {
        Point p1, p2;
        p1 = new Point(0, 0);
        p2 = new Point(0, 1);
        boolean success = board.mark(new Block(p1, p2));

        assertThat(success).isTrue();
        assertThat(board.isEmpty(p1)).isFalse();
        assertThat(board.isEmpty(p2)).isFalse();
    }

    @Test
    public void markTwice() {
        Point p1, p2;
        p1 = new Point(0, 0);
        p2 = new Point(0, 1);
        board.mark(new Block(p1, p2));
        boolean success = board.mark(new Block(p1, p2));

        assertThat(success).isFalse();
    }

    @Test
    public void markOffBoard() {
        Point p1, p2;
        p1 = new Point(4, 0);
        p2 = new Point(4, 1);

        thrown.expect(IllegalArgumentException.class);
        board.mark(new Block(p1, p2));
    }

//    // -- getOpenPositions
//
@Test
public void getOpenPositionsAll() {
    board = new GameBoard();

    Point p1, p2, p3, p4, p5, p6, p7, p8, p9;
    p1 = new Point(0, 0);
    p2 = new Point(1, 0);
    p3 = new Point(2, 0);
    p4 = new Point(0, 1);
    p5 = new Point(1, 1);
    p6 = new Point(2, 1);
    p7 = new Point(0, 2);
    p8 = new Point(1, 2);
    p9 = new Point(2, 2);

    assertThat(board.getOpenPositions().size()).isEqualTo(12);
    assertThat(board.getOpenPositions()).containsOnly(
            new Block(p1, p2),
            new Block(p2, p3),
            new Block(p4, p5),
            new Block(p5, p6),
            new Block(p7, p8),
            new Block(p8, p9),
            new Block(p1, p4),
            new Block(p2, p5),
            new Block(p3, p6),
            new Block(p4, p7),
            new Block(p5, p8),
            new Block(p6, p9)
    );
}

    @Test
    public void getOpenPositionsAllFor2Size() {
        Main.BOARD_SIZE = 2;
        board = new GameBoard();

        Point p1, p2, p3, p4;
        p1 = new Point(0, 0);
        p2 = new Point(1, 0);
        p3 = new Point(0, 1);
        p4 = new Point(1, 1);

        assertThat(board.getOpenPositions().size()).isEqualTo(4);
        assertThat(board.getOpenPositions()).containsOnly(
                new Block(p1, p2),
                new Block(p1, p3),
                new Block(p3, p4),
                new Block(p2, p4)
        );
    }

//    @Test
//    public void getOpenPositions() {
//        board.mark(0, 0, Player.X);
//        assertThat(board.getOpenPositions()).containsOnly(new Block(0, 1), new Block(0, 2),
//                new Block(1, 0), new Block(1, 1), new Block(1, 2), new Block(2, 0),
//                new Block(2, 1), new Block(2, 2));
//    }
}

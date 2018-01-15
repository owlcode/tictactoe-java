package ttsu.game;

import java.awt.*;

public final class Block implements java.io.Serializable {
    public final Point a;
    public final Point b;

    public Block(Point a, Point b) {
        this.a = a;
        this.b = b;
    }

    public boolean hasCommonPoint(Block block) {
        return (block.a == a && block.b == b) || (block.a == b && block.b == a);
    }

    public boolean isConsistent() {
        return a.distance(b) == 1.0;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Block)) {
            return false;
        }
        Block other = (Block) obj;
        return a.equals(other.a) && b.equals(other.b);
    }

    @Override
    public String toString() {
        return (this.a.x) + "x" + (this.a.y) + "_" +
                (this.b.x) + "x" + (this.b.y);
    }

    public Block fromString(String str) {
        String[] points = str.split("_");

        return new Block(
                new Point(
                        Integer.parseInt(points[0].split("x")[0]),
                        Integer.parseInt(points[0].split("x")[1])
                ),
                new Point(
                        Integer.parseInt(points[1].split("x")[0]),
                        Integer.parseInt(points[1].split("x")[1])
                )
        );
    }
}

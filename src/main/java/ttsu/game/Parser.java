package ttsu.game;

import ttsu.game.ai.PropabilityAgent;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class Parser {
    static int parseBoardSize(String input) {
        return Integer.parseInt(input.split("_")[0]);
    }

    static Block parseOpponentMove(String input) {
        String[] splitted = input.split("_");

        Point p1,p2;
        Block block;

        p1 = new Point(
                Integer.parseInt(splitted[0].split("x")[0]),
                Integer.parseInt(splitted[0].split("x")[1])
        );
        p2 = new Point(
                Integer.parseInt(splitted[1].split("x")[0]),
                Integer.parseInt(splitted[1].split("x")[1])
        );

        return new Block(p1,p2);
    }

    static ArrayList<Point> parseExcludedPoints(String input) {
        String[] values = input.split("_");
        ArrayList<Point> output = new ArrayList<Point>();

        for (int i = 1; i < values.length; i++) {
            String[] numbers = values[i].split("x");
            output.add(
                    new Point(
                            Integer.parseInt(numbers[0]),
                            Integer.parseInt(numbers[1])
                    )
            );
        }

        return output;
    }

    public static Object detachReference(Object current) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);
        oos.writeObject(current);
        oos.flush();
        oos.close();
        bos.close();
        byte[] byteData = bos.toByteArray();
        ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
        return new ObjectInputStream(bais).readObject();
    }
}

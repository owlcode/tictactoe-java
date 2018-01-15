package ttsu.game;

import java.awt.*;
import java.util.List;

public class Out {
    public static void std(String output) {
        System.out.print(output);
    }

    public static void std(double[] output) {
        int iterator = 0;
        Out.std("[");
        for (iterator = 0; iterator < output.length; iterator++) {
            Out.std(output[iterator] + (output.length - 1 == iterator ? "" : ", "));

        }
        Out.std("]\n");
    }

    public static void err(String o) {System.err.println(o);}

    public static void std(long output) {
        System.out.print(output);
    }

    public static <T> void std(List<T> output) {
        int iterator = 0;
        Out.std("[");
        for (iterator = 0; iterator < output.size(); iterator++) {
            Out.std(output.get(iterator).toString() + (output.size() - 1 == iterator ? "" : ", "));

        }
        Out.std("]\n");
    }

    public static void stdPointList(List<Point> output) {
        int iterator = 0;
        for (iterator = 0; iterator < output.size(); iterator++) {
            Out.std(output.get(iterator).getX() + "\t" + output.get(iterator).getY() + "\n");

        }
    }
}

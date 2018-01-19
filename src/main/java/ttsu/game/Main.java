package ttsu.game;

import ttsu.game.Exceptions.TooLongException;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class Main {
    static boolean DEBUG = false;
    public static int DEFAULT_SIZE = 5;
    public static int PROP_DEPTH = 100;
    public static long TIME_LIMIT = 250;
    public static int BOARD_SIZE = 4;
    public static Map<
            String,
            List<Block>
            > cache;

    private static final String cacheDir = "/Users/dawidsowa/work/tictactoe-java/cache/";

    public static void main(String[] args) {
        cache = new HashMap<String, List<Block>>();
        //        deserialize();

        try {
            Communication.Communication();
        } catch (Exception e) {
            System.err.println("Wrong argument: \r\n" + Arrays.toString(e.getStackTrace()));
        }
//        } catch (IOException e) {
//            System.err.println("IO Exception: \r\n" + Arrays.toString(e.getStackTrace()));
//        } catch (TooLongException e) {
//            Out.err("TOO LOONG");
//        } catch (InterruptedException e) {
//            Out.err("Interrupted");
//        }

//        serialize();
    }

    public static void deserialize() {
        try {
            FileInputStream fileIn = new FileInputStream(Main.cacheDir + "open-positions");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            // todo: make sure objects are properly created
            cache = (Map<String, List<Block>>) in.readObject();
            in.close();
            fileIn.close();
            if (Main.DEBUG) {
                Out.std("Deserialization success, readed " + cache.size() + " elements\r\n");
            }
        } catch (Exception i) {
            Out.err(i.getMessage());
        }
    }

    public static void serialize() {
        try {
            FileOutputStream fileOut = new FileOutputStream(Main.cacheDir + "open-positions");
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(cache);
            out.close();
            fileOut.close();
            if (Main.DEBUG) {
                Out.std("Serialization success, written " + cache.size() + " elements");
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}

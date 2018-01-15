package ttsu.game;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Main {
    public static int DEFAULT_SIZE = 5;
    public static int PROP_DEPTH = 100;
    public static boolean DEBUG = false;
    public static long TIME_LIMIT = 200;
    public static final String cacheDir = "/Users/dawidsowa/work/tictactoe-java/cache/";
    public static HashMap<HashSet<Point>, List<Block>> cache = new HashMap<HashSet<Point>, List<Block>>();

    public static void main(String[] args) {
//        deserialize();

        try {
            Communication.Communication();
        } catch (IllegalArgumentException e) {
            System.err.println("Wrong argument: \r\n" + Arrays.toString(e.getStackTrace()));
        } catch (IOException e) {
            System.err.println("IO Exception: \r\n" + Arrays.toString(e.getStackTrace()));
        } catch (Exception e) {
            System.err.println("ANY EXCEPTION: \r\n" + Arrays.toString(e.getStackTrace()));
        }

//        serialize();
    }

    public static void deserialize() {
        try {
            FileInputStream fileIn = new FileInputStream(Main.cacheDir + "open-positions");
            ObjectInputStream in = new ObjectInputStream(fileIn);
            cache = (HashMap<HashSet<Point>, List<Block>>) in.readObject();
            in.close();
            fileIn.close();
            if(Main.DEBUG) {
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
            if(Main.DEBUG) {
                Out.std("Serialization success, written " + cache.size() + " elements");
            }
        } catch (IOException i) {
            i.printStackTrace();
        }
    }
}

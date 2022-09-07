package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class UI {
    public static final int widthOfUI = 40;
    public static final int heightOfUI = 60;
    protected static int xOfPlayer = 0;
    protected static int yOfPlayer = 0;
    protected static boolean hasWonTheGame = false;
    protected static boolean hasPausedTheGame = false;

    public static void printOpeningMessage() {
        StdDraw.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
        StdDraw.setPenColor(Color.white);
        StdDraw.text(20, 40, "CS61B: THE GAME");

        StdDraw.setFont(new Font(Font.MONOSPACED, Font.BOLD, 20));
        StdDraw.text(20, 27, "New Game (N)");
        StdDraw.text(20, 25, "Load Game (L)");
        StdDraw.text(20, 23, "Quit (Q)");
    }

    public static void drawOpeningScene() {
        StdDraw.clear(Color.black);

        printOpeningMessage();

        StdDraw.show();
    }

    public static String solicitNCharsInput(int n) {
        char[] userInput = new char[n];
        int numOfLettersLeft = n, indexOfCurLetter = 0;
        while (numOfLettersLeft > 0) {
            if (StdDraw.hasNextKeyTyped()){
                char curLetter = StdDraw.nextKeyTyped();
                userInput[indexOfCurLetter++] = curLetter;
                numOfLettersLeft--;
            }
        }
        String res = String.copyValueOf(userInput);
        StdDraw.clear(Color.black);
        printOpeningMessage();
        StdDraw.text(20, 21, res);
        StdDraw.show();
        return res;
    }

    public static long solicitSeedInput() {
        List<Character> listOfChars = new ArrayList<>();
        char current = '0';
        while (current != 'S' && current != 's') {
            if (StdDraw.hasNextKeyTyped()) {
                current = StdDraw.nextKeyTyped();
                if (current != 'S' && current != 's') listOfChars.add(current);
            }
        }
        long res = charsToLong(listOfChars);
        StdDraw.clear(Color.black);
        printOpeningMessage();
        StdDraw.text(18, 21, "N");
        StdDraw.text(22, 21, String.valueOf(res));
        StdDraw.show();
        StdDraw.pause(1000);
        return res;
    }

    public static long charsToLong(List<Character> lst) {
        int index = 0, len = lst.size();
        long res = 0;
        while (index < len) {
            res *= 10;
            res += (lst.get(index) - '0');
            index++;
        }
        return res;
    }

    public static int[] getMousePressedPoint() {
        if (StdDraw.isMousePressed()) return new int[]{(int) StdDraw.mouseX(), (int) StdDraw.mouseY()};
        return null;
    }

    public static void move(TETile[][] world) throws IOException {
        if (StdDraw.hasNextKeyTyped()) {
            char input = StdDraw.nextKeyTyped();
            if (isDirectionInput(input)) moveDirection(world, input);
            else if (input == ':') {
                while (!StdDraw.hasNextKeyTyped()) {}
                char nextInput = StdDraw.nextKeyTyped();
                if (nextInput == 'q' || nextInput == 'Q') save(world);
            }
        }
    }

    public static boolean isDirectionInput(char input) {
        return input == 'w' || input == 'W' || input == 'a' || input == 'A'
                || input == 's' || input == 'S' || input == 'd' || input == 'D';
    }

    public static void moveDirection(TETile[][] world, char input) {
        if (input == 'w' || input == 'W') {
            if (world[xOfPlayer][yOfPlayer + 1].equals(Tileset.FLOOR)) {
                world[xOfPlayer][yOfPlayer] = Tileset.FLOOR;
                world[xOfPlayer][yOfPlayer + 1] = Tileset.PLAYER;
                yOfPlayer++;
            }
            else if (world[xOfPlayer][yOfPlayer + 1].equals(Tileset.UNLOCKED_DOOR)) {
                world[xOfPlayer][yOfPlayer] = Tileset.FLOOR;
                world[xOfPlayer][yOfPlayer + 1] = Tileset.PLAYER;
                yOfPlayer++;
                hasWonTheGame = true;
            }
        }
        else if (input == 'a' || input == 'A') {
            if (world[xOfPlayer - 1][yOfPlayer].equals(Tileset.FLOOR)) {
                world[xOfPlayer][yOfPlayer] = Tileset.FLOOR;
                world[xOfPlayer - 1][yOfPlayer] = Tileset.PLAYER;
                xOfPlayer--;
            }
            else if (world[xOfPlayer - 1][yOfPlayer].equals(Tileset.UNLOCKED_DOOR)) {
                world[xOfPlayer][yOfPlayer] = Tileset.FLOOR;
                world[xOfPlayer - 1][yOfPlayer] = Tileset.PLAYER;
                xOfPlayer--;
                hasWonTheGame = true;
            }
        }
        else if (input == 's' || input == 'S') {
            if (world[xOfPlayer][yOfPlayer - 1].equals(Tileset.FLOOR)) {
                world[xOfPlayer][yOfPlayer] = Tileset.FLOOR;
                world[xOfPlayer][yOfPlayer - 1] = Tileset.PLAYER;
                yOfPlayer--;
            }
            else if (world[xOfPlayer][yOfPlayer - 1].equals(Tileset.UNLOCKED_DOOR)) {
                world[xOfPlayer][yOfPlayer] = Tileset.FLOOR;
                world[xOfPlayer][yOfPlayer - 1] = Tileset.PLAYER;
                yOfPlayer--;
                hasWonTheGame = true;
            }
        }
        else if (input == 'd' || input == 'D') {
            if (world[xOfPlayer + 1][yOfPlayer].equals(Tileset.FLOOR)) {
                world[xOfPlayer][yOfPlayer] = Tileset.FLOOR;
                world[xOfPlayer + 1][yOfPlayer] = Tileset.PLAYER;
                xOfPlayer++;
            }
            else if (world[xOfPlayer + 1][yOfPlayer].equals(Tileset.UNLOCKED_DOOR)) {
                world[xOfPlayer][yOfPlayer] = Tileset.FLOOR;
                world[xOfPlayer + 1][yOfPlayer] = Tileset.PLAYER;
                xOfPlayer++;
                hasWonTheGame = true;
            }
        }
    }

    public static void save(TETile[][] world) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("world.txt"));
        oos.writeInt(UI.xOfPlayer);
        oos.writeInt(UI.yOfPlayer);
        oos.writeObject(world);
        oos.close();
        hasPausedTheGame = true;
    }
}

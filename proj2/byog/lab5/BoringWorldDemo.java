package byog.lab5;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *  Draws a world that is mostly empty except for a small region.
 */
public class BoringWorldDemo {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 30;

    private static int[] lenSequence(int len) {
        int[] lens = new int[2 * len];
        int lenOfRow = len;
        for (int i = 0; i < len; ++i) {
            lens[i] = lenOfRow;
            lenOfRow += 2;
        }
        lenOfRow -= 2;
        for (int i = len; i < 2 * len; ++i) {
            lens[i] = lenOfRow;
            lenOfRow -= 2;
        }
        return lens;
    }

    @Test
    public void testLenSequence() {
        int[] lensOf2 = lenSequence(2);
        int[] expected2 = {2, 4, 4, 2};
        assertArrayEquals(lensOf2, expected2);
        int[] lensOf5 = lenSequence(5);
        int[] expected5 = {5, 7, 9, 11, 13, 13, 11, 9, 7, 5};
        assertArrayEquals(lensOf5, expected5);
    }

    private static int updateX(int x, int i, int len) {
        if (i < len - 1) return x - 1;
        else if (i == len - 1) return x;
        else return x + 1;
    }

    public static void addHexagon(TETile[][] tiles, int len, int startX, int startY, TETile pattern) {
        int width = tiles.length, height = tiles[0].length;
        try{
            int[] lens = lenSequence(len);
            int x = startX, y = startY;
            for (int i = 0; i < lens.length; ++i) {
                int curRowLength = lens[i];
                for (int j = x; j < x + curRowLength; ++j)
                    tiles[j][y] = pattern;
                x = updateX(x, i, len);
                y++;
            }
        }
        catch (ArrayIndexOutOfBoundsException e){
            System.out.println("Error: improper starting position or insufficient space");
        }
    }

    private static int[] startXs(int X, int len) {
        int[] res = new int[19];
        int x = X;
        // mid one
        for (int i = 0; i < 5; ++i)
            res[i] = x;
        // left 2
        x = x - (2 * len - 1);
        for (int i = 5; i < 9; ++i)
            res[i] = x;
        x = x - (2 * len - 1);
        for (int i = 9; i < 12; ++i)
            res[i] = x;
        // right 2
        x = X + (2 * len - 1);
        for (int i = 12; i < 16; ++i)
            res[i] = x;
        x = x + (2 * len - 1);
        for (int i = 16; i < 19; ++i)
            res[i] = x;
        return res;
    }

    @Test
    public void testStartXs() {
        int[] Xs = startXs(30, 3);
        int[] expectedXs = {30, 30, 30, 30, 30, 25, 25, 25, 25, 20, 20, 20, 35, 35, 35, 35, 40, 40, 40};
        assertEquals(expectedXs.length, 19);
        assertArrayEquals(Xs, expectedXs);
    }


    private static int[] startYs(int Y, int len) {
        int[] res = new int[19];
        int y = Y;
        for (int i = 0; i < 5; ++i) {
            res[i] = y;
            y += 2 * len;
        }
        y = Y + len;
        for (int i = 5; i < 9; ++i) {
            res[i] = y;
            y += 2 * len;
        }
        y = Y + 2 * len;
        for (int i = 9; i < 12; ++i) {
            res[i] = y;
            y += 2 * len;
        }
        y = Y + len;
        for (int i = 12; i < 16; ++i) {
            res[i] = y;
            y += 2 * len;
        }
        y = Y + 2 * len;
        for (int i = 16; i < 19; ++i) {
            res[i] = y;
            y += 2 * len;
        }
        return res;
    }

    @Test
    public void testStartYs() {
        int[] Ys = startYs(0, 3);
        int[] expectedYs = {0, 6, 12, 18, 24, 3, 9, 15, 21, 6, 12, 18, 3, 9, 15, 21, 6, 12, 18};
        assertEquals(expectedYs.length, 19);
        assertArrayEquals(Ys, expectedYs);
    }

    public static void addTesselation(TETile[][] tiles, int len, int startX, int startY) {
        int[] Xs = startXs(startX, len);
        int[] Ys = startYs(startY, len);
        TETile[] patterns = {Tileset.WALL, Tileset.FLOWER, Tileset.FLOOR, Tileset.GRASS, Tileset.MOUNTAIN};
        for (int i = 0; i < 19; ++i)
            addHexagon(tiles, len, Xs[i], Ys[i], patterns[i % patterns.length]);
    }

    public static void main(String[] args) {
        // initialize the tile rendering engine with a window of size WIDTH x HEIGHT
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        // initialize tiles
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        addTesselation(world, 3, 30, 0);
        // draws the world to the screen
        ter.renderFrame(world);

    }


}

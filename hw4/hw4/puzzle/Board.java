package hw4.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Board implements WorldState{
    private final int N;
    private final int[][] tiles;
    private int hamming = -1;
    private int manhattan = -1;
    private int xOfZero, yOfZero;
    public Board(int[][] tiles) {
        this.N = tiles.length;
        this.tiles = new int[tiles.length][tiles[0].length];
        for (int i = 0; i < tiles.length; ++i)
            for (int j = 0; j < tiles[0].length; ++j) {
                this.tiles[i][j] = tiles[i][j];
                if (tiles[i][j] == 0) {
                    xOfZero = i;
                    yOfZero = j;
                }
            }
    }
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }
    public int size() {
        return N;
    }

    public Iterable<WorldState> neighbors() {
        int[][] newTiles = new int[N][N];
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                newTiles[i][j] = tiles[i][j];
        Set<WorldState> res = new HashSet<>();
        if (xOfZero > 0) {
            swap(xOfZero, yOfZero, xOfZero - 1, yOfZero, newTiles);
            res.add(new Board(newTiles));
            swap(xOfZero, yOfZero, xOfZero - 1, yOfZero, newTiles);
        }
        if (xOfZero < N - 1) {
            swap(xOfZero, yOfZero, xOfZero + 1, yOfZero, newTiles);
            res.add(new Board(newTiles));
            swap(xOfZero, yOfZero, xOfZero + 1, yOfZero, newTiles);
        }
        if (yOfZero > 0) {
            swap(xOfZero, yOfZero - 1, xOfZero, yOfZero, newTiles);
            res.add(new Board(newTiles));
            swap(xOfZero, yOfZero - 1, xOfZero, yOfZero, newTiles);
        }
        if (yOfZero < N - 1) {
            swap(xOfZero, yOfZero, xOfZero, yOfZero + 1, newTiles);
            res.add(new Board(newTiles));
            swap(xOfZero, yOfZero, xOfZero, yOfZero + 1, newTiles);
        }
        return res;
    }

    private void swap(int x1, int y1, int x2, int y2, int[][] array) {
        int swap = array[x1][y1];
        array[x1][y1] = array[x2][y2];
        array[x2][y2] = swap;
    }

    public int hamming() {
        if (hamming == -1) {
            hamming = 0;
            for (int i = 0; i < N; ++i)
                for (int j = 0; j < N; ++j)
                    if (tiles[i][j] != 0) hamming += (tiles[i][j] == i * N + j + 1? 0:1);
        }
        return hamming;
    }
    public int manhattan() {
        if (manhattan == -1) {
            manhattan = 0;
            for (int i = 0; i < N; ++i)
                for (int j = 0; j < N; ++j)
                    if (tiles[i][j] != 0) {
                        int destRow = (tiles[i][j] - 1) / N, destCol = (tiles[i][j] - 1) % N;
                        manhattan += (Math.abs(destRow - i) + Math.abs(destCol - j));
                    }
        }
        return manhattan;
    }
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board boardY = (Board) y;
        if (size() != boardY.size()) return false;
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j)
                if (tileAt(i, j) != boardY.tileAt(i, j)) return false;
        return true;
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}

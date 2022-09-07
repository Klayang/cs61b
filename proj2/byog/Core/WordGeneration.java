package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Arrays;
import java.util.Random;

public class WordGeneration {
    // The size of the world and random seed cannot be changed
    protected static final int WIDTH = 80;
    protected static final int HEIGHT = 30;
    protected int initialXOfPlayer;
    protected int initialYOfPlayer;
    private static final int ZERO = 0;
    private static final int smallestLengthOfSide = 2;
    private static final int largestLengthOfSide = 10;
    private static final int smallestNumOfRooms = 7;
    private static final int largestNumOfRooms = 10;
    private final int numOfRooms;
    private final int numOfHallways;
    private final Random random;
    private boolean[][] marked = new boolean[80][30];
    private TETile[][] world = new TETile[80][30];
    private Room[] rooms;
    private Hallway[] hallways;
    public WordGeneration(long seed) {
        random = new Random(seed);
        numOfRooms = RandomUtils.uniform(random, smallestNumOfRooms, largestNumOfRooms);
        numOfHallways = numOfRooms - 1;
        rooms = new Room[numOfRooms];
        hallways = new Hallway[numOfHallways];
        initializeWorld();
        buildRooms();
        buildHallways();
        buildWalls();
        buildCharacter();
        buildUnlockedDoor();
    }

    public TETile[][] world() {
        return world;
    }

    /* marked every slot in world to be Tileset.Nothing */
    private void initializeWorld() {
        for (int i = 0; i < WIDTH; ++i)
            for (int j = 0; j < HEIGHT; ++j)
                world[i][j] = Tileset.NOTHING;
    }

    /* generate 4 rooms with random size in random location */
    private void buildRooms() {
        for (int i = 0; i < numOfRooms; ++i){
            boolean isNotValid = true;
            while (isNotValid) {
                int x = RandomUtils.uniform(random, ZERO + 1, WIDTH);
                int y = RandomUtils.uniform(random, ZERO + 1, HEIGHT);
                int w = RandomUtils.uniform(random, smallestLengthOfSide, largestLengthOfSide);
                int h = RandomUtils.uniform(random, smallestLengthOfSide, largestLengthOfSide);
                if (x + w <= WIDTH - 1 && y + h <= HEIGHT - 1) {
                    boolean hasOverlapped = false;
                    for (int j = 0; j < i; ++j)
                        if (rooms[j].overlap(x, y, x + w - 1, y + w - 1)) {
                            hasOverlapped = true;
                            break;
                        }
                    if (hasOverlapped) continue;
                    isNotValid = false;
                    rooms[i] = new Room(x, y, w, h);
                    mark(x, y, x + w - 1, y + h - 1, Tileset.FLOOR);
                }
            }
        }
//        Arrays.sort(rooms);
    }

    /* generate 3 hallways between 4 rooms */
    private void buildHallways() {
        for (int i = 0; i < numOfHallways; ++i) {
            Room curRoom = rooms[i], nextRoom = rooms[i + 1];
            int curX = curRoom.centerX, curY = curRoom.centerY, nextX = nextRoom.centerX, nextY = nextRoom.centerY;
            int intersectionX = curX + (nextX - curX) / 2;
            mark(curX, curY, intersectionX, curY, Tileset.FLOOR);
            mark(intersectionX, curY, intersectionX, nextY, Tileset.FLOOR);
            mark(intersectionX, nextY, nextX, nextY, Tileset.FLOOR);
            hallways[i] = new Hallway(curX, curY, nextX, nextY, intersectionX);
        }
    }

    /* build walls for rooms and hallways */
    private void buildWalls() {
        // build walls for rooms
        for (int i = 0; i < numOfRooms; ++i) {
            Room curRoom = rooms[i];
            int x = curRoom.startX, y = curRoom.startY, w = curRoom.width, h = curRoom.height;
            buildWallHelper(x, y, w, h);
        }
        // build walls for hallways
        for (int i = 0; i < numOfHallways; ++i) {
            Hallway hw = hallways[i];
            int startX = hw.startX, startY = hw.startY, endX = hw.endX, endY = hw.endY, intersectionX = hw.intersectionX;
            buildWallHelper(Math.min(startX, intersectionX), startY, Math.abs(startX - intersectionX) + 1, 1);
            buildWallHelper(intersectionX, Math.min(startY, endY), 1, Math.abs(startY - endY) + 1);
            buildWallHelper(Math.min(intersectionX, endX), endY, Math.abs(endX - intersectionX) + 1, 1);
        }
    }

    private void buildWallHelper(int x, int y, int w, int h) {
        mark(x - 1, y - 1, x - 1, y + h, Tileset.WALL); // left wall
        mark(x - 1, y + h, x + w, y + h, Tileset.WALL); // up wall
        mark(x + w, y + h, x + w, y - 1, Tileset.WALL); // right wall
        mark(x + w, y - 1, x - 1, y - 1, Tileset.WALL); // down wall
    }

    private void buildCharacter() {
        int x = 0, y = 0;
        while (!world[x][y].equals(Tileset.FLOOR)) {
            x = RandomUtils.uniform(random, ZERO + 1, WIDTH);
            y = RandomUtils.uniform(random, ZERO + 1, HEIGHT);
        }
        initialXOfPlayer = x;
        initialYOfPlayer = y;
        world[x][y] = Tileset.PLAYER;
    }

    private void buildUnlockedDoor() {
        int x = 0, y = 0;
        while (!world[x][y].equals(Tileset.WALL)) {
            x = RandomUtils.uniform(random, ZERO + 1, WIDTH);
            y = RandomUtils.uniform(random, ZERO + 1, HEIGHT);
        }
        world[x][y] = Tileset.UNLOCKED_DOOR;
    }


    /* mark a certain area to given symbol */
    private void mark(int startX, int startY, int targetX, int targetY, TETile pattern) {
        int x = startX;
        while (true) {
            int y = startY;
            while (true) {
                if (!marked[x][y]) {
                    world[x][y] = pattern;
                    marked[x][y] = true;
                }
                if (y < targetY) ++y;
                else if (y > targetY) --y;
                else break;
            }
            if (x < targetX) ++x;
            else if (x > targetX) --x;
            else break;
        }
    }

    public static void main(String[] args) {
        WordGeneration wg = new WordGeneration(3454);
        TETile[][] world = wg.world();
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        ter.renderFrame(world);
    }
}

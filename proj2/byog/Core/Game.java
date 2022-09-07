package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.algs4.StdDraw;

import java.io.*;

public class Game {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() throws IOException, ClassNotFoundException {
        ter.initialize(UI.widthOfUI, UI.heightOfUI);
        UI.drawOpeningScene();
        String option = UI.solicitNCharsInput(1);
        if (option.equals("N") || option.equals("n")) playGame(null);
        else if (option.equals("L") || option.equals("l")) {
            TETile[][] savedWorld = getSavedWord();
            playGame(savedWorld);
        }
        else if (option.equals("Q") || option.equals("q")) {}
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TODO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
//        int start = -1, end = -1;
//        for (int i = 0; i < input.length(); ++i) {
//            if (input.charAt(i) == 'N' || input.charAt(i) == 'n')
//                start = i + 1;
//            else if (input.charAt(i) == 'S' || input.charAt(i) == 's') {
//                end = i;
//                break;
//            }
//        }
        try{
            int i = 1; TETile[][] world = null;
            if (input.charAt(0) == 'n' || input.charAt(0) == 'N') {
                while (i < input.length()) {
                    if (input.charAt(i) == 's' || input.charAt(i) == 'S') break;
                    ++i;
                }
                Long seed = Long.parseLong(input.substring(1, i)); ++i;
                WordGeneration wg = new WordGeneration(seed);
                world = wg.world();
            }
            else if (input.charAt(0) == 'l' || input.charAt(0) == 'L')
                world = getSavedWord();

            while (i < input.length()) {
                char c = input.charAt(i);
                if (UI.isDirectionInput(c)) UI.moveDirection(world, c);
                else if (c == ':') {
                    if (input.charAt(i + 1) == 'q' || input.charAt(i + 1) == 'Q') {
                        UI.save(world);
                        break;
                    }
                }
                ++i;
            }
            return world;
        }
        catch (Exception e) {
            TETile[][] world = new TETile[80][30];
            for (int i = 0; i < 80; ++i)
                for (int j = 0; j < 30; ++j)
                    world[i][j] = Tileset.NOTHING;
            return world;
        }
    }

    private void playGame(TETile[][] world) throws IOException {
        if (world == null){
            long seed = UI.solicitSeedInput();
            WordGeneration wg = new WordGeneration(seed);
            world = wg.world();
            UI.xOfPlayer = wg.initialXOfPlayer; UI.yOfPlayer = wg.initialYOfPlayer;
        }
        ter.initialize(WordGeneration.WIDTH, WordGeneration.HEIGHT);
        ter.renderFrame(world);
        while ((!UI.hasWonTheGame) && (!UI.hasPausedTheGame)){
            String info = null;
//            int[] mousePressedPoint = UI.getMousePressedPoint();
//            String info = null;
//            if (mousePressedPoint != null) {
//                int x = mousePressedPoint[0], y = mousePressedPoint[1];
//                info = world[x][y].description();
//            }
            UI.move(world);
            ter.renderFramePlusHeadInfo(world, info);
        }
        if (UI.hasWonTheGame) ter.renderFramePlusHeadInfo(world, "victory!");
        else ter.renderFramePlusHeadInfo(world, "pause");
    }

    private TETile[][] getSavedWord() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("world.txt"));
        UI.xOfPlayer = ois.readInt();
        UI.yOfPlayer = ois.readInt();
        TETile[][] savedWorld = (TETile[][]) ois.readObject();
        ois.close();
        return savedWorld;
    }
}

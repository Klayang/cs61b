package byog.lab6;


import edu.princeton.cs.algs4.StdDraw;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

public class MemoryGame {
    private int width;
    private int height;
    private int round;
    private Random rand;
    private boolean gameOver;
    private boolean playerTurn;
    private boolean gameHasStarted;
    private static final char[] CHARACTERS = "abcdefghijklmnopqrstuvwxyz".toCharArray();
    private static final String[] ENCOURAGEMENT = {"You can do this!", "I believe in you!",
                                                   "You got this!", "You're a star!", "Go Bears!",
                                                   "Too easy for you!", "Wow, so impressive!"};

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Please enter a seed");
            return;
        }

        int seed = Integer.parseInt(args[0]);
        MemoryGame game = new MemoryGame(40, 40, seed);
        game.startGame();
    }

    public MemoryGame(int width, int height, int seed) {
        /* Sets up StdDraw so that it has a width by height grid of 16 by 16 squares as its canvas
         * Also sets up the scale so the top left is (0,0) and the bottom right is (width, height)
         */
        this.width = width;
        this.height = height;
        StdDraw.setCanvasSize(this.width * 16, this.height * 16);
        Font font = new Font("Monaco", Font.BOLD, 30);
        StdDraw.setFont(font);
        StdDraw.setXscale(0, this.width);
        StdDraw.setYscale(0, this.height);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();

        //TODO: Initialize random number generator
        rand = new Random(seed);
    }

    public String generateRandomString(int n) {
        //TODO: Generate random string of letters of length n
        char[] res = new char[n];
        for (int i = 0; i < n; ++i) {
            char curLetter = CHARACTERS[rand.nextInt(26)];
            res[i] = curLetter;
        }
        return String.copyValueOf(res);
    }

    public void drawFrame(String s) {
        //TODO: Take the string and display it in the center of the screen
        //TODO: If game is not over, display relevant game information at the top of the screen
        StdDraw.clear();
        StdDraw.setFont(new Font(Font.DIALOG_INPUT, Font.BOLD, 30));
        StdDraw.setPenColor();
        if (gameHasStarted) outputRegularMessage();
        StdDraw.text(width / 2, height / 2, s);
        StdDraw.show();
        StdDraw.pause(1000);
    }

    public void outputRegularMessage() {
        String roundMessage = "Round: " + round;
        String hintMessage = "";
        if (playerTurn) hintMessage = "Type!";
        else hintMessage = "Watch!";
        String encouragementMessage = ENCOURAGEMENT[4];
        StdDraw.text(5, 38, roundMessage);
        StdDraw.text(18, 38, hintMessage);
        StdDraw.text(31, 38, encouragementMessage);
        StdDraw.text(0, 35, "--------------------------------------------------------------------------");
    }

    public void flashSequence(String letters) {
        //TODO: Display each character in letters, making sure to blank the screen between letters
        for (int i = 0; i < letters.length(); ++i) {
            String curLetter = String.valueOf(letters.charAt(i));
            drawFrame(curLetter);
        }
        drawFrame(" ");
    }

    public String solicitNCharsInput(int n) {
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
        drawFrame(res);
        return res;
    }

    public void startGame() {
        //TODO: Set any relevant variables before the game starts
        round = 1;
        gameOver = false;
        playerTurn = false;
        gameHasStarted = false;
        //TODO: Establish Game loop
        drawFrame("Game Starts!");
        gameHasStarted = true;
        while (!gameOver) {
            String randomStrOfThisRound = generateRandomString(round);
            flashSequence(randomStrOfThisRound);
            playerTurn = true; drawFrame("");
            String userInput = solicitNCharsInput(round);
            if (!randomStrOfThisRound.equals(userInput)){
                String gameOverMessage = "Game Over! You made it to round: " + round;
                drawFrame(gameOverMessage);
                gameOver = true;
            }
            playerTurn = false;
            round++;
        }
    }

}

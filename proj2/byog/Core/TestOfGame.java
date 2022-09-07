package byog.Core;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestOfGame {
    @Test
    public void testCharsToLong() {
        List<Character> lst = new ArrayList<>();
        lst.add('1'); lst.add('2');
        assertEquals(UI.charsToLong(lst), 12);
        lst.add('3'); lst.add('4');
        assertEquals(UI.charsToLong(lst), 1234);
    }

    @Test
    public void testSerializable() throws IOException, ClassNotFoundException {
        TERenderer ter = new TERenderer();
        ter.initialize(80, 30);
        WordGeneration wg = new WordGeneration(100);
        TETile[][] world = wg.world();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("test.txt"));
        oos.writeObject(world);
        oos.close();
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("test.txt"));
        TETile[][] newWorld = (TETile[][]) ois.readObject();
        ois.close();
        ter.renderFrame(newWorld);
    }
}

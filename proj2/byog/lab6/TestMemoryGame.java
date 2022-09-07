package byog.lab6;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestMemoryGame {
    @Test
    public void testGenerateRandomString() {
        MemoryGame mg1 = new MemoryGame(40, 60, 100), mg2 = new MemoryGame(30, 50, 100);
        String str1 = mg1.generateRandomString(5), str2 = mg2.generateRandomString(5);
        assertEquals(str1.length(), 5);
        assertEquals(str1, str2);
    }

    @Test
    public void testDrawFrame() {
        MemoryGame mg = new MemoryGame(40, 60, 100);
        mg.drawFrame("Yang");
    }

    @Test
    public void testFlashSequence() {
        MemoryGame mg = new MemoryGame(40, 60, 100);
        mg.flashSequence("yang");
    }

    @Test
    public void testSolicitNCharsInput() {
        MemoryGame mg = new MemoryGame(40, 60, 100);
        String res = mg.solicitNCharsInput(4);
        assertEquals(res, "yang");
    }
}

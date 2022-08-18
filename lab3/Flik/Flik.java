import org.junit.Test;

import static org.junit.Assert.*;

/** An Integer tester created by Flik Enterprises. */
public class Flik {
    public static boolean isSameNumber(int a, int b) {
        return a == b;
    }
    @Test
    public void testIsSameNumber(){
        assertTrue(isSameNumber(3, 3));
        assertFalse(isSameNumber(0, -1));
        assertTrue(isSameNumber(129, 129));
    }
}

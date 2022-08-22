import org.junit.Test;

import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    @Test
    public void testOffByN() {
        CharacterComparator cc4 = new OffByN(4);
        CharacterComparator cc1 = new OffByN(1);

        assertTrue(cc4.equalChars('a', 'e'));
        assertTrue(cc4.equalChars('e', 'a'));
        assertFalse(cc4.equalChars('f', 'A'));

        assertTrue(cc1.equalChars('a', 'b'));
        assertFalse(cc1.equalChars('a', 'c'));
    }
}

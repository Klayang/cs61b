import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    }
    @Test
    public void testIsPalindrome() {
        String emptyStr = "";
        String singleCharacterStr = "a";
        String palindromeStr = "abba";
        String unpalindromeStr1 = "abeca";
        String unpalindromeStr2 = "ab";
        assertTrue(palindrome.isPalindrome(emptyStr));
        assertTrue(palindrome.isPalindrome(singleCharacterStr));
        assertTrue(palindrome.isPalindrome(palindromeStr));
        assertFalse(palindrome.isPalindrome(unpalindromeStr1));
        assertFalse(palindrome.isPalindrome(unpalindromeStr2));
        assertFalse(palindrome.isPalindrome(null));
        CharacterComparator cc = new OffByOne();
        assertTrue(palindrome.isPalindrome("flake", cc));
        assertTrue(palindrome.isPalindrome("flke", cc));
        assertFalse(palindrome.isPalindrome("acDb", cc));
        assertTrue(palindrome.isPalindrome("", cc));
        assertFalse(palindrome.isPalindrome(null, cc));
    }
}

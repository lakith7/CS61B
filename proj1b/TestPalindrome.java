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
        assertFalse(palindrome.isPalindrome("dog"));
        assertTrue(palindrome.isPalindrome("racecar"));
        assertTrue(palindrome.isPalindrome(""));
        assertTrue(palindrome.isPalindrome("a"));
        assertFalse(palindrome.isPalindrome("racecar "));
        assertTrue(palindrome.isPalindrome("bb"));
        assertTrue(palindrome.isPalindrome("bnb"));
        assertFalse(palindrome.isPalindrome("gggggggh"));
        assertTrue(palindrome.isPalindrome(" "));
        assertFalse(palindrome.isPalindrome("Aba"));
        assertTrue(palindrome.isPalindrome("AbA"));
    }

    @Test
    public void testIsPalindromeExtended() {
        assertTrue(palindrome.isPalindrome("flake", TestOffByOne.offByOne));
        assertFalse(palindrome.isPalindrome("hello", TestOffByOne.offByOne));
        assertTrue(palindrome.isPalindrome("a", TestOffByOne.offByOne));
        assertTrue(palindrome.isPalindrome("", TestOffByOne.offByOne));
        assertFalse(palindrome.isPalindrome("  ", TestOffByOne.offByOne));
        assertTrue(palindrome.isPalindrome("FLAKE", TestOffByOne.offByOne));
        assertTrue(palindrome.isPalindrome("FlAkE", TestOffByOne.offByOne));
        assertFalse(palindrome.isPalindrome("FLAke", TestOffByOne.offByOne));

    }
}

import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByOne {
    // You must use this CharacterComparator and not instantiate
    // new ones, or the autograder might be upset.
    static CharacterComparator offByOne = new OffByOne();
    Palindrome checker = new Palindrome();

    @Test
    public void testEqualChars() {
        assertTrue(offByOne.equalChars('x', 'y'));
        assertTrue(offByOne.equalChars('y', 'x'));
        assertFalse(offByOne.equalChars('a', 'z'));
        assertFalse(offByOne.equalChars('a', 'q'));
        assertTrue(offByOne.equalChars('&', '%'));
    }

    @Test
    public void testEqualCharsOverloaded() {
        assertTrue(checker.isPalindrome("flake", offByOne));
        assertFalse(checker.isPalindrome("hello", offByOne));
        assertTrue(checker.isPalindrome("a", offByOne));
        assertTrue(checker.isPalindrome("", offByOne));

    }
}
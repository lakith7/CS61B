public class Palindrome {
    public Deque<Character> wordToDeque(String word) {
        Deque<Character> holder =  new LinkedListDeque<>();
        int index = 0;
        while (index < word.length()) {
            holder.addLast(word.charAt(index));
            index += 1;
        }
        return holder;
    }


    public boolean isPalindrome(String word) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        }

        Palindrome checker = new Palindrome();
        Deque check = checker.wordToDeque(word);

        while (check.size() > 1) {
            if (check.removeFirst() != check.removeLast()) {
                return false;
            }
        }

        return true;


    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word.length() == 1 || word.length() == 0) {
            return true;
        }

        Palindrome checker = new Palindrome();
        Deque check = checker.wordToDeque(word);

        while (check.size() > 1) {
            if (cc.equalChars((char) check.removeFirst(), (char) check.removeLast()) == false) {
                return false;
            }
        }

        return true;


    }

}
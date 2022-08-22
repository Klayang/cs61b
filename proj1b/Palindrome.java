public class Palindrome {

    public Deque<Character> wordToDeque(String word){
        Deque<Character> wordDeque = new ArrayDeque<>();
        for (int i = 0; i < word.length(); ++i)
            wordDeque.addLast(word.charAt(i));
        return wordDeque;
    }

    public boolean isPalindrome(String word) {
        if (word == null) return false;
//        int length = word.length();
//        for (int i = 0; i < length; ++i)
//            if (word.charAt(i) != word.charAt(length - i - 1))
//                return false;
//        return true;

        Deque<Character> wordDeque = new Palindrome().wordToDeque(word);
        return isPalindrome(wordDeque);
    }

    private boolean isPalindrome(Deque<Character> wordDeque) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) return true;
        if (wordDeque.removeFirst() != wordDeque.removeLast()) return false;
        return isPalindrome(wordDeque);
    }

    public boolean isPalindrome(String word, CharacterComparator cc) {
        if (word == null) return false;
        Deque<Character> wordDeque = new Palindrome().wordToDeque(word);
        return isPalindrome(wordDeque, new OffByOne());
    }

    private boolean isPalindrome(Deque<Character> wordDeque, CharacterComparator cc) {
        if (wordDeque.size() == 0 || wordDeque.size() == 1) return true;
        if (!cc.equalChars(wordDeque.removeFirst(), wordDeque.removeLast())) return false;
        return isPalindrome(wordDeque, cc);
    }
}

import java.util.Comparator;
import java.util.List;

public class WordComparators {
    private static int compareHelper(String s, char target) {
        int res = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == target) res += 1;
        }
        return res;
    }

    /** Returns a comparator that orders strings by the number of lowercase 'x' characters (ascending). */
    public static Comparator<String> getXComparator() {
        // TODO: Implement this.
        return new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int x1 = 0, x2 = 0;
                x1 = compareHelper(s1, 'x');
                x2 = compareHelper(s2, 'x');
                return x1 - x2;
            }
        };
    }

    /** Returns a comparator that orders strings by the count of the given character (ascending). */
    public static Comparator<String> getCharComparator(char c) {
        // TODO: Implement this.
        return new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int num1, num2;
                num1 = compareHelper(s1, c);
                num2 = compareHelper(s2, c);
                return num1 - num2;
            }
        };
    }

    /** Returns a comparator that orders strings by the total count of the given characters (ascending). */
    public static Comparator<String> getCharListComparator(List<Character> chars) {
        // TODO: Implement this.
        return new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int num1 = 0, num2 = 0;
                for (char c : chars) {
                    num1 += compareHelper(s1, c);
                    num2 += compareHelper(s2, c);
                }
                return num1 - num2;
            }
        };
    }
}

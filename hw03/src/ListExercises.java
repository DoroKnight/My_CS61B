import java.util.ArrayList;
import java.util.List;

public class ListExercises {
    /** Returns the total sum in a list of integers */
    public static int sum(List<Integer> L) {
        // TODO: Implement this method
        int sum = 0;
        for (int i : L) {
            sum += i;
        }
        return sum;
    }

    /** Returns a list containing the even numbers of the given list */
    public static List<Integer> evens(List<Integer> L) {
        // TODO: Implement this method
        List<Integer> evenlst = new ArrayList<>();
        for (int num : L) {
            if ( num % 2 == 0 ) {
                evenlst.add(num);
            }
        }
        return evenlst;
    }

    /** Returns a list containing the common item of the two given lists */
    public static List<Integer> common(List<Integer> L1, List<Integer> L2) {
        // TODO: Implement this method
        List<Integer> result = new ArrayList<>();
        for (int i : L1) {
            for (int j : L2) {
                if ( i == j ) result.add(i);
            }
        }
        return result;
    }

    public static int countOccurrencesOfWord(List<String> words, String w) {
        // TODO: Implement this method
        int sum = 0;
        for (String str : words) {
            if ( str.equals(w) ) {
                sum++;
            }
        }
        return sum;
    }

    /** Returns the number of occurrences of the given character in a list of strings. */
    public static int countOccurrencesOfC(List<String> words, char c) {
        // TODO: Implement this method
        int sum = 0;
        for (String str : words) {
            int len = str.length();
            for ( int i = 0; i < len; i++ ) {
                if ( c == str.charAt(i) )  sum++;
            }
        }
        return sum;
    }
}

import java.util.Map;
import java.util.HashMap;
import java.util.List;

public class MapExercises {
    /** Returns a map from every lower case letter to the number corresponding to that letter, where 'a' is
     * 1, 'b' is 2, 'c' is 3, ..., 'z' is 26.
     */
    public static Map<Character, Integer> letterToNum() {
        // TODO: Implement this method
        String alphaList = "abcdefghijklmnopqrstuvwxyz";
        Map<Character, Integer> letterMap = new HashMap<>();
        for (int i = 0; i < 26; i++) {
            char c = alphaList.charAt(i);
            letterMap.put(c, i + 1);
        }
        return letterMap;
    }

    /** Returns a map from the integers in the list to their squares. For example, if the input list
     *  is [1, 3, 6, 7], the returned map goes from 1 to 1, 3 to 9, 6 to 36, and 7 to 49.
     */
    public static Map<Integer, Integer> squares(List<Integer> nums) {
        // TODO: Implement this method
        Map<Integer, Integer> square = new HashMap<>();
        for ( int num : nums ) {
            square.put( num, num * num );
        }
        return square;
    }

    /** Returns a map of the counts of all words that appear in a list of words. */
    public static Map<String, Integer> countWords(List<String> words) {
        // TODO: Implement this method
        Map<String, Integer> wordTimes = new HashMap<>();
        for ( String word : words ) {
            boolean isExist = false;
            for ( String key : wordTimes.keySet() ) {
                if ( key.equals(word) ) {
                    isExist = true;
                    break;
                }
            }
            if (isExist) {
                wordTimes.put(word, wordTimes.get(word) + 1);
            } else {
                wordTimes.put(word, 1);
            }
        }
        return wordTimes;
    }
}

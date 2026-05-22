import edu.princeton.cs.algs4.In;

import java.util.Comparator;

public class WordFinder {
    /**
     *  Returns the maximum string according to the provider comparator.
     *  If multiple strings are considered equal by c, return the first in
     *  the array.
     *  Use loops. Don't use Collections.max or similar.
     */
    public static String findMax(String[] strings, Comparator<String> c) {
        // TODO: Implement this.
        if (strings.length == 0) return null;
        String returnStr = strings[0];
        for (String str : strings) {
            if (c.compare(returnStr, str) < 0) {
                returnStr = str;
            }
        }
        return returnStr;
    }

    public static void main(String[] args) {
        In in = new In("data/mobydick.txt");
        String[] words = in.readAllStrings();

        // TODO: Print only the word with the most lower case vowels.
        //       Use your findMax method!
        //
        //       Start by creating a Comparator that compares based on lower case vowels.
        Comparator<String> vowelComparator = new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                String targets = "aeiou";
                int num1 = 0, num2 = 0;
                for (int i = 0; i < 5; i++) {
                    for (int j = 0; j < s1.length(); j++) {
                        if (s1.charAt(j) == targets.charAt(i)) num1 += 1;
                    }
                    for (int j = 0; j < s2.length(); j++) {
                        if (s2.charAt(j) == targets.charAt(i)) num2 += 1;
                    }
                }
                return num1 - num2;
            }
        };

        String maxWord = words[0];
        for (int i = 0; i < words.length; i++) {
            if (vowelComparator.compare(maxWord, words[i]) < 0)
                maxWord = words[i];
        }

        System.out.println(maxWord);

        // Optional task: Play around with lists of words from Wikipedia articles.
         String[] zebraWords = ParseUtils.fetchWords("https://en.wikipedia.org/wiki/zebra");
         System.out.println(findMax(zebraWords, vowelComparator));
    }
}

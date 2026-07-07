package main;

import java.util.Collection;
import java.util.TreeMap;
import edu.princeton.cs.algs4.In;

import static main.TimeSeries.MAX_YEAR;
import static main.TimeSeries.MIN_YEAR;

/**
 * An object that provides utility methods for making queries on the
 * Google NGrams dataset (or a subset thereof).
 *
 * An NGramMap stores pertinent data from a "words file" and a "counts
 * file". It is not a map in the strict sense, but it does provide additional
 * functionality.
 *
 * @author Josh Hug
 */
public class NGramMap {

    private TreeMap<String, TimeSeries> wordHistory;
    private TimeSeries yearsHistory;

    /**
     * Constructs an NGramMap from WORDHISTORYFILENAME and YEARHISTORYFILENAME.
     */
    public NGramMap(String wordHistoryFilename, String yearHistoryFilename) {

        In in_Words = new In(wordHistoryFilename);
        In in_Years = new In(yearHistoryFilename);

        wordHistory = new TreeMap<>();
        yearsHistory = new TimeSeries();

        while (!in_Words.isEmpty()) {
            String nextline = in_Words.readLine();
            String[] spiltline = nextline.split("\t");

            String word = spiltline[0];
            int year = Integer.parseInt(spiltline[1]);
            double count = Double.parseDouble(spiltline[2]);

            if (!wordHistory.containsKey(word)) {
                wordHistory.put(word, new TimeSeries());
            }

            TimeSeries ts = wordHistory.get(word);
            ts.put(year, count);
        }

        while (!in_Years.isEmpty()) {
            String nextline = in_Years.readLine();
            String[] spiltline = nextline.split(",");

            int year = Integer.parseInt(spiltline[0]);
            double count = Double.parseDouble(spiltline[1]);

            yearsHistory.put(year, count);
        }
    }

    /**
     * Provides the history of WORD between STARTYEAR and ENDYEAR, inclusive of both ends. The
     * returned TimeSeries should be a copy, not a link to this NGramMap's TimeSeries. In other
     * words, changes made to the object returned by this function should not also affect the
     * NGramMap. This is also known as a "defensive copy". If the word is not in the data files,
     * returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word, int startYear, int endYear) {
        TimeSeries history = wordHistory.get(word);

        if (history == null) return new TimeSeries();

        return new TimeSeries(history, startYear, endYear);
    }

    /**
     * Provides the history of WORD. The returned TimeSeries should be a copy, not a link to this
     * NGramMap's TimeSeries. In other words, changes made to the object returned by this function
     * should not also affect the NGramMap. This is also known as a "defensive copy". If the word
     * is not in the data files, returns an empty TimeSeries.
     */
    public TimeSeries countHistory(String word) {
        return countHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Returns a defensive copy of the total number of words recorded per year in all volumes.
     */
    public TimeSeries totalCountHistory() {
        return new TimeSeries(yearsHistory, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD between STARTYEAR
     * and ENDYEAR, inclusive of both ends. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word, int startYear, int endYear) {
        TimeSeries subWordHistory = countHistory(word, startYear, endYear);
        TimeSeries subYearHistory = new TimeSeries(yearsHistory, startYear, endYear);
        return subWordHistory.dividedBy(subYearHistory);
    }

    /**
     * Provides a TimeSeries containing the relative frequency per year of WORD compared to all
     * words recorded in that year. If the word is not in the data files, returns an empty
     * TimeSeries.
     */
    public TimeSeries weightHistory(String word) {
        return weightHistory(word, MIN_YEAR, MAX_YEAR);
    }

    /**
     * Provides the summed relative frequency per year of all words in WORDS between STARTYEAR and
     * ENDYEAR, inclusive of both ends. If a word does not exist in this time frame, ignore it
     * rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words,
                                          int startYear, int endYear) {
        if (yearsHistory.isEmpty()) return new TimeSeries();

        TimeSeries sumHistory = new TimeSeries();
        for (String word : words) {
            sumHistory = sumHistory.plus(countHistory(word, startYear, endYear));
        }

        return sumHistory.dividedBy(new TimeSeries(yearsHistory, startYear, endYear));
    }

    /**
     * Returns the summed relative frequency per year of all words in WORDS. If a word does not
     * exist in this time frame, ignore it rather than throwing an exception.
     */
    public TimeSeries summedWeightHistory(Collection<String> words) {
        return summedWeightHistory(words, MIN_YEAR, MAX_YEAR);
    }
}

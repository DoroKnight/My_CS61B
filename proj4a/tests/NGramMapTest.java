import main.NGramMap;
import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static main.Main.*;
import static com.google.common.truth.Truth.assertThat;

/** Unit Tests for the NGramMap class.
 *  @author Josh Hug
 */
public class NGramMapTest {
    @Test
    public void testCountHistory() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(2005);
        expectedYears.add(2006);
        expectedYears.add(2007);
        expectedYears.add(2008);

        List<Double> expectedCounts = new ArrayList<>();
        expectedCounts.add(646179.0);
        expectedCounts.add(677820.0);
        expectedCounts.add(697645.0);
        expectedCounts.add(795265.0);

        TimeSeries request2005to2008 = ngm.countHistory("request");
        assertThat(request2005to2008.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2005to2008.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }

        expectedYears = new ArrayList<>();
        expectedYears.add(2006);
        expectedYears.add(2007);
        expectedCounts = new ArrayList<>();
        expectedCounts.add(677820.0);
        expectedCounts.add(697645.0);

        TimeSeries request2006to2007 = ngm.countHistory("request", 2006, 2007);

        assertThat(request2006to2007.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedCounts.size(); i += 1) {
            assertThat(request2006to2007.data().get(i)).isWithin(1E-10).of(expectedCounts.get(i));
        }
    }

    @Test
    public void testOnShortFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE4_FILE, YEAR_HISTORY_FILE);

        // returns the count of the number of occurrences of economically per year between 2000 and 2010.
        TimeSeries econCount = ngm.countHistory("economically", 2000, 2010);
        assertThat(econCount.get(2000)).isWithin(1E-10).of(294258.0);
        assertThat(econCount.get(2010)).isWithin(1E-10).of(222744.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1999)).isWithin(1E-10).of(22668397698.0);

        // returns the relative weight of the word academic in each year between 1999 and 2010.
        TimeSeries academicWeight = ngm.weightHistory("academic", 1999, 2010);
        assertThat(academicWeight.get(1999)).isWithin(1E-7).of(969087.0 / 22668397698.0);
    }

    @Test
    public void testOnLargeFile() {
        // creates an NGramMap from a large dataset
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE14377_FILE, YEAR_HISTORY_FILE);

        // returns the count of the number of occurrences of fish per year between 1850 and 1933.
        TimeSeries fishCount = ngm.countHistory("fish", 1850, 1933);
        assertThat(fishCount.get(1865)).isWithin(1E-10).of(136497.0);
        assertThat(fishCount.get(1922)).isWithin(1E-10).of(444924.0);

        TimeSeries totalCounts = ngm.totalCountHistory();
        assertThat(totalCounts.get(1865)).isWithin(1E-10).of(2563919231.0);

        // returns the relative weight of the word fish in each year between 1850 and 1933.
        TimeSeries fishWeight = ngm.weightHistory("fish", 1850, 1933);
        assertThat(fishWeight.get(1865)).isWithin(1E-7).of(136497.0 / 2563919231.0);

        TimeSeries dogCount = ngm.countHistory("dog", 1850, 1876);
        assertThat(dogCount.get(1865)).isWithin(1E-10).of(75819.0);

        List<String> fishAndDog = new ArrayList<>();
        fishAndDog.add("fish");
        fishAndDog.add("dog");
        TimeSeries fishPlusDogWeight = ngm.summedWeightHistory(fishAndDog, 1865, 1866);

        double expectedFishPlusDogWeight1865 = (136497.0 + 75819.0) / 2563919231.0;
        assertThat(fishPlusDogWeight.get(1865)).isWithin(1E-10).of(expectedFishPlusDogWeight1865);
    }

    @Test
    public void testMissingWordsReturnEmptyTimeSeries() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);

        assertThat(ngm.countHistory("thiswordshouldnotexist").years()).isEmpty();
        assertThat(ngm.countHistory("thiswordshouldnotexist", 2005, 2008).years()).isEmpty();
        assertThat(ngm.weightHistory("thiswordshouldnotexist").years()).isEmpty();
        assertThat(ngm.weightHistory("thiswordshouldnotexist", 2005, 2008).years()).isEmpty();
    }

    @Test
    public void testOutOfRangeHistoriesAreEmpty() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);

        assertThat(ngm.countHistory("request", 1990, 1999).years()).isEmpty();
        assertThat(ngm.weightHistory("airport", 2005, 2006).years()).isEmpty();
    }

    @Test
    public void testCountHistoryReturnsDefensiveCopy() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);

        TimeSeries requestHistory = ngm.countHistory("request");
        requestHistory.put(2005, -1.0);
        requestHistory.put(2009, 9000.0);

        TimeSeries requestHistoryAgain = ngm.countHistory("request");
        assertThat(requestHistoryAgain.get(2005)).isWithin(1E-10).of(646179.0);
        assertThat(requestHistoryAgain.containsKey(2009)).isFalse();
    }

    @Test
    public void testTotalCountHistoryReturnsDefensiveCopy() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);

        TimeSeries totalCounts = ngm.totalCountHistory();
        totalCounts.put(2007, -1.0);

        TimeSeries totalCountsAgain = ngm.totalCountHistory();
        assertThat(totalCountsAgain.get(2007)).isWithin(1E-10).of(28307904288.0);
    }

    @Test
    public void testSummedWeightHistoryIgnoresMissingWords() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);

        List<String> words = new ArrayList<>();
        words.add("request");
        words.add("thiswordshouldnotexist");
        words.add("airport");

        TimeSeries summedWeight = ngm.summedWeightHistory(words, 2006, 2008);

        double expected2006 = 677820.0 / 27695491774.0;
        double expected2007 = (697645.0 + 175702.0) / 28307904288.0;
        double expected2008 = (795265.0 + 173294.0) / 28752030034.0;

        assertThat(summedWeight.get(2006)).isWithin(1E-10).of(expected2006);
        assertThat(summedWeight.get(2007)).isWithin(1E-10).of(expected2007);
        assertThat(summedWeight.get(2008)).isWithin(1E-10).of(expected2008);
    }

    @Test
    public void testSummedWeightHistoryWithOnlyMissingWordsIsEmpty() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);

        List<String> words = new ArrayList<>();
        words.add("thiswordshouldnotexist");
        words.add("thiswordalsoshouldnotexist");

        TimeSeries summedWeight = ngm.summedWeightHistory(words, 2006, 2008);

        assertThat(summedWeight.years()).isEmpty();
        assertThat(summedWeight.data()).isEmpty();
    }

}

package main;

import org.checkerframework.checker.units.qual.A;

import java.util.List;
import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.ArrayList;

/**
 * An object for mapping a year number (e.g. 1996) to numerical data. Provides
 * utility methods useful for data analysis.
 *
 * @author Josh Hug
 */
public class TimeSeries extends TreeMap<Integer, Double> {

    /** If it helps speed up your code, you can assume year arguments to your NGramMap
     * are between 1400 and 2100. We've stored these values as the constants
     * MIN_YEAR and MAX_YEAR here. */
    public static final int MIN_YEAR = 1400;
    public static final int MAX_YEAR = 2100;

    /**
     * Constructs a new empty TimeSeries.
     */
    public TimeSeries() {
        super();
    }

    /**
     * Creates a copy of TS, but only between STARTYEAR and ENDYEAR,
     * inclusive of both end points.
     */
    public TimeSeries(TimeSeries ts, int startYear, int endYear) {
        super();
        NavigableMap<Integer, Double> subSeries = ts.subMap(startYear, true, endYear, true);
        this.putAll(subSeries);
    }

    /**
     *  Returns all years for this time series in ascending order.
     */
    public List<Integer> years() {
        List<Integer> yearSeries = new ArrayList<>();
        yearSeries.addAll(this.keySet());

        return yearSeries;
    }

    /**
     *  Returns all data for this time series. Must correspond to the
     *  order of years().
     */
    public List<Double> data() {
        List<Double> dataSeries = new ArrayList<>();
        dataSeries.addAll(this.values());
        return dataSeries;
    }

    /**
     * Returns the year-wise sum of this TimeSeries with the given TS. In other words, for
     * each year, sum the data from this TimeSeries with the data from TS. Should return a
     * new TimeSeries (does not modify this TimeSeries).
     *
     * If both TimeSeries don't contain any years, return an empty TimeSeries.
     * If one TimeSeries contains a year that the other one doesn't, the returned TimeSeries
     * should store the value from the TimeSeries that contains that year.
     */
    public TimeSeries plus(TimeSeries ts) {
        if (this.isEmpty()) {
            if (ts.isEmpty()) return new TimeSeries();
            else return new TimeSeries(ts, ts.firstKey(), ts.lastKey());
        } else if (ts.isEmpty()) {
            return new TimeSeries(this, this.firstKey(), this.lastKey());
        }

        TimeSeries newTS = new TimeSeries();
        List<Integer> year1 = this.years();
        List<Integer> year2 = ts.years();
        List<Double> data1 = this.data();
        List<Double> data2 = ts.data();

        int i = 0, j = 0;
        int maxSize1 = year1.size(), maxSize2 = year2.size();

        while (i < maxSize1 && j < maxSize2) {
            if (year1.get(i).equals(year2.get(j))) {
                newTS.put(year1.get(i), data1.get(i) + data2.get(j));
                i += 1;
                j += 1;
            } else if (year1.get(i) < year2.get(j)) {
                newTS.put(year1.get(i), data1.get(i));
                i += 1;
            } else {
                newTS.put(year2.get(j), data2.get(j));
                j += 1;
            }
        }

        if (i < maxSize1) {
            for (; i < maxSize1; i += 1) {
                newTS.put(year1.get(i), data1.get(i));
            }
        }
        if (j < maxSize2) {
            for (; j < maxSize2; j += 1) {
                newTS.put(year2.get(j), data2.get(j));
            }
        }

        return newTS;
    }

    /**
     * Returns the quotient of the value for each year this TimeSeries divided by the
     * value for the same year in TS. Should return a new TimeSeries (does not modify this
     * TimeSeries).
     *
     * If TS is missing a year that exists in this TimeSeries, throw an
     * IllegalArgumentException.
     * If TS has a year that is not in this TimeSeries, ignore it.
     */
    public TimeSeries dividedBy(TimeSeries ts) {
        TimeSeries newTS = new TimeSeries();
        List<Integer> year1 = this.years();
        List<Integer> year2 = ts.years();
        List<Double> data1 = this.data();
        List<Double> data2 = ts.data();

        int i = 0, j = 0;
        int maxSize1 = year1.size(), maxSize2 = year2.size();

        if (maxSize1 > maxSize2) {
            throw new IllegalArgumentException();
        }

        while (i < maxSize1 && j < maxSize2) {
            if (year1.get(i) < year2.get(j)) {
                throw new IllegalArgumentException();
            } else if (year1.get(i).equals(year2.get(j))){
                newTS.put(year1.get(i), data1.get(i) / data2.get(j));
                i += 1;
                j += 1;
            } else {
                j += 1;
            }
        }

        if (i < maxSize1) {
            throw new IllegalArgumentException();
        }

        return newTS;
    }

}

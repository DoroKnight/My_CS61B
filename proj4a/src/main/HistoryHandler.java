package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;
import main.TimeSeries;
import browser.Plotter;
import org.knowm.xchart.XYChart;

import java.util.List;
import java.util.ArrayList;

public class HistoryHandler extends NgordnetQueryHandler{

    public static final int YEAR_1400 = 1400;
    public static final int YEAR_1500 = 1500;
    public static final int NUM_1000 = 1000;
    public static final int NUM_500 = 500;
    public static final double NUM_100 = 100.0;
    public static final double NUM_1450 = 1450.0;

    private NGramMap ngm;

    public HistoryHandler(NGramMap map) {
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        ArrayList<TimeSeries> lts = new ArrayList<>();
        ArrayList<String> labels = new ArrayList<>();

        for (String word : words) {
            labels.add(word);
            lts.add(ngm.weightHistory(word, startYear, endYear));
        }

        XYChart chart = Plotter.generateTimeSeriesChart(labels, lts);
        String encodedImage = Plotter.encodeChartAsString(chart);

        return encodedImage;
    }
}

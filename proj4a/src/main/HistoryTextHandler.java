package main;

import browser.NgordnetQuery;
import browser.NgordnetQueryHandler;

import java.util.List;

public class HistoryTextHandler extends NgordnetQueryHandler{

    private NGramMap ngm;

    public HistoryTextHandler(NGramMap map) {
        this.ngm = map;
    }

    @Override
    public String handle(NgordnetQuery q) {
        List<String> words = q.words();
        int startYear = q.startYear();
        int endYear = q.endYear();

        String response = "";
        for (String word : words) {
            response += word;
            response += ": ";
            response += this.ngm.weightHistory(word, startYear, endYear);
            response += "\n";
        }

        return response;
    }
}

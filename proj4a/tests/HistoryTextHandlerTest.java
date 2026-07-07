
import browser.NgordnetQuery;
import main.HistoryHandler;
import main.HistoryTextHandler;
import main.NGramMap;

import org.junit.jupiter.api.Test;
import java.util.Base64;
import java.util.List;

import static main.Main.*;
import static com.google.common.truth.Truth.assertThat;

public class HistoryTextHandlerTest {
    @Test
    public void testHandle() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
        HistoryTextHandler handler = new HistoryTextHandler(ngm);
        NgordnetQuery query = new NgordnetQuery(List.of("request", "airport"), 2006, 2007, 0);
        String actual = handler.handle(query);
        String expected = """
                request: {2006=2.44740192927834E-5, 2007=2.464488338318067E-5}
                airport: {2007=6.2068176510855946E-6}
                """;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testHandleWithMissingWordAndTrailingNewline() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
        HistoryTextHandler handler = new HistoryTextHandler(ngm);
        NgordnetQuery query = new NgordnetQuery(List.of("airport", "thiswordshouldnotexist"), 2007, 2008, 0);
        String actual = handler.handle(query);
        String expected = """
                airport: {2007=6.2068176510855946E-6, 2008=6.0271918120242455E-6}
                thiswordshouldnotexist: {}
                """;
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void testHistoryHandlerReturnsPngImageString() {
        NGramMap ngm = new NGramMap(WORD_HISTORY_SIZE3_FILE, YEAR_HISTORY_FILE);
        HistoryHandler handler = new HistoryHandler(ngm);
        NgordnetQuery query = new NgordnetQuery(List.of("request", "airport"), 2006, 2008, 0);

        String actual = handler.handle(query);
        byte[] decoded = Base64.getDecoder().decode(actual);

        assertThat(decoded.length).isGreaterThan(100);
        assertThat(decoded[0]).isEqualTo((byte) 0x89);
        assertThat(decoded[1]).isEqualTo((byte) 0x50);
        assertThat(decoded[2]).isEqualTo((byte) 0x4E);
        assertThat(decoded[3]).isEqualTo((byte) 0x47);
    }
}

import main.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/** Unit Tests for the TimeSeries class.
 *  @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(0.0);
        expectedTotal.add(100.0);
        expectedTotal.add(600.0);
        expectedTotal.add(500.0);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testConstructorCopiesOnlyRequestedYears() {
        TimeSeries population = new TimeSeries();
        population.put(1990, 10.0);
        population.put(1991, 20.0);
        population.put(1993, 40.0);
        population.put(1995, 80.0);

        TimeSeries population1991To1994 = new TimeSeries(population, 1991, 1994);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1993);

        assertThat(population1991To1994.years()).isEqualTo(expectedYears);
        assertThat(population1991To1994.get(1991)).isWithin(1E-10).of(20.0);
        assertThat(population1991To1994.get(1993)).isWithin(1E-10).of(40.0);

        population.put(1992, 30.0);
        population.put(1993, 4000.0);

        assertThat(population1991To1994.years()).isEqualTo(expectedYears);
        assertThat(population1991To1994.get(1993)).isWithin(1E-10).of(40.0);
    }

    @Test
    public void testYearsAndDataAreInAscendingYearOrder() {
        TimeSeries population = new TimeSeries();
        population.put(2005, 5.0);
        population.put(1999, 9.0);
        population.put(2001, 1.0);
        population.put(2003, 3.0);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1999);
        expectedYears.add(2001);
        expectedYears.add(2003);
        expectedYears.add(2005);

        List<Double> expectedData = new ArrayList<>();
        expectedData.add(9.0);
        expectedData.add(1.0);
        expectedData.add(3.0);
        expectedData.add(5.0);

        assertThat(population.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedData.size(); i += 1) {
            assertThat(population.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
    }

    @Test
    public void testPlusWithInterleavedAndSharedYears() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1990, 10.0);
        catPopulation.put(1992, 20.0);
        catPopulation.put(1995, 50.0);
        catPopulation.put(1998, 80.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1989, 1.0);
        dogPopulation.put(1992, 200.0);
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1998, 800.0);
        dogPopulation.put(2000, 1000.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1989);
        expectedYears.add(1990);
        expectedYears.add(1992);
        expectedYears.add(1994);
        expectedYears.add(1995);
        expectedYears.add(1998);
        expectedYears.add(2000);

        List<Double> expectedTotal = new ArrayList<>();
        expectedTotal.add(1.0);
        expectedTotal.add(10.0);
        expectedTotal.add(220.0);
        expectedTotal.add(400.0);
        expectedTotal.add(50.0);
        expectedTotal.add(880.0);
        expectedTotal.add(1000.0);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }

        assertThat(catPopulation.get(1992)).isWithin(1E-10).of(20.0);
        assertThat(dogPopulation.get(1992)).isWithin(1E-10).of(200.0);
    }

    @Test
    public void testPlusWithOneEmptyTimeSeries() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 100.0);
        catPopulation.put(1994, 400.0);

        TimeSeries dogPopulation = new TimeSeries();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1991);
        expectedYears.add(1994);

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);
        assertThat(totalPopulation.get(1991)).isWithin(1E-10).of(100.0);
        assertThat(totalPopulation.get(1994)).isWithin(1E-10).of(400.0);

        catPopulation.put(1991, 1000.0);

        assertThat(totalPopulation.get(1991)).isWithin(1E-10).of(100.0);
    }

    @Test
    public void testDividedByWithExtraYearsInDenominator() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1990, 10.0);
        catPopulation.put(1992, 30.0);
        catPopulation.put(1995, 60.0);

        TimeSeries totalPopulation = new TimeSeries();
        totalPopulation.put(1988, 2.0);
        totalPopulation.put(1990, 20.0);
        totalPopulation.put(1991, 999.0);
        totalPopulation.put(1992, 120.0);
        totalPopulation.put(1995, 30.0);
        totalPopulation.put(1997, 7.0);

        TimeSeries ratio = catPopulation.dividedBy(totalPopulation);

        List<Integer> expectedYears = new ArrayList<>();
        expectedYears.add(1990);
        expectedYears.add(1992);
        expectedYears.add(1995);

        List<Double> expectedData = new ArrayList<>();
        expectedData.add(0.5);
        expectedData.add(0.25);
        expectedData.add(2.0);

        assertThat(ratio.years()).isEqualTo(expectedYears);

        for (int i = 0; i < expectedData.size(); i += 1) {
            assertThat(ratio.data().get(i)).isWithin(1E-10).of(expectedData.get(i));
        }
    }

    @Test
    public void testDividedByThrowsIfDenominatorMissingYear() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1990, 10.0);
        catPopulation.put(1992, 30.0);
        catPopulation.put(1995, 60.0);

        TimeSeries totalPopulation = new TimeSeries();
        totalPopulation.put(1990, 20.0);
        totalPopulation.put(1995, 30.0);

        assertThrows(IllegalArgumentException.class, () -> {
            catPopulation.dividedBy(totalPopulation);
        });
    }
} 

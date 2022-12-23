package test;

import com.company.enums.CustomerType;
import com.company.Hotel;
import com.company.HotelReservationSystem;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class HotelReservationTest {
    private HotelReservationSystem hotelReservationSystem = new HotelReservationSystem();

    @Test
    public void whenGivenNullAsHotelName_ShouldReturnNull() {
        Hotel observerdResult = hotelReservationSystem.addHotel(null, 110.0);
        Hotel expectedResult = null;
        Assert.assertEquals(expectedResult, observerdResult);
    }

    @Test
    public void whenGivenRatesAsNull_ShouldReturnNull() {
        Hotel observerdResult = hotelReservationSystem.addHotel(null, 110.0);
        Assert.assertEquals(null, observerdResult);
    }

    @Test
    public void whenGivenRatesAsZero_ShouldReturnNull() {
        Hotel observerdResult = hotelReservationSystem.addHotel(null, 110.0);
        Assert.assertEquals(null, observerdResult);
    }

    @Test
    public void whenGivenNonNullValues_ShouldReturnHotelObject() {
        Hotel expectedResult = new Hotel();
        expectedResult.setName("Lakewood");
        expectedResult.setRatesPerDay(110.0);
        Hotel observerdResult = hotelReservationSystem.addHotel("Lakewood", 110.0);
        Assert.assertEquals(expectedResult, observerdResult);
    }

    @Test
    public void whenGivenStartDateGreaterThanEndDate_ShouldReturnNull() {
        String observedResult = hotelReservationSystem.getCheapestHotelForADateRange("09SEP2020", "07SEP2020");
        String expectedResult = null;
        Assert.assertEquals(expectedResult, observedResult);
    }

    @Test
    public void whenGivenDateFormatDoesntFitDDMMMYYYYPattern_ShouldReturnInvalidDateFormat() {
        String observedResult = hotelReservationSystem.getCheapestHotelForADateRange("2sep2020", "03sep2020");
        String expectedResult = "Invalid Date Format";
        Assert.assertEquals(expectedResult, observedResult);
    }

    @Test
    public void whenStartDateOrEndDateOrBothAreNull_ShouldReturnNull() {
        String observedResult = hotelReservationSystem.getCheapestHotelForADateRange(null, null);
        String expectedResult = null;
        Assert.assertEquals(expectedResult, observedResult);
    }

    @Test
    public void whenGivenValidStartAndEndDate_ShouldReturnMinAmountAndHotelName() {
        hotelReservationSystem.addHotel("Lakewood", 110.0);
        String observedResult = hotelReservationSystem.getCheapestHotelForADateRange("10sep2020", "11SEP2020");
        System.out.println(observedResult);
    }
}

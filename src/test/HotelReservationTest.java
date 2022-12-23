package test;

import com.company.enums.CustomerType;
import com.company.Hotel;
import com.company.HotelReservationSystem;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

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
        hotelReservationSystem.addHotel("Bridgewood", 160.0);
        hotelReservationSystem.addHotel("Ridgewood", 220.0);
        String observedResult = hotelReservationSystem.getCheapestHotelForADateRange("10sep2020", "11SEP2020");
        String expectedResult = "Lakewood,Total Rates:$220";
        Assert.assertEquals(expectedResult, observedResult);
    }

    @Test
    public void whenGivenWeekDayAndEndRates_ShouldAddToTheHotelList() {
        Map<CustomerType, Double> weekDayRatesLakeWood = new HashMap<>();
        Map<CustomerType, Double> weekEndRatesLakeWood = new HashMap<>();
        weekDayRatesLakeWood.put(CustomerType.REGULAR, 110.0);
        weekEndRatesLakeWood.put(CustomerType.REGULAR, 90.0);
        Map<CustomerType, Double> weekDayRatesBridgeWood = new HashMap<>();
        Map<CustomerType, Double> weekEndRatesBridgeWood = new HashMap<>();
        weekDayRatesBridgeWood.put(CustomerType.REGULAR, 150.0);
        weekEndRatesBridgeWood.put(CustomerType.REGULAR, 50.0);
        Map<CustomerType, Double> weekDayRatesRidgewood = new HashMap<>();
        Map<CustomerType, Double> weekEndRatesRidgewood = new HashMap<>();
        weekDayRatesRidgewood.put(CustomerType.REGULAR, 220.0);
        weekEndRatesRidgewood.put(CustomerType.REGULAR, 150.0);
        List<Hotel> observedHotelList = new ArrayList<>();
        observedHotelList.add(hotelReservationSystem.addHotel("Lakewood", weekDayRatesLakeWood, weekEndRatesLakeWood));
        observedHotelList.add(hotelReservationSystem.addHotel("Bridgewood", weekDayRatesBridgeWood, weekEndRatesBridgeWood));
        observedHotelList.add(hotelReservationSystem.addHotel("Ridgewood", weekDayRatesRidgewood, weekEndRatesRidgewood));
        List<Hotel> expectedHotelList= Arrays.asList(
                new Hotel("Lakewood", weekDayRatesLakeWood, weekEndRatesLakeWood),
                new Hotel("Bridgewood", weekDayRatesBridgeWood, weekEndRatesBridgeWood),
                new Hotel("Ridgewood", weekDayRatesRidgewood, weekEndRatesRidgewood)
        );
        Assert.assertArrayEquals(expectedHotelList.toArray(),observedHotelList.toArray());
    }
}

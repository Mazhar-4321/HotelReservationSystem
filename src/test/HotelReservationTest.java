package test;

import com.company.enums.CustomerType;
import com.company.Hotel;
import com.company.HotelReservationSystem;
import com.company.exceptions.CustomHotelException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;

public class HotelReservationTest {
    private HotelReservationSystem hotelReservationSystem;

    @Before
    public void initialize() {
        hotelReservationSystem = new HotelReservationSystem();
    }

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
        String expectedResult = "Start Date Can't be Greater Than End Date";
        String observedResult = null;
        try {
            observedResult = hotelReservationSystem.getCheapestHotelForADateRange("09SEP2020", "07SEP2020");
        } catch (CustomHotelException customHotelException) {
            observedResult = customHotelException.getMessage();
        }

        Assert.assertEquals(expectedResult, observedResult);
    }

    @Test
    public void whenGivenDateFormatDoesntFitDDMMMYYYYPattern_ShouldReturnInvalidDateFormat() {
        String expectedResult = "Dates Must Follow DDMMMYYYY Format, Eg: 23SEP2020";
        String observedResult = null;
        try {
            observedResult = hotelReservationSystem.getCheapestHotelForADateRange("2sep2020", "03sep2020");
        } catch (CustomHotelException customHotelException) {
            observedResult = customHotelException.getMessage();
        }
        Assert.assertEquals(expectedResult, observedResult);
    }

    @Test
    public void whenStartDateOrEndDateOrBothAreNull_ShouldReturnNull() {
        String expectedResult = "Start Date or End Date Can't be Null";
        String observedResult = null;
        try {
            observedResult = hotelReservationSystem.getCheapestHotelForADateRange(null, null);
        } catch (CustomHotelException customHotelException) {
            observedResult = customHotelException.getMessage();
        }
        Assert.assertEquals(expectedResult, observedResult);
    }

    @Test
    public void whenGivenValidStartAndEndDate_ShouldReturnMinAmountAndHotelName() {
        hotelReservationSystem.addHotel("Lakewood", 110.0);
        hotelReservationSystem.addHotel("Bridgewood", 160.0);
        hotelReservationSystem.addHotel("Ridgewood", 220.0);
        String observedResult = null;
        try {
            observedResult = hotelReservationSystem.getCheapestHotelForADateRange("10sep2020", "11SEP2020");
        } catch (CustomHotelException customHotelException) {
            observedResult = customHotelException.getMessage();
        }
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
        List<Hotel> expectedHotelList = Arrays.asList(
                new Hotel("Lakewood", weekDayRatesLakeWood, weekEndRatesLakeWood),
                new Hotel("Bridgewood", weekDayRatesBridgeWood, weekEndRatesBridgeWood),
                new Hotel("Ridgewood", weekDayRatesRidgewood, weekEndRatesRidgewood)
        );
        Assert.assertArrayEquals(expectedHotelList.toArray(), observedHotelList.toArray());
    }

    @Test
    public void whenGivenStartAndEndDate_ShouldGiveMinAmountWithHotelNamesBasedOnWeekDayAndEndRates() {
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
        hotelReservationSystem.addHotel("Lakewood", weekDayRatesLakeWood, weekEndRatesLakeWood);
        hotelReservationSystem.addHotel("Bridgewood", weekDayRatesBridgeWood, weekEndRatesBridgeWood);
        hotelReservationSystem.addHotel("Ridgewood", weekDayRatesRidgewood, weekEndRatesRidgewood);
        String expectedResult = "Lakewood and Bridgewood,$200";
        String observedResult = null;
        try {
            observedResult = hotelReservationSystem.getCheapestHotelForADateRangeUsingWeekDayAndEndRates("11sep2020", "12sep2020");
        } catch (CustomHotelException customHotelException) {
            observedResult = customHotelException.getMessage();
        }
        Assert.assertEquals(expectedResult, observedResult);
    }

    @Test
    public void whenGivenRatingsToHotels_ShouldAddToTheHotelList() {
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
        Double lakeWoodRating = 3.0;
        Double bridgeWoodRating = 4.0;
        Double ridgeWoodRating = 5.0;
        observedHotelList.add(hotelReservationSystem.addHotel("Lakewood", weekDayRatesLakeWood, weekEndRatesLakeWood, lakeWoodRating));
        observedHotelList.add(hotelReservationSystem.addHotel("Bridgewood", weekDayRatesBridgeWood, weekEndRatesBridgeWood, bridgeWoodRating));
        observedHotelList.add(hotelReservationSystem.addHotel("Ridgewood", weekDayRatesRidgewood, weekEndRatesRidgewood, ridgeWoodRating));
        List<Hotel> expectedHotelList = Arrays.asList(
                new Hotel("Lakewood", weekDayRatesLakeWood, weekEndRatesLakeWood, lakeWoodRating),
                new Hotel("Bridgewood", weekDayRatesBridgeWood, weekEndRatesBridgeWood, bridgeWoodRating),
                new Hotel("Ridgewood", weekDayRatesRidgewood, weekEndRatesRidgewood, ridgeWoodRating)
        );
        Assert.assertArrayEquals(expectedHotelList.toArray(), observedHotelList.toArray());
    }

    @Test
    public void whenGivenStartAndEndDate_ShouldGiveMinAmountWithHotelNamesBasedOnWeekDayAndEndRatesAndRatings() {
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
        Double lakeWoodRating = 3.0;
        Double bridgeWoodRating = 4.0;
        Double ridgeWoodRating = 5.0;
        hotelReservationSystem.addHotel("Lakewood", weekDayRatesLakeWood, weekEndRatesLakeWood, lakeWoodRating);
        hotelReservationSystem.addHotel("Bridgewood", weekDayRatesBridgeWood, weekEndRatesBridgeWood, bridgeWoodRating);
        hotelReservationSystem.addHotel("Ridgewood", weekDayRatesRidgewood, weekEndRatesRidgewood, ridgeWoodRating);
        String expectedResult = "Bridgewood, Rating: 4 and Total Rates: $200";
        String observedResult = null;
        try {
            observedResult = hotelReservationSystem.getCheapestAndBestRatingHotelForADateRange("11sep2020", "12sep2020");
        } catch (CustomHotelException customHotelException) {
            observedResult = customHotelException.getMessage();
        }
        Assert.assertEquals(expectedResult, observedResult);
    }
}

package test;

import com.company.CustomerType;
import com.company.Hotel;
import com.company.HotelReservationSystem;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HotelReservationTest {
    private HotelReservationSystem hotelReservationSystem = new HotelReservationSystem();

    @Test
    public void whenGivenNullAsHotelName_ShouldReturnNull() {
        /*
        Lakewood with a rating of 3 has weekday rates as 110$ for regular customer and 80$ for rewards
customer. The weekend rates are 90 for regular customer and 80 for a rewards customer.
Bridgewood with a rating of 4 has weekday rates as 160$ for regular customer and 110$ for
rewards customer. The weekend rates are 60 for regular customer and 50 for a rewards customer.
Ridgewood with a rating of 5 has weekday rates as 220$ for regular customer and 100$ for rewards
customer. The weekend rates are 150 for regular customer and 40 for a rewards customer.
         */
        Map<CustomerType, Double> weekDayRates = new HashMap<>();
        Map<CustomerType, Double> weekEndRates = new HashMap<>();
        weekDayRates.put(CustomerType.REGULAR, 20.0);
        weekEndRates.put(CustomerType.REGULAR, 30.0);
        Hotel observerdResult = hotelReservationSystem.addHotel(null, weekDayRates, weekEndRates);
        Hotel expectedResult = null;
        Assert.assertEquals(expectedResult, observerdResult);
    }

    @Test
    public void whenGivenRatesAsNull_ShouldReturnNull() {
        Map<CustomerType, Double> weekDayRates = new HashMap<>();
        Map<CustomerType, Double> weekEndRates = new HashMap<>();
        weekDayRates.put(CustomerType.REGULAR, null);
        weekEndRates.put(CustomerType.REGULAR, null);
        Hotel observerdResult = hotelReservationSystem.addHotel(null, weekDayRates, weekEndRates);
        Assert.assertEquals(null, observerdResult);
    }

    @Test
    public void whenGivenRatesAsZero_ShouldReturnNull() {
        Map<CustomerType, Double> weekDayRates = new HashMap<>();
        Map<CustomerType, Double> weekEndRates = new HashMap<>();
        weekDayRates.put(CustomerType.REGULAR, 0.0);
        weekEndRates.put(CustomerType.REGULAR, null);
        Hotel observerdResult = hotelReservationSystem.addHotel(null, weekDayRates, weekEndRates);
        Assert.assertEquals(null, observerdResult);
    }

    @Test
    public void whenGivenNonNullValues_ShouldReturnHotelObject() {
        Map<CustomerType, Double> weekDayRates = new HashMap<>();
        Map<CustomerType, Double> weekEndRates = new HashMap<>();
        weekDayRates.put(CustomerType.REGULAR, 110.0);
        weekEndRates.put(CustomerType.REGULAR, 90.0);
        Hotel expectedResult = new Hotel();
        expectedResult.setName("Lakewood");
        expectedResult.setWeekDayRates(weekDayRates);
        expectedResult.setWeekendRates(weekEndRates);
        Hotel observerdResult = hotelReservationSystem.addHotel("Lakewood", weekDayRates, weekEndRates);
        Assert.assertEquals(expectedResult, observerdResult);
    }
}

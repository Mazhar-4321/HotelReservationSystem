package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HotelReservationSystem {
    private List<Hotel> hotelList;

    public HotelReservationSystem() {
        hotelList = new ArrayList<>();
    }

    public Hotel addHotel(String name, Map<CustomerType, Double> weekDayRates, Map<CustomerType, Double> weekEndRates) {
        if (weekDayRates == null || weekEndRates == null || name == null) {
            return null;
        }
        long weekDayRatesCount = getCountOfNullOrZeroInMapValues(weekDayRates);
        long weekEndRatesCount = getCountOfNullOrZeroInMapValues(weekEndRates);
        if (weekDayRatesCount == 0 || weekEndRatesCount == 0) {
            return null;
        }
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setWeekDayRates(weekDayRates);
        hotel.setWeekendRates(weekEndRates);
        return hotel;
    }

    private long getCountOfNullOrZeroInMapValues(Map<CustomerType, Double> map) {
        return map.values()
                .stream()
                .filter(rate -> rate != null && rate > 0.0)
                .count();
    }
}

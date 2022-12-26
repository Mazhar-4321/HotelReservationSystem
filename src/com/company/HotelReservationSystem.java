package com.company;

import com.company.enums.CustomerType;
import com.company.exceptions.CustomHotelException;
import com.company.utils.DateUtils;
import com.company.utils.HotelUtils;

import java.time.LocalDate;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HotelReservationSystem {
    private List<Hotel> hotelList;

    public HotelReservationSystem() {
        hotelList = new ArrayList<>();
    }

    public String getCheapestHotelForADateRange(String startDateString, String endDateString) throws CustomHotelException {
        ArrayList<LocalDate> localDateArrayList = HotelUtils.validateStartAndEndDate(startDateString, endDateString);
        LocalDate startDate = localDateArrayList.get(0);
        LocalDate endDate = localDateArrayList.get(1);
        Double minAmount = Double.MAX_VALUE;
        ArrayList<Hotel> hotelObjects = new ArrayList<>();
        for (Hotel hotel : hotelList) {
            double totalAmount = getTotalAmountForGivenDateRange(startDate, endDate, hotel.getRatesPerDay());
            if (totalAmount <= minAmount) {
                minAmount = totalAmount;
                hotelObjects.add(hotel);
            }
        }
        return getMeaningfulMessage(hotelObjects) + ",Total Rates:$" + (minAmount.intValue());
    }

    public String getCheapestHotelForADateRangeUsingWeekDayAndEndRates(String startDateString, String endDateString) throws CustomHotelException {
        ArrayList<LocalDate> localDateArrayList = HotelUtils.validateStartAndEndDate(startDateString, endDateString);
        LocalDate startDate = localDateArrayList.get(0);
        LocalDate endDate = localDateArrayList.get(1);
        Double minAmount = Double.MAX_VALUE;
        ArrayList<Hotel> hotelObjects = new ArrayList<>();
        for (Hotel hotel : hotelList) {
            double totalAmount = getTotalAmountForGivenDateRange(startDate, endDate, hotel.getWeekDayRates().get(CustomerType.REGULAR),
                    hotel.getWeekendRates().get(CustomerType.REGULAR));
            if (totalAmount <= minAmount) {
                minAmount = totalAmount;
                hotelObjects.add(hotel);
            }
        }
        return getMeaningfulMessage(hotelObjects) + ",$" + (minAmount.intValue());
    }

    public String getCheapestAndBestRatingHotelForADateRange(String startDateString, String endDateString) throws CustomHotelException {
        ArrayList<LocalDate> localDateArrayList = HotelUtils.validateStartAndEndDate(startDateString, endDateString);
        LocalDate startDate = localDateArrayList.get(0);
        LocalDate endDate = localDateArrayList.get(1);
        Double minAmount = Double.MAX_VALUE;
        ArrayList<String> hotelNames = new ArrayList<>();
        ArrayList<Hotel> hotelObjects = new ArrayList<>();
        for (Hotel value : hotelList) {
            double totalAmount = getTotalAmountForGivenDateRange(startDate, endDate, value.getWeekDayRates().get(CustomerType.REGULAR),
                    value.getWeekendRates().get(CustomerType.REGULAR));
            if (totalAmount <= minAmount) {
                minAmount = totalAmount;
                hotelNames.add(value.getName());
                hotelObjects.add(value);
            }
        }
        ArrayList<Hotel> hotels = new ArrayList<>();
        int currentIndex = -1;
        for (Hotel hotel : hotelObjects) {
            if (hotels.size() == 0) {
                hotels.add(hotel);
                currentIndex = 0;
                continue;
            }
            if (hotels.get(currentIndex).getRatings() < hotel.getRatings()) {
                hotels.set(currentIndex, hotel);
                continue;
            }
            if (hotels.get(currentIndex).getRatings() == hotel.getRatings()) {
                hotels.add(hotel);
                currentIndex++;
            }
        }
        return String.format(getMeaningfulMessage(hotels) + ", Rating: %d and Total Rates: $%d", hotels.get(0).getRatings().intValue(), minAmount.intValue());
    }

    public String getCheapestAndBestRatingHotelForRewardCustomerForADateRange(String startDateString, String endDateString) throws CustomHotelException {
        ArrayList<LocalDate> localDateArrayList = HotelUtils.validateStartAndEndDate(startDateString, endDateString);
        LocalDate startDate = localDateArrayList.get(0);
        LocalDate endDate = localDateArrayList.get(1);
        Double minAmount = Double.MAX_VALUE;
        ArrayList<String> hotelNames = new ArrayList<>();
        ArrayList<Hotel> hotelObjects = new ArrayList<>();
        for (Hotel value : hotelList) {
            double totalAmount = getTotalAmountForGivenDateRange(startDate, endDate, value.getWeekDayRates().get(CustomerType.REGULAR),
                    value.getWeekendRates().get(CustomerType.REGULAR));
            if (totalAmount <= minAmount) {
                minAmount = totalAmount;
                hotelNames.add(value.getName());
                hotelObjects.add(value);
            }
        }
        ArrayList<Hotel> hotels = new ArrayList<>();
        int currentIndex = -1;
        for (Hotel hotel : hotelObjects) {
            if (hotels.size() == 0) {
                hotels.add(hotel);
                currentIndex = 0;
                continue;
            }
            if (hotels.get(currentIndex).getRatings() < hotel.getRatings()) {
                hotels.set(currentIndex, hotel);
                continue;
            }
            if (hotels.get(currentIndex).getRatings() == hotel.getRatings()) {
                hotels.add(hotel);
                currentIndex++;
            }
        }
        return String.format(getMeaningfulMessage(hotels) + ", Rating: %d and Total Rates: $%d", hotels.get(0).getRatings().intValue(), minAmount.intValue());
    }

    public String getBestRatedHotelForRegularCustomer(String startDateString, String endDateString) throws CustomHotelException {
        return getBestRatedHotel(startDateString, endDateString, CustomerType.REGULAR);
    }

    public String getBestRatedHotelForRewardCustomer(String startDateString, String endDateString) throws CustomHotelException {
        return getBestRatedHotel(startDateString, endDateString, CustomerType.REWARD);
    }

    private String getBestRatedHotel(String startDateString, String endDateString, CustomerType customerType) throws CustomHotelException {
        HotelUtils.validateStartAndEndDate(startDateString, endDateString);
        Optional<Map.Entry<Hotel, Integer>> object = (Optional<Map.Entry<Hotel, Integer>>) getTotalAmountForGivenDateRangeUsingStreams(LocalDate.parse(startDateString), LocalDate.parse(startDateString), customerType);
        return String.format(object.get().getKey().getName() + " & Total Rates $%d", object.get().getValue().intValue());

    }

    private Function<Hotel, Object> getFunction(LocalDate startDate, LocalDate endDate, CustomerType customerType) {
        return hotel -> {
            Map<Hotel, Integer> ratesMap = new HashMap<>();
            Double rate = 0.0;
            List<LocalDate> datesList = Arrays.asList(startDate, endDate);
            while (datesList.get(1).toString().compareTo(datesList.get(0).toString()) >= 0) {
                rate += DateUtils.getStartDayInNumber(datesList.get(0).getDayOfWeek()) <= 5
                        ? hotel.getWeekDayRates().get(customerType)
                        : hotel.getWeekendRates().get(customerType);
                datesList.set(0, datesList.get(0).plusDays(1));
            }
            ratesMap.put(hotel, rate.intValue());
            return ratesMap;
        };
    }

    private Object getTotalAmountForGivenDateRangeUsingStreams(LocalDate startDate, LocalDate endDate, CustomerType customerType) {
        return hotelList.stream()
                .map(getFunction(startDate, endDate, customerType))
                .flatMap(e -> ((HashMap<Hotel, Integer>) e).entrySet().stream())
                .reduce((hotelDoubleEntry, hotelDoubleEntry2) -> hotelDoubleEntry.getValue() <= hotelDoubleEntry2.getValue() && hotelDoubleEntry.getKey().getRatings() >= hotelDoubleEntry2.getKey().getRatings() ? hotelDoubleEntry : hotelDoubleEntry2);

    }

    private String getMeaningfulMessage(List<Hotel> hotelObjects) {
        String data = "";
        if (hotelObjects.size() == 1) {
            return hotelObjects.get(0).getName();
        }
        if (hotelObjects.size() == 2) {
            return hotelObjects.get(0).getName() + " and " + hotelObjects.get(1).getName();
        }
        for (int i = 0; i < hotelObjects.size(); i++) {
            if (i < hotelObjects.size() - 2) {
                data += hotelObjects.get(i).getName() + ",";
            }
            if (i == hotelObjects.size() - 2) {
                data += hotelObjects.get(i).getName() + " and" + hotelObjects.get(i + 1).getName();
                break;
            }
        }
        return data;
    }

    private double getTotalAmountForGivenDateRange(LocalDate startDate, LocalDate endDate, Double weekDayAmount, Double weekEndAmount) {
        int noOfDays = endDate.compareTo(startDate) + 1;
        int startDay = DateUtils.getStartDayInNumber(startDate.getDayOfWeek());
        int noOfWeekDays = 0;
        int noOfWeekEnds = 0;
        while (startDay < 8 && noOfDays > 0) {
            if (startDay <= 5) {
                noOfWeekDays++;
            } else {
                noOfWeekEnds++;
            }
            noOfDays--;
            startDay++;
        }
        double firstWeekAmount = noOfWeekDays * weekDayAmount + noOfWeekEnds * weekEndAmount;
        if (noOfDays == 0) {
            return firstWeekAmount;
        }
        int noOfRemainingWeeks = noOfDays / 7;
        double middleWeekDaysAmount = noOfRemainingWeeks * 5 * weekDayAmount;
        double middleWeekEndAmount = noOfRemainingWeeks * 2 * weekEndAmount;
        noOfDays -= 7 * noOfRemainingWeeks;
        double middleWeeksAmount = middleWeekDaysAmount + middleWeekEndAmount;
        int noOfLastWeekDays = noOfDays >= 5 ? 5 : noOfDays;
        int noOfLastWeekEndDays = noOfDays > 5 ? 1 : 0;
        double lastWeekAmount = noOfLastWeekDays * weekDayAmount + noOfLastWeekEndDays * weekEndAmount;
        double totalAmount = firstWeekAmount + middleWeeksAmount + lastWeekAmount;
        return totalAmount;
    }

    private double getTotalAmountForGivenDateRange(LocalDate startDate, LocalDate endDate, Double ratesPerDay) {
        int noOfDays = endDate.compareTo(startDate) + 1;
        Double totalAmount = noOfDays * ratesPerDay;
        return totalAmount;
    }


    public Hotel addHotel(String name, Double ratesPerDay) {
        if (name == null || ratesPerDay == null || ratesPerDay < 1) {
            return null;
        }
        Hotel hotel = new Hotel();
        hotel.setName(name);
        hotel.setRatesPerDay(ratesPerDay);
        hotelList.add(hotel);
        return hotel;
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
        Hotel hotel = checkIfHotelExistsWithGivenName(name);
        if (hotel == null) {
            hotel = new Hotel();
            hotel.setName(name);
            hotelList.add(hotel);
        }
        hotel.setWeekDayRates(weekDayRates);
        hotel.setWeekendRates(weekEndRates);
        return hotel;
    }

    public Hotel addHotel(String name, Map<CustomerType, Double> weekDayRates, Map<CustomerType, Double> weekEndRates, Double ratings) {
        if (weekDayRates == null || weekEndRates == null || name == null || ratings == null || ratings < 1) {
            return null;
        }
        long weekDayRatesCount = getCountOfNullOrZeroInMapValues(weekDayRates);
        long weekEndRatesCount = getCountOfNullOrZeroInMapValues(weekEndRates);
        if (weekDayRatesCount == 0 || weekEndRatesCount == 0) {
            return null;
        }
        Hotel hotel = checkIfHotelExistsWithGivenName(name);
        if (hotel == null) {
            hotel = new Hotel();
            hotel.setName(name);
            hotelList.add(hotel);
        }
        hotel.setWeekDayRates(weekDayRates);
        hotel.setWeekendRates(weekEndRates);
        hotel.setRatings(ratings);
        return hotel;
    }

    private Hotel checkIfHotelExistsWithGivenName(String name) {
        Optional<Hotel> hotel = hotelList.stream().filter(h -> h.getName().equals(name)).findFirst();
        return hotel.isPresent() ? hotel.get() : null;
    }

    private long getCountOfNullOrZeroInMapValues(Map<CustomerType, Double> map) {
        return map.values()
                .stream()
                .filter(rate -> rate != null && rate > 0.0)
                .count();
    }

}

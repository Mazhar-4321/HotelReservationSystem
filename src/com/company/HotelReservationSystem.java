package com.company;

import com.company.enums.CustomerType;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Stream;

public class HotelReservationSystem {
    private List<Hotel> hotelList;

    public HotelReservationSystem() {
        hotelList = new ArrayList<>();
    }

    private static int getStartDayInNumber(DayOfWeek dayOfWeek) {
        String daysOfWeek = dayOfWeek + "";
        return daysOfWeek.equals("MONDAY") ? 1 :
                daysOfWeek.equals("TUESDAY") ? 2 :
                        daysOfWeek.equals("WEDNESDAY") ? 3 :
                                daysOfWeek.equals("THURSDAY") ? 4 :
                                        daysOfWeek.equals("FRIDAY") ? 5 :
                                                daysOfWeek.equals("SATURDAY") ? 6 : 7;

    }

    public String getCheapestHotelForADateRange(String startDateString, String endDateString) {
        if (startDateString == null || endDateString == null) {
            return "Invalid Date Format";
        }
        startDateString = validateDate(startDateString.toUpperCase());
        endDateString = validateDate(endDateString.toUpperCase());
        if (startDateString == null || endDateString == null) {
            return "Invalid Date Format";
        }
        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);
        if (endDate.compareTo(startDate) + 1 < 1) {
            return null;
        }
        Double minAmount = Double.MAX_VALUE;
        ArrayList<String> hotelNames = new ArrayList<>();
        for (int i = 0; i < hotelList.size(); i++) {
            double totalAmount = getTotalAmountForGivenDateRange(startDate, endDate, hotelList.get(i).getRatesPerDay());
            if (totalAmount <= minAmount) {
                minAmount = totalAmount;
                hotelNames.add(hotelList.get(i).getName());
            }
        }
        return getMeaningfulMessage(hotelNames) + ",Total Rates:$" + (minAmount.intValue());
    }

    public String getCheapestHotelForADateRangeUsingWeekDayAndEndRates(String startDateString, String endDateString) {
        if (startDateString == null || endDateString == null) {
            return "Invalid Date Format";
        }
        startDateString = validateDate(startDateString.toUpperCase());
        endDateString = validateDate(endDateString.toUpperCase());
        if (startDateString == null || endDateString == null) {
            return "Invalid Date Format";
        }
        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);
        if (endDate.compareTo(startDate) + 1 < 1) {
            return null;
        }
        Double minAmount = Double.MAX_VALUE;
        ArrayList<String> hotelNames = new ArrayList<>();
        for (int i = 0; i < hotelList.size(); i++) {
            double totalAmount = getTotalAmountForGivenDateRange(startDate, endDate, hotelList.get(i).getWeekDayRates().get(CustomerType.REGULAR),
                    hotelList.get(i).getWeekendRates().get(CustomerType.REGULAR));
            if (totalAmount <= minAmount) {
                minAmount = totalAmount;
                hotelNames.add(hotelList.get(i).getName());
            }
        }
        return getMeaningfulMessage(hotelNames) + ",$" + (minAmount.intValue());
    }

    private String getMeaningfulMessage(List<String> hotelNamesList) {
        String data = "";
        if (hotelNamesList.size() == 1) {
            return hotelNamesList.get(0);
        }
        if (hotelNamesList.size() == 2) {
            return hotelNamesList.get(0) + " and " + hotelNamesList.get(1);
        }
        for (int i = 0; i < hotelNamesList.size(); i++) {
            if (i < hotelNamesList.size() - 2) {
                data += hotelNamesList.get(i) + ",";
            }
            if (i == hotelNamesList.size() - 2) {
                data += hotelNamesList.get(i) + " and" + hotelNamesList.get(i + 1);
                break;
            }
        }
        return data;
    }

    private double getTotalAmountForGivenDateRange(LocalDate startDate, LocalDate endDate, Double weekDayAmount, Double weekEndAmount) {
        int noOfDays = endDate.compareTo(startDate) + 1;
        int startDay = getStartDayInNumber(startDate.getDayOfWeek());
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

    private String validateDate(String dateString) {
        Map<String, String> monthsMap = new HashMap<String, String>() {
            {
                put("JAN", "01");
                put("FEB", "02");
                put("MAR", "03");
                put("APR", "04");
                put("MAY", "05");
                put("JUN", "06");
                put("JUL", "07");
                put("AUG", "08");
                put("SEP", "09");
                put("OCT", "10");
                put("NOV", "11");
                put("DEC", "12");
            }
        };
        return dateString.matches("^(([0][1-9]|[1-2][0-9]|[3][0-1])(JAN|FEB|MAR|APR|MAY|JUN|JUL|AUG|SEP|OCT|NOV|DEC)([1-9][0-9]{3}))$") ?
                "" + dateString.substring(5, 9) + "-" + monthsMap.get("" + dateString.substring(2, 5)) + "-" + dateString.substring(0, 2) : null;
    }
}

package com.company.utils;

import com.company.exceptions.CustomHotelException;

import java.time.LocalDate;
import java.util.ArrayList;

public class HotelUtils {
    public static ArrayList<LocalDate> validateStartAndEndDate(String startDateString, String endDateString) throws CustomHotelException {
        ArrayList<LocalDate> localDateArrayList = new ArrayList<>();
        if (startDateString == null || endDateString == null) {
            throw new CustomHotelException("Start Date or End Date Can't be Null");
        }
        startDateString = DateUtils.validate(startDateString.toUpperCase());
        endDateString = DateUtils.validate(endDateString.toUpperCase());
        if (startDateString == null || endDateString == null) {
            throw new CustomHotelException("Dates Must Follow DDMMMYYYY Format, Eg: 23SEP2020");
        }
        LocalDate startDate = LocalDate.parse(startDateString);
        LocalDate endDate = LocalDate.parse(endDateString);
        if (endDate.compareTo(startDate) + 1 < 1) {
            throw new CustomHotelException("Start Date Can't be Greater Than End Date");
        }
        localDateArrayList.add(startDate);
        localDateArrayList.add(endDate);
        return localDateArrayList;
    }
}

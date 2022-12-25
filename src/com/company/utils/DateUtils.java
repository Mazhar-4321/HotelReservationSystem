package com.company.utils;

import com.company.enums.CustomerType;

import java.time.DayOfWeek;
import java.util.HashMap;
import java.util.Map;

public class DateUtils {
    public static String validate(String dateString){
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
    public static int getStartDayInNumber(DayOfWeek dayOfWeek){
        String daysOfWeek = dayOfWeek + "";
        return daysOfWeek.equals("MONDAY") ? 1 :
                daysOfWeek.equals("TUESDAY") ? 2 :
                        daysOfWeek.equals("WEDNESDAY") ? 3 :
                                daysOfWeek.equals("THURSDAY") ? 4 :
                                        daysOfWeek.equals("FRIDAY") ? 5 :
                                                daysOfWeek.equals("SATURDAY") ? 6 : 7;
    }
}

package com.app.cs151synter.dataManipulation;
import java.lang.invoke.CallSite;
import java.util.Calendar;

public class CalendarHelper {
    public static int getMonthFromString (String string) {
        switch (string) {
            case "jan":
            case "january":
                return Calendar.JANUARY;
            case "feb":
            case "february":
                return Calendar.FEBRUARY;
            case "mar":
            case "march":
                return Calendar.MARCH;
            case "apr":
            case "april":
                return Calendar.APRIL;
            case "may":
                return Calendar.MAY;
            case "jun":
            case "june":
                return Calendar.JUNE;
            case "jul":
            case "july":
                return Calendar.JULY;
            case "aug":
            case "august":
                return Calendar.AUGUST;
            case "sep":
            case "september":
                return Calendar.SEPTEMBER;
            case "oct":
            case "october":
                return Calendar.OCTOBER;
            case "nov":
            case "november":
                return Calendar.NOVEMBER;
            case "dec":
            case "december":
                return Calendar.DECEMBER;
        }
        return -1;
    }
}

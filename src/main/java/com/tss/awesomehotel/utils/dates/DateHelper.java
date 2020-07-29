package com.tss.awesomehotel.utils.dates;

import java.util.Calendar;

/**
 * This class holds all the date related methods to provide
 * an interface by the calling classes to operate with dates
 */
public class DateHelper
{
    /**
     * This method returns the current day of year
     *
     * @return The current day of year
     */
    public static int getCurrentDayOfYear()
    {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }
}

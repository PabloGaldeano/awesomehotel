package com.tss.awesomehotel.utils.dates;

import java.util.Calendar;

public class DateHelper
{
    public static int getCurrentDayOfYear()
    {
        return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
    }
}

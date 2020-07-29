package com.tss.awesomehotel.utils.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * This class will parse a {@link LogRecord} in order to make sure
 * all the log entries have the same format
 */
public class HotelLogFormatter extends Formatter
{

    /**
     * Method to convert the {@link LogRecord} to an string
     *
     * @param record The record to parse
     * @return The string representing the {@link LogRecord}
     */
    @Override
    public String format(LogRecord record)
    {
        StringBuilder recordBuilder = new StringBuilder();
        recordBuilder.append(record.getLevel().getName()).append("->").append(record.getMessage());
        return recordBuilder.toString();
    }

}

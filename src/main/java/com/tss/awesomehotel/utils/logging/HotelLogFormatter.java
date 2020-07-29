package com.tss.awesomehotel.utils.logging;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class HotelLogFormatter extends Formatter
{

    @Override
    public String format(LogRecord record)
    {
        StringBuilder recordBuilder = new StringBuilder();
        recordBuilder.append(record.getLevel().getName()).append("->").append(record.getMessage());
        return recordBuilder.toString();
    }

}

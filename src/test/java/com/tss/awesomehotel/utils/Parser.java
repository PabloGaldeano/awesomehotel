package com.tss.awesomehotel.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Parser
{
    public static final ObjectMapper mapper = new ObjectMapper();

    static
    {
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
    }
}

package com.tss.awesomehotel.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

public class EnvironmentHelper
{
    public static String getVariableOrDefault(String key, String defaultValue)
    {
        String ret;
        if (StringHelper.checkIfStringContainsSomething(key))
        {
            String environmentVariableValue = System.getenv(key);
            ret = (environmentVariableValue == null) ? defaultValue : environmentVariableValue;

        }
        else
        {
            Logger.getGlobal().log(Level.WARNING, "The key of the desired environment variable is not valid");
            ret = "";
        }
        return ret;
    }
}

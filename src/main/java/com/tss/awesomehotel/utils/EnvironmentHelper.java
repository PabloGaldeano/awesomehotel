package com.tss.awesomehotel.utils;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class provides an interface to operate with the
 * system variables, in order to help with and abstract of
 * the system environment variables to the calling classes
 */
public class EnvironmentHelper
{
    /**
     * This will get the string value of the given system variable in string format,
     * in case the system variable is invalid it will return the default value
     *
     * @param key The key of the system variable
     * @param defaultValue The value to return when the value is not found
     * @return The string mapped to the key or the default value
     */
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

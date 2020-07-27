package com.tss.awesomehotel.utils;

/**
 * This class is being used as a helper to do certain operations with Strings,
 * such as to check if the string contains something  for example.
 */
public class StringHelper
{
    /**
     * This method is used to check if the string given by parameter is
     * not null and contains something beside whitespaces.
     *
     * @param str The string to check.
     * @return <code>true</code> if the string is not null and not blank, <code>false</code>
     */
    public static boolean checkIfStringContainsSomething(String str)
    {
        return str != null && !str.isBlank();
    }

    /**
     * This method will generate a String of a fixed length given by parameter
     *
     * @param n The length of the string
     * @return The random string
     */
    public static String getRandomAlphaNumericString(int n)
    {

        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";

        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n; i++)
        {

            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                    = (int) (AlphaNumericString.length()
                    * Math.random());

            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                    .charAt(index));
        }

        return sb.toString();
    }
}

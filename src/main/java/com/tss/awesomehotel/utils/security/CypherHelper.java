package com.tss.awesomehotel.utils.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * This class is the one used by the callers to provide an
 * interface to cypher information.
 */
public class CypherHelper
{
    /**
     * The salt used for the cypher processes
     */
    @Value("${app.security.salt}")
    private static String salt;

    /**
     * Method to digest an string using MD5 algorithm with {@link #salt}
     * @param toCypher The string to digest
     * @return The digested string
     */
    public static String digestStringWithMD5AndSalt(@NonNull String toCypher)
    {
        MessageDigest md = null;
        String stringCyphered = "";
        try
        {
            md = MessageDigest.getInstance("MD5");
            if (salt != null)
            {
                md.update(salt.getBytes());
            }
            md.update(toCypher.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < byteData.length; i++)
            {
                sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
            }

            stringCyphered = sb.toString();
        } catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return stringCyphered;
    }
}

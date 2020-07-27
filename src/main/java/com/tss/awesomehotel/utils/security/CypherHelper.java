package com.tss.awesomehotel.utils.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CypherHelper
{
    @Value("${app.security.salt}")
    private static String salt;

    public static String cypherString(@NonNull String toCypher)
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

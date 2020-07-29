package com.tss.awesomehotel.utils.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
class CypherHelperTest
{


    @Test
    public void testCyphering()
    {
        String passwordToCypher = "test";
        String cyphered = CypherHelper.digestStringWithMD5AndSalt(passwordToCypher);
        Assertions.assertNotEquals(passwordToCypher, cyphered);

        cyphered = CypherHelper.digestStringWithMD5AndSalt(passwordToCypher);
        Assertions.assertNotEquals(passwordToCypher, cyphered);

        System.out.println(cyphered);
    }
}
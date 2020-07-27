package com.tss.awesomehotel.utils.security;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
class CypherHelperTest
{


    @Test
    public void testCyphering()
    {
        String passwordToCypher = "test";
        String cyphered = CypherHelper.cypherString(passwordToCypher);
        Assertions.assertNotEquals(passwordToCypher, cyphered);

        cyphered = CypherHelper.cypherString(passwordToCypher);
        Assertions.assertNotEquals(passwordToCypher, cyphered);

        System.out.println(cyphered);
    }
}
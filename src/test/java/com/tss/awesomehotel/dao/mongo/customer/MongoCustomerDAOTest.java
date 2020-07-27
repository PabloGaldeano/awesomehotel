package com.tss.awesomehotel.dao.mongo.customer;

import com.tss.awesomehotel.dao.customer.mongo.MongoCustomerDAO;
import com.tss.awesomehotel.model.customer.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = {"spring.data.mongodb.host=localhost", "spring.data.mongodb.port=27017", "spring.data.mongodb.database=awesomehotelTest"})
class MongoCustomerDAOTest
{
    private Customer customer;

    @BeforeEach
    private void beforeEachTest()
    {
        this.customer = new Customer("AMAI!", "Igor", "Rogi", "test",  "Volkov");
    }

    @Autowired
    private MongoCustomerDAO mongoConnection;


    @Test
    void testInsertAndRetrieveCustomer()
    {
        // Creating and inserting the customer
        Assertions.assertTrue(this.mongoConnection.insertCustomerData(this.customer));

        // Testing to insert a duplicate
        Assertions.assertFalse(this.mongoConnection.insertCustomerData(this.customer));

        // Getting the customer and checking if the ID matches
        Customer customers = this.mongoConnection.retrieveCustomerData(this.customer.getCustomerID()).get();
        Assertions.assertEquals(this.customer.getCustomerID(), customers.getCustomerID());

        // Testing some wrong cases
        this.testNullCustomerRetrievalByID("aa");
        this.testNullCustomerRetrievalByID(null);

        // Cleaning up the house
        this.mongoConnection.removeCustomerByID(this.customer.getCustomerID());
    }

    @Test
    public void testDeleteCustomer()
    {
        Assertions.assertTrue(this.mongoConnection.removeCustomerByID(this.customer.getCustomerID()));
        String customerID = null;
        Assertions.assertDoesNotThrow(() -> this.mongoConnection.removeCustomerByID(customerID));
    }

    private void testNullCustomerRetrievalByID(String ID)
    {
        Assertions.assertTrue( this.mongoConnection.retrieveCustomerData(ID).isEmpty());
    }
}
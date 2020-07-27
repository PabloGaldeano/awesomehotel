package com.tss.awesomehotel.model.customer;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tss.awesomehotel.utils.StringHelper;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.StringJoiner;

/**
 * This is the model of the customer entity.
 */
@Document(collection = "customer")
public class Customer
{


    /**
     * The unique ID of the customer
     */
    @Id
    @JsonProperty("customerID")
    private String customerID;
    /**
     * The first name of the customer
     */
    @JsonProperty("firstName")
    private String firstName;
    /**
     * The middle name of the customer
     */
    @JsonProperty("middleName")
    private String middleName;

    /**
     * The customer password
     */
    @JsonProperty("Password")
    private String password;


    /**
     * The last name of the customer
     */
    @JsonProperty("lastName")
    private String lastName;

    /**
     * @param firstName       The first name of the customer. Can't be NULL or blank
     * @param middleName      The middle name of the customer. Can be NULL
     * @param password        The customer password
     * @param lastName        The last name of the customer, can't be NULL or blank.
     */
    @PersistenceConstructor
    public Customer(String customerID, String firstName, String middleName, String password, String lastName)
    {
        this.customerID = customerID;
        this.middleName = middleName;
        this.password = password;
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public String getCustomerID()
    {
        return this.customerID;
    }

    public void setCustomerID(String customerID)
    {
        if (customerID != null && !customerID.isBlank())
        {
            this.customerID = customerID;
        }
    }

    public void setFirstName(String aFirstName)
    {
        this.firstName = aFirstName;
    }

    public String getFirstName()
    {
        return this.firstName;
    }

    public void setMiddleName(String aMiddleName)
    {
        this.middleName = aMiddleName;
    }

    public String getMiddleName()
    {
        return this.middleName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    @JsonProperty("fullName")
    @Transient
    public String getFullName()
    {
        StringJoiner builder = new StringJoiner(" ");
        if (StringHelper.checkIfStringContainsSomething(this.firstName))
        {
            builder.add(this.firstName);
        }
        if (StringHelper.checkIfStringContainsSomething(this.middleName))
        {
            builder.add(this.middleName);
        }
        if (StringHelper.checkIfStringContainsSomething(this.lastName))
        {
            builder.add(this.lastName);
        }

        return builder.toString();
    }
}
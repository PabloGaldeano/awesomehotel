package com.tss.awesomehotel.model.travel;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

@Document(collection = "travels")
public class TravellingCustomers
{
    @Id
    private int travelDay;

    @JsonProperty("signed_customers")
    @Field("signed_customers")
    private HashMap<Integer, Set<String>> customersSignedUp;

    @PersistenceConstructor
    public TravellingCustomers(int travelDay, HashMap<Integer, Set<String>> customersSignedUp)
    {
        this.travelDay = travelDay;
        this.customersSignedUp = customersSignedUp;
    }

    public boolean isSomeCustomerSignedUpFor(int stop)
    {
        return this.customersSignedUp.containsKey(stop);
    }

    public boolean isCustomerSignedUpFor(int stopID, String customerName)
    {
        return this.isSomeCustomerSignedUpFor(stopID) && this.customersSignedUp.get(stopID).contains(customerName);
    }


    public void signCustomerFor(int stopID, String customerName)
    {
        if (!this.customersSignedUp.containsKey(stopID))
        {
            this.customersSignedUp.put(stopID, new HashSet<>());
        }

        if (!this.isCustomerSignedUpFor(stopID, customerName))
        {
            this.customersSignedUp.get(stopID).add(customerName);
        }
    }

    public List<String> getCustomerForCertainStop(int stop)
    {
        return (this.isSomeCustomerSignedUpFor(stop) ? List.copyOf(this.customersSignedUp.get(stop)) : List.of());
    }
}

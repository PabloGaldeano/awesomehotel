package com.tss.awesomehotel.model.travel;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.*;

/**
 * This model represents all the customers that
 * have signed up for the different {@link TourStop} available
 */
@Document(collection = "travels")
public class TravellingCustomers
{
    /**
     * The day of the trip
     */
    @Id
    private int travelDay;

    /**
     * The list of customers indexed by {@link TourStop#travelPathID}
     */
    @JsonProperty("signed_customers")
    @Field("signed_customers")
    private HashMap<Integer, Set<String>> customersSignedUp;

    @PersistenceConstructor
    public TravellingCustomers(int travelDay, HashMap<Integer, Set<String>> customersSignedUp)
    {
        this.travelDay = travelDay;
        this.customersSignedUp = customersSignedUp;
    }

    /**
     * Checks if there is at least one customer in the given {@link TourStop#travelPathID}
     * @param stop the {@link TourStop#travelPathID} to check
     * @return <code>true</code> if some customer found <code>false</code> otherwise
     */
    public boolean isSomeCustomerSignedUpFor(int stop)
    {
        return this.customersSignedUp.containsKey(stop);
    }

    /**
     * Checks if a customer is signed up in a {@link TourStop#travelPathID}
     *
     * @param stopID       The {@link TourStop#travelPathID}
     * @param customerName The name of the customer
     * @return <code>true</code> if signed up correctly <code>false</code> if already signed in
     */
    public boolean isCustomerSignedUpFor(int stopID, String customerName)
    {
        return this.isSomeCustomerSignedUpFor(stopID) && this.customersSignedUp.get(stopID).contains(customerName);
    }


    /**
     * This method signs a customer for a {@link TourStop#travelPathID}
     *
     * @param stopID       The ID of the stop to sign up
     * @param customerName The customer to sign up
     */
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

    /**
     * Returns a list of customer for a given {@link TourStop#travelPathID}
     * @param stop the {@link TourStop#travelPathID}
     * @return The list of customers, empty list otherwise
     */
    public List<String> getCustomerForCertainStop(int stop)
    {
        return (this.isSomeCustomerSignedUpFor(stop) ? List.copyOf(this.customersSignedUp.get(stop)) : List.of());
    }
}

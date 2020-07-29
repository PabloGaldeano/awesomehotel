package com.tss.awesomehotel.controller;

import com.tss.awesomehotel.controller.basic.GenericController;
import com.tss.awesomehotel.dto.TourStepDTO;
import com.tss.awesomehotel.exception.codes.ErrorCodes;
import com.tss.awesomehotel.model.requests.GenericAuthRequest;
import com.tss.awesomehotel.model.ServiceResponse;
import com.tss.awesomehotel.model.requests.SignUpForTravelAuthRequest;
import com.tss.awesomehotel.service.travel.TravelService;
import com.tss.awesomehotel.service.travel.TravelSignUpService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

/**
 * This class is used to provide an interface to operate
 * with the travel service the hotel offers
 */
@RestController
@RequestMapping("/tour")
public class TravelController extends GenericController
{
    /**
     * The service reference to receive information about the trip
     */
    @Autowired
    private TravelService travelService;

    /**
     * The service to sign up customers for a ride
     */
    @Autowired
    private TravelSignUpService signUpService;

    /**
     * Method to get the path of the trip
     *
     * @param request Request containing the necessary information to be able to process the request
     * @return Response with operation result
     */
    @PostMapping("/itinerary")
    public ServiceResponse getShortestPath(@NonNull @RequestBody GenericAuthRequest request)
    {
        return this.handleCallExceptionAndCheckToken(request.getToken(), () ->
                ServiceResponse.createSuccessResponse(new ModelMapper().map(this.travelService.getShortTourWithCustomers(), TourStepDTO.class)));
    }

    /**
     * Method to sign up a customer for a ride
     *
     * @param request The request information for the operation
     * @return A response object containing the info of the operation
     */
    @PostMapping("/sign_up")
    public ServiceResponse singUpForARide(@NonNull @RequestBody SignUpForTravelAuthRequest request)
    {
        return this.handleCallExceptionAndCheckToken(request.getToken(), () ->
        {
            boolean wasCustomerSignedUp = this.signUpService
                    .signCustomerUp(this.getCustomerFromToken(request.getToken()), request.getStopID());

            return (wasCustomerSignedUp) ? ServiceResponse.createSuccessResponse(null) : ServiceResponse.fromErrorCode(ErrorCodes.MASQUERADE);
        });

    }
}

package com.tss.awesomehotel.controller;

import com.tss.awesomehotel.controller.basic.GenericController;
import com.tss.awesomehotel.dto.TourStepDTO;
import com.tss.awesomehotel.model.requests.GenericRequest;
import com.tss.awesomehotel.model.ServiceResponse;
import com.tss.awesomehotel.model.requests.SignUpForTravelRequest;
import com.tss.awesomehotel.service.travel.TravelService;
import com.tss.awesomehotel.service.travel.TravelSignUpService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tour")
@CrossOrigin(origins = "http://localhost:8081")
public class TravelController extends GenericController
{
    @Autowired
    private TravelService travelService;

    @Autowired
    private TravelSignUpService signUpService;

    @PostMapping("/itinerary")
    public ServiceResponse getShortestPath(@NonNull @RequestBody GenericRequest request)
    {
        return this.handleCallExceptionAndCheckToken(request.getToken(), () ->
                ServiceResponse.createSuccessResponse(new ModelMapper().map(this.travelService.getShortTourWithCustomers(), TourStepDTO.class)));
    }

    @PostMapping("/sign_up")
    public ServiceResponse singUpForARide(@NonNull @RequestBody SignUpForTravelRequest request)
    {
        return this.handleCallExceptionAndCheckToken(request.getToken(), () ->
                ServiceResponse.createSuccessResponse(this.signUpService
                        .signCustomerUp(this.getCustomerFromToken(request.getToken()), request.getTo())));

    }
}

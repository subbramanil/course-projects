package com.utd.airtravels.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.utd.airtravels.dao.AirlineDAOImpl;
import com.utd.airtravels.dto.FareDTO;
import com.utd.airtravels.dto.FlightDTO;
import com.utd.airtravels.dto.FlightInstanceDTO;


@Controller
@RequestMapping("/")
public class AirlineController {

//	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
	@Autowired
	AirlineDAOImpl airline;
	
	static Gson gson = new Gson();
	
	@RequestMapping(value="/getFlights", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody String getFlightDetails(@RequestBody final FlightDTO flight){

		System.out.println("Flight details:"+flight.toString());
		List<FlightDTO> flightsList = airline.getFlightsDetails(flight);
		
		for(FlightDTO f:flightsList){
			System.out.println(f);
		}
		
		System.out.println("Result: "+gson.toJson(flightsList).toString());
		
		return gson.toJson(flightsList);
	}
	
	@RequestMapping(value="/getFlightFares", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody String getFlightFareDetails(@RequestBody final FareDTO fare){

		System.out.println("Flight details:"+fare.toString());
		List<FareDTO> fareList = airline.checkFares(fare);
		
		for(FareDTO f:fareList){
			System.out.println(f);
		}
		
		System.out.println("Result: "+gson.toJson(fareList));
		
		return gson.toJson(fareList);
	}
	
	@RequestMapping(value="/getSeatAvailable", method = RequestMethod.POST, produces="application/json")
	public @ResponseBody String getFlightSeatAvailability(@RequestBody final FlightInstanceDTO instance){

		System.out.println("Flight details:"+instance.toString());
		List<FlightInstanceDTO> instanceList = airline.getSeatAvailability(instance);
		
		for(FlightInstanceDTO f:instanceList){
			System.out.println(f);
		}
		
		System.out.println("Result: "+gson.toJson(instanceList));
		
		return gson.toJson(instanceList);
	}
	
}

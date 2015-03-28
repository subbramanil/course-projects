package com.utd.airtravels.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.utd.airtravels.dao.AirlineDAOImpl;
import com.utd.airtravels.dto.FlightDTO;


@Controller
@RequestMapping("/")
public class AirlineController {

	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
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
	
}

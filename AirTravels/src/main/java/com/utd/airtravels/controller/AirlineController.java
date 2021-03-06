package com.utd.airtravels.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.utd.airtravels.dao.AirlineDAOImpl;
import com.utd.airtravels.dto.FareDTO;
import com.utd.airtravels.dto.FlightDTO;
import com.utd.airtravels.dto.FlightInstanceDTO;
import com.utd.airtravels.dto.FlightsListDTO;
import com.utd.airtravels.dto.ReservationDTO;

@Controller
@RequestMapping("/")
public class AirlineController {

	// private static final Logger LOG =
	// LoggerFactory.getLogger(IndexController.class);

	@Autowired
	AirlineDAOImpl airline;

	static Gson gson = new Gson();

	@RequestMapping(value = "/getFlights", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getFlightDetails(
			@RequestBody final FlightDTO flight) {
		System.out.println("Flight details:" + flight.toString());
		
		FlightsListDTO flightsList = airline.getFlightsDetails(flight);

		return gson.toJson(flightsList);
	}

	@RequestMapping(value = "/getFlightFares", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getFlightFareDetails(
			@RequestBody final FareDTO fare) {

		System.out.println("Flight details:" + fare.toString());
		List<FareDTO> fareList = airline.checkFares(fare);

		for (FareDTO f : fareList) {
			System.out.println(f);
		}

		System.out.println("Result: " + gson.toJson(fareList));

		return gson.toJson(fareList);
	}

	@RequestMapping(value = "/getSeatAvailable", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getFlightSeatAvailability(
			@RequestBody final FlightInstanceDTO instance) {

		System.out.println("Flight details:" + instance.toString());
		List<FlightInstanceDTO> instanceList = airline
				.getSeatAvailability(instance);

		for (FlightInstanceDTO f : instanceList) {
			System.out.println(f);
		}

		System.out.println("Result: " + gson.toJson(instanceList));

		return gson.toJson(instanceList);
	}

	@RequestMapping(value = "/getPassengersList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getPassengersList(
			@RequestBody final ReservationDTO reservation) {

		System.out.println("Flight details:" + reservation.toString());
		List<ReservationDTO> passengersList = airline
				.getPassengersList(reservation);

		for (ReservationDTO f : passengersList) {
			System.out.println(f);
		}

		System.out.println("Result: " + gson.toJson(passengersList));

		return gson.toJson(passengersList);
	}
	
	@RequestMapping(value = "/getFlightsList", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody String getFlightsList(
			@RequestBody final ReservationDTO reservation) {

		System.out.println("Flight details:" + reservation.toString());
		List<ReservationDTO> flightsList = airline
				.getFlightsList(reservation);

		for (ReservationDTO f : flightsList) {
			System.out.println(f);
		}

		System.out.println("Result: " + gson.toJson(flightsList));

		return gson.toJson(flightsList);
	}
	
	@RequestMapping(value = "/checkMe", method = RequestMethod.GET)
	public ModelAndView submitForm(@RequestParam String username) {
		System.out.println(username);
		ModelAndView test = new ModelAndView("/form");
		test.addObject("result", "welcome "+username);
		return test;
	}	

}

package com.utd.airtravels.dao;

import java.util.List;

import com.utd.airtravels.dto.FareDTO;
import com.utd.airtravels.dto.FlightDTO;
import com.utd.airtravels.dto.FlightInstanceDTO;

public interface AirlineDAO {
	
	List<FlightDTO> getFlightsDetails(FlightDTO flight);
	
	List<FlightInstanceDTO> getSeatAvailability(FlightInstanceDTO flightInstance);
	
	List<FareDTO> checkFares(FareDTO fare);
	
	void checkPassengerManifest();
	
}

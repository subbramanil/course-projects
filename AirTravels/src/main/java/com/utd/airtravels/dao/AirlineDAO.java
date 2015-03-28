package com.utd.airtravels.dao;

import java.util.List;

import com.utd.airtravels.dto.FareDTO;
import com.utd.airtravels.dto.FlightDTO;

public interface AirlineDAO {
	
	List<FlightDTO> getFlightsDetails(FlightDTO flight);
	
	void getSeatAvailability();
	
	List<FareDTO> checkFares(FareDTO fare);
	
	void checkPassengerManifest();
	
}

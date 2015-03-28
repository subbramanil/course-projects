package com.utd.airtravels.dao;

import java.util.List;

import com.utd.airtravels.dto.FlightDTO;

public interface AirlineDAO {
	
	List<FlightDTO> getFlightsDetails(FlightDTO flight);
	
	void getSeatAvailability();
	
	void checkFares();
	
	void checkPassengerManifest();
	
}

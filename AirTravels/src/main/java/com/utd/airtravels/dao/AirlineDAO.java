package com.utd.airtravels.dao;

import java.util.List;

import com.utd.airtravels.dto.FareDTO;
import com.utd.airtravels.dto.FlightDTO;
import com.utd.airtravels.dto.FlightInstanceDTO;
import com.utd.airtravels.dto.FlightsListDTO;
import com.utd.airtravels.dto.ReservationDTO;

public interface AirlineDAO {
	
	FlightsListDTO getFlightsDetails(FlightDTO flight);
	
	List<FlightInstanceDTO> getSeatAvailability(FlightInstanceDTO flightInstance);
	
	List<FareDTO> checkFares(FareDTO fare);
	
	List<ReservationDTO> getPassengersList(ReservationDTO reservation);
	
	List<ReservationDTO> getFlightsList(ReservationDTO reservation);
	
}

package com.utd.airtravels.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.airtravels.dto.FlightDTO;
import com.utd.airtravels.dto.FlightDetailsDTO;

public class FlightListRowMapper implements RowMapper<FlightDetailsDTO> {

	public FlightDetailsDTO mapRow(ResultSet rs, int rowNum)
			throws SQLException {

		FlightDetailsDTO flightsDetails = new FlightDetailsDTO();
		FlightDTO flight = null;
		int numCols = rs.getMetaData().getColumnCount();
		System.out.println("Num. of Cols: "+numCols);
		
		int numHops = 0;
		
		flight = new FlightDTO();
		flight.setFlightNumber(rs.getString(1));
		flight.setAirline(rs.getString(2));
	    flight.setWeekdays(rs.getString(3));
		flight.setDepCode(rs.getString(4));
		flight.setSchedDepTime(rs.getString(5));
		flight.setArrCode(rs.getString(6));
		flight.setSchedArrTime(rs.getString(7));
		flightsDetails.setFlight1(flight);
		
		if (numCols > 8) {
			flight = new FlightDTO();
			flight.setFlightNumber(rs.getString(8));
			flight.setAirline(rs.getString(9));
			flight.setWeekdays(rs.getString(10));
			flight.setDepCode(rs.getString(6));
			flight.setSchedDepTime(rs.getString(11));
			flight.setArrCode(rs.getString(12));
			flight.setSchedArrTime(rs.getString(13));
			flightsDetails.setFlight2(flight);
			numHops++;
		}
		
		if (numCols > 14) {
			flight = new FlightDTO();
			flight.setFlightNumber(rs.getString(14));
			flight.setAirline(rs.getString(15));
			flight.setWeekdays(rs.getString(16));
			flight.setDepCode(rs.getString(12));
			flight.setSchedDepTime(rs.getString(17));
			flight.setArrCode(rs.getString(18));
			flight.setSchedArrTime(rs.getString(19));
			flightsDetails.setFlight3(flight);
			numHops++;
		}
		
		flightsDetails.setNumHops(numHops);
		return flightsDetails;
	}

}

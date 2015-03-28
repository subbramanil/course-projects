package com.utd.airtravels.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.airtravels.dto.FlightInstanceDTO;

public class FlightInstanceRowMapper implements RowMapper<FlightInstanceDTO>{

	public FlightInstanceDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		FlightInstanceDTO instance = new FlightInstanceDTO();
		instance.setFlightNumber(rs.getString(1));
		instance.setTravelDate(rs.getString(2));
		instance.setNumSeats(rs.getInt(3));
		instance.setAirplaneID(rs.getInt(4));
		instance.setDepartureTime(rs.getString(5));
		instance.setArrivalTime(rs.getString(6));

		return instance;
	}

}

package com.utd.airtravels.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.airtravels.dto.FlightDTO;

public class FlightRowMapper implements RowMapper<FlightDTO>{

	public FlightDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		FlightDTO flight = new FlightDTO();
		flight.setFlightNumber(rs.getString(1));
		flight.setAirline(rs.getString(2));
		flight.setWeekdays(rs.getString(3));
		flight.setDepCode(rs.getString(4));
		flight.setSchedDepTime(rs.getString(5));
		flight.setArrCode(rs.getString(6));
		flight.setSchedArrTime(rs.getString(7));

		return flight;
	}

}

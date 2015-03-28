package com.utd.airtravels.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.airtravels.dto.FareDTO;

public class FareRowMapper implements RowMapper<FareDTO>{

	public FareDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		FareDTO fare = new FareDTO();
		fare.setFlightNumber(rs.getString(1));
		fare.setFareCode(rs.getString(2));
		fare.setFareAmount(rs.getFloat(3));
		fare.setRestrictions(rs.getString(4));
		
		return fare;
	}

}

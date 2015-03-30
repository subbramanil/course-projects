package com.utd.airtravels.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.airtravels.dto.FareDTO;

public class FareRowMapper implements RowMapper<FareDTO>{

	public FareDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		FareDTO fare = new FareDTO();
		fare.setFlightNumber(rs.getString(1));
		fare.setAirline(rs.getString(2));
		fare.setDeptCode(rs.getString(3));
		fare.setArrCode(rs.getString(4));
		fare.setFareCode(rs.getString(5));
		fare.setFareAmount(rs.getFloat(6));
		fare.setRestrictions(rs.getString(7));
		
		return fare;
	}

}

package com.utd.airtravels.util;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.utd.airtravels.dto.ReservationDTO;

public class ReservationRowMapper implements RowMapper<ReservationDTO>{

	public ReservationDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		ReservationDTO instance = new ReservationDTO();
		instance.setFlightNumber(rs.getString(1));
		instance.setTravelDate(rs.getString(2));
		instance.setSeatNumber(rs.getString(3));
		instance.setPassengerName(rs.getString(4));
		instance.setPassengerPhone(rs.getString(5));

		return instance;
	}

}

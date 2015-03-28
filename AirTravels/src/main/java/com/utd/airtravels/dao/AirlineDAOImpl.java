package com.utd.airtravels.dao;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.utd.airtravels.controller.IndexController;
import com.utd.airtravels.dto.FareDTO;
import com.utd.airtravels.dto.FlightDTO;
import com.utd.airtravels.util.FareRowMapper;
import com.utd.airtravels.util.FlightRowMapper;


@Service
public class AirlineDAOImpl implements AirlineDAO {
	
	@Autowired
	private DataSource dataSource;
	
	@Resource
	private Environment env;
	
	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
	@Override
	public List<FlightDTO> getFlightsDetails(FlightDTO flight) {
		
		String sql = env.getProperty("query.getFlightDetails");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug("Received from controller:"+flight.toString());
		List<FlightDTO> flightsList  = jdbcTemplate.query(sql,  new Object[] { flight.getArrCode(), flight.getDepCode() },
				new FlightRowMapper());
		
		System.out.println("Number of flights: "+flightsList.size());
		return flightsList;
	}

	@Override
	public void getSeatAvailability() {
		
	}

	@Override
	public List<FareDTO> checkFares(FareDTO fare) {
		String sql = env.getProperty("query.checkFares");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug("Received from controller:"+fare.toString());
		List<FareDTO> faresList  = jdbcTemplate.query(sql,  new Object[] { fare.getFlightNumber() },
				new FareRowMapper());
		
		System.out.println("Number of flights: "+faresList.size());
		
		return faresList;
	}

	@Override
	public void checkPassengerManifest() {
		// TODO Auto-generated method stub
		
	}

}

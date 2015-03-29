package com.utd.airtravels.dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import com.utd.airtravels.dto.FlightInstanceDTO;
import com.utd.airtravels.dto.ReservationDTO;
import com.utd.airtravels.util.FareRowMapper;
import com.utd.airtravels.util.FlightInstanceRowMapper;
import com.utd.airtravels.util.FlightRowMapper;
import com.utd.airtravels.util.ReservationRowMapper;


@Service
public class AirlineDAOImpl implements AirlineDAO {
	
	@Autowired
	private DataSource dataSource;
	
	@Resource
	private Environment env;
	
	private static final Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
	final static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	
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


	@Override
	public List<FlightInstanceDTO> getSeatAvailability(
			FlightInstanceDTO flightInstance) {
		String sql = env.getProperty("query.checkFlightSeats");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug("Received from controller:"+flightInstance.toString());

		java.util.Date travelDate = null;
		java.sql.Date sqltravelDate = null;
		try {
			travelDate = sdf.parse(flightInstance.getTravelDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sqltravelDate = new java.sql.Date(travelDate.getTime());
		
		System.out.println("Travel Date: "+sqltravelDate);
		
		List<FlightInstanceDTO> instancesList  = jdbcTemplate.query(sql,  new Object[] { flightInstance.getFlightNumber(), sqltravelDate },
				new FlightInstanceRowMapper());
		
		System.out.println("Number of flights: "+instancesList.size());
		
		
		return instancesList;
	}


	@Override
	public List<ReservationDTO> getPassengersList(ReservationDTO reservation) {
		String sql = env.getProperty("query.listPassengers");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug("Received from controller:"+reservation.toString());
		java.util.Date travelDate = null;
		java.sql.Date sqltravelDate = null;
		try {
			travelDate = sdf.parse(reservation.getTravelDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sqltravelDate = new java.sql.Date(travelDate.getTime());
		
		System.out.println("Travel Date:"+sqltravelDate);
		
		List<ReservationDTO> passengersList  = jdbcTemplate.query(sql,  new Object[] { reservation.getFlightNumber(), sqltravelDate },
				new ReservationRowMapper());
		
		System.out.println("Number of Passengers: "+passengersList.size());
		return passengersList;
	}
	
	
	@Override
	public List<ReservationDTO> getFlightsList(ReservationDTO reservation) {
		String sql = env.getProperty("query.listFlights");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug("Received from controller:"+reservation.toString());
		
		List<ReservationDTO> flightsList  = jdbcTemplate.query(sql,  new Object[] { reservation.getPassengerName()},
				new ReservationRowMapper());
		
		System.out.println("Number of Flights: "+flightsList.size());
		return flightsList;
	}
}

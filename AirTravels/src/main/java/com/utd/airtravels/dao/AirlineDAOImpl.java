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
import com.utd.airtravels.dto.FlightDetailsDTO;
import com.utd.airtravels.dto.FlightInstanceDTO;
import com.utd.airtravels.dto.ReservationDTO;
import com.utd.airtravels.util.FareRowMapper;
import com.utd.airtravels.util.FlightInstanceRowMapper;
import com.utd.airtravels.util.FlightListRowMapper;
import com.utd.airtravels.util.ReservationRowMapper;

@Service
public class AirlineDAOImpl implements AirlineDAO {

	@Autowired
	private DataSource dataSource;

	@Resource
	private Environment env;

	private static final Logger LOG = LoggerFactory
			.getLogger(IndexController.class);

	final static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	@Override
	public List<FlightDetailsDTO> getFlightsDetails(FlightDTO flight) {
		String sql = null;
		System.out.println("Received from controller:" + flight.toString());
		sql = env.getProperty("query.getFlightDetails");

		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<FlightDetailsDTO> flightsList = jdbcTemplate.query(sql,
				new Object[] { flight.getArrCode(), flight.getDepCode() },
				new FlightListRowMapper());
		int nHop = flight.getMaxHop();
		if(nHop<=2){
			flightsList.addAll(getFlightsWith1HopDetails(flight));
		}
		if(nHop<=3){
			flightsList.addAll(getFlightsWith2HopDetails(flight));
		}
		if(nHop<4){
			flightsList.addAll(getFlightsWith3HopDetails(flight));
		}
		
		System.out.println("Number of flights: " + flightsList.size());
		return flightsList;
	}

	public List<FlightDetailsDTO> getFlightsWith1HopDetails(FlightDTO flight) {
		String sql = env.getProperty("query.getFlightWith1HopDetails");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<FlightDetailsDTO> flightsList = jdbcTemplate.query(sql,
				new Object[] { flight.getArrCode(), flight.getDepCode() },
				new FlightListRowMapper());
		checkValidFlights(flightsList);
		System.out.println("Number of flights: " + flightsList.size());
		return flightsList;
	}
	
	public List<FlightDetailsDTO> getFlightsWith2HopDetails(FlightDTO flight) {
		String sql = env.getProperty("query.getFlightWith2HopDetails");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<FlightDetailsDTO> flightsList = jdbcTemplate.query(sql,
				new Object[] { flight.getArrCode(), flight.getDepCode(), flight.getDepCode()},
				new FlightListRowMapper());
		checkValidFlights(flightsList);
		System.out.println("Number of flights: " + flightsList.size());
		return flightsList;
	}
	
	public List<FlightDetailsDTO> getFlightsWith3HopDetails(FlightDTO flight) {
		String sql = env.getProperty("query.getFlightWith3HopDetails");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		List<FlightDetailsDTO> flightsList = jdbcTemplate.query(sql,
				new Object[] { flight.getArrCode(), flight.getDepCode(), flight.getDepCode() , flight.getDepCode()  },
				new FlightListRowMapper());
		checkValidFlights(flightsList);
		System.out.println("Number of flights: " + flightsList.size());
		return flightsList;
	}
	
	private void checkValidFlights(List<FlightDetailsDTO> flightsList) {

		for (FlightDetailsDTO flights : flightsList) {
			System.out.println(flights);
			switch (flights.getNumHops()) {
			case 1:
				int f1 = getDayCode(flights.getFlight1().getWeekdays());
				int f2 = getDayCode(flights.getFlight2().getWeekdays());
				int f3 = f1 & f2;
				System.out.println(Integer.toBinaryString(f3));
				String finalDay = getDayString(f3);
				if (f3 > 0) {
					System.out.println("Valid flights");
					flights.getFlight1().setWeekdays(finalDay);
					flights.getFlight2().setWeekdays(finalDay);
				} else {
					System.out.println("Invalid Flights");
					flightsList.remove(flights);
				}
				break;
			case 2:
				f1 = getDayCode(flights.getFlight1().getWeekdays());
				f2 = getDayCode(flights.getFlight2().getWeekdays());
				f3 = getDayCode(flights.getFlight2().getWeekdays());
				f3 = f1 & f2 & f3;
				System.out.println(Integer.toBinaryString(f3));
				finalDay = getDayString(f3);
				if (f3 > 0) {
					System.out.println("Valid flights");
					flights.getFlight1().setWeekdays(finalDay);
					flights.getFlight2().setWeekdays(finalDay);
					flights.getFlight3().setWeekdays(finalDay);
				} else {
					System.out.println("Invalid Flights");
					flightsList.remove(flights);
				}
				break;
			case 3:
				f1 = getDayCode(flights.getFlight1().getWeekdays());
				f2 = getDayCode(flights.getFlight2().getWeekdays());
				f3 = getDayCode(flights.getFlight3().getWeekdays());
				int f4 = getDayCode(flights.getFlight4().getWeekdays());
				f4 = f1 & f2 & f3 & f4;
				System.out.println(Integer.toBinaryString(f4));
				finalDay = getDayString(f4);
				if (f4 > 0) {
					System.out.println("Valid flights");
					flights.getFlight1().setWeekdays(finalDay);
					flights.getFlight2().setWeekdays(finalDay);
					flights.getFlight3().setWeekdays(finalDay);
				} else {
					System.out.println("Invalid Flights");
					flightsList.remove(flights);
				}
				break;
			}
		}
	}

	private String getDayString(int code) {
		String str = "";
		str += ((code & 1) > 1) ? "Sun_" : "";
		str += ((code & 2) > 1) ? "Mon_" : "";
		str += ((code & 4) > 1) ? "Tue_" : "";
		str += ((code & 8) > 1) ? "Wed_" : "";
		str += ((code & 16) > 1) ? "Thu_" : "";
		str += ((code & 32) > 1) ? "Fri_" : "";
		str += ((code & 64) > 1) ? "Sat" : "";
		return str;
	}

	private int getDayCode(String weekdays) {
		int code = 0;
		String[] days = weekdays.split("_");
		for (String day : days) {
			switch (day) {
			case "Sun":
				code |= 1;
				break;
			case "Mon":
				code |= 2;
				break;
			case "Tue":
				code |= 4;
				break;
			case "Wed":
				code |= 8;
				break;
			case "Thu":
				code |= 16;
				break;
			case "Fri":
				code |= 32;
				break;
			case "Sat":
				code |= 64;
			}
		}

		System.out.println(code);
		return code;
	}

	@Override
	public List<FareDTO> checkFares(FareDTO fare) {
		String sql = env.getProperty("query.checkFares");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug("Received from controller:" + fare.toString());
		List<FareDTO> faresList = jdbcTemplate.query(sql,
				new Object[] { fare.getFlightNumber() }, new FareRowMapper());

		System.out.println("Number of flights: " + faresList.size());

		return faresList;
	}

	@Override
	public List<FlightInstanceDTO> getSeatAvailability(
			FlightInstanceDTO flightInstance) {
		String sql = env.getProperty("query.checkFlightSeats");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug("Received from controller:" + flightInstance.toString());

		java.util.Date travelDate = null;
		java.sql.Date sqltravelDate = null;
		try {
			travelDate = sdf.parse(flightInstance.getTravelDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sqltravelDate = new java.sql.Date(travelDate.getTime());

		System.out.println("Travel Date: " + sqltravelDate);

		List<FlightInstanceDTO> instancesList = jdbcTemplate
				.query(sql, new Object[] { flightInstance.getFlightNumber(),
						sqltravelDate }, new FlightInstanceRowMapper());

		System.out.println("Number of flights: " + instancesList.size());

		return instancesList;
	}

	@Override
	public List<ReservationDTO> getPassengersList(ReservationDTO reservation) {
		String sql = env.getProperty("query.listPassengers");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug("Received from controller:" + reservation.toString());
		java.util.Date travelDate = null;
		java.sql.Date sqltravelDate = null;
		try {
			travelDate = sdf.parse(reservation.getTravelDate());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sqltravelDate = new java.sql.Date(travelDate.getTime());

		System.out.println("Travel Date:" + sqltravelDate);

		List<ReservationDTO> passengersList = jdbcTemplate.query(sql,
				new Object[] { reservation.getFlightNumber(), sqltravelDate },
				new ReservationRowMapper());

		System.out.println("Number of Passengers: " + passengersList.size());
		return passengersList;
	}

	@Override
	public List<ReservationDTO> getFlightsList(ReservationDTO reservation) {
		String sql = env.getProperty("query.listFlights");
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		LOG.debug("Received from controller:" + reservation.toString());

		List<ReservationDTO> flightsList = jdbcTemplate.query(sql,
				new Object[] { reservation.getPassengerName() },
				new ReservationRowMapper());

		System.out.println("Number of Flights: " + flightsList.size());
		return flightsList;
	}
}

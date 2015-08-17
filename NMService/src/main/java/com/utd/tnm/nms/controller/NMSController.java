package com.utd.tnm.nms.controller;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.utd.tnm.nms.topology.NWMonitorAckedTopology;
import com.utd.tnm.nms.util.CommonDataUtil;

@Controller
@RequestMapping("/")
public class NMSController {

	@Autowired
    private ServletContext servletContext;
	
	@RequestMapping(value = "/")
	public @ResponseBody String getFlightDetails() {
		CommonDataUtil.contextPath = servletContext.getContextPath();
		System.out.println(CommonDataUtil.contextPath);
		CommonDataUtil.context = servletContext;
		NWMonitorAckedTopology.startNMService();
		return servletContext.getContextPath();
	}
	
	/*
	 * @RequestMapping(value = "/getFlights", method = RequestMethod.POST,
	 * produces = "application/json") public @ResponseBody String
	 * getFlightDetails(
	 * 
	 * @RequestBody final FlightDTO flight) { System.out.println(
	 * "Flight details:" + flight.toString());
	 * 
	 * FlightsListDTO flightsList = airline.getFlightsDetails(flight);
	 * 
	 * return gson.toJson(flightsList); }
	 */

}

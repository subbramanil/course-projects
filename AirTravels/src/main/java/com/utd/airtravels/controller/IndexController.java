package com.utd.airtravels.controller;
/**
 * 
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author Subbu
 *
 */
@Controller
@RequestMapping("/")
public class IndexController {
	
	Logger LOG = LoggerFactory.getLogger(IndexController.class);
	
	private static String welcome_msg = "service running...!!!";
	
	@RequestMapping( value = "/", method = RequestMethod.GET)
    public @ResponseBody String testService() {
        return welcome_msg;
    }
	
	@RequestMapping( value = "/home", method = RequestMethod.GET)
    public String displayLogin() {
        return "/index";
    }
	
	@RequestMapping( value = "/flightDetails", method = RequestMethod.GET)
    public String displayFlights() {
        return "/flights";
    }
	
	@RequestMapping( value = "/fareDetails", method = RequestMethod.GET)
    public String displayFlightFares() {
        return "/fares";
    }
	
	@RequestMapping( value = "/seatDetails", method = RequestMethod.GET)
    public String displayFlightSeatsDeatils() {
        return "/seats";
    }
	
	@RequestMapping( value = "/checkManifest", method = RequestMethod.GET)
    public String displayPassengerManifestDeatils() {
        return "/manifest";
    }
}

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
        return "/home";
    }
	
/*	@RequestMapping( value = "/login", method = RequestMethod.GET)
    public String displayHome() {
//		return welcome_msg;
        return "/login";
    }
	
	@RequestMapping( value = "/error", method = RequestMethod.GET)
    public String displayError() {
//		return welcome_msg;
        return "/error";
    }
	
	@RequestMapping( value = "/login", method = RequestMethod.POST)
    public @ResponseBody String loginUser(@RequestParam String userName, @RequestParam String password) {
		LOG.debug(userName+ " "+password);
//		if(userService.testUser(userName, password)){
//			return welcome_msg;
//		}else{
//			return "error";
//		}
		return userName+ " "+ password;
    }*/
	
}

package com.utd.airtravels.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.utd.airtravels.dto.User;

@SuppressWarnings("deprecation")
public class CheckUserController extends SimpleFormController {
	
	public CheckUserController(){
		setCommandClass(User.class);
		setCommandName("user");
	}

	@Override
	protected ModelAndView onSubmit(Object command) throws Exception {
		User user = (User) command;
		ModelAndView test = new ModelAndView("/form");
		test.addObject("result", "welcome "+user.getMailID());
		return test;
	}
	
	
}

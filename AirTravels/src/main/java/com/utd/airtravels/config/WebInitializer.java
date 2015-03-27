package com.utd.airtravels.config;


import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

public class WebInitializer implements WebApplicationInitializer {

	@Override
    public void onStartup(ServletContext container) {
		AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
		ctx.register(MvcConfig.class, BasicConfig.class);
		container.addListener(new ContextLoaderListener(ctx));
		
		AnnotationConfigWebApplicationContext dispatchCtx = new AnnotationConfigWebApplicationContext();
		ServletRegistration.Dynamic dispatcher;
		dispatcher = container.addServlet("dispatcher",
				new DispatcherServlet(dispatchCtx));
		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		
    }

}

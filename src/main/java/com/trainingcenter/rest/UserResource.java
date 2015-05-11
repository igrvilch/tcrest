package com.trainingcenter.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trainingcenter.model.User;
import com.trainingcenter.service.UserService;

@Component
@Path("/user")
public class UserResource {
	
	@Context private HttpServletRequest request;
	
	@Autowired
	UserService userService;
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public User getUser() {
		User userFromSession = (User) request.getSession().getAttribute("user");
		User user  = userService.getUser(userFromSession.getUserLogin());
		return user;
	}

}

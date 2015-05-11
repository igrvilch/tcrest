package com.trainingcenter.rest;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import com.trainingcenter.model.Category;
import com.trainingcenter.model.Course;
import com.trainingcenter.model.User;
import com.trainingcenter.service.CoursesService;
import com.trainingcenter.service.UserService;

import freemarker.template.TemplateException;


@Component
@Path("/courses")
public class CoursesResource {

		
	@Autowired
	CoursesService coursesService;
	



	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getCourses() {
		List<Course> courses = coursesService.getCourses();
		return courses;
	}
	
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.TEXT_HTML)
	public Response createCourse(Course course,  @Context HttpServletRequest req)  {
		
		
		
	 	User user = (User) req.getSession().getAttribute("user");
		coursesService.saveCourse(course, user);

		return Response
				.status(Response.Status.CREATED)// 201
				.entity("A new resource has been created "
						)
				.build();
	}
	
	@POST
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.TEXT_HTML })
	public Response partialUpdatePodcast(@PathParam("id") int id,
			Course course)  {
		
		coursesService.updateCourse(course, id);
		
		return Response
				.status(Response.Status.OK)
				// 200
				.entity("successfully updated")
				.build();
	}
	
}

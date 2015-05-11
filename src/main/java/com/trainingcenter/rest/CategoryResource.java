package com.trainingcenter.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.trainingcenter.model.Category;
import com.trainingcenter.model.User;
import com.trainingcenter.service.CategoryService;
import com.trainingcenter.service.UserService;

@Component
@Path("/categories")
public class CategoryResource {

	@Autowired
	CategoryService categoryService;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getCategories() {
		List<Category> categories = categoryService.getCategories();
		return categories;
	}
}

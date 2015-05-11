package com.trainingcenter.controller;

import com.trainingcenter.service.CategoryService;
import com.trainingcenter.service.CoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
@Controller
public class CoursesController {

    @Autowired
    CoursesService coursesService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/courses", method = RequestMethod.GET)
    public String showCourses(Model model, HttpServletRequest request) {
        model.addAttribute("courses", coursesService.getCourses());
        model.addAttribute("categories", categoryService.getCategoriesNames());
        model.addAttribute("chosenCategory", request.getParameter("courseCategory"));
        return "courses_ajax";
    }

    @RequestMapping(value = "/mycourses", method = RequestMethod.GET)
    public String showMyCourses(Model model, HttpServletRequest request) {
        model.addAttribute("courses", coursesService.getCourses());
        model.addAttribute("categories", categoryService.getCategoriesNames());
        model.addAttribute("chosenCategory", request.getParameter("courseCategory"));
        return "mycourses";
    }

    @RequestMapping(value = "/courses/{courseId}", method = RequestMethod.GET)
    public String showCourseDetailPage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
        } catch (NumberFormatException e) {
            return "detail";
        }
        return "detail";
    }

    @RequestMapping(value = "/courses/{courseId}/participants", method = RequestMethod.GET)
    public String showParticipantsPage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
        } catch (NumberFormatException e) {
            return "participants";
        }
        return "participants";
    }
}

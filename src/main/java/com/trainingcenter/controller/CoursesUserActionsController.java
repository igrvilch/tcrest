package com.trainingcenter.controller;

import com.trainingcenter.form.MarkForm;
import com.trainingcenter.model.User;
import com.trainingcenter.service.CategoryService;
import com.trainingcenter.service.CoursesService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class CoursesUserActionsController {

    @Autowired
    CoursesService coursesService;

    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/courses/{courseId}/subscribe", method = RequestMethod.GET)
    public String showCourseSubscribePage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
        } catch (NumberFormatException e) {
            return "subscribe";
        }
        return "subscribe";
    }

    @RequestMapping(value = "/courses/{courseId}/subscribe", method = RequestMethod.POST)
    public String subscribeCourse(@PathVariable int courseId, HttpSession session, HttpServletRequest request) throws IOException, TemplateException {
        User user = (User) session.getAttribute("user");
        coursesService.subscribe(user, courseId, request);
        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{courseId}/attend", method = RequestMethod.GET)
    public String showCourseAttendPage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
        } catch (NumberFormatException e) {
            return "attend";
        }
        return "attend";
    }

    @RequestMapping(value = "/courses/{courseId}/attend", method = RequestMethod.POST)
    public String attendCourse(@PathVariable int courseId, HttpSession session, HttpServletRequest request) throws IOException, TemplateException {
        User user = (User) session.getAttribute("user");
        coursesService.attend(user, courseId, request);
        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{courseId}/evaluate", method = RequestMethod.GET)
    public String showCourseEvaluatePage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute(new MarkForm());
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
        } catch (NumberFormatException e) {
            return "evaluate";
        }
        return "evaluate";
    }

    @RequestMapping(value = "/courses/{courseId}/evaluate", method = RequestMethod.POST)
    public String evaluateCourse(@ModelAttribute("markForm") @Valid MarkForm markForm, BindingResult result,
                                 @PathVariable int courseId, HttpSession session, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("course", coursesService.getCourse(courseId));
            return "evaluate";
        }
        User user = (User) session.getAttribute("user");
        coursesService.evaluate(user, courseId, markForm);
        return "redirect:/courses";
    }
}

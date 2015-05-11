package com.trainingcenter.controller;

import com.trainingcenter.form.CoursesForm;
import com.trainingcenter.model.Course;
import com.trainingcenter.model.User;
import com.trainingcenter.service.CategoryService;
import com.trainingcenter.service.CoursesService;
import com.trainingcenter.service.MailService;
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
public class CoursesOwnerActionsController {

    @Autowired
    CoursesService coursesService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MailService mailService;

    @RequestMapping(value = "/courses/create", method = RequestMethod.GET)
    public String showCoursesCreate(Model model) {
        model.addAttribute(new CoursesForm());
        model.addAttribute("categories", categoryService.getCategoriesNames());
        return "create";
    }

    @RequestMapping(value = "/courses/create", method = RequestMethod.POST)
    public String createCourse( @Valid CoursesForm coursesForm, BindingResult result, Model model,
                               HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategoriesNames());
            return "create";
        }
        User user = (User) session.getAttribute("user");
        coursesService.saveCourse(coursesForm, user);
        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{courseId}/update", method = RequestMethod.GET)
    public String showCourseUpdatePage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute(new CoursesForm());
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
            model.addAttribute("categories", categoryService.getCategoriesNames());
        } catch (NumberFormatException e) {
            return "update";
        }
        return "update";
    }

    @RequestMapping(value = "/courses/{courseId}/update", method = RequestMethod.POST)
    public String updateCourse(@ModelAttribute("coursesForm") @Valid CoursesForm coursesForm, BindingResult result,
                               @PathVariable int courseId, Model model, HttpServletRequest request,
                               HttpSession session) throws IOException, TemplateException {
        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getCategoriesNames());
            model.addAttribute("course", coursesService.getCourse(courseId));
            return "update";
        }
        if (request.getParameter("button").equals("review")) {
            Course course = coursesService.getCourse(courseId);
            User user = (User) session.getAttribute("user");
            coursesService.sendToReviewCourse(courseId);
        }
        Course course = coursesService.getCourse(courseId);
        coursesService.updateCourse(coursesForm, courseId, course.getCourseState());
        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{courseId}/start", method = RequestMethod.GET)
    public String showCourseStartPage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
            model.addAttribute("categories", categoryService.getCategoriesNames());
        } catch (NumberFormatException e) {
            return "start";
        }
        return "start";
    }

    @RequestMapping(value = "/courses/{courseId}/start", method = RequestMethod.POST)
    public String startCourse(@PathVariable String courseId, HttpSession session, HttpServletRequest request) throws IOException, TemplateException {
        coursesService.startCourse(Integer.parseInt(courseId));
        Course course = coursesService.getCourse(Integer.parseInt(courseId));
        User user = (User) session.getAttribute("user");
       
        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{courseId}/finish", method = RequestMethod.GET)
    public String showCourseFinishPage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
            model.addAttribute("categories", categoryService.getCategoriesNames());
        } catch (NumberFormatException e) {
            return "finish";
        }
        return "finish";
    }

    @RequestMapping(value = "/courses/{courseId}/finish", method = RequestMethod.POST)
    public String finishCourse(@PathVariable String courseId, HttpSession session, HttpServletRequest request) throws IOException, TemplateException {
        coursesService.finishCourse(Integer.parseInt(courseId));
        Course course = coursesService.getCourse(Integer.parseInt(courseId));
        User user = (User) session.getAttribute("user");
        
        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{courseId}/notify", method = RequestMethod.GET)
    public String showCourseNotifyPage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
            model.addAttribute("categories", categoryService.getCategoriesNames());
        } catch (NumberFormatException e) {
            return "notify";
        }
        return "notify";
    }

    @RequestMapping(value = "/courses/{courseId}/notify", method = RequestMethod.POST)
    public String notifyCourse(@PathVariable String courseId, HttpServletRequest request, HttpSession session) throws IOException, TemplateException {
        Course course = coursesService.getCourse(Integer.parseInt(courseId));
        User user = (User) session.getAttribute("user");
       
        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{courseId}/sendtoreview", method = RequestMethod.GET)
    public String sendToReviewCourse(@PathVariable String courseId, HttpSession session, HttpServletRequest request) throws IOException, TemplateException {
        Course course = coursesService.getCourse(Integer.parseInt(courseId));
        User user = (User) session.getAttribute("user");
      
        coursesService.sendToReviewCourse(Integer.parseInt(courseId));
        return "redirect:/courses";
    }

    @RequestMapping(value = "/courses/{courseId}/delete", method = RequestMethod.GET)
    public String showCourseDeletePage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
            model.addAttribute("categories", categoryService.getCategoriesNames());
        } catch (NumberFormatException e) {
            return "delete";
        }
        return "delete";
    }

    @RequestMapping(value = "/courses/{courseId}/delete", method = RequestMethod.POST)
    public String deleteCourse(@PathVariable String courseId, HttpSession session) throws IOException, TemplateException {
        Course course = coursesService.getCourse(Integer.parseInt(courseId));
        User user = (User) session.getAttribute("user");
        if (course.getKmDecision().equals("Reject") || course.getDmDecision().equals("Reject")) {
            
        }
        coursesService.deleteCourse(Integer.parseInt(courseId));
        return "redirect:/courses";
    }
}

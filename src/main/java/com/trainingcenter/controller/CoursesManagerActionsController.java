package com.trainingcenter.controller;

import com.trainingcenter.form.ApproveForm;
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
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class CoursesManagerActionsController {

    @Autowired
    CoursesService coursesService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    MailService mailService;

    @RequestMapping(value = "/courses/{courseId}/approve", method = RequestMethod.GET)
    public String showApprovePage(@PathVariable String courseId, Model model) {
        try {
            model.addAttribute(new ApproveForm());
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
        } catch (NumberFormatException e) {
            return "approve";
        }
        return "approve";
    }

    @RequestMapping(value = "/courses/{courseId}/approve", method = RequestMethod.POST)
    public String approveCourse(@ModelAttribute("approveForm") @Valid ApproveForm approveForm, BindingResult result,
                                @PathVariable String courseId, Model model, HttpSession session, HttpServletRequest request) throws IOException, TemplateException {
        if ((result.getFieldValue("dmDecision") != null && result.getFieldValue("dmDecision").equals("notselect")) ||
                (result.getFieldValue("kmDecision") != null && result.getFieldValue("kmDecision").equals("notselect"))) {
            model.addAttribute("categories", categoryService.getCategoriesNames());
            model.addAttribute("course", coursesService.getCourse(Integer.parseInt(courseId)));
            return "approve";
        }
        User user = (User) session.getAttribute("user");
        coursesService.setApprove(Integer.parseInt(courseId), approveForm, user, request);
        return "redirect:/courses";
    }
}

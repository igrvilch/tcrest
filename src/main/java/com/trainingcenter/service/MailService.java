package com.trainingcenter.service;

import com.trainingcenter.model.Course;
import com.trainingcenter.model.User;
import freemarker.template.TemplateException;
import org.springframework.mail.SimpleMailMessage;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface MailService {

    public void sendMail(SimpleMailMessage message);

    public void sendCourseAnnouncement(Course course, User user, HttpServletRequest request) throws IOException, TemplateException;

    public void sendCourseApprovalUpdate(Course course, User user) throws IOException, TemplateException;

    public void sendNewCourseAdded(Course course, User user, HttpServletRequest request) throws IOException, TemplateException;

    public void sendCourseRejected(Course course, User user, HttpServletRequest request) throws IOException, TemplateException;

    public void sendCourseDeleted(Course course, User user) throws IOException, TemplateException;

    public void sendCourseOpen(Course course, User user, HttpServletRequest request) throws IOException, TemplateException;

    public void sendCourseReady(Course course, User user, HttpServletRequest request) throws IOException, TemplateException;

    public void sendCourseStarted(Course course, User user, HttpServletRequest request) throws IOException, TemplateException;

    public void sendCourseFinished(Course course, User user, HttpServletRequest request) throws IOException, TemplateException;

    public void sendCourseNotify(Course course, User user, HttpServletRequest request) throws IOException, TemplateException;
}

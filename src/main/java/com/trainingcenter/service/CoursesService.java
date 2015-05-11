package com.trainingcenter.service;

import com.trainingcenter.form.ApproveForm;
import com.trainingcenter.form.CoursesForm;
import com.trainingcenter.form.MarkForm;
import com.trainingcenter.model.Course;
import com.trainingcenter.model.User;
import com.trainingcenter.model.UserCourse;

import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

public interface CoursesService {

    public List<Course> getCourses();

    public Course getCourse(int courseId);

    public void saveCourse(CoursesForm coursesForm, User user);

    public void updateCourse(CoursesForm coursesForm, int courseId, Course.CourseState courseState);

    public void subscribe(User user, int courseId, HttpServletRequest request) throws IOException, TemplateException;

    public void attend(User user, int courseId, HttpServletRequest request) throws IOException, TemplateException;

    public UserCourse getUserCourse(User user, int courseId);

    public void evaluate(User user, int courseId, MarkForm markForm);

    public void setApprove(int courseId, ApproveForm approveForm, User user, HttpServletRequest request) throws IOException, TemplateException;

    public void startCourse(int courseId);

    public void finishCourse(int courseId);

    public void sendToReviewCourse(int courseId);

    public void deleteCourse(int courseId);

	void saveCourse(String courseName, String courseCategory,
			String courseLinks, String courseDescription, User user);

	public void saveCourse(Course course, User user);

	public void updateCourse(Course course, int id);
}

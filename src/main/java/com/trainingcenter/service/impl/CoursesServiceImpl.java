package com.trainingcenter.service.impl;

import com.trainingcenter.dao.CoursesDao;
import com.trainingcenter.form.ApproveForm;
import com.trainingcenter.form.CoursesForm;
import com.trainingcenter.form.MarkForm;
import com.trainingcenter.model.Category;
import com.trainingcenter.model.Course;
import com.trainingcenter.model.User;
import com.trainingcenter.model.UserCourse;
import com.trainingcenter.service.CategoryService;
import com.trainingcenter.service.CoursesService;
import com.trainingcenter.service.MailService;

import freemarker.template.TemplateException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.util.List;

@Service
public class CoursesServiceImpl implements CoursesService {

	@Autowired
	CoursesDao coursesDao;

	@Autowired
	CategoryService categoryService;

	@Autowired
	MailService mailService;

	@Transactional
	@Override
	public List<Course> getCourses() {
		return coursesDao.getCourses();
	}

	@Transactional
	@Override
	public Course getCourse(int courseId) {
		return coursesDao.getCourse(courseId);
	}

	@Transactional
	@Override
	public void saveCourse(String courseName, String courseCategory,
			String courseLinks, String courseDescription, User user) {
		Course course = new Course();
		course.setCourseName(courseName);
		course.setCourseDescription(courseDescription);
		Category category = categoryService.getCategory(courseCategory);
		course.setCourseCategory(category);
		course.setCourseState(Course.CourseState.DRAFT);
		course.setCourseCreator(user.getUserLogin());
		course.setCourseLinks(courseLinks);
		course.setCourseOwner(user);
		course.setDmDecision("Select one");
		course.setKmDecision("Select one");
		course.setCourseMinSubs(1);
		course.setCourseMinAttenders(1);
		coursesDao.saveCourse(course);
	}

	@Transactional
	@Override
	public void updateCourse(CoursesForm coursesForm, int courseId,
			Course.CourseState courseState) {
		Course course = getCourse(courseId);
		course.setCourseName(coursesForm.getCourseName());
		course.setCourseDescription(coursesForm.getCourseDescription());
		course.setCourseLinks(coursesForm.getCourseLinks());
		Category category = categoryService.getCategory(coursesForm
				.getCourseCategory());
		course.setCourseCategory(category);
		course.setCourseState(courseState);
		course.setCourseMinSubs(coursesForm.getMinSubs());
		course.setCourseMinAttenders(coursesForm.getMinAttenders());
		coursesDao.updateCourse(course);
	}

	@Override
	@Transactional
	public void subscribe(User user, int courseId, HttpServletRequest request)
			throws IOException, TemplateException {
		UserCourse userCourse = new UserCourse();
		userCourse.setCourse(getCourse(courseId));
		userCourse.setUser(user);
		userCourse.setUserCourseState("subscriber");
		coursesDao.saveUserCourse(userCourse);

		Course course = getCourse(courseId);
		if (course.getCourseState() == Course.CourseState.NEW
				&& course.getCourseMinSubs() == (course.getSubscribers().size() + 1)) {
			course.setCourseState(Course.CourseState.OPEN);
		}
		coursesDao.updateCourse(course);
	}

	@Override
	@Transactional
	public void attend(User user, int courseId, HttpServletRequest request)
			throws IOException, TemplateException {
		UserCourse userCourse = getUserCourse(user, courseId);
		userCourse.setUserCourseState("attender");
		coursesDao.saveUserCourse(userCourse);

		Course course = getCourse(courseId);
		if (course.getCourseMinAttenders() == (course.getAttenders().size())) {
			course.setCourseState(Course.CourseState.READY);
		}
		coursesDao.updateCourse(course);
	}

	@Override
	@Transactional
	public UserCourse getUserCourse(User user, int courseId) {
		return coursesDao.getUserCourse(user, courseId);

	}

	@Override
	@Transactional
	public void evaluate(User user, int courseId, MarkForm markForm) {
		UserCourse userCourse = getUserCourse(user, courseId);
		userCourse.setUserCourseState("evaluator");
		userCourse.setUserCourseMark(markForm.getMark());
		coursesDao.saveUserCourse(userCourse);
	}

	@Override
	@Transactional
	public void setApprove(int courseId, ApproveForm approveForm, User user,
			HttpServletRequest request) throws IOException, TemplateException {
		Course course = getCourse(courseId);
		if (approveForm.getDmDecision() != null
				&& approveForm.getDmDecision().equals("Approve")
				&& course.getKmDecision().equals("Approve")) {
			course.setDmDecision(approveForm.getDmDecision());
			course.setDmReason(approveForm.getDmReason());
			course.setCourseState(Course.CourseState.NEW);
		}
		if (approveForm.getKmDecision() != null
				&& approveForm.getKmDecision().equals("Approve")
				&& course.getDmDecision().equals("Approve")) {
			course.setKmDecision(approveForm.getKmDecision());
			course.setKmReason(approveForm.getKmReason());
			course.setCourseState(Course.CourseState.NEW);
		}
		if (approveForm.getDmDecision() != null
				&& approveForm.getDmDecision().equals("Approve")) {
			course.setDmDecision(approveForm.getDmDecision());
			course.setDmReason(approveForm.getDmReason());
		}
		if (approveForm.getKmDecision() != null
				&& approveForm.getKmDecision().equals("Approve")) {
			course.setKmDecision(approveForm.getKmDecision());
			course.setKmReason(approveForm.getKmReason());
		}
		if (approveForm.getDmDecision() != null
				&& approveForm.getDmDecision().equals("Reject")) {
			course.setDmDecision(approveForm.getDmDecision());
			course.setDmReason(approveForm.getDmReason());
			course.setCourseState(Course.CourseState.REJECTED);
		}
		if (approveForm.getKmDecision() != null
				&& approveForm.getKmDecision().equals("Reject")) {
			course.setKmDecision(approveForm.getKmDecision());
			course.setKmReason(approveForm.getKmReason());
			course.setCourseState(Course.CourseState.REJECTED);
		}
		coursesDao.updateCourse(course);
	}

	@Override
	@Transactional
	public void startCourse(int courseId) {
		Course course = getCourse(courseId);
		course.setCourseState(Course.CourseState.INPROGRESS);
		coursesDao.updateCourse(course);
	}

	@Override
	@Transactional
	public void finishCourse(int courseId) {
		Course course = getCourse(courseId);
		course.setCourseState(Course.CourseState.FINISHED);
		coursesDao.updateCourse(course);
	}

	@Override
	@Transactional
	public void sendToReviewCourse(int courseId) {
		Course course = getCourse(courseId);
		course.setCourseState(Course.CourseState.PROPOSAL);
		course.setDmDecision("Select one");
		course.setKmDecision("Select one");
		coursesDao.updateCourse(course);
	}

	@Override
	@Transactional
	public void deleteCourse(int courseId) {
		Course course = getCourse(courseId);
		coursesDao.deleteCourse(course);
	}

	@Override
	@Transactional
	public void saveCourse(CoursesForm coursesForm, User user) {
		 Course course = new Course();
	        course.setCourseName(coursesForm.getCourseName());
	        course.setCourseDescription(coursesForm.getCourseDescription());
	        Category category = categoryService.getCategory(coursesForm.getCourseCategory());
	        course.setCourseCategory(category);
	        course.setCourseState(Course.CourseState.DRAFT);
	        course.setCourseCreator(user.getUserLogin());
	        course.setCourseLinks(coursesForm.getCourseLinks());
	        course.setCourseOwner(user);
	        course.setDmDecision("Select one");
	        course.setKmDecision("Select one");
	        course.setCourseMinSubs(coursesForm.getMinSubs());
	        course.setCourseMinAttenders(coursesForm.getMinAttenders());
	        coursesDao.saveCourse(course);

	}

	@Override
	@Transactional
	public void saveCourse(Course course, User user) {
		Category category = categoryService.getCategory(course.getCourseCategory().getCategoryName());
		course.setCourseCategory(category);
		course.setCourseState(Course.CourseState.DRAFT);
		course.setCourseCreator(user.getUserLogin());
		course.setCourseOwner(user);
		course.setDmDecision("Select one");
		course.setKmDecision("Select one");
		course.setCourseMinSubs(1);
		course.setCourseMinAttenders(1);
		coursesDao.saveCourse(course);

	}

	@Override
	@Transactional
	public void updateCourse(Course course, int id) {
		Course course2 = getCourse(id);
		course2.setCourseName(course.getCourseName());
		course2.setCourseDescription(course.getCourseDescription());
		course2.setCourseLinks(course.getCourseLinks());
		Category category = categoryService.getCategory(course.getCourseCategory().getCategoryName());
		System.out.println(category);
		course2.setCourseCategory(category);
		coursesDao.updateCourse(course2);
	}
}

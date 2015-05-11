package com.trainingcenter.dao;

import com.trainingcenter.model.Course;
import com.trainingcenter.model.User;
import com.trainingcenter.model.UserCourse;

import java.util.List;

public interface CoursesDao {

    public List<Course> getCourses();

    public Course getCourse(int CourseId);

    public void saveCourse(Course course);

    public void updateCourse(Course course);

    public void saveUserCourse(UserCourse userCourse);

    public UserCourse getUserCourse(User user, int courseId);

    public void deleteCourse(Course course);
}

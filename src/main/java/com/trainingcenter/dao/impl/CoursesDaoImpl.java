package com.trainingcenter.dao.impl;

import com.trainingcenter.dao.CoursesDao;
import com.trainingcenter.model.Course;
import com.trainingcenter.model.User;
import com.trainingcenter.model.UserCourse;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CoursesDaoImpl implements CoursesDao {

    @Autowired
    SessionFactory sessionFactory;

    @Override
    public List<Course> getCourses() {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Course.class);
        List<Course> courses = criteria.list();
        return courses;
    }

    @Override
    public Course getCourse(int courseId) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Course.class);
        Course course = (Course) criteria.add(Restrictions.eq("courseId", courseId)).uniqueResult();
        return course;
    }

    @Override
    public void saveCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.save(course);
    }

    @Override
    public void updateCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.update(course);
    }

    @Override
    public void saveUserCourse(UserCourse userCourse) {
        Session session = sessionFactory.getCurrentSession();
        session.save(userCourse);
    }

    @Override
    public UserCourse getUserCourse(User user, int courseId) {
        Session session = sessionFactory.getCurrentSession();
        UserCourse userCourse = (UserCourse) session.createCriteria(UserCourse.class)
                .add(Restrictions.eq("course", getCourse(courseId)))
                .add(Restrictions.eq("user", user)).uniqueResult();
        return userCourse;
    }

    public void deleteCourse(Course course) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(course);
    }

}

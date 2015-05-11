package com.trainingcenter.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table
public class UserCourse {

    @Id
    @GeneratedValue
    @Column
    private int userCourseId;

    @Column
    private String userCourseState;

    @Column
    private int userCourseMark;

    public int getUserCourseMark() {
        return userCourseMark;
    }

    public void setUserCourseMark(int userCourseMark) {
        this.userCourseMark = userCourseMark;
    }


    @ManyToOne
    private Course course;

    @ManyToOne
    private User user;

    public int getUserCourseId() {
        return userCourseId;
    }

    public void setUserCourseId(int userCourseId) {
        this.userCourseId = userCourseId;
    }

    public String getUserCourseState() {
        return userCourseState;
    }

    public void setUserCourseState(String userCourseState) {
        this.userCourseState = userCourseState;
    }

    
    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @XmlTransient
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

package com.trainingcenter.model;

import org.eclipse.persistence.oxm.annotations.XmlInverseReference;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Table
@XmlRootElement
public class Course {
	
	

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private int courseId;

    @Column
    private String courseName;

    @Column
    private String courseDescription;

    @Column
    private String courseLinks;

    @Column
    private String courseCreator;

    @Column
    @Enumerated(EnumType.ORDINAL)
    private CourseState courseState;

    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "COURSE_COURSEID")
    private Set<UserCourse> userCourses;

    @ManyToOne
    @JoinColumn(name = "categoryId")
    private Category courseCategory;

    @ManyToOne
    @JoinColumn(name = "ownerId")
    private User courseOwner;

    @Column
    private int courseMinSubs;

    @Column
    private int courseMinAttenders;

    @Transient
    private List<String> subscribers;

    @Transient
    private List<String> attenders;

    @Transient
    private double gradeAvg;

    @Transient
    private List<String> evaluators;

    public enum CourseState {
        DRAFT, PROPOSAL, REJECTED, NEW, OPEN, READY, INPROGRESS, FINISHED
    }

    @Column
    private String dmDecision;

    @Column
    private String kmDecision;

    @Column
    private String dmReason;

    @Column
    private String kmReason;

    public String getDmDecision() {
        return dmDecision;
    }

    public void setDmDecision(String dmDecision) {
        this.dmDecision = dmDecision;
    }

    public String getKmDecision() {
        return kmDecision;
    }

    public void setKmDecision(String kmDecision) {
        this.kmDecision = kmDecision;
    }

    public String getDmReason() {
        return dmReason;
    }

    public void setDmReason(String dmReason) {
        this.dmReason = dmReason;
    }

    public String getKmReason() {
        return kmReason;
    }

    public void setKmReason(String kmReason) {
        this.kmReason = kmReason;
    }

    public int getCourseMinAttenders() {
        return courseMinAttenders;
    }

    public void setCourseMinAttenders(int courseMinAttenders) {
        this.courseMinAttenders = courseMinAttenders;
    }

    public int getCourseMinSubs() {
        return courseMinSubs;
    }

    public void setCourseMinSubs(int courseMinSubs) {
        this.courseMinSubs = courseMinSubs;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseDescription() {
        return courseDescription;
    }

    public void setCourseDescription(String courseDescription) {
        this.courseDescription = courseDescription;
    }

    public String getCourseLinks() {
        return courseLinks;
    }

    public void setCourseLinks(String courseLinks) {
        this.courseLinks = courseLinks;
    }

    public String getCourseCreator() {
        return courseCreator;
    }

    public void setCourseCreator(String courseCreator) {
        this.courseCreator = courseCreator;
    }

    @XmlTransient
    public Set<UserCourse> getUserCourses() {
        return userCourses;
    }

    public void setUserCourses(Set<UserCourse> userCourses) {
        this.userCourses = userCourses;
    }

    public Category getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(Category courseCategory) {
        this.courseCategory = courseCategory;
    }

    public List<String> getSubscribers() {
        Set<UserCourse> userCourses = getUserCourses();
        List<String> subscribers = new ArrayList<String>();
        for (UserCourse userCourse : userCourses) {
            if (userCourse.getUserCourseState().equals("subscriber")) {
                subscribers.add(userCourse.getUser().getUserEmail());
            }
        }
        return subscribers;
    }

    public void setSubscribers(List<String> subscribers) {
        this.subscribers = subscribers;
    }

    public List<String> getAttenders() {
        Set<UserCourse> userCourses = getUserCourses();
        List<String> attenders = new ArrayList<String>();
        for (UserCourse userCourse : userCourses) {
            if (userCourse.getUserCourseState().equals("attender")) {
                attenders.add(userCourse.getUser().getUserEmail());
            }
        }
        return attenders;
    }

    public void setAttenders(List<String> attenders) {
        this.attenders = attenders;
    }

    public double getGradeAvg() {
        Set<UserCourse> userCourses = getUserCourses();
        double gradeAvg = 0;
        for (UserCourse userCourse : userCourses) {
            gradeAvg += userCourse.getUserCourseMark();
        }
        if (getEvaluators().size() != 0) {
            gradeAvg = gradeAvg / getEvaluators().size();
        }
        return gradeAvg;
    }

    public void setGradeAvg(double gradeAvg) {
        this.gradeAvg = gradeAvg;
    }

    public List<String> getEvaluators() {
        Set<UserCourse> userCourses = getUserCourses();
        List<String> evaluators = new ArrayList<String>();
        for (UserCourse userCourse : userCourses) {
            if (userCourse.getUserCourseState().equals("evaluator")) {
                evaluators.add(userCourse.getUser().getUserEmail());
            }
        }
        return evaluators;
    }

    public void setEvaluators(List<String> evaluators) {
        this.evaluators = evaluators;
    }

    public CourseState getCourseState() {
        return courseState;
    }

    public void setCourseState(CourseState courseState) {
        this.courseState = courseState;
    }

    public User getCourseOwner() {
        return courseOwner;
    }

    public void setCourseOwner(User courseOwner) {
        this.courseOwner = courseOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;

        Course course = (Course) o;

        if (courseId != course.courseId) return false;
        if (Double.compare(course.gradeAvg, gradeAvg) != 0) return false;
        if (attenders != null ? !attenders.equals(course.attenders) : course.attenders != null) return false;
        if (courseCreator != null ? !courseCreator.equals(course.courseCreator) : course.courseCreator != null)
            return false;
        if (courseDescription != null ? !courseDescription.equals(course.courseDescription) : course.courseDescription != null)
            return false;
        if (courseLinks != null ? !courseLinks.equals(course.courseLinks) : course.courseLinks != null) return false;
        if (courseName != null ? !courseName.equals(course.courseName) : course.courseName != null) return false;
        if (evaluators != null ? !evaluators.equals(course.evaluators) : course.evaluators != null) return false;
        if (subscribers != null ? !subscribers.equals(course.subscribers) : course.subscribers != null) return false;
        if (userCourses != null ? !userCourses.equals(course.userCourses) : course.userCourses != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = courseId;
        result = 31 * result + (courseName != null ? courseName.hashCode() : 0);
        result = 31 * result + (courseDescription != null ? courseDescription.hashCode() : 0);
        result = 31 * result + (courseLinks != null ? courseLinks.hashCode() : 0);
        result = 31 * result + (courseCreator != null ? courseCreator.hashCode() : 0);
        result = 31 * result + (userCourses != null ? userCourses.hashCode() : 0);
        result = 31 * result + (subscribers != null ? subscribers.hashCode() : 0);
        result = 31 * result + (attenders != null ? attenders.hashCode() : 0);
        temp = Double.doubleToLongBits(gradeAvg);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (evaluators != null ? evaluators.hashCode() : 0);
        return result;
    }
}

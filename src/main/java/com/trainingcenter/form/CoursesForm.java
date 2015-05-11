package com.trainingcenter.form;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CoursesForm {


    @NotEmpty
    private String courseName;
    @NotEmpty
    private String courseDescription;
    private String courseLinks;
    @NotEmpty
    private String courseCategory;
    @NotNull
    @Min(1)
    @Max(10)
    private Integer minSubs;
    @NotNull
    @Min(1)
    @Max(10)
    private Integer minAttenders;

    public Integer getMinSubs() {
        return minSubs;
    }

    public void setMinSubs(Integer minSubs) {
        this.minSubs = minSubs;
    }

    public Integer getMinAttenders() {
        return minAttenders;
    }

    public void setMinAttenders(Integer minAttenders) {
        this.minAttenders = minAttenders;
    }

    public String getCourseCategory() {
        return courseCategory;
    }

    public void setCourseCategory(String courseCategory) {
        this.courseCategory = courseCategory;
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
}

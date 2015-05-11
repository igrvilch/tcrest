<#import "spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en" ng-app="coursesApp">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/resources/css/style.css"/>
    <script
	src="http://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.min.js"></script>
    <script src="${rc.getContextPath()}/resources/angularjs/controller.js"></script>
</head>
<body ng-controller="CoursesController">
<div class="container">
    <header>
        <h1>
            Courses
            <div class="logout">
            <span id="currentUserLogin">
            ${user.userEmail} 
            </span>
                <a href="${rc.getContextPath()}/logout">
                    <i class="icon-off"></i>
                </a>
            </div>
        </h1>
    </header>
    <div class="courses-top-control">
        <a class="btn" href="${rc.getContextPath()}/mycourses">My Courses</a>
    <#if user.userRole == "LECTOR">
        <a id="createCourseButton" class="btn btn-primary" href="${rc.getContextPath()}/courses/create">Create</a>
    </#if>
        <div class="search-box">
            <form class="form-search" action="${rc.getContextPath()}/courses" method="get">
                <select id="categoryField" class="span3" name="courseCategory">
                    <option>All</option>
                <#list categories as category>
                    <option value="${category}">${category}</option>
                </#list>
                </select>
                <button class="btn" type="submit">Apply</button>
            </form>
        </div>
    </div>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th class="span1">Id</th>
            <th>Course</th>
            <th class="span3">Category</th>
            <th class="span1">S/A</th>
            <th class="span1">Grade</th>
            <th class="span2">Action</th>
        </tr>
        </thead>

        <tbody>
        <#if user.userRole == "USER">
            <#include "coursestableuser.ftl">
        </#if>
        <#if user.userRole == "LECTOR">
            <#include "coursestablelector.ftl">
        </#if>
         <#if user.userRole == "DM" || user.userRole == "KM">
            <#include "coursestablemanager.ftl">
        </#if>
        </tbody>
    </table>
</div>
<script src="${rc.getContextPath()}/resources/css/jquery-1.8.1.min.js"></script>
<script src="${rc.getContextPath()}/resources/css/bootstrap-2.2.2.min.js"></script>
</body>
</html>
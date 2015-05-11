<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/resources/css/style.css"/>
</head>
<body>
<div class="container">
    <header>
        <h1>
            Attend
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
<#if !course?? || user.userRole != "USER" ||
(course.courseState != "OPEN" && course.courseState != "READY") ||
course.attenders?seq_contains(user.userEmail)>
    <div class="alert alert-error">
        <div>
            <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <#if user.userRole == "USER" && !course??>
                <li>Unknown course</li>
            </#if>
            <#if user.userRole != "USER" ||
            (course.courseState != "OPEN" && course.courseState != "READY") ||
            course.attenders?seq_contains(user.userEmail)>
                <li>Action not allowed. Say again please.</li>
            </#if>
        </ul>
    </div>
<div class="form-horizontal">
    <div class="form-actions">
        <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
    </div>
</#if>
<#if course?? && user.userRole == "USER" &&
(course.courseState == "OPEN" || course.courseState == "READY") &&
!course.attenders?seq_contains(user.userEmail)>
    <form class="form-horizontal" action="${rc.getContextPath()}/courses/${course.courseId}/attend" method="post">
        <fieldset>
            <div class="control-group">
                <div class="control-label">
                    Course
                </div>
                <div id="titleField" class="controls text">
                ${course.courseName}
                </div>
            </div>
            <div class="control-group">
                <div class="control-label">
                    Lecturer
                </div>
                <div id="ownerField" class="controls text">
                ${course.courseCreator}
                </div>
            </div>
            <div class="form-actions">
                <button id="attendButton" class="btn btn-success" type="submit">Attend</button>
                <a class="btn" href="${rc.getContextPath()}/courses">Cancel</a>
            </div>
        </fieldset>
    </form>
</div>
</#if>
</body>
</html>
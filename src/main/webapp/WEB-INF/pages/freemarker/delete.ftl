<#import "spring.ftl" as spring />
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
            Delete Course
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
<#if !course?? || course.courseOwner.userId != user.userId || (course.courseOwner.userId == user.userId &&
(course.courseState != "DRAFT" && course.courseState != "REJECTED"))>
    <div class="alert alert-error">
        <div>
            <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <#if !course??>
                <li>Unknown course</li>
            </#if>
            <#if course?? && ((course.courseOwner.userId == user.userId &&
            (course.courseState != "DRAFT" && course.courseState != "REJECTED")) || course.courseOwner.userId != user.userId)>
                <li>Action not allowed. Say again please.</li>
            </#if>
        </ul>
    </div>
<div class="form-horizontal">
    <div class="form-actions">
        <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
    </div>
</#if>
<#if course?? && course.courseOwner.userId == user.userId && (course.courseState == "DRAFT" || course.courseState == "REJECTED")>
    <form class="form-horizontal" method="post"
          action="${rc.getContextPath()}/courses/${course.courseId}/delete">
        <fieldset>
            <div class="control-group">
                <div class="control-label">Name</div>
                <div id="titleField" class="controls text">${course.courseName}</div>
            </div>
            <div class="control-group">
                <div class="control-label">Category</div>
                <div id="categoryField" class="controls text">${course.courseCategory.categoryName}</div>
            </div>
            <div class="form-actions">
                <button id="deleteButton" class="btn btn-danger" type="submit">Delete</button>
                <a class="btn" href="${rc.getContextPath()}/courses">Cancel</a>
            </div>
        </fieldset>
    </form>
</#if>
</div>
</body>
</html>
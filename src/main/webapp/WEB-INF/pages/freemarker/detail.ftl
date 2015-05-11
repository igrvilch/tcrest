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
            Course Details
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
<#if !course?? || (course.courseOwner.userId != user.userId && (course.courseState == "DRAFT" || course.courseState == "REJECTED")
|| (course.courseState == "PROPOSAL" && user.userRole == "USER"))>
    <div class="alert alert-error">
        <div>
            <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <li>Unknown course</li>
        </ul>
    </div>
<div class="form-horizontal">
    <div class="form-actions">
        <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
    </div>
</#if>
<#if course?? && ((user.userRole == "USER" && (course.courseState != "DRAFT" && course.courseState != "PROPOSAL" && course.courseState != "REJECTED")) ||
course.courseOwner.userId == user.userId ||
((user.userRole == "DM") || (user.userRole == "KM")) && (course.courseState != "DRAFT" && course.courseState != "REJECTED"))>
    <div class="form-horizontal">
        <div class="control-group">
            <div class="control-label">Name</div>
            <div id="titleField" class="controls text">${course.courseName}</div>
        </div>
        <div class="control-group">
            <div class="control-label">Lecturer</div>
            <div id="ownerField" class="controls text">${course.courseCreator}</div>
        </div>
        <div class="control-group">
            <div class="control-label">Category</div>
            <div id="categoryField" class="controls text">${course.courseCategory.categoryName}</div>
        </div>
        <div class="control-group">
            <div class="control-label">Description</div>
            <div id="descriptionField" class="controls text">${course.courseDescription}</div>
        </div>
        <div class="control-group">
            <div class="control-label">Links</div>
            <div id="linksField" class="controls text">${course.courseLinks}</div>
        </div>
        <#if course.subscribers?has_content>
            <div class="control-group">
                <div class="control-label">Subscribers</div>
                <div id="subscribersField" class="controls text">
                    <a href="${rc.getContextPath()}/courses/${course.courseId}/participants">${course.subscribers?size}</a>
                </div>
            </div>
        </#if>
        <#if course.attenders?has_content || course.evaluators?has_content>
            <div class="control-group">
                <div class="control-label">Attendee</div>
                <div id="attendeeField" class="controls text">
                    <a href="${rc.getContextPath()}/courses/${course.courseId}/participants">
                    ${course.attenders?size + course.evaluators?size}
                    </a>
                </div>
            </div>
        </#if>
        <#if course.gradeAvg != 0>
            <div class="control-group">
                <div class="control-label">Grade</div>
                <div id="gradeField" class="controls text">${course.gradeAvg}</div>
            </div>
        </#if>
        <div class="form-actions">
            <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
        </div>
    </div>
</div>
</#if>
</body>
</html>
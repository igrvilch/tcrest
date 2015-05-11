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
            Evaluation
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
<@spring.bind "markForm.*" />
<#if spring.status.error || !course?? || user.userRole != "USER" ||
(user.userRole == "USER" && (course.evaluators?seq_contains(user.userEmail) || !course.attenders?seq_contains(user.userEmail))) ||
course.courseState != "FINISHED" >
    <div class="alert alert-error">
        <div>
            <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <#if user.userRole == "USER" && !course??>
                <li>Unknown course</li>
            </#if>
            <#if user.userRole != "USER" ||
            (user.userRole == "USER" && (course.evaluators?seq_contains(user.userEmail) || !course.attenders?seq_contains(user.userEmail))) ||
            course.courseState != "FINISHED">
                <li>Action not allowed. Say again please.</li>
            </#if>
            <@spring.bind "markForm.*" />
            <#if spring.status.errorCode == "Min" || spring.status.errorCode == "Max">
                <li>Grade must be between 1 and 5.</li>
            </#if>
            <@spring.bind "markForm.*" />
            <#if spring.status.errorCode == "typeMismatch">
                <li>Grade must be integer.</li>
            </#if>
            <@spring.bind "markForm.*" />
            <#if spring.status.errorCode == "NotNull">
                <li>Must be not blank.</li>
            </#if>
        </ul>
    </div>
<div class="form-horizontal">
    <div class="form-actions">
        <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
    </div>
</#if>
<#if course?? && user.userRole == "USER" && !course.evaluators?seq_contains(user.userEmail) && course.courseState == "FINISHED" &&
course.attenders?seq_contains(user.userEmail)>
    <form name="markForm" class="form-horizontal" action="${rc.getContextPath()}/courses/${course.courseId}/evaluate"
          method="post">
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
            <div class="control-group">
                <label id="gradeField" class="control-label">Grade</label>

                <div class="controls">
                    <@spring.formInput "markForm.mark" />
                </div>
            </div>
            <div class="form-actions">
                <button id="evaluateButton" class="btn btn-success" type="submit">Evaluate</button>
                <a class="btn" href="${rc.getContextPath()}/courses">Cancel</a>
            </div>
        </fieldset>
    </form>
</div>
</#if>
</body>
</html>
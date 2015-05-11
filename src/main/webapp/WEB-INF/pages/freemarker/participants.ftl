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
            Course Participants
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
<#if !course?? || user.userRole != "USER">
    <div class="alert alert-error">
        <div>
            <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <#if user.userRole == "USER" && !course??>
                <li>Unknown course</li>
            </#if>
            <#if user.userRole != "USER">
                <li>Action not allowed. Say again please.</li>
            </#if>
        </ul>
    </div>
<div class="form-horizontal">
    <div class="form-actions">
        <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
    </div>
</#if>
<#if course?? && user.userRole == "USER">
    <div class="form-horizontal">
        <div class="control-group">
            <div class="control-label">Name</div>
            <div id="titleField" class="controls text">${course.courseName}</div>
        </div>
        <div class="control-group">
            <div class="control-label">Lecturer</div>
            <div id="ownerField" class="controls text">${course.courseCreator}</div>
        </div>
        <#if course.subscribers?has_content>
            <div class="control-group">
                <div class="control-label">Subscribers</div>
                <div id="subscribersList" class="controls text">
                    <ul class="unstyled">
                        <#list course.subscribers as subscriber>
                            <li>${subscriber}</li>
                        </#list>
                    </ul>
                </div>
            </div>
        </#if>
        <#if course.attenders?has_content || course.evaluators?has_content>
            <div class="control-group">
                <div class="control-label">Attendee</div>
                <div id="attendeeList" class="controls text">
                    <ul class="unstyled">
                        <#list course.attenders as attender>
                            <li>${attender}</li>
                        </#list>
                        <#list course.evaluators as evaluator>
                            <li>${evaluator}</li>
                        </#list>
                    </ul>
                </div>
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
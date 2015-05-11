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
<#if !course?? || (user.userRole != "DM" && user.userRole != "KM") || (course.courseState != "PROPOSAL" && course.courseState != "REJECTED")>
    <div class="alert alert-error">
        <div>
            <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <#if !course??>
                <li>Unknown course</li>
            </#if>
            <#if course?? && ((user.userRole != "DM" && user.userRole != "KM") || (course.courseState != "PROPOSAL" && course.courseState != "REJECTED"))>
                <li>Action not allowed. Say again please.</li>
            </#if>
        </ul>
    </div>
<div class="form-horizontal">
    <div class="form-actions">
        <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
    </div>
</#if>
<#if course?? && (user.userRole == "DM" || user.userRole == "KM") && (course.courseState == "PROPOSAL" || course.courseState == "REJECTED") >
    <form name="approveForm" class="form-horizontal" action="${rc.getContextPath()}/courses/${course.courseId}/approve"
          method="post">
        <div class="control-group">
            <div class="control-label">Name</div>
            <@spring.bind "approveForm.dmDecision" />
        ${spring.status.displayValue}
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

        <div class="control-group">
            <div class="control-label">Department Manager</div>
            <div id="dmField" class="controls text">${user.userLogin}</div>
            <div id="kmField" class="controls text"></div>
        </div>
        <div class="control-group">
            <label class="control-label">Decision</label>

            <div class="controls">
                <#if course.dmDecision == "Select one" && user.userRole == "DM">
                    <select class="span5" name="dmDecision">
                        <option value="Select one">Select one</option>
                        <option value="Approve">Approve</option>
                        <option value="Reject">Reject</option>
                    </select>
                </#if>
                <#if course.dmDecision != "Select one" || user.userRole == "KM">
                    <select class="span5" name="dmDecision" disabled="disabled">
                        <option value="${course.dmDecision}">${course.dmDecision}</option>
                    </select>
                </#if>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">Reason</label>

            <div class="controls">
                <#if course.dmDecision == "Select one" && user.userRole == "DM">
                    <textarea name="dmReason" class="span5" rows="3"></textarea>
                </#if>
                <#if course.dmDecision != "Select one">
                    <textarea name="dmReason" class="span5" rows="3"
                              disabled="disabled">${course.dmReason}</textarea>
                </#if>
                <#if user.userRole == "KM" && !course.dmReason??>
                    <textarea name="dmReason" class="span5" rows="3"
                              disabled="disabled"></textarea>
                </#if>
            </div>
        </div>


        <div class="control-group">
            <label class="control-label">Decision</label>

            <div class="controls">
                <#if course.kmDecision == "Select one" && user.userRole == "KM">
                    <select class="span5" name="kmDecision">
                        <option value="Select one">Select one</option>
                        <option value="Approve">Approve</option>
                        <option value="Reject">Reject</option>
                    </select>
                </#if>
                <#if course.kmDecision != "Select one" || user.userRole == "DM">
                    <select class="span5" name="kmDecision" disabled="disabled">
                        <option value="${course.kmDecision}">${course.kmDecision}</option>
                    </select>
                </#if>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label">Reason</label>

            <div class="controls">
                <#if course.kmDecision == "Select one" && user.userRole == "KM">
                    <textarea name="kmReason" class="span5" rows="3"></textarea>
                </#if>
                <#if course.kmDecision != "Select one">
                    <textarea name="kmReason" class="span5" rows="3" disabled="disabled">${course.kmReason}</textarea>
                </#if>
                <#if user.userRole == "DM" && !course.kmReason??>
                    <textarea name="kmReason" class="span5" rows="3" disabled="disabled"></textarea>
                </#if>
            </div>
        </div>

        <div class="form-actions actions-without-form">
            <button class="btn btn-primary">Save</button>
            <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
        </div>
    </form>
</#if>
</div>
</body>
</html>
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
            Update Course
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
<@spring.bind "coursesForm.*" />
<#if spring.status.error || user.userRole == "USER" || !course?? || course.courseState == "PROPOSAL">
    <div class="alert alert-error">
        <div>
            <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <#if !course??>
                <li>Unknown course.</li>
            </#if>
            <#if user.userRole == "USER" || course.courseState == "PROPOSAL">
                <li>Action not allowed. Say again please.</li>
            </#if>
            <@spring.bind "coursesForm.courseName" />
            <#if spring.status.error>
                <li>Name field is required.</li>
            </#if>
            <@spring.bind "coursesForm.courseDescription" />
            <#if spring.status.error>
                <li>Description field is required.</li>
            </#if>
            <@spring.bind "coursesForm.courseCategory" />
            <#if spring.status.error>
                <li>Category select is required.</li>
            </#if>
            <@spring.bind "coursesForm.minSubs" />
            <#if spring.status.errorCode == "typeMismatch">
                <li>Quantity must be integer.</li>
            </#if>
            <@spring.bind "coursesForm.minSubs" />
            <#if spring.status.errorCode == "Min" || spring.status.errorCode == "Max">
                <li>Quantity must be between 1 and 10.</li>
            </#if>
            <@spring.bind "coursesForm.minSubs" />
            <#if spring.status.errorCode == "NotNull">
                <li>Minimum Subs Field must not be blank.</li>
            </#if>
            <@spring.bind "coursesForm.minAttenders" />
            <#if spring.status.errorCode == "typeMismatch">
                <li>Quantity must be integer.</li>
            </#if>
            <@spring.bind "coursesForm.minAttenders" />
            <#if spring.status.errorCode == "Min" || spring.status.errorCode == "Max">
                <li>Quantity must be between 1 and 10.</li>
            </#if>
            <@spring.bind "coursesForm.minAttenders" />
            <#if spring.status.errorCode == "NotNull">
                <li>Minimum Attenders Field must not be blank.</li>
            </#if>
        </ul>
    </div>
    <div class="form-horizontal">
        <div class="form-actions">
            <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
        </div>
    </div>
</#if>
<#if user.userRole != "USER" && course?? && course.courseState != "PROPOSAL">
    <form name="coursesForm" class="form-horizontal" method="post"
          action="${rc.getContextPath()}/courses/${course.courseId}/update">
        <fieldset>
            <div class="control-group">
                <label class="control-label">Name</label>

                <div class="controls">
                    <input name="courseName" id="titleField" class="span5" type="text" value="${course.courseName}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Category</label>

                <div class="controls">
                    <@spring.bind "coursesForm.courseCategory"/>
                    <select id="categoryField" class="span5" name="courseCategory">
                        <#list categories as category>
                            <option value="${category}"<@spring.checkSelected category/>>${category}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Description</label>

                <div class="controls">
                    <textarea name="courseDescription" id="descriptionField" class="span5"
                              rows="3">${course.courseDescription}</textarea>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Links</label>

                <div class="controls">
                    <#if course.courseLinks?has_content>
                        <textarea name="courseLinks" id="linksField" class="span5"
                                  rows="3">${course.courseLinks}</textarea>
                    </#if>
                    <#if !course.courseLinks?has_content>
                        <textarea name="courseLinks" id="linksField" class="span5" rows="3"></textarea>
                    </#if>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Minimal Subscribers</label>

                <div class="controls">
                    <input name="minSubs" id="titleField" class="span5" type="text" value="${course.courseMinSubs}"/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Minimal Attenders</label>

                <div class="controls">
                    <input name="minAttenders" id="titleField" class="span5" type="text"
                           value="${course.courseMinAttenders}"/>
                </div>
            </div>
            <div class="form-actions">
                <button value="update" name="button" id="updateButton" class="btn btn-primary" type="submit">Update
                </button>
                <button value="review" name="button" id="reviewButton" class="btn btn-warning" type="submit">Review
                </button>
                <a class="btn" href="${rc.getContextPath()}/courses">Cancel</a>
            </div>
        </fieldset>
    </form>



</#if>
<#if user.userRole == "USER" || !course??>
    <div class="form-horizontal">
        <div class="form-actions">
            <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
        </div>
    </div>
</#if>
</div>
</body>
</html>
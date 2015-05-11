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
            Create Course
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
<#if spring.status.error || user.userRole != "LECTOR">
    <div class="alert alert-error">
        <div>
            <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <#if user.userRole != "LECTOR">
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
</#if>
<#if user.userRole == "LECTOR">
    <form name="coursesForm" class="form-horizontal" action="${rc.getContextPath()}/courses/create" method="post">
        <fieldset>
            <div class="control-group">
                <label class="control-label">Name</label>

                <div class="controls">
                    <@spring.formInput "coursesForm.courseName" />
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Category</label>

                <div class="controls">
                    <@spring.bind "coursesForm.courseCategory"/>
                    <select id="categoryField" class="span5" name="courseCategory">
                        <option></option>
                        <#list categories as category>
                            <option value="${category}"<@spring.checkSelected category/>>${category}</option>
                        </#list>
                    </select>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Description</label>

                <div class="controls">
                    <@spring.formTextarea "coursesForm.courseDescription"/>
                </div>
                `
            </div>
            <div class="control-group">
                <label class="control-label">Links</label>

                <div class="controls">
                    <textarea name="courseLinks" id="linksField" class="span5" rows="3"></textarea>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Minimal Subscribers</label>

                <div class="controls">
                    <input name="minSubs" id="titleField" class="span5" type="text" value=""/>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Minimal Attenders</label>

                <div class="controls">
                    <input name="minAttenders" id="titleField" class="span5" type="text" value=""/>
                </div>
            </div>
            <div class="form-actions">
                <button id="createButton" class="btn btn-primary" type="submit">Create</button>
                <a class="btn" href="${rc.getContextPath()}/courses">Cancel</a>
            </div>
        </fieldset>
    </form>
</#if>
<#if user.userRole != "LECTOR">
    <div class="form-horizontal">
        <div class="form-actions">
            <a class="btn" href="${rc.getContextPath()}/courses">Back</a>
        </div>
    </div>
</#if>
</div>
</body>
</html>

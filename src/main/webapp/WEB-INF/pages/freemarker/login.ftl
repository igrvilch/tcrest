<#import "spring.ftl" as spring />
<!DOCTYPE html>
<html lang="en" xmlns:form="http://www.w3.org/1999/html">
<head>
    <meta charset="utf-8"/>
    <link rel="stylesheet" type="text/css" href="${rc.getContextPath()}/resources/css/style.css"/>
</head>
<body>
<div class="container">
    <header>
        <h1>Login</h1>
    </header>
<@spring.bind "loginForm.*" />
<#if spring.status.error || isValid>
    <div class="alert alert-error">
        <div>
            <strong>Okay, Houston, we've had a problem here.</strong>
        </div>
        <ul>
            <@spring.bind "loginForm.login" />
            <#if spring.status.error>
                <li>Login must not be blank.</li>
            </#if>
            <@spring.bind "loginForm.password" />
            <#if spring.status.error>
                <li>Password must not be blank.</li>
            </#if>
            <#if isValid>
                <li>Unknown user or invalid password.</li>
            </#if>
        </ul>
    </div>
</#if>
    <form name="loginForm" class="form-horizontal" action="${rc.getContextPath()}/login" method="post">
        <fieldset>
            <div class="control-group">
                <label class="control-label">Login</label>

                <div class="controls">
                    <div class="input-prepend">
                        <span class="add-on">@</span>
                    <@spring.formInput "loginForm.login" />
                    </div>
                </div>
            </div>
            <div class="control-group">
                <label class="control-label">Password</label>

                <div class="controls">
                <@spring.formPasswordInput "loginForm.password" />
                </div>
            </div>
            <div class="form-actions">
                <button id="loginButton" class="btn btn-primary" type="submit">Login</button>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
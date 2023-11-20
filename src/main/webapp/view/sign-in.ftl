<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" type="text/css" href="styles/form_style.css">
</head>
<#include "menu.ftl">
<body>
<div class="wave"></div>
<div class="wave"></div>
<div class="wave"></div>
<form method="post">
    <div>
        <label>
            <input name="email" type="email" placeholder="Email"/>
        </label>
    </div>
    <div>
        <label>
            <input name="password" type="password" placeholder="Password"/>
        </label>
    </div>
    <div>
        <input type="submit" value="Sign in">
    </div>
</form>
<#if errorMessage??>
    <p style="color: red;">${errorMessage}</p>
</#if>

<div>
    <p>New to EmoNote? <a href="signup">Create an account</a></p>
</div>
</body>
</html>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign in</title>
    <link rel="stylesheet" type="text/css" href="/EmoNote/styles/form_style.css">
</head>
<body>
<header>
    <#include "includes/menu.ftl">
</header>
<div class="wave"></div>
<div class="wave"></div>
<div class="wave"></div>
<div class="column" style="box-sizing: revert !important">
    <h1>Sign in</h1>
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
        <p style="color: whitesmoke; text-align: center">New to EmoNote? <a href="signup" style="color: white;">Create an account</a></p>
    </div></div>
</body>
</html>

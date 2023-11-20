<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Sign up</title>
    <link rel="stylesheet" type="text/css" href="styles/form_style.css">
</head>
<#include "menu.ftl">
<body>
<div class="wave"></div>
<div class="wave"></div>
<div class="wave"></div>
<div class="column"><form method="post">
        <div>
            <label>
                <input name="nickname" type="text" placeholder="Nickname" required>
            </label>
        </div>
        <div>
            <label>
                <input name="email" type="email" placeholder="Email" required>
            </label>
        </div>
        <div>
            <label>
                <input name="password" type="password" placeholder="Password" required>
            </label>
        </div>
        <div>
            <input type="submit" value="Sign-up">
        </div>
    </form>
    <#if errorMessage??>
        <p style="color: red;">${errorMessage}</p>
    </#if>

    <div>
        <p style="color: whitesmoke; text-align: center">Already have an account? <a href="signin" style="color: white;">Sign in</a></p>
    </div></div>
</body>
</html>
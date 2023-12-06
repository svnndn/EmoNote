<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Account</title>
    <link rel="stylesheet" href="/EmoNote/styles/form_style.css">
</head>
<header>
    <#include "includes/menu.ftl">
</header>
<body>
<div class="wave"></div>
<div class="wave"></div>
<div class="wave"></div>
<#include "includes/side_menu.ftl">
<div class="column" style="box-sizing: revert !important">
<h1>Delete Account</h1>

<form action="/EmoNote/deleteprofile" method="post">
    <p>Please confirm that you want to delete your account:</p>
    <input type="checkbox" id="confirmDelete" name="confirmDelete" value="true">
    <label for="confirmDelete">Yes, I want to delete my account</label><br><br>

    <label for="oldPassword">Enter your old password:</label><br>
    <input type="password" id="oldPassword" name="oldPassword"><br><br>

    <input type="submit" value="Delete Account">
</form>
    <#if errorMessage??>
        <p style="color: red;">${errorMessage}</p>
    </#if>
</div>
</body>
</html>

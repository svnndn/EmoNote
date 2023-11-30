<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Edit Profile</title>
    <link rel="stylesheet" href="/EmoNote/styles/form_style.css">
</head>
<header>
    <#include "includes/menu.ftl">
</header>
<body>
<#include "includes/side_menu.ftl">
<div class="column" style="box-sizing: revert !important">
    <h1>Edit Profile</h1>
    <form action="/EmoNote/edit_main" method="post">
        <label for="nickname">Nickname:</label>
        <input type="text" id="nickname" name="nickname" value="${user.nickname}"><br><br>

        <label for="email">Email:</label>
        <input type="email" id="email" name="email" value="${user.email}"><br><br>

        <label for="oldPassword">Old Password:</label>
        <input type="password" id="oldPassword" name="oldPassword"><br><br>

        <label for="newPassword">New Password:</label>
        <input type="password" id="newPassword" name="newPassword"><br><br>

        <input type="submit" value="Save Changes">
    </form>
    <#if errorMessage??>
        <p style="color: red;">${errorMessage}</p>
    </#if>
</div>
</body>
</html>

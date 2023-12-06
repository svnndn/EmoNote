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
<div class="wave"></div>
<div class="wave"></div>
<div class="wave"></div>
<div class="column" style="box-sizing: revert !important"><h1>Edit Profile</h1>
    <form action="/EmoNote/edit" method="post">
        <label for="name">Name:</label>
        <input type="text" id="name" name="name" value="${user.name}"><br><br>

        <label for="surname">Surname:</label>
        <input type="text" id="surname" name="surname" value="${user.surname}"><br><br>

        <label for="dateOfBirth">Date of Birth:</label>
        <input type="date" id="dateOfBirth" name="dateOfBirth" value="${user.dateOfBirth?string('yyyy-MM-dd')}"><br><br>

        <label for="gender">Gender:</label>
        <select id="gender" name="gender">
            <option value="Male" <#if user.gender == 'Male'>selected</#if>>Male</option>
            <option value="Female" <#if user.gender == 'Female'>selected</#if>>Female</option>
            <option value="Other" <#if user.gender == 'Other'>selected</#if>>Other</option>
        </select><br><br>

        <label for="description">Description:</label><br>
        <textarea id="description" name="description">${user.description}</textarea><br><br>

        <input type="submit" value="Save Changes">
    </form>
    <#if errorMessage??>
        <p style="color: red;">${errorMessage}</p>
    </#if></div>
</body>
</html>
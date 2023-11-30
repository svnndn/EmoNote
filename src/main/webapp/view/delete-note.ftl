<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Delete Note</title>
    <link rel="stylesheet" href="/EmoNote/styles/form_style.css">
</head>
<header>
    <#include "includes/menu.ftl">
</header>
<body>
<div class="column" style="box-sizing: revert !important">
    <h1>Delete Note</h1>

    <form action="/EmoNote/deletenote" method="post">
        <input type="hidden" id="moodJournalId" name="moodJournalId" value="${moodJournalDto.id}">
        <p>Please confirm that you want to delete this note:</p>
        <input type="checkbox" id="confirmDelete" name="confirmDelete" value="true">
        <label for="confirmDelete">Yes, I want to delete this note</label><br><br>

        <label for="oldPassword">Enter your password:</label><br>
        <input type="password" id="oldPassword" name="oldPassword"><br><br>

        <input type="submit" value="Delete Note">
    </form>
    <#if errorMessage??>
        <p style="color: red;">${errorMessage}</p>
    </#if>
</div>
</body>
</html>

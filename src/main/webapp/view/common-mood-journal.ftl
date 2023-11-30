<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>All Notes</title>
    <link rel="stylesheet" href="/EmoNote/styles/mood_journal_style.css">
</head>
<header>
    <#include "includes/menu.ftl">
</header>
<body>
<h1>All Notes</h1>
<#list dataModel.notes as note>
    <div class="note">
        <div class="mood-image">
            <img src="/EmoNote/images/emotions/${note.moodRating}.png" alt="Mood Image" style="width: 80px">
        </div>
        <div style="margin: 20px"><#list dataModel.users as user>
                <#if user.id == note.userId>
                    <div class="user-info">
                        <a href="/EmoNote/profile/${user.id}" class="nickname" style="font-style: italic; color: blueviolet; font-size: 2em;">Note from ${user.nickname}</a>
                    </div>
                </#if>
            </#list>
            <h2>Title: ${note.title}</h2>
            <p>Notes: ${note.notes}</p>
            <p>Date Time: ${note.dateTime?string["dd-MM-yyyy HH:mm:ss"]}</p></div>
    </div>
</#list>
</body>
</html>
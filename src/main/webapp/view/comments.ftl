<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Comments</title>
    <link rel="stylesheet" href="/EmoNote/styles/mood_journal_style.css">
    <link rel="stylesheet" href="/EmoNote/styles/profile_style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/EmoNote/styles/mood_journal_button.css">
    <style>
        .btn {
            background-color: #6d4caf;
            border: none;
            color: white;
            padding: 10px 20px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 16px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 8px;
        }

        .btn:hover {
            background-color: #3f2d81;
        }

        .comment-form {
            display: flex;
            flex-direction: column;
            align-items: center;
            width: 20%;
            margin: 20px auto;
        }

        textarea {
            resize: none;
            width: 100%;
            min-height: 100px;
            padding: 8px;
            border-radius: 4px;
            margin-bottom: 10px;
            font-size: 14px;
            outline: none;
            border: 4px solid #6d4caf;
            transition: border-color 0.3s;
        }

        .btn {
            width: 100%;
            padding: 10px;
            background-color: #4a3696;
            border: none;
            color: white;
            font-size: 16px;
            border-radius: 4px;
            cursor: pointer;
        }

    </style>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<header>
    <#include "includes/menu.ftl">
</header>
<body>
<#if errorMessage??>
    <p style="color: red;">${errorMessage}</p>
</#if>

<div class="note" style="margin-top:60px;" data-note-id="${dataModel.note.id}">
    <div class="mood-image">
        <img src="/EmoNote/images/emotions/${dataModel.note.moodRating}.png" alt="Mood Image" style="width: 80px">
    </div>
    <div class="note-details" style="margin: 20px">
        <#list dataModel.users as user>
            <#if user.id == dataModel.note.userId>
                <div class="user-info">
                    <a href="/EmoNote/profile/${user.id}" class="nickname" style="font-style: italic; color: blueviolet; font-size: 2em;">Note from ${user.nickname}</a>
                </div>
            </#if>
        </#list>
        <h2>Title: ${dataModel.note.title}</h2>
        <p>Notes: ${dataModel.note.notes}</p>
        <p>Date Time: ${dataModel.note.dateTime?string["dd-MM-yyyy HH:mm:ss"]}</p>
    </div>
</div>

<div class="profile-header"><h1>Add comment here</h1></div>

<form action="comments" method="post" class="comment-form">
    <input type="hidden" name="moodJournalId" value="${dataModel.note.id}">
    <textarea name="comment" rows="4" cols="50" placeholder="Add a comment..." style="width:1800px;"></textarea>
    <input type="submit" value="Comment" class="btn">
</form>

<div class="profile-header"><h1>Read all comments</h1></div>

<#list dataModel.comments as comment>
    <#if comment.moodJournalId == dataModel.note.id>
        <div class="note" style="background-color: rgba(255, 255, 255, 0.9); margin: 50px;" data-comment-id="${comment.id}">
            <div class="note-details" style="margin: 20px">
                <#list dataModel.users as user>
                    <#if user.id == comment.userId>
                        <div class="user-info">
                            <a href="/EmoNote/profile/${user.id}" class="nickname" style="font-style: italic; color: blueviolet; font-size: 2em;">Comment from ${user.nickname}</a>
                        </div>
                    </#if>
                </#list>
                <p>${comment.notes}</p>
                <p>Date Time: ${comment.dateTime?string["dd-MM-yyyy HH:mm:ss"]}</p>
            </div>
        </div>
    </#if>
</#list>

</body>
</html>
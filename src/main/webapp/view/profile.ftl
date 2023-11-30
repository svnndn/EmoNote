<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
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
        }</style>

    <script src="/EmoNote/view/js/note-script.js"></script>
</head>
<header>
    <#include "includes/menu.ftl">
</header>
<body>
<#if errorMessage??>
    <p style="color: red;">${errorMessage}</p>
</#if>
<div class="profile-card">
    <div class="profile-header">
        <h1>User Profile</h1>
    </div>
    <div class="profile-details">
        <div class="profile-info">
            <label>Nickname:</label>
            <span>${dataModel.user.nickname}</span>
        </div>
        <div class="profile-info">
            <label>Name:</label>
            <span>${dataModel.user.name}</span>
        </div>
        <div class="profile-info">
            <label>Surname:</label>
            <span>${dataModel.user.surname}</span>
        </div>
        <div class="profile-info">
            <#if dataModel.user.dateOfBirth??>
                <label>Date of Birth:</label>
                <span>${dataModel.user.dateOfBirth?string("dd.MM.yyyy")}</span>
            <#else>
                <label>Date of Birth:</label>
                <span>Unknown</span>
            </#if>
        </div>
        <div class="profile-info">
            <label>Gender:</label>
            <span>${dataModel.user.gender}</span>
        </div>
        <div class="profile-info">
            <label>Description:</label>
            <p>${dataModel.user.description}</p>
        </div>

        <#if dataModel.currentUser.id == dataModel.user.id>
            <a href="edit"><button class="btn"><i class="fa fa-cog" aria-hidden="true"> Настройки профиля</i></button></a>
        </#if>
    </div>
</div>

<div class="profile-header"><h1>Read all ${dataModel.user.nickname} Notes</h1></div>

<#list dataModel.notes as note>
    <#if note.userId == dataModel.user.id>
        <div class="note" data-note-id="${note.id}">
            <div class="mood-image">
                <img src="/EmoNote/images/emotions/${note.moodRating}.png" alt="Mood Image" style="width: 80px">
            </div>
            <div class="note-details" style="margin: 20px">
                <h2>Title: ${note.title}</h2>
                <p>Notes: ${note.notes}</p>
                <p>Date Time: ${note.dateTime?string["dd-MM-yyyy HH:mm:ss"]}</p>
            </div>
            <#if dataModel.currentUser.id == dataModel.user.id>
            <div class="list-container">
                <button class="more-button" aria-label="Menu Button">
                    <div class="menu-icon-wrapper">
                        <div class="menu-icon-line half first"></div>
                        <div class="menu-icon-line"></div>
                        <div class="menu-icon-line half last"></div>
                    </div>
                </button>
                <ul class="more-button-list">
                    <li class="more-button-list-item">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-share edit-button">
                            <path d="M4 12v8a2 2 0 002 2h12a2 2 0 002-2v-8M16 6l-4-4-4 4M12 2v13" />
                        </svg>
                        <span>Edit</span>
                    </li>
                    <li class="more-button-list-item">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="feather feather-trash-2 delete-button">
                            <path d="M3 6h18M19 6v14a2 2 0 01-2 2H7a2 2 0 01-2-2V6m3 0V4a2 2 0 012-2h4a2 2 0 012 2v2M10 11v6M14 11v6" />
                        </svg>
                        <span>Delete</span>
                    </li>
                </ul>
            </div>
            </#if>
        </div>
    </#if>
</#list>
</body>
</html>
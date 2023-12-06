<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>User Profile</title>
    <link rel="stylesheet" href="/EmoNote/styles/mood_journal_style.css">
    <link rel="stylesheet" href="/EmoNote/styles/profile_style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="/EmoNote/styles/mood_journal_button.css">
    <link rel="stylesheet" href="/EmoNote/styles/non-button.css">

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
    <script src="/EmoNote/view/js/comment-script.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script>
        $(document).ready(function() {
            $('.btn').click(function(e) {
                e.preventDefault();
                $.ajax({
                    type: 'GET',
                    url: '/EmoNote/edit',
                    success: function(response) {
                        window.location.href = '/EmoNote/edit';
                        console.log(response);
                    },
                    error: function(error) {
                        console.log(error.responseText);
                        $('.errorMessage').text(error.responseText);
                    }
                });
            });
        });
    </script>
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
            <div class="non-list-container" style="padding-right: 10px;">
                <button class="non-more-button" aria-label="Menu Button">
                    <div class="non-menu-icon-wrapper">
                        <svg xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-chat-dots" viewBox="0 0 16 16"> <path d="M5 8a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm4 0a1 1 0 1 1-2 0 1 1 0 0 1 2 0zm3 1a1 1 0 1 0 0-2 1 1 0 0 0 0 2z"/> <path d="m2.165 15.803.02-.004c1.83-.363 2.948-.842 3.468-1.105A9.06 9.06 0 0 0 8 15c4.418 0 8-3.134 8-7s-3.582-7-8-7-8 3.134-8 7c0 1.76.743 3.37 1.97 4.6a10.437 10.437 0 0 1-.524 2.318l-.003.011a10.722 10.722 0 0 1-.244.637c-.079.186.074.394.273.362a21.673 21.673 0 0 0 .693-.125zm.8-3.108a1 1 0 0 0-.287-.801C1.618 10.83 1 9.468 1 8c0-3.192 3.004-6 7-6s7 2.808 7 6c0 3.193-3.004 6-7 6a8.06 8.06 0 0 1-2.088-.272 1 1 0 0 0-.711.074c-.387.196-1.24.57-2.634.893a10.97 10.97 0 0 0 .398-2z"/> </svg>
                    </div>
                </button>
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
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="/EmoNote/styles/menu.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display&display=swap" rel="stylesheet">
</head>

<#if user?? && user?has_content>
    <div class="menu-wrapper no-center">
        <div class="menu-container">
            <div class="menu-items">
                <a href="/EmoNote/profile" class="menu-item">My Profile</a>
                <a href="/EmoNote/addMoodJournalEntry" class="menu-item">New Note</a>
                <a href="/EmoNote/notes" class="menu-item">All Notes</a>
                <a href="/EmoNote/signout" class="menu-item">Sign Out</a>
            </div>
        </div>
    </div>
<#else>

    <div class="menu-wrapper no-center">
        <div class="menu-container">
            <div class="menu-items">
                <a href="signin" class="menu-item">Sign In</a>
                <a href="signup" class="menu-item">Sign Up</a>
            </div>
        </div>
    </div>
</#if>
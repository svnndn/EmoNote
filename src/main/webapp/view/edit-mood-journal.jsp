<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Edit Mood Journal</title>
  <link rel="stylesheet" href="/EmoNote/styles/form_style.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=DM+Serif+Display&display=swap" rel="stylesheet">
  <style>
    .emoji-container {
      display: flex;
      flex-wrap: wrap;
    }

    .emoji {
      width: 50px;
      height: 50px;
      margin: 5px;
      cursor: pointer;
    }

    .highlight {
      outline: 2px solid mediumpurple;
    }
  </style>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script src="/EmoNote/view/js/emotion-script.js"></script>
</head>
<body>
<div class="wave"></div>
<div class="wave"></div>
<div class="wave"></div>
<div class="column">
  <h1>Edit Mood Journal</h1>
  <form action="editnote" method="post">

    <input type="hidden" id="moodJournalId" name="moodJournalId" value="${moodJournalDto.id}">

    <label for="title">Title:</label>
    <input type="text" id="title" name="title" value="${moodJournalDto.title}"><br><br>

    <label for="notes">Notes:</label><br>
    <textarea id="notes" name="notes">${moodJournalDto.notes}</textarea><br><br>

    <div class="emoji-container">
      <img class="emoji" src="/EmoNote/images/emotions/1.png" alt="1">
      <img class="emoji" src="/EmoNote/images/emotions/2.png" alt="2">
      <img class="emoji" src="/EmoNote/images/emotions/3.png" alt="3">
      <img class="emoji" src="/EmoNote/images/emotions/4.png" alt="4">
      <img class="emoji" src="/EmoNote/images/emotions/5.png" alt="5">
      <img class="emoji" src="/EmoNote/images/emotions/6.png" alt="6">
      <img class="emoji" src="/EmoNote/images/emotions/7.png" alt="7">
      <img class="emoji" src="/EmoNote/images/emotions/8.png" alt="8">
      <img class="emoji" src="/EmoNote/images/emotions/9.png" alt="9">
      <img class="emoji" src="/EmoNote/images/emotions/10.png" alt="10">
      <img class="emoji" src="/EmoNote/images/emotions/11.png" alt="11">
      <img class="emoji" src="/EmoNote/images/emotions/12.png" alt="12">
      <img class="emoji" src="/EmoNote/images/emotions/13.png" alt="13">
      <img class="emoji" src="/EmoNote/images/emotions/14.png" alt="14">
      <img class="emoji" src="/EmoNote/images/emotions/15.png" alt="15">
      <img class="emoji" src="/EmoNote/images/emotions/16.png" alt="16">
      <img class="emoji" src="/EmoNote/images/emotions/17.png" alt="17">
      <img class="emoji" src="/EmoNote/images/emotions/18.png" alt="18">
      <img class="emoji" src="/EmoNote/images/emotions/19.png" alt="19">
      <img class="emoji" src="/EmoNote/images/emotions/20.png" alt="20">
      <img class="emoji" src="/EmoNote/images/emotions/21.png" alt="21">
      <img class="emoji" src="/EmoNote/images/emotions/22.png" alt="22">
      <img class="emoji" src="/EmoNote/images/emotions/23.png" alt="23">
      <img class="emoji" src="/EmoNote/images/emotions/24.png" alt="24">
      <img class="emoji" src="/EmoNote/images/emotions/25.png" alt="25">
    </div>

    <input type="hidden" id="moodRatingInput" name="moodRating" value="${moodJournalDto.moodRating}">
    <input type="submit" style="display: none;">

    <input type="submit" value="Save Changes">
  </form>
</div>
</body>
</html>
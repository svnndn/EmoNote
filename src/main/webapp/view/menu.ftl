<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="styles/menu.css">
</head>

<#if user?? && user?has_content>
    <!-- Показать это содержимое, если пользователь вошел в систему -->
    <!-- Код для после входа -->
<#else>
    <div class="menu-wrapper">
        <div class="menu-container">
            <div class="menu-items">
                <a href="signin" class="menu-item">Sign In</a>
                <a href="signup" class="menu-item">Sign Up</a>
            </div>
        </div>
    </div>
</#if>
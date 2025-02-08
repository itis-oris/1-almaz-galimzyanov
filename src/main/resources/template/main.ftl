<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Главная страница</title>
</head>
<body>

<div class="navigation">
    <ul>
        <#if !user??>
            <li><a href="/semWork/login" class="button">Вход</a></li>
            <li><a href="/semWork/register" class="button">Регистрация</a></li>
        </#if>
        <#if user??>
            <li><a href="/semWork/profile" class="button">Профиль</a></li>
        </#if>
        <li><a href="/semWork/cars" class="button">Список машин</a></li>
    </ul>
    <img src="/semWork/static/images/shop.jpg" class="full-width-img">
</div>
</body>
</html>

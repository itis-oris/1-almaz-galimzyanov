<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Профиль</title>

</head>
<body>
<div class="container">
    <h1>Добро пожаловать, ${user.name}!</h1>

    <div class="profile-info">
        <p><strong>Роль:</strong> ${user.role}</p>
        <#if user.role == "customer">
            <p><strong>Счет:</strong> ${user.bill}</p>
        </#if>
    </div>


    <#if user.role == "customer">
        <a href="/semWork/mycars?user=${user.id}" class="button">Мои автомобили</a>
    <#else>
        <a href="/semWork/mycars?user=${user.id}" class="button">Управление автомобилями</a>
    </#if>
    <a href="/semWork/editProfile" class="button">Изменить профиль</a>
    <a href="/semWork/logout" class="button">Выйти</a>
    <#if user.role == "customer">
        <a href="/semWork/pay" class="button">Оплатить счет</a>
    </#if>
    <a href="/semWork/" class="button">На главную</a>
</div>
</body>
</html>

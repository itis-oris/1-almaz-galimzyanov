<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Машина</title>

</head>
<body>
<div class="car_item">
    <h1>Машина</h1>
    <ul>
        <li>Номер машины: ${car.id}</li>
        <li>Название: ${car.name}</li>
        <li>Цена: ${car.price}</li>
    </ul>

    <#if car.isForSale()?c = "true" && forRent = "false">
        <a href="/semWork/return?car=${car.id}&name=${car.name}" class="button">Возврат арендованной машины</a>
    <#else>
        <#if forRent = "true" && user.role = "customer">
            <a href="/semWork/buy?car=${car.id}&name=${car.name}" class="button">Купить</a>
            <a href="/semWork/rent?car=${car.id}&name=${car.name}" class="button">Арендовать</a>
        </#if>
    </#if>

    <a href="javascript:history.back()" class="button">К списку машин</a>
</div>
</body>
</html>

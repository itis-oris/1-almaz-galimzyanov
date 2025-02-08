<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Автомобили</title>

</head>
<body>
<div class="container">
    <h1 class="cars">Список машин</h1>
    <table class="cars">
        <#list cars as car>
            <tr class="cars">
                <td class="cars"><a href="/semWork/car_item?car=${car.id}" class="cars">${car.name}</a></td>
                <#if user.role = "seller">
                    <td class="cars"><a href="/semWork/change?car=${car.id}" class="cars">Изменить</a></td>
                    <td class="cars"><a href="/semWork/delete?car=${car.id}" class="cars">Удалить</a></td>
                </#if>
            </tr>
        </#list>
        <#if user.role = "seller">
            <a href="/semWork/addCar" class="button" class="cars">Добавить машину</a>
        </#if>
    </table>
</div>

<div class="footer">
    <div  class="cars">
        <#list pages as n>
            <span>
                <a href="/semWork/mycars?page=${n}" class="cars">${n}&nbsp;</a>
            </span>
        </#list>
        <h4><a href="/semWork/profile" class="cars">В профиль</a></h4>
    </div>
</div>

</body>
</html>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Машины</title>

</head>
<body>
<div class="container">
    <h1 class="cars">Список машин</h1>
    <table class="cars">
        <#list cars as car>
            <tr class="cars">
                <td class="cars"><a href="/semWork/car_item?car=${car.id}" class="cars">${car.name}</a></td>
            </tr>
        </#list>
    </table>
</div>

<div class="footer" >
    <div class="cars">
        <#list pages as n>
            <span>
                <a href="/semWork/cars?page=${n}" class="cars">${n}&nbsp;</a>
            </span>
        </#list>
        <h4><a href="/semWork/" class="cars">На главную</a></h4>
    </div>
</div>

</body>
</html>

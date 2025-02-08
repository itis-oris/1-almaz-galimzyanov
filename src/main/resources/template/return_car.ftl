<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Возврат автомобиля</title>

</head>
<body>
<form action="/semWork/return" method="post">

    <div class="form-section">
        <h1>Форма возврата автомобиля</h1>
        <input type="hidden" name="carId" value="${id}">

        <label>Возможные повреждения:</label>
        <div>
            <input type="checkbox" id="scratch" name="damage" value="scratch">
            <label for="scratch">Потертости</label>
        </div>
        <div>
            <input type="checkbox" id="dent" name="damage" value="dent">
            <label for="dent">Вмятины</label>
        </div>
        <div>
            <input type="checkbox" id="scuff" name="damage" value="scuff">
            <label for="scuff">Царапины</label>
        </div>
        <button type="submit">Отправить</button>
        <a href="javascript:history.back()" class="button">Назад</a>
    </div>

</form>
</body>
</html>

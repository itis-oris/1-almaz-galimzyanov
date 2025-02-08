<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Изменить машину</title>

</head>
<body>
<div class="container">
    <h1>Изменить машину</h1>

    <form method="post" action="/semWork/change">
        <input type="hidden" name="id" value="${id}">
        <div class="form-group">
            <label for="name">Название ${id}</label>
            <input type="text" id="name" name="name" placeholder="Enter car name" required>
        </div>
        <div class="form-group">
            <label for="price">Цена</label>
            <input type="number" id="price" name="price" placeholder="Enter price" required>
        </div>
        <button type="submit" class="button">Сохранить изменения</button>
    </form>

    <a href="/semWork/profile" class="button">Назад в профиль</a>
</div>
</body>
</html>

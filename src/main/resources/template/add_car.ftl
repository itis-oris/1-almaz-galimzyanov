<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Добавить машину</title>
</head>
<body>
<div class="container">
    <h1>Добавить машину</h1>
    <form action="/semWork/addCar" method="POST">
        <label for="name">Название</label>
        <input type="text" id="name" name="name" placeholder="Enter car name" required>

        <label for="price">Цена</label>
        <input type="number" id="price" name="price" placeholder="Enter price" required>

        <button type="submit">Добавить машину</button>
    </form>
</div>
</body>
</html>

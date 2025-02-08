<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Купить машину</title>

</head>
<body>
<div class="container">
    <h1>Купить машину: ${name}</h1>

    <form action="/semWork/buy" method="post">
        <input type="hidden" name="carName" value="${name}">
        <input type="hidden" name="carId" value="${id}">

        <label for="buyerEmail">Email:</label>
        <input type="email" id="buyerEmail" name="buyerEmail" required>

        <label for="paymentMethod">Способ оплаты:</label>
        <select id="paymentMethod" name="paymentMethod" required>
            <option value="card">Карта</option>
            <option value="cash">Наличные</option>
        </select>

        <label for="terms">
            <input type="checkbox" id="terms" name="terms" required>
            Подтвердить
        </label>

        <button type="submit" class="button">Оформить</button>
    </form>
    <a href="javascript:history.back()" class="button">К машине</a>
</div>
</body>
</html>

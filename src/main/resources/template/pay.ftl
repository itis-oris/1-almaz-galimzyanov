<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Страница оплаты</title>
</head>
<body>
<div class="container">
    <h1 class="pay">Ваш текущий счет: <strong>${user.bill}</strong></h1>

    <form action="/semWork/pay" method="post" class="pay">
        <h2><label for="paymentAmount" class="pay">Введите сумму:</label></h2>
        <input type="number" id="paymentAmount" name="paymentAmount" min="1" max="${user.bill}" required
               class="pay"><br><br>

        <button type="submit" class="pay">Оплатить</button>
    </form>
</div>
</body>
</html>

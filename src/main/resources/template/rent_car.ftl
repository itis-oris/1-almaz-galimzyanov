<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Аренда машины</title>

</head>
<body>
<div class="container">
    <h1>Аренда машины: ${name}</h1>

    <form action="/semWork/rent" method="post">
        <input type="hidden" name="carName" value="${name}">
        <input type="hidden" name="carId" value="${id}">

        <label for="renterEmail">Email:</label>
        <input type="email" id="renterEmail" name="renterEmail" required>

        <label for="rentalStartDate">Дата начала:</label>
        <input type="date" id="rentalStartDate" name="rentalStartDate" required>

        <label for="rentalEndDate">Дата окончания:</label>
        <input type="date" id="rentalEndDate" name="rentalEndDate" required>


        <label for="paymentMethod">Способ оплаты:</label>
        <select id="paymentMethod" name="paymentMethod" required>
            <option value="card">Карта</option>
            <option value="cash">Наличные</option>
        </select>

        <label for="terms">
            <input type="checkbox" id="terms" name="terms" required>
            Подтвердить
        </label>

        <button type="submit" class="button">Арендовать</button>
    </form>
    <a href="javascript:history.back()" class="button">К машине</a>
</div>
</body>
</html>

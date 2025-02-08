<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Регистрация</title>
</head>
<body>
<div class="registration-container">
    <h2>Регистрация</h2>
    <form action="/semWork/register" method="POST">
        <div class="form-group">
            <label for="name">Имя</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password" required>
            <button type="button" id="togglePassword" style="margin-left: 10px;">Показать</button>
        </div>
        <div class="form-group">
            <label for="role">Роль</label>
            <select id="role" name="role" required>
                <option value="customer">Покупатель</option>
                <option value="seller">Продавец</option>
            </select>
        </div>
        <div class="form-group">
            <button type="submit">Зарегистрироваться</button>
        </div>
    </form>
</div>
<script>
    document.getElementById('togglePassword').addEventListener('click', function () {
        const passwordField = document.getElementById('password');
        const passwordFieldType = passwordField.getAttribute('type');

        if (passwordFieldType === 'password') {
            passwordField.setAttribute('type', 'text');
            this.textContent = 'Скрыть';
        } else {
            passwordField.setAttribute('type', 'password');
            this.textContent = 'Показать';
        }
    });
</script>
</body>
</html>

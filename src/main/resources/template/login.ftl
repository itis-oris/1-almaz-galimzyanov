<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Страница входа</title>

</head>
<body>
<div class="login-container">
    <h2>Вход</h2>
    <form action="/semWork/usercheck" method="POST">

        <div class="form-group">
            <label for="name">Логин</label>
            <input type="text" id="name" name="name" required>
        </div>

        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password" required>
            <button type="button" id="togglePassword">Показать</button>
        </div>

        <div class="form-group">
            <button type="submit">Войти</button>
        </div>

    </form>

    <div class="form-footer">
        <p>Нет аккаунта? <a href="/semWork/register">Зарегистрироваться</a></p>
    </div>

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

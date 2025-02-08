<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="/semWork/static/css/style.css">
    <title>Изменить профиль</title>

</head>
<body>
<div class="container">
    <h1>Изменить профиль</h1>

    <form method="post" action="/semWork/editProfile">
        <input type="hidden" name="id" value="${user.id}">
        <div class="form-group">
            <label for="username">Логин</label>
            <input type="text" id="username" name="username" value="${user.name}" required>
        </div>
        <div class="form-group">
            <label for="password">Пароль</label>
            <input type="password" id="password" name="password" required>
            <button type="button" id="togglePassword" style="margin-left: 10px;">Показать</button>
        </div>
        <button type="submit" class="button">Сохранить изменения</button>
    </form>

    <a href="/semWork/profile" class="button">Назад в профиль</a>
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

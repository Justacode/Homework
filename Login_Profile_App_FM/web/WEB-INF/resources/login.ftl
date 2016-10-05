<html>
<head>
    <title>Login</title>
</head>
<body align="center">
<form method="post">
    <label>Username:</label>
    <input type="text" name="user"><br><br>
    <label>Password:</label>
    <input type="password" name="pass"><br><br>
    <label>Remember me</label>
    <input type="checkbox" value="Remember me" name="remember">
    <input type="submit" value="Sign In"><br><br>
</form>
<b style="color: #FF0000";>${error}</b>
</body>
</html>
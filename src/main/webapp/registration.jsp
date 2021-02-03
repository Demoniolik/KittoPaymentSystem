<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/register" method="post">
    <h3>First name</h3><input type="text" name="first_name" required>
    <h3>Second name</h3><input type="text" name="last_name" required>
    <h3>Login</h3><input type="text" name="login">
    <h3>Password</h3><input type="password" name="password">
    <h3>Confirm password</h3><input type="password" name="confirm_password">
    <input type="submit" value="Sign up">
</form>
</body>
</html>

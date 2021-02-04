<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="icon" href="../favicon.svg"><link href="resources/css/app.css" rel="stylesheet"></head>

<body>
<div class="form">
    <form action="${pageContext.request.contextPath}/register" method="post">
        <div class="title">Register</div>

        <input type="text" name="first_name" required placeholder="First name" minlength="2">
        <input type="text" name="last_name" required placeholder="Second name" minlength="2">
        <input type="email" name="login" placeholder="Login">
        <input type="password" name="password" placeholder="Password" minlength="8">
        <input type="password" name="confirm_password" placeholder="Confirm password" minlength="8">

        <button type="submit">Sign up</button>
    </form>

    <a href="${pageContext.request.contextPath}/login">Sign in</a>
</div>
</body>

</html>

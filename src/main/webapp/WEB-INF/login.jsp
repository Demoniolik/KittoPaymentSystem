<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="icon" href="../favicon.svg"><link href="resources/css/app.css" rel="stylesheet"></head>

<body>
<div class="form">
    <form action="${pageContext.request.contextPath}/login" method="POST">
        <div class="title">Login</div>

        <input type="email" name="login" required placeholder="E-mail">
        <input type="password" name="password" required placeholder="Password" minlength="4">

        <button type="submit">Sign in</button>
    </form>

    <a href="${pageContext.request.contextPath}/register">Register</a>
</div>
</body>

</html>

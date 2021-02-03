<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Logged user</title>
</head>
<body>
    <h1 align="center">Hello ${sessionScope.get("firstName")}, ${sessionScope.get("lastName")}</h1>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Logged user</title>
</head>
<body>
    <h1 align="center">Hello ${sessionScope.get("firstName")}, ${sessionScope.get("lastName")}</h1>
    <table>
        <c:forEach items="${user_credit_cards}" var="item">
            <tr>
                <td><c:out value="${item.getName()}" /></td>
                <td><c:out value="${item.getNumber()}" /></td>
                <td><c:out value="${item.getMoneyOnCard()}" /></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>

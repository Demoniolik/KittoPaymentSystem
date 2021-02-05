<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Logged user</title>
</head>
<body>
    <h1 align="center">Hello ${sessionScope.get("user").getFirstName()}, ${sessionScope.get("user").getLastName()}</h1>
    <h3>List of your active cards</h3>
    <table>
        <c:forEach items="${user_credit_cards}" var="item">
            <tr>
                <td><c:out value="${item.getName()}" /></td>
                <td><c:out value="${item.getNumber()}" /></td>
                <td><c:out value="${item.getMoneyOnCard()}" /></td>
            </tr>
        </c:forEach>
    </table>

    <h2>Replenishing your credit card</h2>

    <form action="${pageContext.request.contextPath}/replenishCreditCard" method="post">
        <input type="number" name="replenishMoney" placeholder="0" required>
        <select name="chosenCreditCard">
            <c:forEach items="${user_credit_cards}" var="item">
                <option><c:out value="${item.getNumber()}" /></option>
            </c:forEach>
        </select>
        <button type="submit">Replenish</button>
    </form>

</body>
</html>

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

    <h2>Create new card</h2>

    <form action="${pageContext.request.contextPath}/createNewCreditCard">
        <input type="text" name="cardName">
        <button type="submit">Create new card</button>
    </form>

    <h2>Replenishing your credit card</h2>

    <form action="${pageContext.request.contextPath}/replenishCreditCard" method="post">
        <input type="number" min="0" name="replenishMoney" placeholder="0" required>
        <select name="chosenCreditCard">
            <c:forEach items="${user_credit_cards}" var="item">
                <option><c:out value="${item.getNumber()}" /></option>
            </c:forEach>
        </select>
        <button type="submit">Replenish</button>
    </form>

    <h2>Creating new payment</h2>

    <form action="${pageContext.request.contextPath}/createPayment" method="post">
        <input type="number" name="destinationNumber" placeholder="destination" required>
        <input type="number" name="moneyToPay" placeholder="money to pay" required>
        <select name="sourceNumber">
            <c:forEach items="${user_credit_cards}" var="item">
                <option><c:out value="${item.getNumber()}" /></option>
            </c:forEach>
        </select>
        <button type="submit">Create transaction</button>
    </form>

    <h2>All payments</h2>

    table>
    <c:forEach items="${user_payments}" var="item">
        <tr>
            <td><c:out value="${item.getName()}" /></td>
            <td><c:out value="${item.getNumber()}" /></td>
            <td><c:out value="${item.getMoneyOnCard()}" /></td>
        </tr>
    </c:forEach>
    </table>

</body>
</html>

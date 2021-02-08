<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Payments</title>
</head>
<body>
    <h2>Create payment</h2>
    <form action="${pageContext.request.contextPath}/createPayment">
        <input type="number" min="0" name="destination" placeholder="requisite number">
        <select name="category">
            <c:forEach items="${categories}" var="item">
                <option><c:out value="${item}" /></option>
            </c:forEach>
        </select>
        <input type="number" min="0" name="moneyToPay" placeholder="0">
        <select name="sourceNumber">
            <c:forEach items="${userCreditCards}" var="item">
                <option><c:out value="${item.getNumber()}" /></option>
            </c:forEach>
        </select>
        <button type="submit">Pay</button>
    </form>

    <h2>All payments</h2>
    <table>
        <c:forEach items="${userPayments}" var="item">
            <tr>
                <td><c:out value="${item.getMoney()}" /></td>
                <td><c:out value="${item.getDate()}" /></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>

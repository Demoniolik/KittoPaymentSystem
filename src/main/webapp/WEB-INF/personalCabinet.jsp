<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Personal cabinet</title>
</head>
<body>
    <div class="personal_cabinet_container">
        <form action="${pageContext.request.contextPath}/changeUserData" method="post">

            <label for="userName">First name</label>
            <input type="text" name="firstName" placeholder="${user.getFirstName()}" id="userName">
            <br>
            <label for="userLastName">Last name</label>
            <input type="text" name="lastName" placeholder="${user.getLastName()}" id="userLastName">
            <br>
            <label for="login">Login</label>
            <input type="text" name="login" placeholder="${user.getLogin()}" id="login">
            <br>
            <label for="password">Password</label>
            <input type="password" minlength="8" name="password" placeholder="password" id="password">
            <br>
            <label for="confirmPassword">Confirm password</label>
            <input type="text" name="confirmPassword" placeholder="0" id="confirmPassword">
            <br>
            <button type="submit">Confirm changes</button>
        </form>

        <form action="${pageContext.request.contextPath}/createUnblockRequest" method="post">
            <select name="chosenCard">
                <c:forEach items="${userBlockedCreditCards}" var="item">
                    <option>
                        <c:out value="${item.getNumber()}"/>
                    </option>
                </c:forEach>
            </select>
            <br>
            <textarea name="reasonDescription" cols="30" rows="10"></textarea>
            <br>
            <button type="submit">Send unblock request</button>
        </form>
        
    </div>
</body>
</html>

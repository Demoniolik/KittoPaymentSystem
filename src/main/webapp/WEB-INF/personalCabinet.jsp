<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="icon" href="../favicon.svg"><link href="resources/css/app.css" rel="stylesheet"></head>
<head>
    <title>Personal cabinet</title>
</head>
<body>
<div class="container">
    <div class="grid">

        <!-- Sidebar -->
        <div class="sidebar">

            <!-- head -->
            <div class="sidebar__head">
                <div class="head">
                    <a href="${pageContext.request.contextPath}/goToPersonalCabinet">
                        <div class="container-img"><img src="resources/img/content/menu/logo.webp" alt=""></div>
                        <div class="text">
                            <div class="name">${user.getFirstName()} ${user.getLastName()}</div>
                            <div class="specialty">${user.getLogin()}</div>
                        </div>
                    </a>
                </div>
            </div>

            <!-- Menu -->
            <div class="sidebar__menu">
                <div class="title"><img src="resources/img/content/menu/exchange-rates.svg" alt="">Exchange Rates</div>

                <a href='#' class="link">
                    <div class="icon"><img src="resources/img/content/menu/dollar.svg" alt=""></div>
                    <div class="title">Dollar</div>
                </a>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/euro.svg" alt=""></div>
                    <div class="title">Euro</div>
                </a>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/yen.svg" alt=""></div>
                    <div class="title">Yen</div>
                </a>

                <div class="title"><img src="resources/img/content/menu/settings.svg" alt=""> Settings</div>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/languages.svg" alt=""></div>
                    <div class="title">Languages: EN</div>
                </a>

                <a href="${pageContext.request.contextPath}/logout" class="link logout">
                    <div class="icon"><img src="resources/img/content/menu/logout.svg" alt=""></div>
                    <div class="title">Logout</div>
                </a>

            </div>
        </div>
        <div class="personal_cabinet_container">
            <div class="form personal-cabinet">
                <form action="${pageContext.request.contextPath}/changeUserData" method="post">

                    <label for="userName">First name</label>
                    <input type="text" name="firstName" placeholder="${user.getFirstName()}" id="userName">

                    <label for="userLastName">Last name</label>
                    <input type="text" name="lastName" placeholder="${user.getLastName()}" id="userLastName">

                    <label for="login">Login</label>
                    <input type="text" name="login" placeholder="${user.getLogin()}" id="login">

                    <label for="password">Password</label>
                    <input type="password" minlength="8" name="password" placeholder="password" id="password">

                    <label for="confirmPassword">Confirm password</label>
                    <input type="text" name="confirmPassword" placeholder="0" id="confirmPassword">

                    <button type="submit">Confirm changes</button>
                </form>
            </div>

            <div class="form personal-cabinet">
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
        </div>
    </div>
</div>
</body>
</html>


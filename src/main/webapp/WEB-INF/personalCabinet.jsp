<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="icon" href="../favicon.svg"><link href="resources/css/app.css" rel="stylesheet"></head>
<head>

    <%--Localization--%>
    <c:if test="${sessionScope.locale == null}">
        <fmt:setLocale value="en"/>
    </c:if>
    <c:if test="${sessionScope.locale != null}">
        <fmt:setLocale value="${sessionScope.locale}"/>
    </c:if>

    <fmt:setBundle basename="localization" var="bundle"/>
    <%----%>

    <title><fmt:message key="personal_cabinet_title" bundle="${bundle}"/></title>
</head>
<body>
<div class="container">
    <div class="grid grid-cabinet">

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
                <div class="title"><img src="resources/img/content/menu/exchange-rates.svg" alt="">
                    <fmt:message key="exchange_rates" bundle="${bundle}"/>
                </div>

                <a href='#' class="link">
                    <div class="icon"><img src="resources/img/content/menu/dollar.svg" alt=""></div>
                    <div class="title"><fmt:message key="dollar" bundle="${bundle}"/></div>
                </a>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/euro.svg" alt=""></div>
                    <div class="title"><fmt:message key="euro" bundle="${bundle}"/></div>
                </a>

                <a class="link">
                    <div class="icon"><img src="resources/img/content/menu/yen.svg" alt=""></div>
                    <div class="title"><fmt:message key="yen" bundle="${bundle}"/></div>
                </a>

                <div class="title"><img src="resources/img/content/menu/settings.svg" alt="">
                    <fmt:message key="settings" bundle="${bundle}"/>
                </div>

                <c:choose>
                    <c:when test="${locale != null && locale.equals(\"ru\")}">
                        <a href="${pageContext.request.contextPath}/changeLocale?locale=en" class="link">
                            <div class="icon"><img src="resources/img/content/menu/languages.svg" alt=""></div>
                            <div class="title"><fmt:message key="language" bundle="${bundle}"/></div>
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/changeLocale?locale=ru" class="link">
                            <div class="icon"><img src="resources/img/content/menu/languages.svg" alt=""></div>
                            <div class="title"><fmt:message key="language" bundle="${bundle}"/></div>
                        </a>
                    </c:otherwise>
                </c:choose>

                <a href="${pageContext.request.contextPath}/logout" class="link logout">
                    <div class="icon"><img src="resources/img/content/menu/logout.svg" alt=""></div>
                    <div class="title"><fmt:message key="logout" bundle="${bundle}"/></div>
                </a>

            </div>
        </div>

        <div class="personal_cabinet_container">
            <div class="title"><fmt:message key="personal_cabinet_title" bundle="${bundle}"/></div>
            <div class="form-container">
                <div class="personal-cabinet">
                    <div class="form-title"><fmt:message key="personal_data" bundle="${bundle}"/></div>
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

                        <button type="submit"><fmt:message key="personal_cabinet_confirm_changes" bundle="${bundle}"/></button>
                    </form>
                </div>
                <div class="personal-cabinet">
                    <div class="form-title"><fmt:message key="personal_cabinet_unblocking_request" bundle="${bundle}"/></div>
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
                        <button type="submit"><fmt:message key="sending_unblocking_request" bundle="${bundle}"/></button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>


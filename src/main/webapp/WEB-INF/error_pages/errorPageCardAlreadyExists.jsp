<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
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
    <title><fmt:message key="error_page_card_already_exists_title" bundle="${bundle}"/> </title>
</head>
<body>
<a href="${pageContext.request.contextPath}/login"><h2>Back</h2></a>
<h2><fmt:message key="error_page_card_already_exists_header" bundle="${bundle}"/></h2>
<p>
    <fmt:message key="error_page_card_help_message" bundle="${bundle}"/>
</p>
<a href=""><fmt:message key="support_email" bundle="${bundle}"/></a>
</body>
</html>

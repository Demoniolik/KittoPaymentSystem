<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AdminPage</title>
</head>
<body>
<h3>Users</h3>
<h3>Sort by</h3>
<c:choose>
    <c:when test="">
        <a href="">Blocked</a>
    </c:when>
    <c:otherwise>
        <a href="">All</a>
    </c:otherwise>
</c:choose>
<table>
    <c:forEach items="${allUsers}" var="item">
        <tr>
            <td><c:out value="${item.getFirstName()}"/></td>
            <td><c:out value="${item.getLastName()}"/></td>
            <td><c:out value="${item.getLogin()}"/></td>
            <td><c:out value="${item.isBlocked()}"/></td>
            <td>
                <c:choose>
                    <c:when test="${item.isBlocked()}">
                        <a href="${pageContext.request.contextPath}/admin/changeUserStatus?userId=${item.getId()}&option=unblock">
                            Unblock
                        </a>
                    </c:when>
                    <c:otherwise>
                        <a href="${pageContext.request.contextPath}/admin/changeUserStatus?userId=${item.getId()}&option=block">
                            Block
                        </a>
                    </c:otherwise>
                </c:choose>
            </td>
        </tr>
    </c:forEach>
</table>

<br>
   <table>
       <c:forEach items="${unblockingRequests}" var="item">
            <tr>
                <td>${item.getCreditCardId()}</td>
                <td>${item.getDescription()}</td>
                <td>${item.getRequestStatus()}</td>
                <td><a href="${pageContext.request.contextPath}/admin/unblockUserCard?cardId=${item.getCreditCardId()}">Unblock</a></td>
            </tr>
       </c:forEach>
   </table>
</body>
</html>

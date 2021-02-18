<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>AdminPage</title>
</head>
<body>
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
</body>
</html>

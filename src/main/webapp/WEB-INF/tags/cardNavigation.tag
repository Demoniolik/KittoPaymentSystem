<%@ attribute name="path" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="page" type="java.lang.Integer" required="true" %>
<%@ attribute name="pageSize" type="java.lang.Integer" required="true" %>
<%@ attribute name="maxPage" type="java.lang.Integer" required="true" %>

<c:choose>
    <c:when test="${page == null || page == 1}">
        Previous
    </c:when>
    <c:otherwise>
        <a href="${path}?pageSize=${pageSize}&page=${page-1}">Previous</a>
    </c:otherwise>
</c:choose>

<c:choose>
    <c:when test="${page != null && page == maxPage}">
        Next
    </c:when>
    <c:otherwise>
        <a href="${path}?pageSize=${pageSize}&page=${page+1}">Next</a>
    </c:otherwise>
</c:choose>
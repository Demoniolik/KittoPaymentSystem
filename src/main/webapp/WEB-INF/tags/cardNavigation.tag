<%@ attribute name="path" required="true" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ attribute name="page" type="java.lang.Integer" required="true" %>
<%@ attribute name="pageSize" type="java.lang.Integer" required="true" %>
<%@ attribute name="maxPage" type="java.lang.Integer" required="true" %>

<div class="slider-card">
    <c:choose>
        <c:when test="${page == null || page == 1}">
            <div class="slider-arrow disabled"><img src="resources/img/content/icons/left-arrow.svg" alt=""></div>
        </c:when>
        <c:otherwise>
            <a href="${path}?pageSize=${pageSize}&page=${page-1}" class="slider-arrow"><img src="resources/img/content/icons/left-arrow.svg" alt=""></a>
        </c:otherwise>
    </c:choose>

    <c:choose>
        <c:when test="${page != null && page >= maxPage}">
            <div class="slider-arrow disabled"><img src="resources/img/content/icons/right-arrow.svg" alt=""></div>
        </c:when>
        <c:otherwise>
            <a href="${path}?pageSize=${pageSize}&page=${page+1}" class="slider-arrow"><img src="resources/img/content/icons/right-arrow.svg" alt=""></a>
        </c:otherwise>
    </c:choose>
</div>
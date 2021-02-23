<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Database error page</title>
</head>
<body>
<a href="${pageContext.request.contextPath}/login"><h2>Back</h2></a>
<h2>#{errorCause}</h2>
<p>
    <fmt:message key="error_page_card_help_message" bundle="${bundle}"/>
</p>
<a href=""><fmt:message key="support_email" bundle="${bundle}"/></a>
</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: JuanmaAlonso
  Date: 07/05/2018
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <link rel="stylesheet" href="<c:url value="/resources/css/style.css"/>"/>
</head>
<body>
<h2><spring:message code="user.greeting" arguments="${user.username}"/></h2>
<h5><spring:message code="user.id" arguments="${user.id}"/></h5>
</body>
</html>
<%--
  Created by IntelliJ IDEA.
  User: nacho
  Date: 4/25/18
  Time: 3:56 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Register</title>
    <%--<link rel="stylesheet" href="<c:url value="/css/style.css"/>"/>--%>
</head>
<body>
<h2>Register User</h2>
<c:url value="create" var="postPath"></c:url>
<form:form modelAttribute="registerForm" action="${postPath}" method="post">
    <div>
        <form:label path="username">Username: </form:label>
        <form:input type="text" path="username"/>
        <form:errors path="username" cssClass="fo rmError" element="p"/>
    </div>
    <div>
        <form:label path="password">Password: </form:label>
        <form:errors path="password" cssClass="fo rmError" element="p"/>
    </div>
    <div>
        <form:label path="repeatPassword">Repeat password: </form:label>
        <form:input type="password" path="repeatPassword"/>
        <form:errors path="repeatPassword" cssClass="formError" element="p"/>
    </div>
    <div>
        <input type="submit" value="Register!"/></div>
</form:form>
</body>
</html>

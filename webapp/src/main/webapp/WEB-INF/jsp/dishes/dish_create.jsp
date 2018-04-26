<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Create Dish</title>
<body>
<c:url value="/dishes/create" var="postPath"/>
<form:form modelAttribute="dishCreateForm" action="${postPath}" method="post">
    <div>
        <form:label path="name">Name: </form:label>
        <form:input type="text" path="name"/>
        <form:errors path="name" cssStyle="color: red;" element="p"/>
    </div>

    <div>
        <form:label path="price">Price: </form:label>
        <form:input type="number" step="0.01" path="price"/>
        <form:errors path="price" cssStyle="color: red;" element="p"/>
    </div>
    <div>
        <input type="submit" value="Add!"/></div>
</form:form>
</body>
</html>

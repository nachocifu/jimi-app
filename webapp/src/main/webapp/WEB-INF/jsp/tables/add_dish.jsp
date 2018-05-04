<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<body>

<c:url value="/tables/${table.id}/add_dish" var="postPath"/>
<form:form modelAttribute="tableAddDishForm" action="${postPath}" method="post">
    <form:label for="dishid" path="dishid">Please select a dish to add to table: <b>${table.name}</b></form:label><br>
    <form:select id="dishid" name="dishid" path="dishid">
        <c:forEach items="${dishes}" var="dish">
            <option value="${dish.id}">${dish.name}</option>
        </c:forEach>
        <input type="number" step="1" min="1" max="100" name="amount"/>
        <form:errors path="amount" cssClass="formError" element="p"/>
    </form:select>
    <input type="submit" value="Add dish!"/>
</form:form>

<form action="/tables/${table.id}">
    <input type="submit" value="Cancel"/>
</form>

</body>
</html>
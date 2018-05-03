<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>Tables</title>
</head>
<body>
<table border="1">
    <tr>
        <td>Name</td>
        <td>Diners</td>
        <td>Status</td>
        <td>...</td>

    </tr>
    <c:forEach items="${tables}" var="table">
        <tr>
            <td><c:out value="${table.name}"/></td>
            <td><c:out value="${table.diners}"/></td>
            <td><c:out value="${table.status}"/></td>
            <td>
                <a href="/tables/${table.id}">=</a>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="/tables/register">
    <input type="submit" value="Add table!"/>
</form>

</body>
</html>
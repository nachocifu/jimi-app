<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<body>
<table border="1">
    <tr>
        <td>Name</td>
        <td>Price</td>
        <td>Stock</td>
        <td></td>
        <td></td>
    </tr>
    <c:forEach items="${dishes}" var="dish">
        <tr>
            <td><c:out value="${dish.name}"/></td>
            <td><c:out value="${dish.price}"/></td>
            <td><c:out value="${dish.stock}"/></td>
            <td>
                <form action="/dishes/setstock">
                    <input type="submit" value="+" />
                    <input type="hidden" value="${dish.id}" name="dishid">
                    <input type="hidden" value="${dish.stock+1}" name="stock">
                </form>
            </td>
            <td>
                <form action="/dishes/setstock">
                    <input type="submit" value="-" />
                    <input type="hidden" value="${dish.id}" name="dishid">
                    <input type="hidden" value="${dish.stock-1}" name="stock">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>

<form action="/dishes/create">
    <input type="submit" value="Add dish!" />
</form>

</body>
</html>
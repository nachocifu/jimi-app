<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<body>
<h2>${name} --- $${price} x ${stock} | $${price * stock}</h2>

<form action="/dishes">
    <input type="submit" value="View all dishes" />
</form>

</body>
</html>
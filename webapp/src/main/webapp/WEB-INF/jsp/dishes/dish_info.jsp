<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<body>
<h2>${name} --- $${price} x ${stock} | $${price * stock}</h2>

<a href="<c:url value="/dishes"/>"></a>

</body>
</html>
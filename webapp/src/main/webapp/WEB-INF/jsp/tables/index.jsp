<%--
  Created by IntelliJ IDEA.
  User: nacho
  Date: 4/25/18
  Time: 4:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<html>
<head>
    <title>Table ${table.getName()} </title>
</head>
<body>

<h1>TABLE ${table.getName()}</h1>

<c:if test="${table.getStatus() == 'Free'}">
    <h2>Table is <span style="color: green;">FREE</span>. Do you want to occupy it?
        <form action="/tables/${table.getId()}/status" method="post">
            <input value="1" name="status" type="hidden"/>
            <input type="submit" value="OCCUPY"/>
        </form>
    </h2>
</c:if>
<c:if test="${table.getStatus() == 'CleaningRequired'}">
    <h2>Table is <span style="color: yellow;">CLEANING</span>. Is it ready for use?
        <form action="/tables/${table.getId()}/status" method="post">
            <input value="2" name="status" type="hidden"/>
            <input type="submit" value="FREE"/>
        </form>
    </h2>
</c:if>
<c:if test="${table.getStatus() == 'Busy'}">
    <h2>Table is <span style="color: green;">BUSY</span>. Do you want to close it and clean?

        <form action="/tables/${table.getId()}/status" method="post">
            <input value="3" name="status" type="hidden"/>
            <input type="submit" value="CLEAN"/>
        </form>

    </h2>
</c:if>

<h4><a href="/tables">Return to table list?</a></a></h4><br>

</body>
</html>

<%--
  Created by IntelliJ IDEA.
  User: nacho
  Date: 4/25/18
  Time: 4:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<html>
<head>
    <title>Table ${table.getName()} </title>
</head>
<body>
<h1>TABLE ${table.getName()}
    <br>
    Diners >>>>${table.getDiners()}
    <br>
    Status ${table.getStatus()}
</body>
</html>

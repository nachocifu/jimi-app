<%@ page import="edu.itba.paw.jimi.models.TableStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="BusyCode" value="<%=TableStatus.BUSY.ordinal()%>"/>
<c:set var="PayingCode" value="<%=TableStatus.PAYING.ordinal()%>"/>


<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <title>Jimi Rest</title>
    <!-- google font -->
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet"
          type="text/css"/>
    <!-- icons -->
    <link href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>" rel="stylesheet"
          type="text/css"/>

    <!--Material-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"
          integrity="sha256-e22BQKCF7bb/h/4MFJ1a4lTRR2OuAe8Hxa/3tgU5Taw=" crossorigin="anonymous"/>

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/users/create.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="table-container">
    <div class="card">
        <div class="card-content">
            <span class="card-title"><spring:message code="user.register_header"/></span>
            <c:url value="/web/admin/users/create" var="postPath"/>
            <form:form modelAttribute="registerForm" action="${postPath}" method="post">

                <form:label type="text" path="username"
                            cssClass="mdl-textfield__label"><spring:message
                        code="user.username_form_label"/></form:label>
                <form:input type="text" path="username" cssClass="validate"/>
                <form:errors path="username" cssClass="formError" element="p"/>

                <form:label path="password"
                            cssClass="mdl-textfield__label"><spring:message
                        code="user.password_form_label"/></form:label>
                <form:input type="password" path="password" cssClass="validate"/>
                <form:errors path="password" cssClass="formError" element="p"/>

                <form:label path="repeatPassword"
                            cssClass="mdl-textfield__label"><spring:message
                        code="user.repeat_password_form_label"/></form:label>
                <form:input type="password" path="repeatPassword"
                            cssClass="validate"/>
                <form:errors path="repeatPassword" cssClass="formError" element="p"/>

                <button type="submit"
                        class="waves-effect waves-light btn">
                    <spring:message code="dish.add"/>
                </button>
                <a href="<c:url value="/web/admin/dishes/"/>"
                   class="waves-effect waves-light btn">
                    <spring:message code="dish.cancel"/>
                </a>
            </form:form>
        </div>
    </div>
</div>


<!-- start js include path -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<!-- end js include path -->
</body>
</html>


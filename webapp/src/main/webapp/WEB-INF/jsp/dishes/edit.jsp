<%@ page import="edu.itba.paw.jimi.models.TableStatus" %>
<%@ page contentType="text/html;charset=UTF-8" %>
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
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css"
          integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

    <!--Material-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"
          integrity="sha256-e22BQKCF7bb/h/4MFJ1a4lTRR2OuAe8Hxa/3tgU5Taw=" crossorigin="anonymous"/>

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/dishes/create.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="table-container">
    <div class="card">
        <div class="card-content">
            <span class="card-title"><spring:message code="table.edit_dish"/></span>
            <c:url value="/admin/dishes/edit/${dish.id}" var="postPath"/>
            <form:form modelAttribute="dishCreateForm" action="${postPath}" method="post">

                <form:label class="mdl-textfield__label" path="name"><spring:message
                        code="dish.name"/></form:label>
                <form:input disabled="true" class="mdl-textfield__input" type="text" path="name"/>
                <form:errors path="name" element="p" cssClass="formError"/>


                <form:label class="mdl-textfield__label" path="price">
                    <spring:message code="dish.price"/></form:label>
                <form:input class="validate" type="number" path="price"
                            min="0" step="0.01" required="required"/>
                <form:errors path="price" element="p" cssClass="formError"/>

                <form:label class="mdl-textfield__label" path="stock"><spring:message
                        code="dish.stock"/></form:label>
                <form:input class="validate" type="number"
                            path="stock" min="0" step="1"/>
                <form:errors path="stock" element="p" cssClass="formError"/>

                <form:label class="mdl-textfield__label" path="minStock"><spring:message
                        code="dish.minStock"/></form:label>
                <form:input class="validate" type="number"
                            path="minStock" min="0" step="1"/>
                <form:errors path="minStock" element="p" cssClass="formError"/>


                <form:label class="mdl-textfield__label" path="discontinued"><spring:message
                        code="dish.discontinued"/></form:label>
                <form:checkbox class="validate" path="discontinued"
                               checked="${dish.discontinued == 'true' ? 'checked' : '' }"/>
                <button type="submit"
                        class="waves-effect waves-light btn">
                    <spring:message code="dish.edit"/>
                </button>
                <a href="<c:url value="/admin/dishes/"/>"
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


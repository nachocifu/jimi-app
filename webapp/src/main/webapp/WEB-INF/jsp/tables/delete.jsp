<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
    <link href="<c:url value="/resources/css/tables/delete.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="table-container">
    <div class="card">
        <div class="card-content">
            <span class="card-title"><spring:message code="table.delete"/> <b>${table.name}</b></span>
            <!-- Modal Trigger -->
            <button data-target="modal2" class="btn modal-trigger"><spring:message
                    code="table.delete"/></button>
            <a href="<c:url value="/tables/"/>"
               class="waves-effect waves-light btn">
                <spring:message code="dish.cancel"/>
            </a>
        </div>
    </div>
    <!-- Modal Structure -->
    <div id="modal2" class="modal">
        <div class="modal-content">
            <h4><spring:message code="table.sure_delete"/></h4>
            <form action="<c:url value="/tables/delete/${table.id}"/>" method="post">
                <div class="modal-footer">
                    <a class="modal-close btn blue-gray"><spring:message code="table.back"/></a>
                    <input type="submit"
                           class="btn blue-gray"
                           value="<spring:message code="table.delete_caps_confirm"/>"/>
                </div>
            </form>
        </div>
    </div>
</div>


<!-- start js include path -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script>
    $(document).ready(function () {
        $('.modal').modal();
    });
</script>
<!-- Compiled and minified JavaScript -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>
<%--<script src="https://code.getmdl.io/1.3.0/material.min.js"></script>--%>
<!-- end js include path -->
</body>
</html>


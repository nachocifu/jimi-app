<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <title>Jimi Rest</title>
    <!-- google font -->
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet"
          type="text/css"/>
    <!-- icons -->
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css" integrity="sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/" crossorigin="anonymous">

    <!--Material-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/tables/list.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="table-container">
    <div class="card">
        <div class="card-content">
            <span class="card-title"><spring:message code="table.tables_header"/></span>
            <c:choose>
                <c:when test="${tables.size() > 0}">
                    <table class="mdl-data-table mdl-js-data-table mdl-shadow--2dp highlight">
                        <thead>
                        <tr>
                            <th><spring:message code="table.name"/></th>
                            <th><spring:message code="table.diners"/></th>
                            <th><spring:message code="table.status"/></th>
                            <th class=""></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${tables}" var="table">
                            <tr>
                                <td><c:out value="${table.name}"/></td>
                                <td><c:out value="${table.order.diners}"/></td>

                                <c:choose>
                                    <c:when test="${table.status.name() == 'FREE'}">
                                        <td><span
                                                class="label label-success label-mini"><spring:message
                                                code="table.free"/></span>
                                        </td>
                                    </c:when>
                                    <c:when test="${table.status.name() == 'BUSY'}">
                                        <td><span
                                                class="label label-danger label-mini"><spring:message
                                                code="table.busy"/></span>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td> <span
                                                class="label label-warning label-mini"><spring:message
                                                code="table.paying"/></span>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                    <a href=<c:url
                                            value="/tables/${table.id}"/>>
                                        <i class="fas fa-utensils fa-lg"></i>
                                    </a>
                                </td>
                                <td>
                                    <a href=<c:url
                                            value="/tables/edit/${table.id}"/>>
                                        <i class="fa fa-edit fa-lg"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <ul class="pagination paginator">
                        <c:forEach var="i" begin="1" end="${qp.pageCount}">
                            <c:if test="${qp.currentPage +1 == i}">
                                <li class="active"><a href="<c:url value="/tables/page/${i}"/>"><c:out
                                        value="${i}"/></a></li>
                            </c:if>
                            <c:if test="${qp.currentPage +1 != i}">
                                <li class="waves-effect"><a href="<c:url value="/tables/page/${i}"/>"><c:out
                                        value="${i}"/></a></li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-danger text-center">
                        <strong><spring:message code="ouch"/></strong> <spring:message
                            code="table.no_tables"/>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>


<!-- start js include path -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>

<!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>

<!-- end js include path -->
</body>
</html>


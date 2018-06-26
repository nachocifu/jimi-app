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
    <link href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>" rel="stylesheet"
          type="text/css"/>

    <!--Material-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

    <!-- Compiled and minified CSS -->

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">

    <!-- Compiled and minified JavaScript -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/dishes/list.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<%-- TODO hay que hacer responsive esta tabla --%>
<div class="table-container">
    <div class="card">

        <div class="card-content">
            <%--TODO estos titulos se ven raros--%>
            <span class="card-title"><spring:message code="dishes"/></span>
            <c:choose>
                <c:when test="${dishes.size() > 0}">
                    <table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp highlight">
                        <thead>
                        <tr>
                            <th><spring:message code="dish.name"/></th>
                            <th><spring:message code="dish.price"/></th>
                            <th><spring:message code="dish.stock"/></th>
                            <th><spring:message code="dish.status"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${dishes}" var="dish">
                            <tr>
                                <td><c:out value="${dish.name}"/></td>
                                <td>$<c:out value="${dish.price}"/></td>
                                <td><c:out value="${dish.stock}"/></td>
                                <td>
                                    <c:choose>
                                        <c:when test="${dish.stock > 0}">
                                            <span class="label label-success label-mini"><spring:message
                                                    code="dish.available"/></span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="label label-danger label-mini"><spring:message
                                                    code="dish.unavailable"/></span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <form method="POST"
                                          action="<c:url value="/admin/dishes/stock/increase"/>">
                                        <input type="hidden" value="${dish.id}"
                                               name="dishid">
                                        <input type="hidden" value="${qp.currentPage + 1}" name="page">
                                        <button type="submit"
                                                class="btn btn-success btn-xs">
                                            <i class="fa fa-plus"></i>
                                        </button>
                                    </form>
                                </td>
                                <td>
                                    <c:if test="${dish.stock > 0}">
                                        <form method="POST"
                                              action="<c:url value="/admin/dishes/stock/decrease"/>">
                                            <input type="hidden" value="${dish.id}"
                                                   name="dishid">
                                            <input type="hidden" value="${qp.currentPage + 1}" name="page">
                                            <button type="submit"
                                                    class="btn btn-primary btn-xs">
                                                <i class="fa fa-minus"></i>
                                            </button>
                                        </form>
                                    </c:if>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <ul class="pagination paginator">
                        <c:forEach var = "i" begin = "1" end = "${qp.pageCount}">
                            <c:if test="${qp.currentPage +1 == i}">
                                <li class="active"><a href="<c:url value="/admin/dishes/page/${i}"/>"><c:out value = "${i}"/></a></li>
                            </c:if>
                            <c:if test="${qp.currentPage +1 != i}">
                                <li class="waves-effect"><a href="<c:url value="/admin/dishes/page/${i}"/>"><c:out value = "${i}"/></a></li>
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
<!-- end js include path -->
</body>
</html>


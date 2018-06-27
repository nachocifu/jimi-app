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

    <!-- Compiled and minified CSS -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admin/dashboard.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<%-- TODO hay que hacer responsive esta tabla --%>
<div class="table-container">
    <div class="card">
        <div class="card-content">

            <span class="card-title"><spring:message code="order.title"/></span>
            <c:choose>
                <c:when test="${lastOrders.size() <= 0}">
                    <div class="alert alert-info text-center">
                        <strong><spring:message code="ouch"/></strong>
                        <spring:message code="order.no_order"/>
                    </div>
                </c:when>
                <c:otherwise>
                    <span><spring:message code="admin.latest_bills"/></span>
                    <table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp highlight">
                        <thead>
                        <tr>
                            <td><strong><spring:message code="bill.date"/></strong></td>
                            <td><strong><spring:message code="bill.ammount"/></strong></td>
                            <td><strong><spring:message code="bill.diners"/></strong></td>
                            <td><strong></strong></td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${lastOrders}" var="order">
                            <tr>
                                <td><fmt:formatDate pattern="dd/MM/yyyy" value="${order.closedAt}"/> - <fmt:formatDate
                                        pattern="H:mm" value="${order.closedAt}"/></td>
                                <td>$${order.total}</td>
                                <td>${order.diners}</td>
                                <td>
                                    <a href=<c:url
                                            value="/admin/order_edit/${order.id}"/>>
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
                                <li class="active"><a href="<c:url value="/admin/bills/page/${i}"/>"><c:out
                                        value="${i}"/></a></li>
                            </c:if>
                            <c:if test="${qp.currentPage +1 != i}">
                                <li class="waves-effect"><a href="<c:url value="/admin/bills/page/${i}"/>"><c:out
                                        value="${i}"/></a></li>
                            </c:if>
                        </c:forEach>
                    </ul>
                </c:otherwise>
            </c:choose>

        </div>
    </div>
</div>

<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script defer src="https://cdn.plot.ly/plotly-latest.min.js"></script>

<!-- Compiled and minified JavaScript -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>

<!-- end js include path -->
</body>
</html>


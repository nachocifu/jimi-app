<%@ page import="edu.itba.paw.jimi.models.TableStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <title>
        Jimi Restaurant
    </title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no' name='viewport' />
    <!--     Fonts and icons     -->
    <link rel="stylesheet" type="text/css" href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons" />
    <!-- CSS Files -->
    <link href="<c:url value="/resources/css/Final/material-dashboard.css?v=2.1.0"/>" rel="stylesheet"/>

    <!--Material-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">

</head>

<body class="">
<div class="wrapper ">
    <div class="sidebar" data-color="purple" data-background-color="white">

        <div class="logo">
            <a href="#" class="simple-text logo-normal">
                JIMI RESTAURANT APP
            </a>
        </div>

        <jsp:include page="/WEB-INF/UTILS/sidbar.jsp"/>

    </div>
    <div class="main-panel">
        <!-- Navbar -->
        <nav class="navbar navbar-expand-lg navbar-transparent navbar-absolute fixed-top">
            <div class="container-fluid">
                <div class="navbar-wrapper">
                    <a class="navbar-brand">Kitchen</a>
                </div>
            </div>
        </nav>
        <!-- End Navbar -->
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <div class="card">
                            <div class="card-header card-header-success">
                                <h4 class="card-title">New orders</h4>
                            </div>
                            <c:choose>
                                <c:when test="${lastOrders.size() <= 0}">
                                    <div class="alert text-center">
                                        <strong><spring:message code="ouch"/></strong>
                                        <spring:message code="order.no_order"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="card-body table-responsive">
                                        <table class="table table-hover">
                                            <tbody>
                                            <c:forEach items="${lastOrders}" var="order" end="9">
                                                <tr>
                                                    <td>${order.id}</td>
                                                    <td>${order.diners}</td>
                                                    <td>$${order.total}</td>
                                                    <th>${order.closedAt}</th>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <div class="card">
                            <div class="card-header card-header-warning">
                                <h4 class="card-title">Total Dishes</h4>
                            </div>
                            <c:choose>
                                <c:when test="${lastOrders.size() <= 0}">
                                    <div class="alert text-center">
                                    <strong><spring:message code="ouch"/></strong>
                                        <spring:message code="order.no_order"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="card-body table-responsive">
                                        <table class="table table-hover">
                                            <thead class="text-warning">
                                            <th>ID</th>
                                            <th>Diners</th>
                                            <th>Total</th>
                                            <th>Closed At</th>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${lastOrders}" var="order" end="9">
                                                <tr>
                                                    <td>${order.id}</td>
                                                    <td>${order.diners}</td>
                                                    <td>$${order.total}</td>
                                                    <th>${order.closedAt}</th>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <div class="card">
                            <div class="card-header card-header-danger">
                                <h4 class="card-title">Orders Waiting > 20 min</h4>
                            </div>
                            <c:choose>
                                <c:when test="${lastOrders.size() <= 0}">
                                    <div class="alert text-center">
                                    <strong><spring:message code="ouch"/></strong>
                                        <spring:message code="order.no_order"/>
                                    </div>
                                </c:when>
                                <c:otherwise>
                                    <div class="card-body table-responsive">
                                        <table class="table table-hover">
                                            <thead class="text-warning">
                                            <th>ID</th>
                                            <th>Diners</th>
                                            <th>Total</th>
                                            <th>Closed At</th>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${lastOrders}" var="order" end="9">
                                                <tr>
                                                    <td>${order.id}</td>
                                                    <td>${order.diners}</td>
                                                    <td>$${order.total}</td>
                                                    <th>${order.closedAt}</th>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<!--   Core JS Files   -->
<script src="<c:url value="/resources/js/core/jquery.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/core/popper.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/core/bootstrap-material-design.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/plugins/perfect-scrollbar.jquery.min.js"/>"></script>

<!--  Notifications Plugin    -->
<script src="<c:url value="/resources/js/plugins/bootstrap-notify.js"/>"></script>
<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
<script src="<c:url value="/resources/js/material-dashboard.min.js?v=2.1.0"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/jimi-charts.js"/>" type="text/javascript"></script>

</body>
</html>
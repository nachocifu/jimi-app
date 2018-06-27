<%@ page import="edu.itba.paw.jimi.models.TableStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="BusyCode" value="<%=TableStatus.BUSY.ordinal()%>"/>
<c:set var="PayingCode" value="<%=TableStatus.PAYING.ordinal()%>"/>

<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <title>
        Jimi Restaurant
    </title>
    <meta content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0, shrink-to-fit=no'
          name='viewport'/>
    <!--     Fonts and icons     -->
    <link rel="stylesheet" type="text/css"
          href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700|Roboto+Slab:400,700|Material+Icons"/>
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
                    <a class="navbar-brand"><spring:message code="dishes"/></a>
                </div>
            </div>
        </nav>

        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header card-header-primary">
                                <h4 class="card-title "><spring:message code="dishes"/></h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <c:choose>
                                        <c:when test="${dishes.size() > 0}">
                                            <table class="table">
                                                <thead>
                                                    <tr>
                                                        <th><spring:message code="dish.name"/></th>
                                                        <th><spring:message code="dish.price"/></th>
                                                        <th><spring:message code="dish.stock"/></th>
                                                        <th><spring:message code="dish.status"/></th>
                                                        <th>Actions</th>
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
                                                                    <span class="label label-success label-mini"><spring:message code="dish.available"/></span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                        <span class="label label-danger label-mini"><spring:message code="dish.unavailable"/></span>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </td>
                                                        <td>
                                                            <form method="POST"
                                                                  action="<c:url value="/admin/dishes/stock/increase"/>">
                                                                <input type="hidden" value="${dish.id}" name="dishid">
                                                                <button type="submit" class="btn btn-success btn-xs">
                                                                    <i class="material-icons">add</i>
                                                                </button>
                                                            </form>
                                                        </td>
                                                        <td>
                                                            <c:if test="${dish.stock > 0}">
                                                                <form method="POST"
                                                                      action="<c:url value="/admin/dishes/stock/decrease"/>">
                                                                    <input type="hidden" value="${dish.id}"
                                                                           name="dishid">
                                                                    <button type="submit"
                                                                            class="btn btn-primary btn-xs">
                                                                        <i class="material-icons">remove</i>
                                                                    </button>
                                                                </form>
                                                            </c:if>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="alert alert-danger text-center">
                                                <strong><spring:message code="ouch"/></strong> <spring:message
                                                    code="dishes.no_dishes"/>
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
    </div>


</div>

<!--   Core JS Files   -->
<script src="<c:url value="/resources/js/core/jquery.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/core/popper.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/core/bootstrap-material-design.min.js"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/plugins/perfect-scrollbar.jquery.min.js"/>"></script>

<!-- Chartist JS -->
<script src="<c:url value="/resources/js/plugins/chartist.min.js"/>"></script>
<!--  Notifications Plugin    -->
<script src="<c:url value="/resources/js/plugins/bootstrap-notify.js"/>"></script>
<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
<script src="<c:url value="/resources/js/material-dashboard.min.js?v=2.1.0"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/jimi-charts.js"/>" type="text/javascript"></script>

</body>
</html>
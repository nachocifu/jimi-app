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
                    <a class="navbar-brand"> Checkout</a>
                </div>
            </div>
        </nav>

        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header card-header-primary">
                                <h4 class="card-title ">Bill - <fmt:formatDate value="${table.order.closedAt}"
                                                                               pattern="yyyy-MM-dd HH:mm"/></h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <table class="table">
                                        <thead>
                                            <tr>
                                                <th><spring:message code="checkout.table.description"/></th>
                                                <th><spring:message code="checkout.table.charges"/></th>
                                                <th><spring:message code="checkout.table.items"/></th>
                                                <th><spring:message code="bill.ammount"/> </th>
                                            </tr>
                                        </thead>

                                        <tbody>
                                            <c:forEach items="${table.order.dishes}" var="dish">
                                                <tr>
                                                    <td class="text-center">${dish.key.name}</td>
                                                    <td class="text-center">${dish.key.price}</td>
                                                    <td class="text-center">${dish.value}</td>
                                                    <td class="text-right"><fmt:formatNumber value="${dish.value * dish.key.price}" maxFractionDigits="2"/></td>
                                                </tr>
                                            </c:forEach>
                                            <th>
                                                <tr>
                                                <td class="text-center"></td>
                                                <td class="text-center"></td>
                                                <td class="text-right"><h5><b><spring:message code="checkout.table.total"/>
                                                    :</b></h5></td>
                                                <td class="text-right"><h5>$${total}</h5></td>
                                                </tr>
                                            </th>
                                        </tbody>
                                    </table>
                                    <form action="<c:url value="/tables/${table.id}/status"/>" method="POST">
                                        <input value="1" name="status" type="hidden"/>
                                        <input type="submit"
                                               class="btn btn-primary pull-right"
                                               value="<spring:message code="checkout.charged"/>"/>
                                    </form>
                                    <button onclick="window.print();" class="waves-effect waves-light btn" type="button">
                                            <span> <spring:message
                                                    code="checkout.print"/></span>
                                    </button>
                                    <a href="<c:url value="/tables/"/>">
                                        <button class="waves-effect waves-light btn" type="button">
                                                <span><i class="fa fa-list"></i> <spring:message
                                                        code="checkout.tables"/></span>
                                        </button>
                                    </a>
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
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
                    <a class="navbar-brand">Dashboard</a>
                </div>
            </div>
        </nav>
        <!-- End Navbar -->
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-lg-3 col-md-6 col-sm-6">
                        <div class="card card-stats">
                            <div class="card-header card-header-warning card-header-icon">
                                <div class="card-icon">
                                    <i class="material-icons">content_copy</i>
                                </div>
                                <p class="card-category">Used Space</p>
                                <h3 class="card-title">${busyTables}/${totalTables}</h3>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">local_offer</i> Tracked from Github
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6">
                        <div class="card card-stats">
                            <div class="card-header card-header-success card-header-icon">
                                <div class="card-icon">
                                    <i class="material-icons">store</i>
                                </div>
                                <p class="card-category">Revenue</p>
                                <h3 class="card-title">$34,245</h3>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">date_range</i> Last 24 Hours
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6">
                        <div class="card card-stats">
                            <div class="card-header card-header-danger card-header-icon">
                                <div class="card-icon">
                                    <i class="material-icons">info_outline</i>
                                </div>
                                <p class="card-category">Fixed Issues</p>
                                <h3 class="card-title">75</h3>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">local_offer</i> Tracked from Github
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-lg-3 col-md-6 col-sm-6">
                        <div class="card card-stats">
                            <div class="card-header card-header-info card-header-icon">
                                <div class="card-icon">
                                    <i class="material-icons">info_outline</i>
                                </div>
                                <p class="card-category">Followers</p>
                                <h3 class="card-title">+245</h3>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">update</i> Just Updated
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- CHARTS -->
                <div class="row">
                    <div class="col-md-4">
                        <div class="card card-chart">
                            <div class="card-header card-header-success">
                                <div class="ct-chart" id="chart-1"></div>
                            </div>
                            <div class="card-body">
                                <h4 class="card-title">Daily Sales</h4>
                                <p class="card-category">
                                    <span class="text-success"><i class="fa fa-long-arrow-up"></i> 55% </span> increase in today sales.</p>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">access_time</i> updated 4 minutes ago
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card card-chart">
                            <div class="card-header card-header-warning">
                                <div class="ct-chart" id="chart-2"></div>
                            </div>
                            <div class="card-body">
                                <h4 class="card-title">Email Subscriptions</h4>
                                <p class="card-category">Last Campaign Performance</p>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">access_time</i> campaign sent 2 days ago
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card card-chart">
                            <div class="card-header card-header-danger">
                                <div class="ct-chart" id="chart-3"></div>
                            </div>
                            <div class="card-body">
                                <h4 class="card-title">Completed Tasks</h4>
                                <p class="card-category">Last Campaign Performance</p>
                            </div>
                            <div class="card-footer">
                                <div class="stats">
                                    <i class="material-icons">access_time</i> campaign sent 2 days ago
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- END CHART -->

                <div class="row">
                    <div class="col-lg-12 col-md-12">
                        <div class="card">
                            <div class="card-header card-header-warning">
                                <h4 class="card-title"><spring:message code="admin.latest_bills"/></h4>
                            </div>
                            <c:choose>
                                <c:when test="${lastOrders.size() <= 0}">
                                    <div class="alert alert-info text-center">
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
                                            <c:forEach items="${lastOrders}" var="order">
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

<!-- Chartist JS -->
<script src="<c:url value="/resources/js/plugins/chartist.min.js"/>"></script>
<!--  Notifications Plugin    -->
<script src="<c:url value="/resources/js/plugins/bootstrap-notify.js"/>"></script>
<!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
<script src="<c:url value="/resources/js/material-dashboard.min.js?v=2.1.0"/>" type="text/javascript"></script>
<script src="<c:url value="/resources/js/jimi-charts.js"/>" type="text/javascript"></script>

</body>
</html>
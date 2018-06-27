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
    <%--<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"
          integrity="sha256-e22BQKCF7bb/h/4MFJ1a4lTRR2OuAe8Hxa/3tgU5Taw=" crossorigin="anonymous"/>

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/tables/index.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
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
                    <a class="navbar-brand"><spring:message code="checkout.tables"/></a>
                </div>
            </div>
        </nav>

        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header card-header-primary">
                                <h4 class="card-title ">${table.name} - ${diners} Diners</h4>
                            </div>
                            <div class="card-body">
                                <div class="table-responsive">
                                    <c:choose>
                                        <c:when test="${table.order.dishes.size() > 0}">
                                            <table class="table">
                                                <thead>
                                                <tr>
                                                    <th><spring:message code="dish.name"/></th>
                                                    <th><spring:message code="dish.price"/></th>
                                                    <th><spring:message code="dish.amount"/></th>
                                                    <th><spring:message code="dish.total"/></th>
                                                    <th></th>
                                                    <th></th>
                                                    <th></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${dishes}" var="dishEntry">
                                                    <tr>
                                                        <td><c:out value="${dishEntry.key.name}"/></td>
                                                        <td><c:out value="${dishEntry.key.price}"/></td>
                                                        <td><c:out value="${dishEntry.value}"/></td>
                                                        <td>
                                                            <fmt:formatNumber
                                                                    value="${dishEntry.value * dishEntry.key.price}"
                                                                    maxFractionDigits="2"/>
                                                        </td>
                                                        <td>
                                                            <c:if test="${dishEntry.key.stock != 0}">
                                                                <form action="<c:url value="/tables/${table.id}/add_one_dish"/>"
                                                                      method="post"
                                                                      class="form-with-buttons">
                                                                    <button type="submit"
                                                                            class="btn btn-success btn-xs">
                                                                        <i class="fa fa-plus"></i>
                                                                    </button>
                                                                    <input type="hidden"
                                                                           value="${dishEntry.key.id}"
                                                                           name="dishid"/>
                                                                </form>
                                                            </c:if>
                                                        </td>
                                                        <td>
                                                            <form action="<c:url value="/tables/${table.id}/remove_one_dish"/>"
                                                                  method="post" class="form-with-buttons">
                                                                <button type="submit"
                                                                        class="btn btn-primary btn-xs">
                                                                    <i class="fa fa-minus"></i>
                                                                </button>
                                                                <input type="hidden" value="${dishEntry.key.id}"
                                                                       name="dishid"/>
                                                            </form>
                                                        </td>
                                                        <td>
                                                            <form action="<c:url value="/tables/${table.id}/remove_all_dish"/>"
                                                                  method="post" class="form-with-buttons">
                                                                <button type="submit"
                                                                        class="btn btn-danger btn-xs">
                                                                    <i class="fa fa-trash-o "></i>
                                                                </button>
                                                                <input type="hidden" value="${dishEntry.key.id}"
                                                                       name="dishid"/>
                                                            </form>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="alert alert-info text-center">
                                                <spring:message code="table.no_dishes"/>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                    <a href="<c:url value="/tables/"/>" class="btn blue-gray"><spring:message
                                            code="table.return_to_table_list"/>
                                    </a>

                                    <form style="float:left; padding-right:10px" action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                        <input value="${PayingCode}" name="status" type="hidden"/>
                                        <input type="submit" class="btn btn-primary" value="<spring:message code="table.charge_caps"/>"/>
                                    </form>

                                    <form style="float:left; padding-right:10px" action="<c:url value="/tables/${table.id}/add_dish"/>">
                                        <input type="submit" value="<spring:message code="table.add_dish"/>"
                                               class="btn btn-primary"/>
                                    </form>

                                    <form style="float:left; padding-right:10px" action="<c:url value="/tables/${table.id}/add_diner"/>" method="post">
                                        <input type="submit" value="+ diNers"
                                               class="btn btn-default"/>
                                    </form>

                                    <form  style="float:left; padding-right:10px" action="<c:url value="/tables/${table.id}/subtract_diner"/>" method="post">
                                        <input type="submit" value="- diNers"
                                               class="btn btn-default <c:if test="${diners == 0}">disabled</c:if>">
                                    </form>
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


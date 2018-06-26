<%@ page import="edu.itba.paw.jimi.models.TableStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                    <a class="navbar-brand"><spring:message code="table.add_dish"/></a>
                </div>
            </div>
        </nav>
        <div class="content">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-12">
                        <div class="card">
                            <div class="card-header card-header-primary">
                                <h4 class="card-title "><spring:message code="table.add_dish"/></h4>
                            </div>
                            <c:url value="/tables/create" var="postPath"/>
                            <div class="table-responsive">
                                <c:choose>
                                    <c:when test="${dishes.size() > 0}">
                                        <form:form modelAttribute="tableAddDishForm" action="${postPath}" method="post">
                                            <c:url value="/tables/${table.id}/add_dish" var="postPath"/>
                                            <div class="row">
                                                <div class="input-field col s12">
                                                    <form:select style="float:left; padding-right:10px" id="dishid" name="dishid" path="dishid">
                                                        <c:forEach items="${dishes}" var="dish">
                                                            <option value="${dish.id}"
                                                                    data-max="${dish.stock}">${dish.name}</option>
                                                        </c:forEach>
                                                    </form:select>
                                                    <form:input type="number" id="amount" path="amount" step="1" min="1"
                                                                max="100" value="1" class="hide"/>
                                                    <form:errors path="amount" element="p"/>
                                                </div>
                                            </div>

                                            <button type="submit" class="btn btn-primary pull-right"><spring:message
                                                    code="dish.add"/></button>
                                            <a href="<c:url value="/tables/${table.id}"/>"
                                               class="waves-effect waves-light btn"><spring:message
                                                    code="dish.cancel"/> </a>
                                        </form:form>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="alert alert-info text-center">
                                            <strong><spring:message code="ouch"/></strong>
                                            <spring:message
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
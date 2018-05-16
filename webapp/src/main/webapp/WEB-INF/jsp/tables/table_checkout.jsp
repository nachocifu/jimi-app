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
    <meta charset="UTF-8"/>
    <title>Jimi Rest</title>
    <!-- google font -->
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet"
          type="text/css"/>
    <!-- icons -->
    <link href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>" rel="stylesheet"
          type="text/css"/>
    <!--bootstrap -->
    <link href="<c:url value="/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <!-- Template Styles -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/plugins.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/responsive.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/jimi-rest/jimi-rest.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon"
          href="${pageContext.request.contextPath}/resources/img/jimi-rest/favicon.ico"/>
</head>
<body class="page-header-fixed sidemenu-closed-hidelogo page-content-white page-md header-white dark-sidebar-color logo-dark">
<div class="page-wrapper">
    <!-- start header -->
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <!-- end header -->

    <!-- start page container -->
    <div class="page-container">
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <!-- start sidebar menu -->
            <jsp:include page="/WEB-INF/jsp/sidebar.jsp"/>
            <!-- end sidebar menu -->
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_USER')">
            <!-- start sidebar menu -->
            <jsp:include page="/WEB-INF/jsp/sidebar_user.jsp"/>
            <!-- end sidebar menu -->
        </sec:authorize>

        <!-- start page content -->
        <div class="page-content-wrapper">
            <div class="page-content">
                <div class="row">
                    <div class="col-md-12">
                        <div class="white-box">
                            <h3><b><spring:message code="checkout.receipt"/></b> ${table.order.diners} <spring:message
                                    code="checkout.diners"/> <span class="pull-right"><fmt:formatDate
                                    value="${table.order.closedAt}" pattern="yyyy-MM-dd HH:mm"/></span></h3>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="table-responsive m-t-40">
                                        <table class="table table-hover">
                                            <thead>
                                            <tr>
                                                <th class="text-center"><spring:message
                                                        code="checkout.table.description"/></th>
                                                <th class="text-center"><spring:message
                                                        code="checkout.table.charges"/></th>
                                                <th class="text-center"><spring:message
                                                        code="checkout.table.items"/></th>
                                                <th class="text-right"><spring:message
                                                        code="checkout.table.amount"/></th>
                                            </tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${table.order.dishes}" var="dish">
                                                <tr>
                                                    <td class="text-center">${dish.key.name}</td>
                                                    <td class="text-center">${dish.key.price}</td>
                                                    <td class="text-center">${dish.value}</td>
                                                    <td class="text-right"><fmt:formatNumber
                                                            value="${dish.value * dish.key.price}"
                                                            maxFractionDigits="2"/></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="col-md-12">
                                    <div class="pull-right m-t-30 text-right">
                                        <hr>
                                        <h3><b><spring:message code="checkout.table.total"/> :</b> $${total}</h3></div>
                                    <div class="clearfix"></div>
                                    <hr>
                                    <div class="text-right">
                                        <form action="<c:url value="/tables/${table.id}/status"/>" method="POST">
                                            <input value="2" name="status" type="hidden"/>
                                            <input type="submit"
                                                   class="btn btn-success"
                                                   value="<spring:message code="checkout.charged"/>"/>
                                        </form>
                                        <button onclick="window.print();" class="btn btn-info" type="button">
                                            <span><i class="fa fa-print"></i> <spring:message
                                                    code="checkout.print"/></span>
                                        </button>
                                        <a href="<c:url value="/tables/"/>">
                                            <button class="btn btn-secondary" type="button">
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
        <!-- end page content -->

    </div>
    <!-- end page container -->

    <!-- start footer -->
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
    <!-- end footer -->
</div>
<!-- start js include path -->
<script src="<c:url value="/webjars/jquery/3.0.0/jquery.min.js"/>"></script>
<script src="<c:url value="/webjars/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"/>"></script>
<!-- bootstrap -->
<script src="<c:url value="/webjars/bootstrap/4.0.0/js/bootstrap.js"/>"></script>
<!-- Common js-->
<script src="<c:url value="/resources/js/app.js"/>"></script>
<script src="<c:url value="/resources/js/layout.js"/>"></script>
<!-- end js include path -->
</body>
</html>
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
    <link href="<c:url value="/resources/plugins/simple-line-icons/simple-line-icons.min.css"/>" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>" rel="stylesheet"
          type="text/css"/>
    <!--bootstrap -->
    <link href="<c:url value="/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <!-- animation -->
    <link href="<c:url value="/resources/css/pages/animate_page.css"/>" rel="stylesheet"/>
    <!-- Template Styles -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/plugins.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/responsive.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/jimi-rest/jimi-rest.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon"
          href="${pageContext.request.contextPath}/resources/img/jimi-rest/favicon.ico"/>
</head>

<body class="page-header-fixed page-content-white page-md header-white logo-dark">
<div class="page-wrapper">
    <!-- start header -->
    <jsp:include page="/WEB-INF/jsp/clean_header.jsp"/>
    <!-- end header -->

    <!-- start page container -->
    <div class="dishes-page-container">

        <sec:authorize access="hasRole('ROLE_ADMIN')">
        <!-- start page container -->
        <div class="page-container">
                <!-- start sidebar menu -->
                <jsp:include page="/WEB-INF/jsp/sidebar.jsp"/>
                <!-- end sidebar menu -->
            </sec:authorize>

            <!-- start page content -->
            <div class="page-content-wrapper">
                <div class="page-content">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="card-box">
                                <div class="card-head">
                                    <header><spring:message code="table.tables_header"/></header>
                                </div>
                                <div class="card-body">
                                    <c:choose>
                                        <c:when test="${tables.size() > 0}">
                                            <div class="table-responsive">
                                                <table class="table table-striped custom-table">
                                                    <thead class="text-left">
                                                    <tr>
                                                        <th><spring:message code="table.name"/></th>
                                                        <th><spring:message code="table.diners"/></th>
                                                        <th><spring:message code="table.status"/></th>
                                                        <th></th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${tables}" var="table">
                                                        <tr>
                                                        <tr>
                                                            <td><c:out value="${table.name}"/></td>
                                                            <td><c:out value="${table.order.diners}"/></td>

                                                            <c:choose>
                                                                <c:when test="${table.status.toString() == 'FREE'}">
                                                                    <td><span
                                                                            class="label label-success label-mini"><spring:message
                                                                            code="table.free"/></span>
                                                                    </td>
                                                                </c:when>
                                                                <c:when test="${table.status.toString() == 'BUSY'}">
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
                                                                    <i class="fa fa-edit fa-lg"></i>
                                                                </a>
                                                            </td>
                                                        </tr>
                                                        </tr>
                                                    </c:forEach>
                                                    </tbody>
                                                </table>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="alert alert-danger text-center">
                                                <strong><spring:message code="ouch"/></strong> <spring:message
                                                    code="table.no_tables"/>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>
                                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                                        <div>
                                            <form action="<c:url value="/tables/register"/>">
                                                <button type="submit" class="btn btn-success"><i
                                                        class="fa fa-plus"></i><spring:message code="table.add"/>
                                                </button>
                                            </form>
                                        </div>
                                    </sec:authorize>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- end page content -->
<sec:authorize access="hasRole('ROLE_ADMIN')">
        </div>
            </sec:authorize>
        <!-- end page container -->

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
<!-- animation -->
<script src="<c:url value="/resources/js/pages/ui/animations.js"/>"></script>
<!-- end js include path -->
</body>
</html>


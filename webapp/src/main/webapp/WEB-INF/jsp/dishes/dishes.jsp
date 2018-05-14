<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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

                    <!-- start page content -->
                    <div class="page-content-wrapper">
                        <div class="page-content">

                            <div class="row">
                                <div class="col-sm-12">
                                    <div class="card-box">
                                        <div class="card-head">
                                            <header><spring:message code="dishes"/></header>
                                        </div>
                                        <div class="card-body">
                                            <div class="table-responsive">
                                                <c:choose>
                                                    <c:when test="${dishes.size() > 0}">
                                                        <table class="table table-striped custom-table">
                                                            <thead class="text-left">
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
                                                                        <form action="<c:url value="/dishes/setstock"/>">
                                                                            <input type="hidden" value="${dish.id}"
                                                                                   name="dishid">
                                                                            <input type="hidden" value="${dish.stock+1}"
                                                                                   name="stock">
                                                                            <button type="submit"
                                                                                    class="btn btn-success btn-xs">
                                                                                <i class="fa fa-plus"></i>
                                                                            </button>
                                                                        </form>
                                                                    </td>
                                                                    <td>
                                                                        <form action="<c:url value="/dishes/setstock"/>">
                                                                            <input type="hidden" value="${dish.id}"
                                                                                   name="dishid">
                                                                            <input type="hidden" value="${dish.stock-1}"
                                                                                   name="stock">
                                                                            <button type="submit"
                                                                                    class="btn btn-primary btn-xs">
                                                                                <i class="fa fa-minus"></i>
                                                                            </button>
                                                                        </form>
                                                                    </td>
                                                                </tr>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="alert alert-danger text-center">
                                                            <strong><spring:message code="dish.ouch"/></strong>
                                                            <spring:message code="dish.no_dishes"/>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div>
                                                <form action="<c:url value="/dishes/create"/>">
                                                    <button type="submit" class="btn rebeccapurple-color"><i
                                                            class="fa fa-plus"></i><spring:message code="dish.add"/>
                                                    </button>
                                                </form>
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

        </sec:authorize>

        <sec:authorize access="hasRole('ROLE_USER')">
            <!-- start page content -->
            <div class="page-content-wrapper">
                <div class="dishes-page-content">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="col-md-10 mx-auto mt-5">
                                    <div class="card card-topline-purple">
                                        <div class="card-head">
                                            <header><spring:message code="dishes"/></header>
                                        </div>
                                        <div class="card-body">
                                            <div class="table-responsive">
                                                <c:choose>
                                                    <c:when test="${dishes.size() > 0}">
                                                        <table class="table table-striped custom-table">
                                                            <thead class="text-left">
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
                                                                        <form action="<c:url value="/dishes/setstock"/>">
                                                                            <input type="hidden" value="${dish.id}"
                                                                                   name="dishid">
                                                                            <input type="hidden" value="${dish.stock+1}"
                                                                                   name="stock">
                                                                            <button type="submit"
                                                                                    class="btn btn-success btn-xs">
                                                                                <i class="fa fa-plus"></i>
                                                                            </button>
                                                                        </form>
                                                                    </td>
                                                                    <td>
                                                                        <form action="<c:url value="/dishes/setstock"/>">
                                                                            <input type="hidden" value="${dish.id}"
                                                                                   name="dishid">
                                                                            <input type="hidden" value="${dish.stock-1}"
                                                                                   name="stock">
                                                                            <button type="submit"
                                                                                    class="btn btn-primary btn-xs">
                                                                                <i class="fa fa-minus"></i>
                                                                            </button>
                                                                        </form>
                                                                    </td>
                                                                </tr>
                                                                </tr>
                                                            </c:forEach>
                                                            </tbody>
                                                        </table>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <div class="alert alert-danger text-center">
                                                            <strong><spring:message code="dish.ouch"/></strong>
                                                            <spring:message code="dish.no_dishes"/>
                                                        </div>
                                                    </c:otherwise>
                                                </c:choose>
                                            </div>
                                            <div>
                                                <form action="<c:url value="/dishes/create"/>">
                                                    <button type="submit" class="btn rebeccapurple-color"><i
                                                            class="fa fa-plus"></i><spring:message code="dish.add"/>
                                                    </button>
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
            <!-- end page content -->
        </sec:authorize>
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


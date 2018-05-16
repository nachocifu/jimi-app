<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <!-- google font -->
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet"
          type="text/css"/>
    <!-- icons -->
    <link href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>" rel="stylesheet"
          type="text/css"/>
    <!--bootstrap -->
    <link href="<c:url value="/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css"/>
    <!-- Material Design Lite CSS -->
    <link rel="stylesheet" href="<c:url value="/webjars/material-design-lite/1.1.0/material.min.css"/>"/>
    <link rel="stylesheet" href="<c:url value="/resources/css/material_style.css"/>"/>
    <!-- Template Styles -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/plugins.min.css"/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value="/resources/css/responsive.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/jimi-rest/jimi-rest.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon"
          href="${pageContext.request.contextPath}/resources/img/jimi-rest/favicon.ico"/>
</head>
<body class="page-header-fixed page-content-white page-md header-white logo-dark">

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
                                <header><spring:message code="table.greeting" arguments=": ${table.name}"/></header>
                            </div>
                            <div class="card-body">
                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                        <c:if test="${table.status == 'FREE'}">
                                            <h2><spring:message code="table.table_is"/>
                                                <strong>
                                                    <span style="color: green;"><spring:message
                                                            code="table.free"/>.</span>
                                                </strong>
                                            </h2>
                                            <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                                <input value="1" name="status" type="hidden"/>
                                                <input type="submit"
                                                       class="btn btn-default rebeccapurple-color"
                                                       value="<spring:message code="table.occupy"/>"/>
                                            </form>
                                        </c:if>
                                        <c:if test="${table.status == 'BUSY'}">
                                        <h2><spring:message code="table.table_is"/>
                                            <strong><span style="color: red;"><spring:message
                                                    code="table.busy"/></span></strong>.
                                        </h2>
                                        <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                            <input value="3" name="status" type="hidden"/>
                                            <input type="submit"
                                                   class="btn btn-default rebeccapurple-color"
                                                   value="<spring:message code="table.charge_caps"/>"/>
                                        </form>
                                        <c:url value="/tables/${table.id}/set_diners" var="postPath"/>
                                        <form:form modelAttribute="tableSetDinersForm" action="${postPath}"
                                                   method="post">
                                            <div class="form-row">
                                                <div class="form-group col-md-6">
                                                    <form:label for="diners"
                                                                path="diners"
                                                                placeholder=".col-lg-2"><spring:message
                                                            code="table.number_of_diners"/>:</form:label>
                                                    <div class="form-row">
                                                        <div class="col-md-2">
                                                            <form:input type="number" id="diners" name="diners"
                                                                        value="${diners}"
                                                                        path="diners" step="1" min="1" max="10"
                                                                        class="form-control"/>
                                                            <form:errors path="diners" cssStyle="color: red;"
                                                                         element="p"/>
                                                        </div>
                                                        <div class="col-md-2">
                                                            <input type="submit"
                                                                   value="<spring:message code="table.set_diners"/>"
                                                                   class="btn btn-default"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </form:form>
                                        <c:choose>
                                        <c:when test="${table.order.dishes.size() > 0}">
                                        <div class="table-responsive">
                                            <table class="table table-striped custom-table">
                                                <thead class="text-left">
                                                <tr>
                                                    <th><spring:message code="dish.name"/></th>
                                                    <th><spring:message code="dish.price"/></th>
                                                    <th><spring:message code="dish.amount"/></th>
                                                    <th><spring:message code="dish.total"/></th>
                                                    <th></th>
                                                </tr>
                                                </thead>
                                                <tbody>
                                                <c:forEach items="${dishes}" var="dishEntry">
                                                    <tr>
                                                        <td><c:out value="${dishEntry.key.name}"/></td>
                                                        <td><c:out value="${dishEntry.key.price}"/></td>
                                                        <td><c:out value="${dishEntry.value}"/></td>
                                                        <td><fmt:formatNumber
                                                                value="${dishEntry.value * dishEntry.key.price}"
                                                                maxFractionDigits="2"/></td>
                                                        <td>
                                                            <div class="row">
                                                                <div class="col-md-3">
                                                                    <c:choose>
                                                                        <c:when test="${dishEntry.key.stock != 0}">
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
                                                                        </c:when>
                                                                    </c:choose>
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <form action="<c:url value="/tables/${table.id}/remove_one_dish"/>"
                                                                          method="post" class="form-with-buttons">
                                                                        <button type="submit"
                                                                                class="btn btn-primary btn-xs">
                                                                            <i class="fa fa-minus"></i>
                                                                        </button>
                                                                        <input type="hidden" value="${dishEntry.key.id}"
                                                                               name="dishid"/>
                                                                    </form>
                                                                </div>
                                                                <div class="col-md-3">
                                                                    <form action="<c:url value="/tables/${table.id}/remove_all_dish"/>"
                                                                          method="post" class="form-with-buttons">
                                                                        <button type="submit"
                                                                                class="btn btn-danger btn-xs">
                                                                            <i class="fa fa-trash-o "></i>
                                                                        </button>
                                                                        <input type="hidden" value="${dishEntry.key.id}"
                                                                               name="dishid"/>
                                                                    </form>
                                                                </div>
                                                            </div>
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
                                        </div>
                                    </div>

                                    <form action="<c:url value="/tables/${table.id}/add_dish"/>">
                                        <input type="submit" value="<spring:message code="table.add_dish"/>"
                                               class="btn btn-default"/>
                                    </form>
                                    </c:if>
                                    <div class="col-lg-12 text-center">
                                        <a href="<c:url value="/tables/"/>"
                                           class="btn btn-default rebeccapurple-color"><spring:message
                                                code="table.return_to_table_list"/></a><br>
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


    <!-- start footer -->
    <jsp:include page="/WEB-INF/jsp/footer.jsp"/>
    <!-- end footer -->

    <!-- start js include path -->
    <script src="<c:url value="/webjars/jquery/3.0.0/jquery.min.js"/>"></script>
    <script src="<c:url value="/webjars/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"/>"></script>
    <!-- bootstrap -->
    <script src="<c:url value="/webjars/bootstrap/4.0.0/js/bootstrap.js"/>"></script>
    <!-- Common js-->
    <script src="<c:url value="/resources/js/app.js"/>"></script>
    <script src="<c:url value="/resources/js/layout.js"/>"></script>
    <!-- Material -->
    <script src="<c:url value="/webjars/material-design-lite/1.1.0/material.min.js"/>"></script>
    <!-- end js include path -->
</body>
</html>

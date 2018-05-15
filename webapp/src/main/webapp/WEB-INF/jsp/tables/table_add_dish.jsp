<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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


    <!-- start page content -->
    <div class="page-content-wrapper fixed">
        <div class="page-content-register-user">
            <div class="row">
                <div class="col-sm-12">
                    <div class="card-box card-box-table-index mx-auto mt-5">

                        <div class="card-head">
                            <header><spring:message code="table.please_select_dish"/>
                                <b> ${table.name}</b><br></header>
                        </div>

                        <div class="card-body">

                            <div class="row">
                                <div class="col-sm-12">

                                    <c:choose>
                                        <c:when test="${dishes.size() > 0}">

                                            <c:url value="/tables/${table.id}/add_dish" var="postPath"/>
                                            <form:form modelAttribute="tableAddDishForm" action="${postPath}"
                                                       method="post">

                                                <div class="row">
                                                    <div class="form-group col-sm-7">

                                                        <form class="form-inline well">
                                                            <div class="row">

                                                                <div class="form-group col-sm-5 display-4">
                                                                    <form:select cssClass="control-label" id="dishid"
                                                                                 name="dishid" path="dishid">
                                                                        <c:forEach items="${dishes}" var="dish">
                                                                            <option value="${dish.id}"
                                                                                    data-max="${dish.stock}">${dish.name}</option>
                                                                        </c:forEach>
                                                                    </form:select>
                                                                </div>

                                                                <div class="form-group col-sm-7">
                                                                    <form:input type="number" id="amount" path="amount"
                                                                                step="1" min="1"
                                                                                max="100"
                                                                                value="1" class="form-control"/>
                                                                    <form:errors path="amount" cssClass="formError"
                                                                                 element="p"/>
                                                                </div>
                                                            </div>
                                                        </form>


                                                    </div>

                                                    <div class="form-group col-sm-3">
                                                        <input type="submit" value="<spring:message code="dish.add"/>"
                                                               class="mdl-button mdl-button--raised mdl-js-ripple-effect rebeccapurple-color"/>
                                                    </div>

                                                </div>

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

                            <div class="row">
                                <div class="col-sm-12 p-t-20 text-center">
                                    <form action="<c:url value="/tables/${table.id}"/>">
                                        <input type="submit"
                                               class="mdl-button mdl-button--raised mdl-js-ripple-effect btn-default"
                                               value="<spring:message code="dish.cancel"/>"/>
                                    </form>
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
        </div>
    </div>


    <!-- start js include path -->
    <script src="<c:url value="/webjars/jquery/3.0.0/jquery.min.js"/>"></script>
    <script src="<c:url value="/webjars/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"/>"></script>
    <!-- bootstrap -->
    <script src="<c:url value="/webjars/bootstrap/4.0.0/js/bootstrap.js"/>"></script>
    <!-- Material -->
    <script src="<c:url value="/webjars/material-design-lite/1.1.0/material.min.js"/>"></script>
    <!-- animation -->
    <script src="<c:url value="/resources/js/pages/ui/animations.js"/>"></script>
    <!-- jimi-rest -->
    <script src="<c:url value="/resources/js/jimi-rest/set_input_on_select_change.js"/>"></script>
</body>
</html>
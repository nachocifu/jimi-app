<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
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
    <jsp:include page="/WEB-INF/jsp/clean_header.jsp"/>
    <!-- end header -->


    <!-- start page content -->
    <div class="page-content-wrapper fixed">
        <div class="page-content-register-user">

            <div class="row">
                <div class="col-sm-12">
                    <div class="card-box card-box-table-index mx-auto mt-5">

                        <div class="card-head">
                            <header><spring:message code="table.greeting" arguments=": ${table.name}"/></header>
                        </div>

                        <div class="card-body">
                            <div class="col-lg-15 p-t-20">
                                <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

                                    <c:if test="${table.status == 'Free'}">

                                        <h2><spring:message code="table.table_is"/> <span
                                                style="color: green;"><spring:message
                                                code="table.free"/></span>.</h2>

                                        <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                            <input value="1" name="status" type="hidden"/>
                                            <input type="submit"
                                                   class="btn btn-default btn-pink"
                                                   value="<spring:message code="table.occupy"/>"/>
                                        </form>

                                    </c:if>

                                    <c:if test="${table.status == 'CleaningRequired'}">

                                        <h2><spring:message code="table.table_is"/> <span
                                                style="color: orange;"><spring:message
                                                code="table.cleaning"/></span>.</h2>

                                        <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                            <input value="2" name="status" type="hidden"/>
                                            <input type="submit"
                                                   class="btn btn-default btn-pink"
                                                   value="<spring:message code="table.free"/>"/>
                                        </form>

                                    </c:if>

                                    <c:if test="${table.status == 'Busy'}">

                                    <h2><spring:message code="table.table_is"/>
                                        <span style="color: red;"><spring:message code="table.busy"/></span>.
                                    </h2>

                                    <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                        <input value="3" name="status" type="hidden"/>
                                        <input type="submit"
                                               class="btn btn-default btn-pink"
                                               value="<spring:message code="table.cleaning_caps"/>"/>
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
                                                        <form:errors path="diners" cssStyle="color: red;" element="p"/>
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
                                                    <td><c:out value="${dishEntry.value * dishEntry.key.price}"/></td>
                                                    <td>
                                                        <div class="row">
                                                            <div class="col-md-3">
                                                                <form action="<c:url value="/tables/${table.id}/add_one_dish"/>"
                                                                      method="post" class="form-with-buttons">
                                                                    <button type="submit"
                                                                            class="btn btn-success btn-xs">
                                                                        <i class="fa fa-plus"></i>
                                                                    </button>
                                                                    <input type="hidden" value="${dishEntry.key.id}"
                                                                           name="dishid"/>
                                                                </form>
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
                                                                    <button type="submit" class="btn btn-danger btn-xs">
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
                                    </div>
                                </div>

                                <form action="<c:url value="/tables/${table.id}/add_dish"/>">
                                    <input type="submit" value="<spring:message code="table.add_dish"/>"
                                           class="btn btn-default"/>
                                </form>
                                </c:if>

                                <div class="col-lg-12 text-center">
                                    <a href="<c:url value="/tables/"/>" class="btn btn-default btn-pink"><spring:message
                                            code="table.return_to_table_list"/></a><br>
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

    </div>

    <!-- start js include path -->
    <script src="<c:url value="/webjars/jquery/3.0.0/jquery.min.js"/>"></script>
    <!-- bootstrap -->
    <script src="<c:url value="/webjars/bootstrap/4.0.0/js/bootstrap.js"/>"></script>

</body>
</html>

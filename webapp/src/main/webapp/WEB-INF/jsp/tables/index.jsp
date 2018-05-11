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
                                                code="table.free"/></span>. <spring:message
                                                code="table.ask_occupy"/></h2>

                                        <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                            <input value="1" name="status" type="hidden"/>
                                            <input type="submit"
                                                   class="mdl-button mdl-button--raised mdl-js-ripple-effect btn-pink"
                                                   value="OCCUPY"/>
                                        </form>

                                    </c:if>

                                    <c:if test="${table.status == 'CleaningRequired'}">

                                        <h2><spring:message code="table.table_is"/> <span
                                                style="color: orange;"><spring:message
                                                code="table.cleaning"/></span>. <spring:message
                                                code="table.ask_ready_for_use"/></h2>

                                        <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                            <input value="2" name="status" type="hidden"/>
                                            <input type="submit"
                                                   class="mdl-button mdl-button--raised mdl-js-ripple-effect btn-pink"
                                                   value="FREE"/>
                                        </form>

                                    </c:if>

                                    <c:if test="${table.status == 'Busy'}">

                                        <h2><spring:message code="table.table_is"/> <span
                                                style="color: red;"><spring:message
                                                code="table.busy"/></span>. <spring:message
                                                code="table.ask_close_and_clean"/></h2>

                                        <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                            <input value="3" name="status" type="hidden"/>
                                            <input type="submit"
                                                   class="mdl-button mdl-button--raised mdl-js-ripple-effect btn-pink"
                                                   value="CLEAN"/>
                                        </form>


                                    </c:if>


                                    <c:if test="${table.status == 'Busy'}">
                                    <c:url value="/tables/${table.id}/set_diners" var="postPath"/>
                                    <form:form modelAttribute="tableSetDinersForm" action="${postPath}"
                                               method="post">
                                        <form:label for="diners" path="diners">Number of diners</form:label>
                                        <form:input type="number" id="diners" name="diners" value="${table.diners}"
                                                    path="diners"/>
                                        <form:errors path="diners" cssStyle="color: red;" element="p"/>
                                        <input type="submit" value="Set"/>
                                    </form:form>
                                    <div class="table-responsive">
                                        <table class="table table-striped custom-table">
                                            <thead>
                                            <tr>
                                                <th><spring:message code="dish.name"/></th>
                                                <th><spring:message code="dish.price"/></th>
                                                <th><spring:message code="dish.amount"/></th>
                                                <th><spring:message code="dish.total"/></th>
                                                <th>Actions</th>
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
                                                        <form action="<c:url value="/tables/${table.id}/add_one_dish"/>" method="post">
                                                            <button type="submit" class="btn btn-success btn-xs">
                                                                <i class="fa fa-plus"></i>
                                                            </button>
                                                            <input type="hidden" value="${dishEntry.key.id}"
                                                                   name="dishid"/>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="<c:url value="/tables/${table.id}/remove_one_dish"/>"
                                                              method="post">
                                                            <button type="submit" class="btn btn-primary btn-xs">
                                                                <i class="fa fa-minus"></i>
                                                            </button>
                                                            <input type="hidden" value="${dishEntry.key.id}"
                                                                   name="dishid"/>
                                                        </form>
                                                    </td>
                                                    <td>
                                                        <form action="<c:url value="/tables/${table.id}/remove_all_dish"/>"
                                                              method="post">
                                                            <button class="btn btn-danger btn-xs">
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
                                    </div>
                                </div>

                                <form action="<c:url value="/tables/${table.id}/add_dish"/>">
                                    <input type="submit" value="Add dish"/>
                                </form>
                                </c:if>

                            </div>
                        </div>


                        <div class="col-lg-12 p-t-20 text-center">
                            <a href="<c:url value="/tables"/>"><spring:message code="table.return_to_table_list"/></a><br>
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

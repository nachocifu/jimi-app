<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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

<body class="page-header-fixed sidemenu-closed-hidelogo page-content-white page-md header-white
             dark-sidebar-color logo-dark">
<div class="page-wrapper">
    <!-- start header -->
    <jsp:include page="/WEB-INF/jsp/header.jsp"/>
    <!-- end header -->

    <!-- start page container -->
    <div class="page-container">
        <!-- start sidebar menu -->
        <jsp:include page="/WEB-INF/jsp/sidebar.jsp"/>
        <!-- end sidebar menu -->

        <!-- start page content -->
        <div class="page-content-wrapper">
            <div class="page-content">
                <!-- start widget -->
                <div class="row">
                    <div class="col-lg-6 col-md-6 col-sm-12 col-12">

                        <div class="card card-box">
                            <div class="card-head">
                                <header><spring:message code="admin.free_tables_title"/> <c:out
                                        value="${freeTablesPercentage}"/>%
                                </header>
                            </div>
                            <div class="card-body ">
                                <div class="progress">
                                    <div class="progress-bar bg-success"
                                         style="width:<c:out value="${freeTablesPercentage}"/>%"></div>
                                </div>

                                <span class="text-small margin-top-10 full-width">
                                <spring:message code="admin.busy_tables"
                                                arguments="${busyTables}, ${totalTables}"/></span>
                            </div>
                        </div>
                    </div>

                    <div class="col-lg-6 col-md-6 col-sm-12 col-12">

                        <div class="card card-box">
                            <div class="card-head">
                                <header><spring:message code="admin.stock_status"/> <c:out
                                        value="${stockStatePercentage}"/>%
                                </header>
                            </div>
                            <div class="card-body ">
                                <div class="progress">
                                    <div class="progress-bar bg-danger"
                                         style="width:<c:out value="${stockStatePercentage}"/>%"></div>
                                </div>

                                <span class="text-small margin-top-10 full-width"><spring:message
                                        code="admin.stock_under_50"/></span>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- end widget -->
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card-box">
                            <div class="card-head">
                                <header><spring:message code="admin.latest_bills"/></header>
                            </div>
                            <div class="card-body ">
                                <div class="mdl-tabs mdl-js-tabs">
                                    <div class="mdl-tabs__panel is-active p-t-20" id="tab4-panel">
                                        <div class="table-responsive">
                                            <table class="table">
                                                <tbody>
                                                <tr>
                                                    <th><spring:message code="table.name"/></th>
                                                    <th><spring:message code="bill.date"/></th>
                                                    <th><spring:message code="admin.status"/></th>
                                                    <th><spring:message code="bill.ammount"/></th>
                                                    <th><spring:message code="bill.transaction_id"/></th>
                                                </tr>
                                                <tr>
                                                    <td>Table 1</td>
                                                    <td>05-01-2017</td>
                                                    <td><span class="label label-danger">Unpaid</span></td>
                                                    <td>1200$</td>
                                                    <td>#7234486</td>
                                                </tr>
                                                </tbody>
                                            </table>
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
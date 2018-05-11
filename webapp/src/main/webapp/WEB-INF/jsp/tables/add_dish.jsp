<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
    <jsp:include page="/WEB-INF/jsp/clean_header.jsp"/>
    <!-- end header -->


    <!-- start page content -->
    <div class="page-content-wrapper fixed">
        <div class="page-content-register-user">

            <div class="row">
                <div class="col-sm-12">
                    <div class="card-box card-box-table-index mx-auto mt-5">
                        <div class="card-head">
                            <header>Please select a dish to add to table:
                                <b>${table.name}</b><br></header>
                        </div>

                        <c:url value="/tables/${table.id}/add_dish" var="postPath"/>
                        <form:form modelAttribute="tableAddDishForm" action="${postPath}" method="post">

                            <div class="card-body">

                                <form:select id="dishid" name="dishid" path="dishid">
                                    <c:forEach items="${dishes}" var="dish">
                                        <option value="${dish.id}">${dish.name}</option>
                                    </c:forEach>
                                    <input type="number" step="1" min="1" max="100" name="amount"/>
                                    <form:errors path="amount" cssClass="formError" element="p"/>
                                </form:select>
                                <input type="submit" value="Add dish"/>

                            </div>
                        </form:form>

                        <div class="col-lg-12 p-t-20 text-center">
                            <form action="<c:url value="/tables/${table.id}"/>">
                                <input type="submit"
                                       class="mdl-button mdl-button--raised mdl-js-ripple-effect btn-pink "
                                       value="Cancel"/>
                            </form>
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
<script src="<c:url value="/resources/js/pages/material_select/getmdl-select.js"/>"></script>
</body>
</html>
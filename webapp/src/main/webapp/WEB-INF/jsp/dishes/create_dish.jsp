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
                <div class="row">
                    <div class="col-sm-12">
                        <div class="card-box mt-3">
                            <div class="card-head">
                                <header><spring:message code="dish.add_dish_header"/></header>
                            </div>
                            <c:url value="/admin/dishes/create" var="postPath"/>
                            <form:form modelAttribute="dishCreateForm" action="${postPath}" method="post">
                                <div class="card-body">
                                    <div class="col-lg-12 p-t-20">
                                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                            <form:input class="mdl-textfield__input" type="text" path="name"/>
                                            <form:label class="mdl-textfield__label" path="name"><spring:message
                                                    code="dish.name"/></form:label>
                                            <form:errors path="name" element="p" cssClass="formError"/>
                                        </div>
                                    </div>
                                    <div class="col-lg-12 p-t-20">
                                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                            <form:input class="mdl-textfield__input" type="number" path="price"
                                                        min="0" step="0.01" required="required"/>
                                            <form:label class="mdl-textfield__label" path="price">
                                                <spring:message
                                                        code="dish.price"/></form:label>
                                            <form:errors path="price" element="p" cssClass="formError"/>
                                        </div>
                                    </div>
                                    <div class="col-lg-12 p-t-20">
                                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                            <form:input class="mdl-textfield__input" type="number"
                                                        path="stock" min="1" step="0.01"/>
                                            <form:label class="mdl-textfield__label" path="stock"><spring:message
                                                    code="dish.stock"/></form:label>
                                            <form:errors path="stock" element="p" cssClass="formError"/>
                                        </div>
                                    </div>
                                    <div class="col-lg-12 p-t-20 text-center">
                                        <button type="submit"
                                                class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect m-b-10 m-r-20 rebeccapurple-color">
                                            <spring:message code="dish.add"/>
                                        </button>
                                        <a href="<c:url value="/admin/dishes/"/>"
                                           class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect m-b-10 btn-default"><spring:message
                                                code="dish.cancel"/></a>
                                        <br>
                                    </div>
                                </div>
                            </form:form>
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
<!-- Material -->
<script src="<c:url value="/webjars/material-design-lite/1.1.0/material.min.js"/>"></script>
<!-- end js include path -->
</body>
</html>
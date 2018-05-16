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
                                <header><spring:message code="user.register_header"/></header>
                            </div>

                            <c:url value="/admin/users/create" var="postPath"/>
                            <form:form modelAttribute="registerForm" action="${postPath}" method="post">

                                <div class="card-body">

                                    <div class="col-lg-12 p-t-20">
                                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                            <form:label type="text" path="username"
                                                        cssClass="mdl-textfield__label"><spring:message
                                                    code="user.username_form_label"/></form:label>
                                            <form:input type="text" path="username" cssClass="mdl-textfield__input"/>
                                            <form:errors path="username" cssClass="formError" element="p"/>
                                        </div>

                                    </div>

                                    <div class="col-lg-12 p-t-20">
                                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

                                            <form:label path="password"
                                                        cssClass="mdl-textfield__label"><spring:message
                                                    code="user.password_form_label"/></form:label>
                                            <form:input type="password" path="password"
                                                        cssClass="mdl-textfield__input"/>
                                            <form:errors path="password" cssClass="formError" element="p"/>
                                        </div>
                                    </div>

                                    <div class="col-lg-12 p-t-20">
                                        <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

                                            <form:label path="repeatPassword"
                                                        cssClass="mdl-textfield__label"><spring:message
                                                    code="user.repeat_password_form_label"/></form:label>
                                            <form:input type="password" path="repeatPassword"
                                                        cssClass="mdl-textfield__input"/>
                                            <form:errors path="repeatPassword" cssClass="formError" element="p"/>
                                        </div>
                                    </div>

                                    <div class="col-lg-12 p-t-20 text-center">
                                        <button type="submit"
                                                class="mdl-button mdl-button--raised mdl-js-ripple-effect rebeccapurple-color">
                                            <spring:message code="register_button_label"/>
                                        </button>
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
<script src="<c:url value="/resources/js/pages/material_select/getmdl-select.js"/>"></script>
</body>
</html>

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
            <div class="row justify-content-center">
                <div class="col-sm-12">
                    <div class="card-box card-box-user mx-auto mt-3">
                        <div class="card-head">
                            <header><spring:message code="login.header"/></header>
                        </div>

                        <div class="card-body">

                            <c:url value="/login" var="loginUrl"/>
                            <form action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                        <label for="username" class="mdl-textfield__label"><spring:message
                                                code="user.username_form_label"/>: </label>
                                        <input id="username" name="j_username" type="text"
                                               class="mdl-textfield__input"/>
                                    </div>
                                </div>
                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                        <label for="password" class="mdl-textfield__label"><spring:message
                                                code="user.password_form_label"/>: </label>
                                        <input id="password" name="j_password" type="password"
                                               class="mdl-textfield__input"/>
                                    </div>
                                </div>
                                <div class="col-lg-15 p-t-20">

                                    <div class="centeritems mdl-grid">
                                        <div class="mdl-layout-spacer"></div>
                                        <div class="col-md-6">

                                            <label class="mdl-checkbox mdl-js-checkbox" for="checkbox_remember_me">
                                                <input name="j_rememberme" type="checkbox" id="checkbox_remember_me"
                                                       class="mdl-checkbox__input">
                                                <span class="mdl-checkbox__label"><spring:message
                                                        code="user.ask_remember_me"/></span>
                                            </label>

                                        </div>
                                        <div class="mdl-layout-spacer"></div>
                                    </div>

                                </div>
                                <div class="col-lg-12 p-t-20 text-center">
                                    <input type="submit" value="<spring:message
                code="login.button_label"/>" class="mdl-button mdl-button--raised rebeccapurple-color">
                                </div>
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


<!-- start js include path -->
<script src="<c:url value="/webjars/jquery/3.0.0/jquery.min.js"/>"></script>
<!-- bootstrap -->
<script src="<c:url value="/webjars/bootstrap/4.0.0/js/bootstrap.js"/>"></script>
<!-- Material -->
<script src="<c:url value="/webjars/material-design-lite/1.1.0/material.min.js"/>"></script>
</body>
</html>
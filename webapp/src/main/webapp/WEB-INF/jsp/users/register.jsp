<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <title>Register</title>
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
    <link href="<c:url value="/resources/css/jimi-rest/register_user.css"/>" rel="stylesheet" type="text/css">
</head>
<body class=" page-header-fixed sidemenu-closed-hidelogo page-content-white page-md
          header-white dark-sidebar-color logo-dark">

<div class="page-wrapper">
    <!-- start header -->
    <jsp:include page="/WEB-INF/jsp/header_register.jsp"/>
    <!-- end header -->


    <!-- start page content -->
    <div class="page-content-wrapper">
        <div class="page-content-register-user">

            <div class="row">
                <div class="col-sm-12">
                    <div class="card-box card-box-register-user mx-auto">
                        <div class="card-head">
                            <header>Register User</header>
                        </div>

                        <c:url value="/create" var="postPath"/>
                        <form:form modelAttribute="registerForm" action="${postPath}" method="post">

                            <div class="card-body">

                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

                                        <form:label path="username">Username: </form:label>
                                        <form:input type="text" path="username"/>
                                        <form:errors path="username" cssClass="formError" element="p"/>
                                    </div>

                                </div>

                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

                                        <form:label path="password">Password: </form:label>
                                        <form:input type="password" path="password"/>
                                        <form:errors path="password" cssClass="formError" element="p"/>
                                    </div>
                                </div>


                                <div class="col-lg-15 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">

                                        <form:label path="repeatPassword">Repeat password: </form:label>
                                        <form:input type="password" path="repeatPassword"/>
                                        <form:errors path="repeatPassword" cssClass="formError" element="p"/>
                                    </div>
                                </div>


                                <div class="col-lg-12 p-t-20 text-center">
                                    <button type="submit"
                                            class="mdl-button mdl-button--raised mdl-js-ripple-effect btn-pink ">
                                        Register
                                    </button>
                                </div>

                            </div>

                        </form:form>

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
    <script src="<c:url value="/webjars/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"/>"></script>
    <!-- bootstrap -->
    <script src="<c:url value="/webjars/bootstrap/4.0.0/js/bootstrap.js"/>"></script>
</body>
</html>

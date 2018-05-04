<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<head>
    <meta charset="utf-8" />
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport" />
    <title>Jimi Rest</title>
    <!-- google font -->
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet" type="text/css" />
    <!-- icons -->
    <link href="<c:url value="/resources/plugins/simple-line-icons/simple-line-icons.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
    <!--bootstrap -->
    <link href="<c:url value="/webjars/bootstrap/4.0.0/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" />
    <!-- Material Design Lite CSS -->
    <link rel="stylesheet" href="<c:url value="/webjars/material-design-lite/1.1.0/material.min.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/css/material_style.css"/>" />
    <!-- animation -->
    <link href="<c:url value="/resources/css/pages/animate_page.css"/>" rel="stylesheet" />
    <!-- Template Styles -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/plugins.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/responsive.css"/>" rel="stylesheet" type="text/css" />
</head>

<body class="page-header-fixed sidemenu-closed-hidelogo page-content-white page-md
             header-white dark-sidebar-color logo-dark">
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
                    <div class="page-bar">
                        <div class="page-title-breadcrumb">
                            <div class=" pull-left">
                                <div class="page-title">Add Dish</div>
                            </div>
                            <ol class="breadcrumb page-breadcrumb pull-right">
                                <li><i class="fa fa-home"></i>&nbsp;<a class="parent-item" href="#">Home</a>&nbsp;<i class="fa fa-angle-right"></i>
                                </li>
                                <li><a class="parent-item" href="">Dishes</a>&nbsp;<i class="fa fa-angle-right"></i>
                                </li>
                                <li class="active">Add</li>
                            </ol>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="card-box">
                                <div class="card-head">
                                    <header>Add Dish</header>
                                </div>
                                <c:url value="/dishes/create" var="postPath"/>
                                <form:form modelAttribute="dishCreateForm" action="${postPath}" method="post">
                                    <div class="card-body row">
                                        <div class="col-lg-6 p-t-20">
                                            <div class = "mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                                <form:input class = "mdl-textfield__input" type = "text" path="name"/>
                                                <label class = "mdl-textfield__label">Name</label>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 p-t-20">
                                            <div class = "mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                                <form:input class = "mdl-textfield__input" type = "text"
                                                       pattern = "[0-9]*(\.[0-9]+)?" path="price"/>
                                                <label class = "mdl-textfield__label">Price</label>
                                                <span class = "mdl-textfield__error">Number required!</span>
                                            </div>
                                        </div>
                                        <div class="col-lg-6 p-t-20">
                                            <div class = "mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                                <form:input class = "mdl-textfield__input" type = "text"
                                                            pattern = "[0-9]+" path="stock"/>
                                                <label class = "mdl-textfield__label">Amount</label>
                                                <span class = "mdl-textfield__error">Number required!</span>
                                            </div>
                                        </div>
                                        <div class="col-lg-12 p-t-20 text-center">
                                            <button type="submit" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect m-b-10 m-r-20 btn-pink">Add</button>
                                            <button type="button" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect m-b-10 btn-default">Cancel</button>
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
    <script src="<c:url value="/webjars/popper/popper.min.js"/>"></script>
    <script src="<c:url value="/webjars/jQuery-slimScroll/1.3.8/jquery.slimscroll.min.js"/>"></script>
    <!-- bootstrap -->
    <script src="<c:url value="/webjars/bootstrap/4.0.0/js/bootstrap.js"/>"></script>
    <!-- Common js-->
    <script src="<c:url value="/resources/js/app.js"/>" ></script>
    <script src="<c:url value="/resources/js/layout.js"/>" ></script>
    <!-- Material -->
    <script src="<c:url value="/webjars/material-design-lite/1.1.0/material.min.js"/>"></script>
    <script src="<c:url value="/resources/js/pages/material_select/getmdl-select.js"/>" ></script>
    <!-- animation -->
    <script src="<c:url value="/resources/js/pages/ui/animations.js"/>" ></script>
    <!-- end js include path -->
</body>
</html>

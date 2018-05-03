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
    <link href="<c:url value="/resources/plugins/font-awesome/css/font-awesome.min.css"/>" rel="stylesheet" type="text/css"/>
    <!--bootstrap -->
    <link href="<c:url value="/resources/plugins/bootstrap/css/bootstrap.min.css"/>" rel="stylesheet" type="text/css" />
    <!-- Material Design Lite CSS -->
    <link rel="stylesheet" href="<c:url value="/resources/plugins/material/material.min.css"/>" />
    <link rel="stylesheet" href="<c:url value="/resources/css/material_style.css"/>" />
    <!-- animation -->
    <link href="<c:url value="/resources/css/pages/animate_page.css"/>" rel="stylesheet" />
    <!-- Template Styles -->
    <link href="<c:url value="/resources/css/style.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/plugins.min.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/responsive.css"/>" rel="stylesheet" type="text/css" />
    <link href="<c:url value="/resources/css/theme-color.css"/>" rel="stylesheet" type="text/css" />
    <!-- favicon -->
    <link rel="shortcut icon" href="assets/img/favicon.ico" />

<body class="page-header-fixed sidemenu-closed-hidelogo page-content-white page-md
             header-white dark-sidebar-color logo-dark">
    <div class="page-wrapper">
        <!-- start sidebar menu -->
        <div class="sidebar-container">
            <div class="sidemenu-container navbar-collapse collapse fixed-menu">
                <div id="remove-scroll">
                    <ul class="sidemenu page-header-fixed p-t-20" data-keep-expanded="false" data-slide-speed="200">
                        <li class="sidebar-toggler-wrapper hide">
                            <div class="sidebar-toggler">
                                <span></span>
                            </div>
                        </li>
                        <li class="sidebar-user-panel">
                            <div class="user-panel">
                                <div class="row">
                                    <div class="sidebar-userpic">
                                        <img src="assets/img/dp.jpg" class="img-responsive" alt=""> </div>
                                </div>
                                <div class="profile-usertitle">
                                    <div class="sidebar-userpic-name"> Martin Capparelli </div>
                                    <div class="profile-usertitle-job"> Manager </div>
                                </div>
                            </div>
                        </li>
                        <li class="nav-item start">
                            <a href="#" class="nav-link nav-toggle">
                                <i class="material-icons">dashboard</i>
                                <span class="title">Dashboard</span>
                                <span class="arrow"></span>
                            </a>
                            <ul class="sub-menu">
                                <li class="nav-item">
                                    <a href="#" class="nav-link ">
                                        <span class="title">Dashboard 1</span>
                                    </a>
                                </li>
                                <li class="nav-item ">
                                    <a href="#" class="nav-link ">
                                        <span class="title">Dashboard 2</span>
                                    </a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
        </div>
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
                                                   pattern = "-?[0-9]*(\.[0-9]+)?" path="price"/>
                                            <label class = "mdl-textfield__label">Price</label>
                                            <span class = "mdl-textfield__error">Number required!</span>
                                        </div>
                                    </div>
                                    </div>
                                    <div class="col-lg-12 p-t-20 text-center">
                                        <button type="button submit" class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect m-b-10 m-r-20 btn-pink">Add</button>
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
        <!-- end page container -->
    </div>
<!-- start js include path -->
<script src="<c:url value="/resources/plugins/jquery-slimscroll/jquery.slimscroll.min.js"/>"></script>
<!-- bootstrap -->
<script src="<c:url value="/resources/plugins/bootstrap/js/bootstrap.min.js"/>"></script>
<!-- Common js-->
<script src="<c:url value="/resources/js/app.js"/>" ></script>
<script src="<c:url value="/resources/js/layout.js"/>" ></script>
<script src="<c:url value="/resources/js/theme-color.js"/>" ></script>
<!-- Material -->
<script src="<c:url value="/resources/plugins/material/material.min.js"/>"></script>
<script src="<c:url value="/resources/js/pages/material_select/getmdl-select.js"/>" ></script>
<!-- animation -->
<script src="<c:url value="/resources/js/pages/ui/animations.js"/>" ></script>
<!-- end js include path -->

</body>
</html>

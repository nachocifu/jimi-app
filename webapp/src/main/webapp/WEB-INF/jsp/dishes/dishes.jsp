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
                                <div class="page-title">Dishes</div>
                            </div>
                            <ol class="breadcrumb page-breadcrumb pull-right">
                                <li><i class="fa fa-home"></i>&nbsp;<a class="parent-item" href="index.html">Home</a>&nbsp;<i class="fa fa-angle-right"></i>
                                </li>
                                <li><a class="parent-item" href="">Data Tables</a>&nbsp;<i class="fa fa-angle-right"></i>
                                </li>
                                <li class="active">Basic Tables</li>
                            </ol>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-md-12">
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="card card-topline-purple">
                                        <div class="card-head">
                                            <header>Dishes</header>
                                        </div>
                                        <div class="card-body">
                                            <div class="table-responsive">
                                                <table class="table table-striped custom-table table-hover">
                                                    <thead>
                                                    <tr>
                                                        <th>Name</th>
                                                        <th>Price</th>
                                                        <th>Stock</th>
                                                        <th>Status</th>
                                                        <th>Action</th>
                                                    </tr>
                                                    </thead>
                                                    <tbody>
                                                    <c:forEach items="${dishes}" var="dish">
                                                    <tr>
                                                        <td><c:out value="${dish.name}"/></td>
                                                        <td>$<c:out value="${dish.price}"/></td>
                                                        <td><c:out value="${dish.stock}"/></td>
                                                        <td><span class="label label-warning label-mini">Available</span>
                                                        </td>
                                                        <td>
                                                            <form action="/dishes/setstock">
                                                                <input type="hidden" value="${dish.id}" name="dishid">
                                                                <input type="hidden" value="${dish.stock+1}" name="stock">
                                                                <button type="submit" class="btn btn-success btn-xs">
                                                                    <i class="fa fa-plus"></i>
                                                                </button>
                                                            </form>
                                                        </td>
                                                        <td>
                                                            <form action="/dishes/setstock">
                                                                <input type="hidden" value="${dish.id}" name="dishid">
                                                                <input type="hidden" value="${dish.stock-1}" name="stock">
                                                                <button type="submit" class="btn btn-primary btn-xs">
                                                                    <i class="fa fa-minus"></i>
                                                                </button>
                                                            </form>
                                                        </td>
                                                        <td>
                                                            <button class="btn btn-danger btn-xs">
                                                                <i class="fa fa-trash-o "></i>
                                                            </button>
                                                        </td>
                                                    </tr>
                                                    </c:forEach>
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


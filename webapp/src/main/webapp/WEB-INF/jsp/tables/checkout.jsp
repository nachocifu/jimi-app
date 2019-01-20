<%@ page import="edu.itba.paw.jimi.models.TableStatus" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="BusyCode" value="<%=TableStatus.BUSY.ordinal()%>"/>
<c:set var="PayingCode" value="<%=TableStatus.PAYING.ordinal()%>"/>


<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, initial-scale=1" name="viewport"/>
    <title>Jimi Rest</title>
    <!-- google font -->
    <link href="<c:url value="https://fonts.googleapis.com/css?family=Poppins:300,400,500,600,700"/>" rel="stylesheet"
          type="text/css"/>
    <!-- icons -->
    <link href="<c:url value="/webjars/font-awesome/4.7.0/css/font-awesome.min.css"/>" rel="stylesheet"
          type="text/css"/>

    <!--Material-->
    <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"
          integrity="sha256-e22BQKCF7bb/h/4MFJ1a4lTRR2OuAe8Hxa/3tgU5Taw=" crossorigin="anonymous"/>

    <%--<!-- Compiled and minified CSS -->--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/tables/checkout.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="table-container">
    <div class="card">
        <div class="card-content">
            <div class="row">
                <div class="col s6">
                    <h5>
                        <span><b><spring:message code="checkout.receipt"/></b> ${table.order.diners} <spring:message
                                code="checkout.diners"/></span>
                    </h5>
                </div>
                <div class="col s6">
                    <h5>
                        <span class="pull-right"><fmt:formatDate value="${table.order.closedAt}"
                                                                 pattern="yyyy-MM-dd HH:mm"/></span>
                    </h5>
                </div>
            </div>
            <div class="row">
                <table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp">
                    <thead>
                    <tr>
                        <th class="text-center"><spring:message
                                code="checkout.table.description"/></th>
                        <th class="text-center"><spring:message
                                code="checkout.table.charges"/></th>
                        <th class="text-center"><spring:message
                                code="checkout.table.items"/></th>
                        <th class="text-right"><spring:message
                                code="checkout.table.amount"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${table.order.dishes}" var="dish">
                        <tr>
                            <td class="text-center">${dish.key.name}</td>
                            <td class="text-center">${dish.key.price}</td>
                            <td class="text-center">${dish.value}</td>
                            <td class="text-right"><fmt:formatNumber
                                    value="${dish.value * dish.key.price}"
                                    maxFractionDigits="2"/></td>
                        </tr>
                    </c:forEach>
                    <tr></tr>
                    <tr>
                        <td class="text-center"></td>
                        <td class="text-center"></td>
                        <td class="text-right"><h5><b><spring:message code="checkout.table.total"/> :</b></h5></td>
                        <td class="text-right"><h5>$${total}</h5></td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="row">
                <div class="col s4">

                    <!-- Modal Trigger -->
                    <button data-target="modal1" class="btn modal-trigger"><spring:message code="checkout.charged"/></button>

                    <!-- Modal Structure -->
                    <div id="modal1" class="modal">
                        <div class="modal-content">
                            <h4><spring:message code="table.sure_charged"/></h4>
                            <form action="<c:url value="/tables/${table.id}/status"/>" method="POST">
                                <input value="1" name="status" type="hidden"/>
                                <a class="modal-close btn blue-gray"><spring:message code="table.back"/></a>
                                <input type="submit"
                                       class="btn blue-gray"
                                       value="<spring:message code="checkout.charged"/>"/>
                            </form>
                        </div>
                    </div>
                </div>

                <div class="col s4">
                    <button onclick="window.print();" class="waves-effect waves-light btn" type="button">
                                            <span><i class="fa fa-print"></i> <spring:message
                                                    code="checkout.print"/></span>
                    </button>
                </div>
                <div class="col s4">
                    <a href="<c:url value="/web/tables/"/>">
                        <button class="waves-effect waves-light btn" type="button">
                                                <span><i class="fa fa-list"></i> <spring:message
                                                        code="checkout.tables"/></span>
                        </button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>



<!-- start js include path -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>



<!-- Compiled and minified JavaScript -->

<!-- end js include path -->

<!-- start js include path -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<!-- end js include path -->



<script>
    $(document).ready(function () {
        $('.modal').modal();
    });
</script>

<script defer src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>

</body>
</html>


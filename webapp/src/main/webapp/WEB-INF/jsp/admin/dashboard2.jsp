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

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/admin/dashboard.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>">
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

<div class="table-container">
    <div class="card">
        <div class="card-content">
            <div class="row">

                <c:choose>
                    <c:when test="${totalTables <= 0}">
                        <div class="alert alert-info text-center">
                            <strong><spring:message code="ouch"/></strong>
                            <spring:message code="table.no_tables"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col s6">
                            <div id="table-status-pie"></div>
                        </div>
                    </c:otherwise>
                </c:choose>

                <c:choose>
                    <c:when test="${monthOrderTotals.size() <= 0}">
                        <div class="alert alert-info text-center">
                            <spring:message code="order.no_order"/>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="col s6">
                            <div id="monthly-order-total-time-series"></div>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
</div>


<!-- start js include path -->
<script>

    var tableStatusPie = {
        free: {
            title: "<spring:message code="table.free"/>",
            count: "${freeTables}"
        },
        paying: {
            title: "<spring:message code="table.paying"/>",
            count: "${payingTables}"
        },
        busy: {
            title: "<spring:message code="table.busy"/>",
            count: "${busyTables}"
        },
        plotTitle: "<spring:message code="dashboard.current_table_report"/>"
    };

    var monthlyOrderTotalTimeSeries = {
        values: {
            x: [<c:forEach items="${monthOrderTotals}" var="v" varStatus="loop">
                '${v.key}'${!loop.last ? ',' : ''}
                </c:forEach>],
            y: [<c:forEach items="${monthOrderTotals}" var="v" varStatus="loop">
                '${v.value}'${!loop.last ? ',' : ''}
                </c:forEach>]
        },
        length: ${monthOrderTotals.size()},
        plotTitle: "<spring:message code="dashboard.monthly_order_total_time_series"/>"
    };

</script>
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script defer src="https://cdn.plot.ly/plotly-latest.min.js"></script>
<script defer src="<c:url value="/resources/js/admin/dashboard.js"/>"></script>
<!-- end js include path -->
</body>
</html>


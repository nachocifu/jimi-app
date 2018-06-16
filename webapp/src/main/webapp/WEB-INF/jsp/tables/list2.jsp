<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


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
    <link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/tables/list.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon"
          href="${pageContext.request.contextPath}/resources/img/jimi-rest/favicon.ico"/>
</head>

<body >

    <jsp:include page="/WEB-INF/jsp/header2.jsp"/>

    <%-- TODO hay que hacer responsive esta tabla --%>
    <div class="table-container">
        <c:choose>
            <c:when test="${tables.size() > 0}">
                <table  class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp">
                    <thead>
                    <tr>
                        <th><spring:message code="table.name"/></th>
                        <th><spring:message code="table.diners"/></th>
                        <th><spring:message code="table.status"/></th>
                        <th class=""></th>
                    </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${tables}" var="table">
                            <tr>
                                <td><c:out value="${table.name}"/></td>
                                <td><c:out value="${table.order.diners}"/></td>

                                <c:choose>
                                    <c:when test="${table.status.toString() == 'FREE'}">
                                        <td><span
                                                class="label label-success label-mini"><spring:message
                                                code="table.free"/></span>
                                        </td>
                                    </c:when>
                                    <c:when test="${table.status.toString() == 'BUSY'}">
                                        <td><span
                                                class="label label-danger label-mini"><spring:message
                                                code="table.busy"/></span>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td> <span
                                                class="label label-warning label-mini"><spring:message
                                                code="table.paying"/></span>
                                        </td>
                                    </c:otherwise>
                                </c:choose>
                                <td>
                                    <a href=<c:url
                                            value="/tables/${table.id}"/>>
                                        <i class="fa fa-edit fa-lg"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </c:when>
            <c:otherwise>
                <div class="alert alert-danger text-center">
                    <strong><spring:message code="ouch"/></strong> <spring:message
                        code="table.no_tables"/>
                </div>
            </c:otherwise>
        </c:choose>
    </div>



<!-- start js include path -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<!-- end js include path -->
</body>
</html>


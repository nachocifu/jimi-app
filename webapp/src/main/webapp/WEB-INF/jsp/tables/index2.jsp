<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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
    <%--<link rel="stylesheet" href="https://code.getmdl.io/1.3.0/material.indigo-pink.min.css">--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"
          integrity="sha256-e22BQKCF7bb/h/4MFJ1a4lTRR2OuAe8Hxa/3tgU5Taw=" crossorigin="anonymous"/>

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/tables/index.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon"
          href="${pageContext.request.contextPath}/resources/img/jimi-rest/favicon.ico"/>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header2.jsp"/>

<%-- TODO hay que hacer responsive esta tabla --%>
<div class="table-container">
    <div class="card">
        <div class="card-content">
            <%--TODO--%>
            <c:if test="${table.status == 'FREE'}">
                <h2><spring:message code="table.table_is"/>
                    <strong>
                                                    <span style="color: green;"><spring:message
                                                            code="table.free"/>.</span>
                    </strong>
                </h2>
                <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                    <input value="1" name="status" type="hidden"/>
                    <input type="submit"
                           class="btn btn-default rebeccapurple-color"
                           value="<spring:message code="table.occupy"/>"/>
                </form>
            </c:if>
            <c:if test="${table.status == 'BUSY'}">
                <h2>
                    <strong><span>${table.name}</span></strong>
                </h2>
                <table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp">
                    <thead>
                    <tr>
                        <th><spring:message code="dish.name"/></th>
                        <th><spring:message code="dish.price"/></th>
                        <th><spring:message code="dish.amount"/></th>
                        <th><spring:message code="dish.total"/></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${dishes}" var="dishEntry">
                        <tr>
                            <td><c:out value="${dishEntry.key.name}"/></td>
                            <td><c:out value="${dishEntry.key.price}"/></td>
                            <td><c:out value="${dishEntry.value}"/></td>
                            <td>
                                <fmt:formatNumber value="${dishEntry.value * dishEntry.key.price}"
                                                  maxFractionDigits="2"/>
                            </td>

                            <td>
                                <div class="row">
                                    <div class="col s3">
                                        <c:choose>
                                            <c:when test="${dishEntry.key.stock != 0}">
                                                <form action="<c:url value="/tables/${table.id}/add_one_dish"/>"
                                                      method="post"
                                                      class="form-with-buttons">
                                                    <button type="submit"
                                                            class="btn btn-success btn-xs">
                                                        <i class="fa fa-plus"></i>
                                                    </button>
                                                    <input type="hidden"
                                                           value="${dishEntry.key.id}"
                                                           name="dishid"/>
                                                </form>
                                            </c:when>
                                        </c:choose>
                                    </div>
                                    <div class="col s3">
                                        <form action="<c:url value="/tables/${table.id}/remove_one_dish"/>"
                                              method="post" class="form-with-buttons">
                                            <button type="submit"
                                                    class="btn btn-primary btn-xs">
                                                <i class="fa fa-minus"></i>
                                            </button>
                                            <input type="hidden" value="${dishEntry.key.id}"
                                                   name="dishid"/>
                                        </form>
                                    </div>
                                    <div class="col s3">
                                        <form action="<c:url value="/tables/${table.id}/remove_all_dish"/>"
                                              method="post" class="form-with-buttons">
                                            <button type="submit"
                                                    class="btn btn-danger btn-xs">
                                                <i class="fa fa-trash-o "></i>
                                            </button>
                                            <input type="hidden" value="${dishEntry.key.id}"
                                                   name="dishid"/>
                                        </form>
                                    </div>
                                </div>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <div class="card-action">
                    <form action="<c:url value="/tables/${table.id}/add_dish"/>">
                        <input type="submit" value="<spring:message code="table.add_dish"/>"
                               class="btn btn-default"/>
                    </form>
                    <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                        <input value="3" name="status" type="hidden"/>
                        <input type="submit"
                               class="btn btn-default rebeccapurple-color"
                               value="<spring:message code="table.charge_caps"/>"/>
                    </form>
                    <%-- TODO manejo de diners --%>
                    <button>add diner</button>
                    <button>rest diner</button>
                    <a href="<c:url value="/tables/"/>"
                       class="btn blue-gray waves-effect"><spring:message
                            code="table.return_to_table_list"/></a>
                </div>
            </c:if>
        </div>
    </div>
</div>


<!-- start js include path -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<!-- end js include path -->
</body>
</html>


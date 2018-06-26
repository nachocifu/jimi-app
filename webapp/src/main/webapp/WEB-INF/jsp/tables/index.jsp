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
    <%--<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.100.2/css/materialize.min.css"--%>
    <%--integrity="sha256-e22BQKCF7bb/h/4MFJ1a4lTRR2OuAe8Hxa/3tgU5Taw=" crossorigin="anonymous"/>--%>

    <%--<!-- Compiled and minified CSS -->--%>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/css/materialize.min.css">

    <link href="<c:url value="/resources/css/header.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/common.css"/>" rel="stylesheet" type="text/css">
    <link href="<c:url value="/resources/css/tables/index.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/header.jsp"/>

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
                        <%--TODO deberiamos sacar estos hardcodeos de numeros en value--%>
                    <input value="${BusyCode}" name="status" type="hidden"/>
                    <input type="submit"
                           class="btn btn-default rebeccapurple-color"
                           value="<spring:message code="table.occupy"/>"/>
                </form>
            </c:if>
            <c:if test="${table.status == 'BUSY'}">
                <div class="row">
                    <h2>
                        <strong><span>${table.name}</span></strong>
                    </h2>
                </div>
                <div class="row">

                    <div class="col s4">
                        <form action="<c:url value="/tables/${table.id}/subtract_diner"/>" method="post">
                                <%--TODO sacar hardcoded valude del input--%>
                            <input type="submit" value="-"
                                   class="btn btn-default pull-right <c:if test="${diners == 0}">disabled</c:if>">
                        </form>
                    </div>
                    <div class="col s4">
                        <h4>
                            (${diners} <spring:message code="table.diners"/>)
                        </h4>
                    </div>
                    <div class="cols s4">
                        <form action="<c:url value="/tables/${table.id}/add_diner"/>" method="post">
                            <input type="submit" value="+"
                                   class="btn btn-default pull-left"/>
                        </form>
                    </div>
                </div>

                <div class="card-action">
                    <div class="row">
                        <div class="col s4">
                            <form action="<c:url value="/tables/${table.id}/add_dish"/>">
                                <input type="submit" value="<spring:message code="table.add_dish"/>"
                                       class="btn btn-default"/>
                            </form>
                        </div>
                        <div class="col s4">
                            <form action="<c:url value="/tables/${table.id}/status"/>" method="post">
                                <input value="${PayingCode}" name="status" type="hidden"/>
                                <input type="submit"
                                       class="btn blue-gray"
                                       value="<spring:message code="table.charge_caps"/>"/>
                            </form>
                        </div>
                        <div class="col s4">
                            <a href="<c:url value="/tables/"/>"
                               class="btn blue-gray"><spring:message
                                    code="table.return_to_table_list"/>
                            </a>
                        </div>
                    </div>
                </div>
                <c:choose>
                    <c:when test="${table.order.dishes.size() > 0}">
                        <table class="mdl-data-table mdl-js-data-table  mdl-shadow--2dp">
                            <thead>
                            <tr>
                                <th><spring:message code="dish.name"/></th>
                                <th><spring:message code="dish.price"/></th>
                                <th><spring:message code="dish.amount"/></th>
                                <th><spring:message code="dish.total"/></th>
                                <th></th>
                                <th></th>
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
                                        $<fmt:formatNumber value="${dishEntry.value * dishEntry.key.price}"
                                                           maxFractionDigits="2"/>
                                    </td>
                                    <td>
                                        <c:if test="${dishEntry.key.stock != 0}">
                                            <form action="<c:url value="/tables/${table.id}/add_one_dish"/>"
                                                  method="post"
                                                  class="form-with-buttons">
                                                <a class="btn btn-success btn-xs"
                                                   onclick="M.toast({html: '<spring:message code="table.added"/> ${dishEntry.key.name}'})">
                                                    <i class="fa fa-plus"></i>
                                                </a>
                                                <input type="hidden"
                                                       value="${dishEntry.key.id}"
                                                       name="dishid"/>
                                            </form>
                                        </c:if>
                                    </td>
                                    <td>
                                        <form action="<c:url value="/tables/${table.id}/remove_one_dish"/>"
                                              method="post" class="form-with-buttons" id="remove-dish-form">
                                                <%--<button type="submit"--%>
                                                <%--class="btn btn-primary btn-xs">--%>
                                                <%--<i class="fa fa-minus"></i>--%>
                                                <%--</button>--%>
                                            <%--<button class="btn btn-success btn-xs"--%>
                                               <%--onclick="myFunction()">--%>
                                                <%--<i class="fa fa-minus"></i>--%>
                                            <%--</button>--%>
                                                    <a id="removed-dish-a" class="btn btn-success btn-xs"
                                                       onclick="M.toast({html: '<spring:message code="table.removed"/> ${dishEntry.key.name}'})">
                                                        <i class="fa fa-minus"></i>
                                                    </a>
                                            <input type="hidden" value="${dishEntry.key.id}"
                                                   name="dishid"/>
                                        </form>
                                    </td>
                                    <td>
                                        <form action="<c:url value="/tables/${table.id}/remove_all_dish"/>"
                                              method="post" class="form-with-buttons">
                                            <button type="submit"
                                                    class="btn btn-danger btn-xs">
                                                <i class="fa fa-trash-o "></i>
                                            </button>
                                            <input type="hidden" value="${dishEntry.key.id}"
                                                   name="dishid"/>
                                        </form>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </c:when>
                    <c:otherwise>
                        <div class="alert alert-info text-center">
                            <spring:message code="table.no_dishes"/>
                        </div>
                    </c:otherwise>
                </c:choose>
            </c:if>
        </div>
    </div>
</div>

<!-- start js include path -->
<script>
    document.getElementById("removed-dish-a").onclick = function() {
        M.toast({html: '<spring:message code="table.removed"/> ${dishEntry.key.name}'})
        document.getElementById("remove-dish-form").submit();
    }

</script>
<!-- Compiled and minified JavaScript -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0-rc.2/js/materialize.min.js"></script>

<!-- end js include path -->
</body>
</html>


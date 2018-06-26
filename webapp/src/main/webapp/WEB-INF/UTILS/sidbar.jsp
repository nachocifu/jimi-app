<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="sidebar-wrapper">
    <ul class="nav">
        <li class="nav-item  ">
            <a class="nav-link" href="<c:url value="/admin/"/>">
                <i class="material-icons">dashboard</i>
                <p><spring:message code="sidebar.dashboard"/></p>
            </a>
        </li>
        <li class="nav-item ">
            <a class="nav-link" href="<c:url value="/tables/"/>">
                <i class="material-icons">widgets</i>
                <p><spring:message code="sidebar.table_list"/></p>
            </a>
        </li>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li class="nav-item ">
            <a class="nav-link" href="<c:url value="/tables/register"/>">
                <i class="material-icons">library_add</i>
                <p><spring:message code="sidebar.new_table"/></p>
            </a>
        </li>
        </sec:authorize>
        <li class="nav-item ">
            <a class="nav-link" href="<c:url value="/admin/dishes/"/>">
                <i class="material-icons">fastfood</i>
                <p><spring:message code="sidebar.dish_list"/></p>
            </a>
        </li>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
        <li class="nav-item ">
            <a class="nav-link" href="<c:url value="/admin/dishes/create"/>">
                <i class="material-icons">playlist_add</i>
                <p><spring:message code="sidebar.new_dish"/></p>
            </a>
        </li>

        <li class="nav-item ">
            <a class="nav-link" href="<c:url value="/admin/users/"/>">
                <i class="material-icons">person</i>
                <p><spring:message code="sidebar.user_list"/></p>
            </a>
        </li>
        <li class="nav-item ">
            <a class="nav-link" href="<c:url value="/admin/users/register/"/>">
                <i class="material-icons">group_add</i>
                <p><spring:message code="sidebar.new_user"/></p>
            </a>
        </li>
        </sec:authorize>
        <li class="nav-item ">
            <a class="nav-link" href="<c:url value="/kitchen/"/>">
                <i class="material-icons">kitchen</i>
                <p><spring:message code="sidebar.kitchen"/></p>
            </a>
        </li>
    </ul>
</div>
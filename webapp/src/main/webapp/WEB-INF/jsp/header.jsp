<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<header>
    <nav>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div>
                <a><span>Admin </span>1</a>
                <div>
                    <a href="<c:url value="/admin/"/>" class="nav-link nav-toggle">
                        <i class="fa fa-columns"></i>
                        <span class="title"><spring:message code="sidebar.dashboard"/></span>
                    </a>
                    <a href="<c:url value="/admin/bills"/>" class="nav-link ">
                        <i class="fa fa-list"></i>
                        <span class="title"><spring:message code="sidebar.bills_list"/></span>
                    </a>
                </div>
            </div>
        </sec:authorize>
        <div>
            <a><span><spring:message code="sidebar.tables"/></span></a>
            <div>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <a href="<c:url value="/tables/register"/>" class="nav-link ">
                        <i class="fa fa-plus"></i>
                        <span class="title"><spring:message code="sidebar.new_table"/></span>
                    </a>
                </sec:authorize>
                <a href="<c:url value="/tables/"/>" class="nav-link ">
                    <i class="fa fa-list"></i>
                    <span class="title"><spring:message code="sidebar.table_list"/></span>
                </a>
            </div>
        </div>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div>
                <a><i class="fa fa-bars"></i><span class="title"><spring:message code="sidebar.dishes"/></span></a>
                <div>
                    <a href="<c:url value="/admin/dishes/create"/>" class="nav-link ">
                        <i class="fa fa-plus"></i>
                        <span class="title"><spring:message code="sidebar.new_dish"/></span>
                    </a>
                    <a href="<c:url value="/admin/dishes/"/>" class="nav-link ">
                        <i class="fa fa-list"></i>
                        <span class="title"><spring:message code="sidebar.dish_list"/></span>
                    </a>
                </div>
            </div>
        </sec:authorize>
        <sec:authorize access="hasRole('ROLE_ADMIN')">
            <div>
                <a><i class="fa fa-users"></i>
                    <span class="title"><spring:message code="sidebar.users"/></span></a>
                <div>
                    <a href="<c:url value="/admin/users/register/"/>" class="nav-link ">
                        <i class="fa fa-plus"></i>
                        <span class="title"><spring:message code="sidebar.new_user"/></span>
                    </a>
                    <a href="<c:url value="/admin/users/"/>" class="nav-link ">
                        <i class="fa fa-list"></i>
                        <span class="title"><spring:message code="sidebar.user_list"/></span>
                    </a>
                </div>
            </div>
        </sec:authorize>
    </nav>
</header>
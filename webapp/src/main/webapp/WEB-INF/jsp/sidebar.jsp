<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="sidebar-container">
    <div class="sidemenu-container navbar-collapse collapse fixed-menu">
        <div id="remove-scroll">
            <ul class="sidemenu page-header-fixed p-t-20" data-keep-expanded="false" data-auto-scroll="true"
                data-slide-speed="200">
                <li class="sidebar-toggler-wrapper hide">
                    <div class="sidebar-toggler">
                        <span></span>
                    </div>
                </li>
                <li class="sidebar-user-panel">
                    <div class="user-panel">
                        <div class="profile-usertitle">
                            <div class="sidebar-userpic-name"> John Deo</div>
                            <div class="profile-usertitle-job"> Manager</div>
                        </div>
                    </div>
                </li>
<sec:authorize access="hasRole('ROLE_ADMIN')">
                <li class="nav-item">
                    <a href="<c:url value="/admin/"/>" class="nav-link nav-toggle">
                        <i class="fa fa-columns"></i>
                        <span class="title"><spring:message code="sidebar.dashboard"/></span>
                    </a>
                </li>
</sec:authorize>
                <li class="nav-item">
                    <a href="#" class="nav-link nav-toggle">
                        <i class="fa fa-home"></i>
                        <span class="title"><spring:message code="sidebar.tables"/></span>
                    </a>
                    <ul class="sub-menu">
                        <li class="nav-item">
                            <a href="<c:url value="/tables/register"/>" class="nav-link ">
                                <i class="fa fa-plus"></i>
                                <span class="title"><spring:message code="sidebar.new_table"/></span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="<c:url value="/tables/"/>" class="nav-link ">
                                <i class="fa fa-list"></i>
                                <span class="title"><spring:message code="sidebar.table_list"/></span>
                            </a>
                        </li>
                    </ul>
                </li>
<sec:authorize access="hasRole('ROLE_ADMIN')">
                <li class="nav-item">
                    <a href="#" class="nav-link nav-toggle">
                        <i class="fa fa-bars"></i>
                        <span class="title"><spring:message code="sidebar.dishes"/></span>
                    </a>
                    <ul class="sub-menu">
                        <li class="nav-item">
                            <a href="<c:url value="/admin/dishes/create"/>" class="nav-link ">
                                <i class="fa fa-plus"></i>
                                <span class="title"><spring:message code="sidebar.new_dish"/></span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="<c:url value="/admin/dishes/"/>" class="nav-link ">
                                <i class="fa fa-list"></i>
                                <span class="title"><spring:message code="sidebar.dish_list"/></span>
                            </a>
                        </li>
                    </ul>
                </li>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_ADMIN')">
                <li class="nav-item">
                    <a href="#" class="nav-link nav-toggle">
                        <i class="fa fa-users"></i>
                        <span class="title"><spring:message code="sidebar.users"/></span>
                    </a>
                    <ul class="sub-menu">
                        <li class="nav-item">
                            <a href="<c:url value="/admin/users/register/"/>" class="nav-link ">
                                <i class="fa fa-plus"></i>
                                <span class="title"><spring:message code="sidebar.new_user"/></span>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a href="<c:url value="/admin/users/"/>" class="nav-link ">
                                <i class="fa fa-list"></i>
                                <span class="title"><spring:message code="sidebar.user_list"/></span>
                            </a>
                        </li>
                    </ul>
                </li>
</sec:authorize>
                <li class="nav-item">
                    <a href="<c:url value="/logout"/>" class="nav-link">
                        <i class="fa fa-sign-out"></i>
                        <span class="title"><spring:message code="sidebar.logout"/></span>
                    </a>
                </li>
            </ul>
        </div>
    </div>
</div>

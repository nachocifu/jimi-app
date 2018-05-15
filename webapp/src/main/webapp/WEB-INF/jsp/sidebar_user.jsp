<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
                <li class="nav-item">
                    <a href="#" class="nav-link nav-toggle">
                        <i class="fa fa-home"></i>
                        <span class="title"><spring:message code="sidebar.tables"/></span>
                    </a>
                    <ul class="sub-menu">
                        <li class="nav-item">
                            <a href="<c:url value="/tables/"/>" class="nav-link ">
                                <i class="fa fa-list"></i>
                                <span class="title"><spring:message code="sidebar.table_list"/></span>
                            </a>
                        </li>
                    </ul>
                </li>
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

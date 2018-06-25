<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:url value="/login" var="loginUrl"/>

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

    <link href="<c:url value="/resources/css/users/login.css"/>" rel="stylesheet" type="text/css">
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="<c:url value="/resources/img/jimi-rest/favicon.ico"/>"/>
</head>
<body>

<div class="materialContainer">


    <div class="box">

        <div class="title">JIMI</div>
        <form id="login-form" action="${loginUrl}" method="post" enctype="application/x-www-form-urlencoded">
            <div class="input">
                <label for="username"><spring:message code="user.username_form_label"/></label>
                <input id="username" name="j_username" type="text"/>
                <span class="spin"></span>
            </div>

            <div class="input">
                <label for="password">Password</label>
                <input id="password" name="j_password" type="password"/>
                <span class="spin"></span>
            </div>
            <div class="button login">
                <button><span>GO</span> <i class="fa fa-check"></i></button>
            </div>
        </form>

    </div>

</div>




<!-- Material -->
<script defer src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
<script defer src="https://code.getmdl.io/1.3.0/material.min.js"></script>
<script defer src="<c:url value="/resources/js/login.js"/>"></script>

</body>
</html>
<!-- start page content -->
<div class="page-container">
    <!-- start sidebar menu -->
    <jsp:include page="/WEB-INF/jsp/sidebar.jsp"/>
    <!-- end sidebar menu -->

    <!-- start page content -->
    <div class="page-content-wrapper">
        <div class="page-content">

            <div class="row">
                <div class="col-sm-12">
                    <div class="card-box">
                        <div class="card-head">
                            <header><spring:message code="dish.add_dish_header"/></header>
                        </div>
                        <c:url value="/dishes/create" var="postPath"/>
                        <form:form modelAttribute="dishCreateForm" action="${postPath}" method="post">
                            <div class="card-body">
                                <div class="col-lg-6 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                        <form:input class="mdl-textfield__input" type="text" path="name"/>
                                        <label class="mdl-textfield__label"><spring:message code="dish.name"/></label>
                                    </div>
                                </div>
                                <div class="col-lg-6 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                        <form:input class="mdl-textfield__input" type="text" pattern="[0-9]*(\.[0-9]+)?" path="price"/>
                                        <label class="mdl-textfield__label"><spring:message code="dish.price"/></label>
                                        <span class="mdl-textfield__error"><spring:message code="dish.number_required"/></span>
                                    </div>
                                </div>
                                <div class="col-lg-6 p-t-20">
                                    <div class="mdl-textfield mdl-js-textfield mdl-textfield--floating-label txt-full-width">
                                        <form:input class="mdl-textfield__input" type="text" pattern="[0-9]+" path="stock"/>
                                        <label class="mdl-textfield__label"><spring:message code="dish.amount"/></label>
                                        <span class="mdl-textfield__error"><spring:message code="dish.number_required"/></span>
                                    </div>
                                </div>
                                <div class="col-lg-12 p-t-20 text-center">
                                    <button type="submit"
                                            class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect m-b-10 m-r-20 rebeccapurple-color"><spring:message code="dish.add"/>
                                    </button>
                                    <a href="<c:url value="/dishes/"/>"
                                       class="mdl-button mdl-js-button mdl-button--raised mdl-js-ripple-effect m-b-10 btn-default"><spring:message code="dish.cancel"/></a><br>
                                </div>
                            </div>
                        </form:form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end page content -->
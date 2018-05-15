            <div class="card-box">
                <div class="card-head">
                    <header><spring:message code="table.tables_header"/></header>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${tables.size() > 0}">
                            <div class="table-responsive">
                                <table class="table table-striped custom-table">
                                    <thead class="text-left">
                                    <tr>
                                        <th><spring:message code="table.name"/></th>
                                        <th><spring:message code="table.diners"/></th>
                                        <th><spring:message code="table.status"/></th>
                                        <th></th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <c:forEach items="${tables}" var="table">
                                        <tr>
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
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-danger text-center">
                                <strong><spring:message code="ouch"/></strong> <spring:message
                                    code="table.no_tables"/>
                            </div>
                        </c:otherwise>
                    </c:choose>
                    <sec:authorize access="hasRole('ROLE_ADMIN')">
                        <div>
                            <form action="<c:url value="/tables/register"/>">
                                <button type="submit" class="btn btn-success"><i
                                        class="fa fa-plus"></i><spring:message code="table.add"/>
                                </button>
                            </form>
                        </div>
                    </sec:authorize>
                </div>
            </div>
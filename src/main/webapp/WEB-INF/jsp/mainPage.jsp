<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017-05-05
  Time: 21:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="//cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>LoansApp</title>
</head>
<body>
<div class="container" style="margin-top: 70px">
    <form:form id="searchForm" modelAttribute="query" method="post" class="form-horizontal">
        <div class="col-md-3">
            <form:select path="loanType" cssClass="form-control">
                <form:option value="" label="Select loan type"/>
                <form:options items="${loanTypes}"/>
            </form:select>
        </div>

        <div class="col-md-3">
            <form:input path="fiscalCode" cssClass="form-control" placeholder="Enter fiscal code"/>
        </div>

        <div class="col-md-3">
            <button type="submit" class="btn btn-primary" id="submit">Filter Results</button>
        </div>

        <div class="col-md-3">
            <button type="button" class="btn btn-primary" onclick="location.href='/loansCreateForm'">Request Loan
            </button>
        </div>
    </form:form>
</div>
<div class="container" style="margin-top: 10px">
    <div class="panel panel-default">
        <table class="table table-striped">
            <thead>
            <tr>
                <th>Customer</th>
                <th>Type loan</th>
                <th>Total Amount</th>
                <th>Rates</th>
                <th>Details</th>
            </tr>
            </thead>
            <tbody>
            <c:if test="${!empty customersList}">
                <c:forEach items="${customersList}" var="customer">
                    <tr>
                        <td>${customer.name} ${customer.surname}</td>
                        <td>${customer.loanType}</td>
                        <td>${customer.totalAmount}</td>
                        <td>${customer.rates}</td>
                        <td><a href="<c:url value='/customerLoanDetails/${customer.id}'/>" class="btn btn-info"
                               role="button">Show</a>
                        </td>
                    </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>

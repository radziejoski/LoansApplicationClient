<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017-05-04
  Time: 17:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/additional-methods.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>LoansApp</title>
</head>
<body>
<div class="container" style="margin-top: 70px">
    <form:form id="form" modelAttribute="customer" method="post" class="form-horizontal">
    <div class="row">
        <div class="col-sm-6">
            <div class="form-group">
                <label for="loanTypeSelect">Loan type:</label>
                <form:select id="loanTypeSelect" path="loanType" cssClass="form-control" required="required">
                    <form:option value="" label="Select loan type"/>
                    <form:options items="${loanTypes}"/>
                </form:select>
            </div>
            <div class="form-group">
                <label for="totalAmount">Total amount:</label>
                <form:input path="totalAmount" class="form-control" required="required"/>
            </div>
            <div class="form-group">
                <label for="rates">Rates:</label>
                <form:input path="rates" type="text" class="form-control" required="required"/>
            </div>
        </div>
    </div>
    <button type="submit" class="btn btn-primary">Next</button>
    </form:form>
</body>
</html>

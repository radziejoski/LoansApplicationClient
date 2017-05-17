<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017-05-07
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="https://cdn.jsdelivr.net/jquery.validation/1.16.0/jquery.validate.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>LoanApp</title>
    <script>
        $().ready(function () {
            $("#form").validate();
        })
    </script>
</head>
<body>
<div class="container">
    <form:form id="form" modelAttribute="customer" method="post" class="form-horizontal">
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label for="name">Name:</label>
                    <form:input path="name" class="form-control" required="required"/>
                </div>
                <div class="form-group">
                    <label for="surname">Surname:</label>
                    <form:input path="surname" class="form-control" required="true"/>
                </div>
                <div class="form-group">
                    <label for="fiscalCode">Fiscal Code:</label>
                    <form:input path="fiscalCode" class="form-control" required="true"/>
                </div>
                <div class="form-group">
                    <label for="birthDate">Date of Birth</label>
                    <form:input path="birthDate" class="form-control" type="date" required="true"/>
                </div>
                <div class="form-group">
                    <label for="address">Address:</label>
                    <form:input path="address" class="form-control" required="true"/>
                </div>
                <div class="form-group">
                    <label for="phone">Phone:</label>
                    <form:input path="phone" class="form-control" required="true"/>
                </div>
            </div>
        </div>
        <button type="submit" class="btn btn-primary">Next</button>
    </form:form>
</div>
</body>
</html>

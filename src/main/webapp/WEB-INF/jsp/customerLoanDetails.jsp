<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017-05-06
  Time: 11:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.3/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <title>LoanApp</title>
</head>
<body>
<div class="container" style="margin-top: 70px">
    <h1>Details</h1>
    <div class="col-md-6">
        <div class="page-header">
            <h3>Loan details</h3>
        </div>
        <div>
            <label>Loan type:</label>
            <label>${customer.loanType}</label>
        </div>
        <form:form method="post" modelAttribute="customer" action="update">
            <div>
                <label>Total amount:</label>
                <form:input path="totalAmount" cssClass="form-control" required="required"/>
            </div>
            <div>
                <label>Rates:</label>
                <form:input path="rates" cssClass="form-control" required="required"/>
            </div>
            </br>
            <input type="submit" value="Save" class="btn btn-info"/>
        </form:form>
        <div class="page-header">
            <h3>Customer details</h3>
        </div>
        <div>
            <label>Name: ${customer.name} ${customer.surname}</label>
        </div>
        <div>
            <label>Fiscal code: ${customer.fiscalCode}</label>
        </div>
        <div>
            <label>Date of birth: ${customer.birthDate}</label>
        </div>
        <div>
            <label>Address: ${customer.address}</label>
        </div>
        <div>
            <label>Phone: ${customer.phone}</label>
        </div>
    </div>
    <div class="col-md-6">
        <div class="page-header">
            <h3>Attachments</h3>
        </div>
        <div>
            <c:if test="${!empty files}">
                <div class="panel panel-default">
                    <table class="table table-striped">
                        <tbody>
                        <c:forEach items="${files}" var="file">
                            <tr>
                                <td>${file.name}</td>
                                <td><a href="<c:url value='/download/${file.id}'/>"
                                       class="btn btn-info"
                                       role="button">Download</a>
                                </td>
                                <td><a href="<c:url value='/delete/${file.id}'/>"
                                       class="btn btn-info"
                                       role="button">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:if>
            <form method="post" action="addFile" enctype="multipart/form-data">
                <input type="file" name="file">
                <input type="submit" value="Add an attachment" class="btn btn-primary">
            </form>
            </form>
        </div>
    </div>
</div>

</body>
</html>

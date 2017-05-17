<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2017-05-07
  Time: 12:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="//netdna.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/js/toastr.min.js"></script>
    <link rel="stylesheet" type="text/css" href="//netdna.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="//cdnjs.cloudflare.com/ajax/libs/toastr.js/latest/css/toastr.min.css">
    <title>LoanApp</title>
    <script>
        $(document).ready(function () {
            $('#proceed').click(function () {
                toastr.success('Changes were succesfuly saved');
            });
        });
    </script>
</head>
<body>
<div class="container">
    <div class="col-md-6">
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
        <button id="proceed" onclick="location.href='/proceed'" class="btn btn-primary">Proceed</button>
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
                            <td><a href="<c:url value='/step3/download/${file.name}'/>"
                                   class="btn btn-info"
                                   role="button">Download</a>
                            </td>
                            <td><a href="<c:url value='/step3/delete/${file.name}'/>"
                                   class="btn btn-info"
                                   role="button">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                </c:if>
            </div>
            <form method="post" action="add" enctype="multipart/form-data">
                <input type="file" name="file" accept="application/pdf">
                <input id="submit" type="submit" value="Add an attachment" class="btn btn-primary">
            </form>
            </form>
        </div>
    </div>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>ADMIN</title>
    <link href="${pageContext.request.contextPath}/resources/css/admin.css" rel="stylesheet"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>

<body style="background: url('<c:url value="/resources/images/admin.jpg" />')">
<div class="card shadow p-3 mb-5 rounded" id="login-card">
    <div class="card-header text-center" id="card-heading">
        ADMIN LOGIN
    </div>
    <br>
    <div class="card-body text-center">
        <form:form action="adminEdit" method="post" modelAttribute="admin">
            <form:label path="username">Username :</form:label> <form:input path="username" required="required"/><br><br>
            <form:label path="password">Password :</form:label> <form:password path="password" required="required"/> <br><br>
            <c:if test="${not empty error}">
                <span style="color:red;">${error}</span>
            </c:if><br><br>
            <input type="submit" name="submit" class="btn btn-success" style="width: 30%;" >
        </form:form>
    </div>
</div>
</body>
</html>

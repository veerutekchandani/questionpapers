<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Question Papers</title>
    <link href="<c:url value='/resources/css/show.css'/>" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
</head>

<body style="background: url('<c:url value="/resources/images/img2.jpg" />')">
<div class="card shadow p-3 mb-5 rounded" id="card-center">
    <div class="card-header text-center" id="card-heading">
        <h3 style="color:white;">${subjectName}</h3>
    </div>

    <div class="card-body">
        <div class="scrollable">
            <c:set var="count" value="1" scope="page" />
            <ul class="list-group list-group-flush">
                <c:forEach items="${downloadfiles}" var="downloadfiles">
                    <li class="list-group-item">
                        <ul class="list-inline">
                            <li class="list-inline-item">${count}.</li>
                            <li class="list-inline-item">${downloadfiles.fileName}</li>
                            <li style="float: right;" class="list-inline-item"><a href="${downloadfiles.fileLink}" class="btn btn-success">DOWNLOAD</a></li>
                        </ul>
                    </li>
                    <c:set var="count" value="${count + 1}" scope="page"/>
                </c:forEach>
            </ul>
        </div>
    </div>

<%--    <div class="card-body">--%>
<%--        <div class="scrollable">--%>
<%--        <table class="table table-striped table-borderless">--%>
<%--            <c:set var="count" value="1" scope="page" />--%>
<%--            <c:forEach items="${downloadfiles}" var="downloadfiles">--%>
<%--                <tr>--%>
<%--                    <th scope="row" style="font-size: 20px;">${count}</th>--%>
<%--                    <td style="font-weight: bold;font-size: 20px;">${downloadfiles.fileName}</td>--%>
<%--                    <td style="float: right;"><a href="${downloadfiles.fileLink}" class="btn btn-success" style="color:white;">--%>
<%--                        DOWNLOAD</a></td>--%>
<%--                </tr>--%>
<%--                <c:set var="count" value="${count + 1}" scope="page"/>--%>
<%--            </c:forEach>--%>
<%--        </table>--%>
<%--        </div>--%>
<%--    </div>--%>
</div>

</body>
</html>


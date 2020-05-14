<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Question Papers</title>
    <link href="<c:url value='/resources/css/home.css'/>" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="<c:url value='/resources/js/home.js'/>"></script>
</head>

<body onload="populateSubjects()">
<div class="card shadow p-3 mb-5 rounded" id="card-center">
    <div class="card-header text-center" id="card-heading">
        PREVIOUS QUESTION PAPERS
    </div>
    <hr>

    <div class="card-body">

        <form:form id="specify" name="specify" action="showQuestionPapers" modelAttribute="questionpaper" method="post">
            <div class="row text-center">
                <div class="col-md-12">
                    <form:select name="semester" id="semester" class="select" path="semester" required="required"
                                 onchange="populateSubjects()">
                        <form:option value="" hidden="hidden">Choose Semester</form:option>
                        <form:options items="${semesterList}"></form:options>
                    </form:select>
                    <br><br>

                    <form:select name="term" class="select" path="term" required="required">
                        <form:option value="" hidden="hidden">Choose Exam Type</form:option>
                        <form:option value="MID">MID SEMESTER</form:option>
                        <form:option value="END">END SEMESTER</form:option>
                    </form:select>
                    <br><br>

                    <form:select name="subject" id="subject"  class="select" path="subject" required="required">
                        <form:option value="" hidden="hidden">Choose Subject</form:option>
                    </form:select>
                    <br><br><br>
                    <input type="submit" name="submit" class="btn btn-primary" style="width: 100px;">
                </div>
            </div>
        </form:form>

    </div>
    <div class="card-footer" id="footer">
        <a href="adminLogin" class="btn btn-success admin">ADMIN LOGIN</a>
    </div>
</div>

</body>
</html>


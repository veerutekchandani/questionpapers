<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>EDIT</title>
    <link href="${pageContext.request.contextPath}/resources/css/edit.css" rel="stylesheet"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"></script>
    <script src="<c:url value='/resources/js/home.js'/>"></script>
    <script src="<c:url value='/resources/js/edit.js'/>"></script>
</head>

<body onload="populateSubjects()">
<div class="card" id="edit-card">
    <div class="card-header" id="heading">
        <nav class="navbar navbar-expand-sm bg-light" id="navigation">
            <ul class="navbar-nav">
                <li onclick="toggle(this.id)" class="nav-item active" id="editUpload"><a class="nav-link" href="#">UPLOAD</a></li>
                <li onclick="toggle(this.id)" class="nav-item" id="editChange"><a class="nav-link" href="#">CHANGE</a></li>
                <!-- <li class="nav-item"><a class="nav-link" href="#">ADD</a></li> -->
            </ul>
        </nav>
    </div>
    <hr>


    <!-- FOR UPDATE -->
    <div class="card-body" id="upload-body">
        <form:form name="upload" id="upload" action="upload" modelAttribute="uploadPaper" enctype="multipart/form-data">
            <div class="row text-center">
                <div class="col-md-6">
                    <form:select path="semester" id="semester" name="semester" cssClass="select-upload"
                                 onchange="populateSubjects()" required="required">
                        <form:option value="" hidden="hidden">Choose Semester</form:option>
                        <form:option value="1">FIRST</form:option>
                        <form:option value="2">SECOND</form:option>
                        <form:option value="3">THIRD</form:option>
                        <form:option value="4">FOURTH</form:option>
                        <form:option value="5">FIFTH</form:option>
                    </form:select>
                </div>
                <div class="col-md-6">
                    <form:select path="term" id="term" name="term" cssClass="select-upload" required="required">
                        <form:option value="" hidden="hidden">Choose Exam Type</form:option>
                        <form:option value="MID">MID SEMESTER</form:option>
                        <form:option value="END">END SEMESTER</form:option>
                    </form:select>
                </div>
            </div>
                    <br>
            <div class="row text-center">
                <div class="col-md-6">
                    <form:select path="subject" id="subject" name="subject" cssClass="select-upload" required="required">
                        <form:option value="" hidden="hidden">Choose Subject</form:option>
                    </form:select>
                </div>
                <div class="col-md-6">
                    <form:input path="year" type="text" placeholder=" Enter year : " />
                </div>
            </div>
            <div class="row text-center">
                <div class="col-md-12">
                    <br><br>
                    <form:input path="file" type="file" name="file"/>
                </div>
            </div>
            <div class="row text-center">
                <div class="col-md-12">
                    <br><br>
                    <button class="btn btn-primary" type="submit" name="submit">Upload</button>
                </div>
            </div>
            <br>
            <c:if test="${not empty success}">
                <span style="color:green;font-weight: bold;text-align: center;">${success}</span>
            </c:if><br><br>
            <c:if test="${not empty error}">
                <span style="color:red;font-weight: bold;text-align: center;">${error}</span>
            </c:if><br><br>
            </form:form>
    </div>


    <!-- FOR CHANGE -->
    <div class="card-body" id="change-body" hidden>
        <form name="change" id="change" onsubmit="changeSemester();return false;">
            <div class="row text-center">
                <div class="col-md-12">
                    <select class="select-change" name="oldsemester" id="oldsemester" required="required" onchange="populateEditChangeSubjects()">
                        <option value="" hidden="hidden">Choose Current Semester</option>
                        <option value="1">FIRST</option>
                        <option value="2">SECOND</option>
                        <option value="3">THIRD</option>
                        <option value="4">FOURTH</option>
                        <option value="5">FIFTH</option>
                    </select>
                    <br><br>
                    <select class="select-change" name="cSubject" id="cSubject" required="required">
                        <option value="" hidden="hidden">Choose Subject</option>
                    </select>
                    <br><br>
                    <select class="select-change" name="newsemester" id="newsemester" required="required">
                        <option value="" hidden="hidden">Choose New Semester</option>
                        <option value="1">FIRST</option>
                        <option value="2">SECOND</option>
                        <option value="3">THIRD</option>
                        <option value="4">FOURTH</option>
                        <option value="5">FIFTH</option>
                    </select>
                    <br><br>
                    <input class="btn btn-primary" type="submit" name="submit">
                </div>
            </div>
        </form>
        <br>
        <br>
        <div id="message"></div>
    </div>

    <div class="card-body" id="add-body" hidden>
        add
    </div>

</div>
</body>
</html>

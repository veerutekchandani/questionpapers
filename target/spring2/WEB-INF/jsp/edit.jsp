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

<body onload="populateSubjects()" style="background: url('<c:url value="/resources/images/admin.jpg" />')">
<div class="card" id="edit-card">
    <div class="card-header" id="heading">
        <nav class="navbar navbar-expand-sm bg-light" id="navigation">
            <ul class="navbar-nav">
                <li onclick="toggle(this.id)" class="nav-item active" id="tab1"><a class="nav-link" href="#">UPLOAD</a></li>
                <li onclick="toggle(this.id)" class="nav-item" id="tab2"><a class="nav-link" href="#">CHANGE</a></li>
                <li onclick="toggle(this.id)" class="nav-item" id="tab3"><a class="nav-link" href="#">ADD</a></li>
                <li onclick="toggle(this.id)" class="nav-item" id="tab4"><a class="nav-link" href="#">DELETE</a></li>
                <!-- <li class="nav-item"><a class="nav-link" href="#">ADD</a></li> -->
            </ul>
        </nav>
    </div>
    <hr>


    <!-- FOR UPDATE -->
    <div class="card-body" id="tab1-body">
        <form:form name="upload" id="upload" action="uploadPaper" modelAttribute="uploadPaper" enctype="multipart/form-data">
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
                    <button form="upload" class="btn btn-primary" type="submit" name="submit">Upload</button>
                </div>
            </div>
            <br>
            <c:if test="${not empty success}">
                <span style="color:green;font-weight: bold;text-align: center;">${success}</span>
            </c:if><br>
            <c:if test="${not empty error}">
                <span style="color:red;font-weight: bold;text-align: center;">${error}</span>
            </c:if><br><br>
            </form:form>
    </div>


    <!-- FOR CHANGE -->
    <div class="card-body" id="tab2-body" hidden>
        <form:form name="change" id="change" action="changeSemester" modelAttribute="changeSemester">
            <div class="row text-center">
                <div class="col-md-12">
                    <form:select class="select-change" name="oldSemester" id="oldSemester" path="oldSemester" required="required" onchange="populateEditChangeSubjects()">
                        <option value="" hidden="hidden">Choose Current Semester</option>
                        <option value="1">FIRST</option>
                        <option value="2">SECOND</option>
                        <option value="3">THIRD</option>
                        <option value="4">FOURTH</option>
                        <option value="5">FIFTH</option>
                    </form:select>
                    <br><br>
                    <form:select class="select-change" path="changeSubject" name="changeSubject" id="changeSubject" required="required">
                        <option value="" hidden="hidden">Choose Subject</option>
                    </form:select>
                    <br><br>
                    <form:select class="select-change" path="newSemester" name="newSemester" id="newSemester" required="required">
                        <option value="" hidden="hidden">Choose New Semester</option>
                        <option value="1">FIRST</option>
                        <option value="2">SECOND</option>
                        <option value="3">THIRD</option>
                        <option value="4">FOURTH</option>
                        <option value="5">FIFTH</option>
                    </form:select>
                    <br><br>
                    <input form="change" class="btn btn-primary" type="submit" name="submit">
                </div>
            </div>
        </form:form>
        <br>
        <br>
        <div id="message"></div>
    </div>

    <!-- FOR ADD -->
    <div class="card-body" id="tab3-body" hidden>
        <form:form name="add" id="add" action="addSubject" modelAttribute="addSubject">
            <div class="row text-center">
                <div class="col-md-12">
                    <form:select cssClass="select-change" path="addSemester" name="addSemester" id="addSemester">
                        <option value="" hidden="hidden">Choose Semester</option>
                        <option value="1">FIRST</option>
                        <option value="2">SECOND</option>
                        <option value="3">THIRD</option>
                        <option value="4">FOURTH</option>
                        <option value="5">FIFTH</option>
                    </form:select>
                    <br><br>
                    <form:input cssStyle="width: 40%;" path="addSubjectCode" name="addSubjectCode" id="addSubjectCode"
                            placeholder=" Enter subject code : "></form:input>
                    <br><br>
                    <form:input cssStyle="width: 40%;" path="addSubjectName" name="addSubjectName" id="addSubjectName"
                            placeholder=" Enter subject name : "></form:input>
                    <br><br>
                    <input form="add" class="btn btn-primary" type="submit" name="submit">
                </div>
            </div>
        </form:form>
    </div>

    <div class="card-body" id="tab4-body" hidden>
        <form:form name="delete" id="delete" action="deleteSubject" modelAttribute="deleteSubject">
            <div class="row text-center">
                <div class="col-md-12">
                    <form:select cssClass="select-change" path="deleteSemester" name="deleteSemester" id="deleteSemester"
                            onchange="populateEditDeleteSubjects()">
                        <option value="" hidden="hidden">Choose Semester</option>
                        <option value="1">FIRST</option>
                        <option value="2">SECOND</option>
                        <option value="3">THIRD</option>
                        <option value="4">FOURTH</option>
                        <option value="5">FIFTH</option>
                    </form:select>
                    <br><br>
                    <form:select class="select-change" path="deleteSubject" name="deleteSubject" id="deleteSubject"
                                 required="required">
                        <option value="" hidden="hidden">Choose Subject</option>
                    </form:select>
                    <br><br>
                    <input form="delete" class="btn btn-primary" type="submit" name="submit">
                </div>
            </div>
        </form:form>
    </div>

    <div class="card-footer">

    </div>

</div>
</body>
</html>

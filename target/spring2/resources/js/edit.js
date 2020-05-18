function toggle(id) {
    for(var i=1;i<=4;i++) {
        var ID = "tab"+i;
        var element = document.getElementById(ID);
        var element_body = document.getElementById(ID+"-body");
        if(ID == id) {
            element_body.removeAttribute("hidden");
            element.classList.add("active");
        }
        else {
            element_body.setAttribute("hidden","hidden");
            element.classList.remove("active");
        }
    }
}



function populateEditChangeSubjects() {
        var e = document.getElementById("oldSemester");
        var value = e.options[e.selectedIndex].value;

        if(value<1 || value>5) {
            $('#changeSubject').find('option').remove();
            $("#changeSubject").append('<option value="" hidden="hidden">Choose Subject</option>');
        }
        else {
            $.ajax({
                type: "POST",
                url: "populateSubjects?semester=" + value,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (res) {
                    $('#changeSubject').find('option').remove();
                    $("#changeSubject").append('<option value="" hidden="hidden">Choose Subject</option>');
                    for (var i = 0; i < res.length; i++) {
                        $("#changeSubject").append('<option value=' + res[i].subjectCode + '>' + res[i].subjectName + '</option>');
                    }
                },
                error: function (res) {
                    console.log(res);
                }
            });
        }
}

function populateEditDeleteSubjects() {
    var e = document.getElementById("deleteSemester");
    var value = e.options[e.selectedIndex].value;

    if(value<1 || value>5) {
        $('#deleteSubject').find('option').remove();
        $("#deleteSubject").append('<option value="" hidden="hidden">Choose Subject</option>');
    }
    else {
        $.ajax({
            type: "POST",
            url: "populateSubjects?semester=" + value,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (res) {
                $('#deleteSubject').find('option').remove();
                $("#deleteSubject").append('<option value="" hidden="hidden">Choose Subject</option>');
                for (var i = 0; i < res.length; i++) {
                    $("#deleteSubject").append('<option value=' + res[i].subjectCode + '>' + res[i].subjectName + '</option>');
                }
            },
            error: function (res) {
                console.log(res);
            }
        });
    }
}

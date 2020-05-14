function toggle(id) {
    var upload = document.getElementById("editUpload");
    var change = document.getElementById("editChange");
    var uploaddiv = document.getElementById("upload-body");
    var changediv = document.getElementById("change-body");
    if(id == "editUpload") {
        uploaddiv.removeAttribute("hidden");
        changediv.setAttribute("hidden","hidden");
        upload.classList.add("active");
        change.classList.remove("active");
    }
    else {
        uploaddiv.setAttribute("hidden","hidden");
        changediv.removeAttribute("hidden");
        upload.classList.remove("active");
        change.classList.add("active");
    }
}



function populateEditChangeSubjects() {
        var e = document.getElementById("oldsemester");
        var value = e.options[e.selectedIndex].value;

        if(value<1 || value>5) {
            $('#cSubject').find('option').remove();
            $("#cSubject").append('<option value="" hidden="hidden">Choose Subject</option>');
        }
        else {
            $.ajax({
                type: "POST",
                url: "/populateSubjects?semester=" + value,
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (res) {
                    $('#cSubject').find('option').remove();
                    $("#cSubject").append('<option value="" hidden="hidden">Choose Subject</option>');
                    for (var i = 0; i < res.length; i++) {
                        $("#cSubject").append('<option value=' + res[i].subjectCode + '>' + res[i].subjectName + '</option>');
                    }
                },
                error: function (res) {
                    console.log(res);
                }
            });
        }
}

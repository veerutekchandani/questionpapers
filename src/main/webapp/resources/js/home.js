function populateSubjects() {
    var e = document.getElementById("semester");
    var value = e.options[e.selectedIndex].value;

    if(value<1 || value>5) {
        $('#subject').find('option').remove();
        $("#subject").append('<option value="" hidden="hidden">Choose Subject</option>');
    }
    else {
        $.ajax({
            type: "POST",
            url: "populateSubjects?semester=" + value,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (res) {
                $('#subject').find('option').remove();
                $("#subject").append('<option value="" hidden="hidden">Choose Subject</option>');
                for (var i = 0; i < res.length; i++) {
                    $("#subject").append('<option value=' + res[i].subjectCode + '>' + res[i].subjectName + '</option>');
                }
            },
            error: function (res) {
                console.log(res);
            }

        });
    }
}

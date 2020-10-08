$(document).ready(function () {
    $('form[name="cms"]').submit(function (event) {
        var content = $(this).serializeToString(String);
        console.log(content);
        $.ajax({
            url: $(event.target).attr("action"),
            type: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (rs) {
                console.log(rs);
            }
        });
        event.preventDefault();
    });
});
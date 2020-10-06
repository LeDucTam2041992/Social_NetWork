$(document).ready(function () {
    var likeLink = $("a:contains('Like')");
    $(likeLink).click(function (event) {
        $.ajax({
            url: $(event.target).attr("href"),
            type: "POST",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (likes) {
                var respContent = "";

                $("#sPhoneFromResponse").html(respContent);
            }
        });
        event.preventDefault();
    });
});
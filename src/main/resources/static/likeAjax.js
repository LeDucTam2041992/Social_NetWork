$(document).ready(function () {
    var likeLink = $("a:contains('Like')");
    $(likeLink).click(function (event) {
        var idLike = $(event.target).attr("id");
        var idDislike = "dis" + idLike;
        console.log(idLike);
        console.log(idDislike);
        var like = document.getElementById(idLike);
        var disLike = document.getElementById(idDislike);
        $.ajax({
            url: $(event.target).attr("href"),
            type: "GET",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            success: function (likeStatus) {
                if(likeStatus==1) {
                    like.className = 'text-primary mr-2';
                    disLike.className = 'text-secondary mr-2';
                } else {
                    like.className = 'text-secondary mr-2';
                    disLike.className = 'text-secondary mr-2';
                }
            }
        });
        event.preventDefault();
    });
});
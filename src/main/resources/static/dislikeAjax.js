$(document).ready(function () {
    var dislikeLink = $("a:contains('Dislike')");
    $(dislikeLink).click(function (event) {
        var idDislike = $(event.target).attr("id");
        var idLike = idDislike.slice(3);
        console.log(idDislike);
        console.log(idLike);
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
                if(likeStatus==2) {
                    like.className = 'text-secondary mr-2';
                    disLike.className = 'text-primary mr-2';
                } else {
                    like.className = 'text-secondary mr-2';
                    disLike.className = 'text-secondary mr-2';
                }
            }
        });
        event.preventDefault();
    });
});
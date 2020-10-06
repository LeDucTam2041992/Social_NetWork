$(document).ready(function () {
    $('#newSmartphoneForm').submit(function (event) {
        var producer = $('#producer').val();
        var model = $('#model').val();
        var price = $('#price').val();
        var json = {"producer": producer, "model": model, "price": price};
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: "POST",
            data: JSON.stringify(json),
            url: "/smartphones/create",
            success: function (smartphones) {
                var respContent = "";
                respContent += "<table id='listProduct' border='1px' class='success'>";
                respContent += "<thead>" +
                    "<tr>" +
                    "<th>Producer</th>" +
                    "<th>Model</th>" +
                    "<th>Price</th>" +
                    "<th>Actions</th>" +
                    "</tr>" +
                    "</thead><tbody>";

                for (let i = 0; i < smartphones.length; i++) {
                    respContent += "<tr><td>" + smartphones[i].producer + "</td>" +
                        "<td>" + smartphones[i].model + "</td>" +
                        "<td>" + smartphones[i].price + "</td>" +
                        "<td><a href=/smartphones/edit/" + smartphones[i].id + ">Edit</a><br/>" +
                        "<a href=/smartphones/delete/" + smartphones[i].id + ">Delete</a></td></tr>";
                }
                respContent += "</tbody></table>";
                $("#listProduct").html(respContent);
            }
        });
        event.preventDefault();
    });
});
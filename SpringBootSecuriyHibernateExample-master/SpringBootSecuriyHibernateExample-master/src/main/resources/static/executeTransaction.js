$(document).ready(function () {
    $("#transaction-form").submit(function (event) {

        var value = $("#value-input").val();
        var toUser = $("#toUser-input").val();
        var transactObject = {
            transactionVale: value,
            toUser: toUser
        };
        var transactJsonObject = JSON.stringify(transactObject);

        if (value !== 0
            && toUser !== "") {
            event.preventDefault();
            executeTransaction(transactJsonObject, event);
        }
        else alert("Both have to be filled");


        function executeTransaction(jsonObject, event) {
            $.ajax({
                url: "/createNewTransaction",
                type: "POST",
                contentType: "application/json",
                data: jsonObject,
                success: function (result) {
                        window.location.replace("/personalroom");
                },
                error: function (result) {

                    var message = JSON.parse(result.responseText).message;
                    alert("Something went wrong : " + message);
                    event.preventDefault();
                }
            })
        }
    })
});

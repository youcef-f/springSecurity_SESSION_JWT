$(document).ready(function () {
    $("#registration_form").submit(function (event) {
        var username = $("#username").val();
        var password = $("#password").val();
        var userDTO = {
            username: username,
            password: password
        };
        var resultJson = JSON.stringify(userDTO);
        if (username !== "" && password !== "") {
            sentUserData(resultJson);
            // username.val("");
            // password.val("");
            event.preventDefault();
        } else {
            alert("Both fields must be required");
            event.preventDefault();
        }
    });

    function sentUserData(json) {
        $.ajax({
            type: "POST",
            url: "/users",
            contentType: "application/json",
            // dataType : "jsonp",
            data: json,
            success: function () {
                window.location.replace("/login");
            },
            error: function () {
                alert("Registration error");
            }

        });
    }
});

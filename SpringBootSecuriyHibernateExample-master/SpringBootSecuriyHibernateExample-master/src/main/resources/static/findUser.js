$(document).ready(function (event) {



    $("#search-button").click(function(event) {
        var searchValue = $("#search-input").val();
        event.preventDefault();
        findUser(searchValue);
        console.log(searchValue);
    });

    function findUser(name) {
        $.ajax({
            url : "/findUser?name=" + name,
            type : "GET",
            success : function (result) {
                $("#error-search-result-div").text("");
                $("#search-result-div").attr("style", "visibility: visible");
                $("#found-user-name").text(result.username);
                $("#found-user-budget").text(result.budget + "$");
                $("#user-lock-button").text(checkEnable(result.accountNonLocked));
            },
            error : function () {
                alert("User was not found");
            }
        })
    }

    function checkEnable(e) {
        if (e){
            return "lock"
        } else return "unlock";
    }
});
$(document).ready(function () {

    fillPersonalRoomFunction();

    function fillPersonalRoomFunction() {
        $.ajax({
            type : "GET",
            url : "/person",
            success : function(result){
                $("#personalId").append("<strong>" + result.username + "</strong>");
                $("#personalBudget").append("<strong>" + result.budget + "$" + "</strong>");
            }
        })
    }
});
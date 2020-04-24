$(document).ready(function (event) {

    fillPersonalRoomFunction();

    function fillPersonalRoomFunction() {
        $.ajax({
            type : "GET",
            url : "/userList",
            success : function(result){
                $.each(result, function (i, user) {
                    var enable = user.accountNonLocked;
                    var line = "<li> User : " + "<strong>" + user.username + "</strong>" +
                        " is " + "<strong><label id='"+user.username+"'>" + checkEnabled(enable) + "</label></strong>" + "</li>";
                    $("#user-list").append(line);
                })
            }
        })
    }

    function checkEnabled(enable) {
        if (enable){
            return "unlocked"
        } else return"locked"
    }
});
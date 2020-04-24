$(document).ready(function (event) {

    fillPersonalRoomFunction();

    function fillPersonalRoomFunction() {
        $.ajax({
            type : "GET",
            url : "/transactions",
            success : function(result){
               $.each(result, function (i, transaction) {
                   var line = "<li>" +
                       "Sent to User : " + "<strong>" + transaction.toUser + "</strong>" +
                           ", Amount : " + "<strong>" + transaction.transactionValue + "$" + "</strong>" +
                       " at " + "<strong>" + transaction.beginDate + "</strong>" + "</li>";
                   $("#transaction-list").append(line);
               })
            }
        })
    }
});
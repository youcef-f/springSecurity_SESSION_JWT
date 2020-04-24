$(document).ready(function (event) {
    $("#search-form").submit(function (event) {
        var name = $("#found-user-name").text();
        lockUser(name);
        event.preventDefault();
    });
});
function lockUser(name) {
    var $user = $("#user-lock-button");
    var lineName = "#"+name;
    var $line = $(lineName);
    $.ajax({
        url : "/lock?name="+name,
        type : "POST",
        success : function () {
            changeButton($user);
            var s = checkEnable($line);
            $line.text(s);
        },
        error : function () {
            alert("Something has gone wrong");
        }
    })
}

function checkEnable($value) {
    if ($value.text() === "unlocked")
       return "locked";
    else if ($value.text() === "locked") return "unlocked";
}

function changeButton($value) {
    var val = $value.text();
    if (val === "unlock") $value.text("lock");
    else $value.text("unlock");
}
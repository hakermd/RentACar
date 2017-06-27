$(document).ready(function () {

    $('.centralContentTable').find('tr').click(function () {
        var id = $(this).attr("id");
        document.forms[id].submit();
    });

    $('#createNewAccount').click(function () {
        var id = $(this).attr("id");
        document.forms[id].submit();
    });
    $('#createNewCar').click(function () {
        var id = $(this).attr("id");
        document.forms[id].submit();
    });
});
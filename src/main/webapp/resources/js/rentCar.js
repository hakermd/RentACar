/**
 * Created by aplesca on 6/5/2017.
 */
$(document).ready(function () {

    //------------max year
    var d = new Date();
    var day = d.getDate();
    var month = d.getMonth() + 1;
    var year = d.getFullYear() - 18;
    var value = day + "-" + month + "-" + year;
    $('#userBirthDayRegister').attr({max: value});

    $('#centralContentTable').find('tr').click(function () {
        var id = $(this).attr("id");
        console.log(id)
        document.forms[id].submit();
    });

    $('#createNewAccount').click(function () {
        var id = $(this).attr("id");
        console.log(id)
        document.forms[id].submit();
    });


});
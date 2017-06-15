/**
 * Created by aplesca on 6/5/2017.
 */
$(document).ready(function () {

    $('#centralContentTable').find('tr').click(function () {
        var id = $(this).attr("id");
        console.log(id)
        document.forms[id].submit();
    });
    // $('#carViewDetailedId').click(function () {
    //     var id = $(this).attr("id");
    //     console.log(id)
    //     document.forms[id].submit();
    // });


});
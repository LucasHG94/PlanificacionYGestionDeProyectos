/*
 * 
 * Script para seleccionar las vacaciones
 */

jQuery("#enviarDias").click(function () {
    var user = getCookie("username");
    var ano1 = jQuery("#ano1").val();
    var mes1 = jQuery("#mes1").val();
    var dia1 = jQuery("#dia1").val();

    var ano2 = jQuery("#ano2").val();
    var mes2 = jQuery("#mes2").val();
    var dia2 = jQuery("#dia2").val();
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/vacaciones", {
        user:user, ano1:ano1, mes1:mes1, dia1:dia1, ano2:ano2, mes2:mes2, dia2:dia2
    }, function (resultado) {
        if(resultado){
        $("#asignarAlertBad").hide();
        $("#asignarAlertGood").show(500);
        $("#aAlertGoodText").text("Semanas de vacaciones guardadas");
    }
        else{
            $("#asignarAlertGood").hide();
            $("#asignarAlertBad").show(500);
            $("#aAlertBadText").text("Semanas inválidas");
        }
    });
});


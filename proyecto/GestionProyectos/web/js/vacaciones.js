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
    /*jQuery.ajax({
         type: "PUT",
         url: "http://localhost:8080/GestionProyectos/webresources/SimpleRoot/vacaciones",
         contentType: "application/json; charset=utf-8",
         data: {user:user, ano1:ano1, mes1:mes1, dia1:dia1, ano2:ano2, mes2:mes2, dia2:dia2},
         dataType: "json",
         success: function (data, status, jqXHR) {
             window.alert("semanas de vacaciones guardadas");
         },
    
         error: function (jqXHR, status) {}
    });*/
    jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/vacaciones", {
        user:user, ano1:ano1, mes1:mes1, dia1:dia1, ano2:ano2, mes2:mes2, dia2:dia2
    }, function (resultado) {
        if(resultado){window.alert("semanas de vacaciones guardadas");
    }
        else{window.alert("tienes actividades en esas semanas");}
    });
});


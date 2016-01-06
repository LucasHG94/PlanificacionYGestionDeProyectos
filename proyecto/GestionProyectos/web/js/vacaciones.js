jQuery("#enviar").click(function () {
    window.alert("llego");
    var user = getCookie("username");
    var ano1 = jQuery("#ano1").val();
    var mes1 = jQuery("#mes1").val();
    var dia1 = jQuery("#dia1").val();

    var ano2 = jQuery("#ano2").val();
    var mes2 = jQuery("#mes2").val();
    var dia2 = jQuery("#dia1").val();

    jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/usuario", {
        user: user, ano1:ano1, mes1:mes1, dia1:dia1, ano2:ano2, mes2:mes2, dia2:dia2
    }, function (resultado) {
        window.alert("semanas de vacaciones guardadas");
    });
});



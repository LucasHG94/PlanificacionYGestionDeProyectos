/*
 * Cambia el número máximo de proyectos paralelos
 */

checkAdmin();
jQuery("#submitBtn").click(function () {
    var numP = $("#numP").val();
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/admin/conf", {
        numP: numP
    }, function () {
        window.alert("Datos cambiados con exito");
        var pagina = 'admin.html';
        document.location.href = pagina;
    });


});

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

checkAdmin();
jQuery("#submitBtn").click(function () {
    var numP = $("#numP").val();
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/admin/conf", {
        numP: numP
    }, function () {
        window.alert("Datos cambiados con exito");
        var pagina = '/GestionProyectos/admin.html';
        document.location.href = pagina;
    });


});

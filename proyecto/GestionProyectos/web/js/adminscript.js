/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var user = getCookie("username");
jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/admin", {
    user: user
}, function (resultado) {
    if (resultado != 1) {
        window.alert("Acceso denegado");
        var pagina = 'http://localhost:8080/GestionProyectos/index.html';
        document.location.href = pagina;

    }
});




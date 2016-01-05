/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
jQuery(function () {
    var user = getCookie("username");
    window.alert("llego2");
    jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/proyectos", {
        user: user
    }, function (resultado) {
        window.alert("llego");
        for (j = 0; j < resultado.length; j++) {
            document.write("<p>" + resultado[j].nombre + ' <button type=\n\
                            "button" id="operaciones">Operaciones</button>\n\
                            <button type="button" id="consultar">Consultar</button>\n\
                            <button type="button" id="calendario">Calendario</button></p>');
        }
    });
    document.write('<h3>Proyectos en los que eres jefe:</h3>');
    jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/proyectosjefe", {
        user: user
    }, function (resultado) {
        for (j = 0; j < resultado.length; j++) {
            document.write("<p>" + resultado[j].nombre + ' <button type=\n\
                            "button" id="operaciones">Operaciones</button>\n\
                            <button type="button" id="consultar">Consultar</button>\n\
                            <button type="button" id="calendario">Calendario</button></p>');
        }
    });
});
jQuery("#operaciones").click(function () {

});
jQuery("#consultar").click(function () {

});
jQuery("#calendario").click(function () {

});


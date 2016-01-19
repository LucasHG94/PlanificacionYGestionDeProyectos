/*
 * Presentación de los datos correspondientes a una actividad de un desarrollador
 */

jQuery(function (){
    var user = getCookie("username");
    var id = parametro("id",window.location.href );
    var nombre = parametro("nombre",window.location.href );
    var rol = parametro("rol",window.location.href );
    var fechaIni = parametro("fechaIni",window.location.href);
    var fechaFin = parametro("fechaFin",window.location.href);
    var esf = parametro("esf",window.location.href);
        var p = document.createElement("p");
        var t = document.createTextNode("identificador: "+id);       
        p.appendChild(t);
        document.body.appendChild(p);
        
        var p = document.createElement("p");
        var t = document.createTextNode("nombre: "+nombre);       
        p.appendChild(t);
        document.body.appendChild(p);
        
        var p = document.createElement("p");
        var t = document.createTextNode("rol: "+rol);       
        p.appendChild(t);
        document.body.appendChild(p);
        
        var p = document.createElement("p");
        var t = document.createTextNode("fecha de inicio: "+fechaIni);       
        p.appendChild(t);
        document.body.appendChild(p);
        
        var p = document.createElement("p");
        var t = document.createTextNode("fecha de finalizacion: "+fechaFin);       
        p.appendChild(t);
        document.body.appendChild(p);
        
        var p = document.createElement("p");
        var t = document.createTextNode("esfuerzo estimado: "+esf);       
        p.appendChild(t);
        document.body.appendChild(p);
        
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/actividades", {
        user: user, idP:idP
    }, function (resultado) {
        jQuery("#resultado span").text(resultado);
    });

});


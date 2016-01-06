/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
jQuery(function () {
    var user = getCookie("username");
    jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/proyectos", {
        user: user
    }, function (resultado) {
        var h = document.createElement("h4");
        var t = document.createTextNode("Tus proyectos: ");       
        h.appendChild(t);
        document.body.appendChild(h);
        for (j = 0; j < resultado.length; j++) {
            var p = document.createElement("p");
            var t = document.createTextNode(resultado[j].nombre + " ");       
            p.appendChild(t);
            var btn = document.createElement("a");
            btn.setAttribute("id", "crear");
            btn.setAttribute("href","");
            var t = document.createTextNode("crearInforme,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("a");
            btn.setAttribute("id", "modificar");
            btn.setAttribute("href", "");
            var t = document.createTextNode("modificarInforme,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("a");
            btn.setAttribute("id", "consultar");
            btn.setAttribute("href","http://localhost:8080/GestionProyectos/consultarActividad.html?idP="+resultado[j].id);
            var t = document.createTextNode("consultarActividades,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("a");
            btn.setAttribute("id", "escoger");
            btn.setAttribute("href","http://localhost:8080/GestionProyectos/vacaciones.html");
            var t = document.createTextNode("escogerVacaciones,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            document.body.appendChild(p);
        }
    });
    
    jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/proyectosjefe", {
        user: user
    }, function (resultado) {
        var h = document.createElement("h4");
        var t = document.createTextNode("Proyectos en los que eres jefe: ");
        h.appendChild(t);
        document.body.appendChild(h);
        for (j = 0; j < resultado.length; j++) {
            var p = document.createElement("p");
            var t = document.createTextNode(resultado[j].nombre +" " );       
            p.appendChild(t);
            var btn = document.createElement("a");
            btn.setAttribute("id", "iniciar");
            btn.setAttribute("href","");
            var t = document.createTextNode(" iniciarProyecto,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("a");
            btn.setAttribute("id", "asiganar");
            btn.setAttribute("href","");
            var t = document.createTextNode(" asignarTrabajador,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("a");
            btn.setAttribute("id", "informe");
            btn.setAttribute("href","");
            var t = document.createTextNode(" obtenerInforme,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("a");
            btn.setAttribute("id", "planificar");
            btn.setAttribute("href","");
            var t = document.createTextNode(" planificarProyecto,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("a");
            btn.setAttribute("id", "cerrar");
            btn.setAttribute("href","");
            var t = document.createTextNode(" cerrarActividad,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("a");
            btn.setAttribute("id", "datos");
            btn.setAttribute("href","");
            var t = document.createTextNode(" consultarDatos,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            document.body.appendChild(p);
        }
    });
    
});


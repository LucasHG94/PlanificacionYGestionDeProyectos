/* 
Script para presentar los proyectos en los que participas en de los que eres jefe
 */
jQuery(function () {
    var user = getCookie("username");
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/proyectos", {
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
            var btn = document.createElement("button");
            btn.setAttribute("id", "consultar");
            btn.setAttribute("onClick","location.href="+ "'"+"consultarActividad.html?idP="+resultado[j].id+"'");
            var t = document.createTextNode("consultarActividades");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "escoger");
            btn.setAttribute("onClick","location.href='vacaciones.html'");
            var t = document.createTextNode("escogerVacaciones");
            btn.appendChild(t);
            p.appendChild(btn);
            document.body.appendChild(p);
        }

        var p = document.createElement("p");
        var btn = document.createElement("button");
        btn.setAttribute("id", "crear");
        btn.setAttribute("onClick","location.href='crearInforme.html'");
        var t2 = document.createTextNode("crearInforme");
        btn.appendChild(t2);
        p.appendChild(btn);
        document.body.appendChild(p);
    });
    
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/proyectosjefe", {
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
            var btn = document.createElement("button");
            btn.setAttribute("id", "iniciar");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" iniciarProyecto");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "asiganar");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" asignarTrabajador");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "informe");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" obtenerInforme");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "planificar");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" planificarProyecto");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "cerrar");
            btn.setAttribute("onClick","location.href='cerrarActividades.html'");
            var t = document.createTextNode(" cerrarActividad");
            btn.appendChild(t);
            p.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "datos");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" consultarDatos");
            btn.appendChild(t);
            p.appendChild(btn);
            document.body.appendChild(p);
        }
    });
    
});


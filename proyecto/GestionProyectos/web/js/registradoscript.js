/* 
Script para presentar los proyectos en los que participas en de los que eres jefe
 */
jQuery(function () {
    var user = getCookie("username");
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/proyectos", {
        user: user
    }, function (resultado) {
        var lista = document.getElementById("lista-asignacion");
        for (j = 0; j < resultado.length; j++) {
            var itemlista = document.createElement("li");
            var poritem = document.createElement("span");
            itemlista.setAttribute("class", "list-group-item");
            itemlista.setAttribute("style", "display:none");
            itemlista.textContent = resultado[j].nombre + " ";
            var btn = document.createElement("button");
            btn.setAttribute("id", "consultar");
            btn.setAttribute("class", "badge");
            btn.setAttribute("onClick","location.href="+ "'"+"consultarActividad.html?idP="+resultado[j].id+"'");
            var t = document.createTextNode("consultarActividades");
            btn.appendChild(t);
            itemlista.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "escoger");
            btn.setAttribute("class", "badge");
            btn.setAttribute("onClick","location.href='vacaciones.html'");
            var t = document.createTextNode("escogerVacaciones");
            btn.appendChild(t);
            itemlista.appendChild(btn);
            lista.appendChild(itemlista);
            $(itemlista).show(500);
        }
        var titulo = document.getElementById("titulo");
        var p = document.createElement("p");
        var btn = document.createElement("button");
        btn.setAttribute("id", "crear");
        btn.setAttribute("class", "btn btn-info");
        btn.setAttribute("onClick","location.href='crearInforme.html'");
        var t2 = document.createTextNode("crearInforme");
        btn.appendChild(t2);
        p.appendChild(btn);
        titulo.appendChild(p);
    });
    
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/proyectosjefe", {
        user: user
    }, function (resultado) {
        var lista = document.getElementById("lista-asignacion2");
        for (j = 0; j < resultado.length; j++) {
            var itemlista = document.createElement("li");
            itemlista.setAttribute("class", "list-group-item");
            itemlista.setAttribute("style", "display:none");
            itemlista.textContent = resultado[j].nombre + " ";
            var btn = document.createElement("button");
            btn.setAttribute("id", "iniciar");
            btn.setAttribute("class", "badge");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" iniciarProyecto");
            btn.appendChild(t);
            itemlista.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "asiganar");
            btn.setAttribute("class", "badge");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" asignarTrabajador");
            btn.appendChild(t);
            itemlista.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "informe");
            btn.setAttribute("class", "badge");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" obtenerInforme");
            btn.appendChild(t);
            itemlista.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "planificar");
            btn.setAttribute("class", "badge");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" planificarProyecto");
            btn.appendChild(t);
            itemlista.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "cerrar");
            btn.setAttribute("class", "badge");
            btn.setAttribute("onClick","location.href='cerrarActividades.html'");
            var t = document.createTextNode(" cerrarActividad");
            btn.appendChild(t);
            itemlista.appendChild(btn);
            var btn = document.createElement("button");
            btn.setAttribute("id", "datos");
            btn.setAttribute("class", "badge");
            btn.setAttribute("onClick","location.href=''");
            var t = document.createTextNode(" consultarDatos");
            btn.appendChild(t);
            lista.appendChild(itemlista);
            $(itemlista).show(500);
        }
    });
    
});


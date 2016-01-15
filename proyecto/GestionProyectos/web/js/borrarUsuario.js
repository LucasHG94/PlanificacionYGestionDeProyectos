checkAdmin();
jQuery(function () {
    jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/Trabajadores", {
         
    }, function (resultado) {
        var h = document.createElement("h4");
        var t = document.createTextNode("Trabajadores: ");       
        h.appendChild(t);
        document.body.appendChild(h);
        for (j = 0; j < resultado.length; j++) {
            var p = document.createElement("p");
            var t = document.createTextNode(resultado[j].nombre + " ");       
            p.appendChild(t);
            var btn = document.createElement("a");
            btn.setAttribute("id", "crear");
            btn.setAttribute("href","http://localhost:8080/GestionProyectos/borrarUsuario.html?idP="+resultado[j].id);
            var t = document.createTextNode("crearInforme,  ");
            btn.appendChild(t);
            p.appendChild(btn);
            /*var btn = document.createElement("a");
            btn.setAttribute("id", "modificar");
            btn.setAttribute("href", "");
            var t = document.createTextNode("modificarInforme,  ");
            btn.appendChild(t);
            p.appendChild(btn);*/
            p.appendChild(btn);
            document.body.appendChild(p);
        }
    });
});




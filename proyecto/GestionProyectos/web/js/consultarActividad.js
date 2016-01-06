jQuery(function () {
    var user = getCookie("username");
    var idP = location.search.split('idP=')[1];
    jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/actividades", {
        user: user, idP:idP
    }, function (resultado) {
        for (j = 0; j < resultado.length; j++) {
            var p = document.createElement("p");
            var t = document.createTextNode(resultado[j].nombre + " ");       
            p.appendChild(t);
            var btn = document.createElement("a");
            btn.setAttribute("id", "crear");
            btn.setAttribute("href","http://localhost:8080/GestionProyectos/actividad.html?id="
                    +resultado[j].actividadPK.id+"&nombre="+resultado[j].nombre+"&rol="+resultado[j].rol/*+
                "&fehcaIni="+resultado[j].fechaInicio+"&fechaFin="+resultado[j].fechaFin+"&esf="
                +resultado[j].esfuerzoEstimado*/);
            var t = document.createTextNode("consultar");
            btn.appendChild(t);
            p.appendChild(btn);
            
            document.body.appendChild(p);
        }
    });
});

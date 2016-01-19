/*
 * Script para presentar las actividades de un usuario para un proyecto dado
 */
jQuery(function () {
    var user = getCookie("username");
    var idP = location.search.split('idP=')[1];
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/actividades", {
        user: user, idP:idP
    }, function (resultado) {
        for (j = 0; j < resultado.length; j++) {
            var p = document.createElement("p");
            var t = document.createTextNode(resultado[j].nombre + " ");       
            p.appendChild(t);
            var btn = document.createElement("button");
            btn.setAttribute("id", "crear");
            btn.setAttribute("OnClick","location.href='actividad.html?id="
                    +resultado[j].actividadPK.id+"&nombre="+resultado[j].nombre+"&rol="+resultado[j].rol+
                "&fechaIni="+resultado[j].fechainicio+"&fechaFin="+resultado[j].fechafin+"&esf="
                +resultado[j].esfuerzoestimado+"'");
            var t = document.createTextNode("consultar");
            btn.appendChild(t);
            p.appendChild(btn);
            
            document.body.appendChild(p);
        }
    });
});

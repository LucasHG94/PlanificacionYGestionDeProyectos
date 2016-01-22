/*
 * Script para recoger los datos del informe rellenado por el desarrollador
 * Muestra los datos adecuados y solicita el número de horas
 */

/* global idPs, idActividades, idEtapas */

jQuery(function () {
    var user = getCookie("username");
    idPs[0] = 1;
    idP = idPs[0];
    var lista = document.getElementById("lista-asignacion");
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/actividadesSemana", {
        user: user, idP:idP
    }, function (resultado) {
        longitud=resultado.length;
        for (j = 0; j < resultado.length; j++) {
            idActividades[j] = resultado[j].actividadPK.id;
            idEtapas[j] = resultado[j].actividadPK.idetapa;
            idPs[j] = resultado[j].actividadPK.idproyecto;
            var itemlista = document.createElement("li");
            var poritem = document.createElement("span");
            itemlista.setAttribute("class", "list-group-item");
            itemlista.setAttribute("style", "display:none");
            itemlista.textContent = resultado[j].nombre + " ";
            //etnrada de datos
            var inp = document.createElement("input");
            inp.setAttribute("id","hora0"+j);
            inp.setAttribute("value",0);
            inp.setAttribute("maxlength",3);
            inp.setAttribute("size",3);
            inp.setAttribute("type","text");
            itemlista.appendChild(inp);
            var inp = document.createElement("input");
            inp.setAttribute("id","hora1"+j);
            inp.setAttribute("value",0);
            inp.setAttribute("maxlength",3);
            inp.setAttribute("size",3);
            inp.setAttribute("type","text");
            itemlista.appendChild(inp);
            var inp = document.createElement("input");
            inp.setAttribute("id","hora2"+j);
            inp.setAttribute("value",0);
            inp.setAttribute("maxlength",3);
            inp.setAttribute("size",3);
            inp.setAttribute("type","text");
            itemlista.appendChild(inp);
            
            var inp = document.createElement("input");
            inp.setAttribute("id","hora3"+j);
            inp.setAttribute("value",0);
            inp.setAttribute("maxlength",3);
            inp.setAttribute("size",3);
            inp.setAttribute("type","text");
            itemlista.appendChild(inp);
            var inp = document.createElement("input");
            inp.setAttribute("id","hora4"+j);
            inp.setAttribute("value",0);
            inp.setAttribute("maxlength",3);
            inp.setAttribute("size",3);
            inp.setAttribute("type","text");
            itemlista.appendChild(inp);
            var inp = document.createElement("input");
            inp.setAttribute("id","hora5"+j);
            inp.setAttribute("value",0);
            inp.setAttribute("maxlength",3);
            inp.setAttribute("size",3);
            inp.setAttribute("type","text");
            itemlista.appendChild(inp);
            
            lista.appendChild(itemlista);
            $(itemlista).show(500);
        }
    });
});


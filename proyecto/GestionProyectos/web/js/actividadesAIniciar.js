/*
 * Script para presentar las actividades de un usuario para un proyecto dado
 */
jQuery(function () {
    var user = getCookie("username");
    var idP = location.search.split('idP=')[1];
    var lista = document.getElementById("lista-asignacion");
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/actividadesDisponibles", {
        idP:idP
    }, function (resultado) {
        for (j = 0; j < resultado.length; j++) {
            var itemlista = document.createElement("li");
            itemlista.setAttribute("class", "list-group-item");
            itemlista.setAttribute("style", "display:none");
            itemlista.textContent = resultado[j].nombre + " ";
            var btn = document.createElement("button");
            btn.setAttribute("id", "enviar");
            btn.setAttribute("onclick", "Enviar("+idP+","+resultado[j].actividadPK.id+","+resultado[j].actividadPK.idetapa+")");
            btn.setAttribute("class", "btn btn-info");
            var t = document.createTextNode("Iniciar");
            btn.appendChild(t);
            itemlista.appendChild(btn);
            
            lista.appendChild(itemlista);
            $(itemlista).show(500);
        }
    });
});

function Enviar(idP,idA, idE) {
    var user = getCookie("username");
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/setFechaInicio", {
        idP:idP, idA:idA, idE:idE
    }, function (resultado) {
        if(resultado){window.alert(resultado);
        $("#asignarAlertBad").hide();
        $("#asignarAlertGood").show(500);
        $("#aAlertGoodText").text("Facha guardada");
    }
        else{
            $("#asignarAlertGood").hide();
            $("#asignarAlertBad").show(500);
            $("#aAlertBadText").text("Fecha inválida");
        }
    });
};

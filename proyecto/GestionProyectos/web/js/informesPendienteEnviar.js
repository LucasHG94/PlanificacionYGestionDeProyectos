/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* global res */

function borrarElementos() {
    $("#workercontainer").children().remove();
}

function crearElementoTrabajador(j) {
    borrarElementos();
    var inf=res[j];
    var container = document.getElementById("workercontainer");
    var elemento = document.createElement("div");
    elemento.setAttribute("class", "panel panel-primary");
    var titulo = document.createElement("div");
    titulo.setAttribute("class", "panel-heading");
    titulo.textContent = "Informe seleccionado";
    var cuerpo = document.createElement("div");
    cuerpo.setAttribute("class", "panel-body");
    var listatareas = document.createElement("ul");
    listatareas.setAttribute("class", "list-group");
    var act;
    
    act = document.createElement("li");
    act.setAttribute("class", "list-group-item");
    act.textContent = "Trabajador: "+inf.trabajador.nick;
    listatareas.appendChild(act);
    act = document.createElement("li");
    act.setAttribute("class", "list-group-item");
    act.textContent = "Fecha: "+inf.informesemanalPK.fechasemana;
    listatareas.appendChild(act);
    act = document.createElement("li");
    act.setAttribute("class", "list-group-item");
    act.textContent = "id del proyecto: "+inf.informesemanalPK.idproyecto;
    listatareas.appendChild(act);
    act = document.createElement("li");
    act.setAttribute("class", "list-group-item");
    act.textContent = "id de la actividad: "+inf.informesemanalPK.idactividad;
    listatareas.appendChild(act);
    act = document.createElement("li");
    act.setAttribute("class", "list-group-item");
    act.textContent = "Estado: "+inf.estado;
    listatareas.appendChild(act);
    act = document.createElement("li");
    act.setAttribute("class", "list-group-item");
    act.textContent = "Horas de cada tarea: "+inf.horastarea1+"+"+inf.horastarea2+"+"+inf.horastarea3+"+"+inf.horastarea4+"+"+inf.horastarea5+"+"+inf.horastarea6+"";
    listatareas.appendChild(act);
    
    
    act = document.createElement("li");
    act.setAttribute("class", "list-group-item");
    var btn = document.createElement("button");
    btn.setAttribute("id", "iniciar");
    btn.setAttribute("class", "btn btn-info");
    btn.setAttribute("onClick","location.href=''");
    var t = document.createTextNode("Pasar a aceptado");
    btn.appendChild(t);
    act.appendChild(btn);
    listatareas.appendChild(act);
    
    cuerpo.appendChild(listatareas);
    elemento.appendChild(titulo);
    elemento.appendChild(cuerpo);
    $(elemento).hide();
    $("#workercontainer").append(elemento);
    $(elemento).slideDown(500);
}

jQuery(function () {
    var user = getCookie("username");
    var idP = location.search.split('idP=')[1];
    var lista = document.getElementById("lista-asignacion");
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/informesPendienteEnviar", {
        idP:idP
    }, function (resultado) {
        for (j = 0; j < resultado.length; j++) {
            var itemlista = document.createElement("li");
            itemlista.setAttribute("class", "list-group-item");
            itemlista.setAttribute("style", "display:none");
            itemlista.textContent = resultado[j].trabajador.nick + ", "+
                    resultado[j].informesemanalPK.fechasemana+" ";
            res[j]=resultado[j];
            var btn = document.createElement("button");
            btn.setAttribute("id", "enviar");
            btn.setAttribute("onclick","crearElementoTrabajador("+j+")");
            btn.setAttribute("class", "btn btn-info");
            var t = document.createTextNode("Ver informe");
            btn.appendChild(t);
            itemlista.appendChild(btn);
            
            lista.appendChild(itemlista);
            $(itemlista).show(500);
        }
    });
});


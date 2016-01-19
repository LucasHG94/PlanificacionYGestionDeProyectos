/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function borrarElementos(){
    $("#workercontainer").children().remove();
}

function crearElementoTrabajador(nick, actividades) {
    var container = document.getElementById("workercontainer");
    var elemento = document.createElement("div");
    elemento.setAttribute("class", "panel panel-primary");
    var titulo = document.createElement("div");
    titulo.setAttribute("class", "panel-heading");
    titulo.textContent = "Actividades de " + nick;
    var cuerpo = document.createElement("div");
    cuerpo.setAttribute("class", "panel-body");
    var listatareas = document.createElement("ul");
    listatareas.setAttribute("class", "list-group");
    var act;
    var fechaAct;
    for (i = 0; i < actividades.length; i++) {
        act = document.createElement("li");
        act.setAttribute("class","list-group-item");
        act.textContent = actividades[i].nombre;
        fechaAct = document.createElement("span");
        fechaAct.setAttribute("class", "badge");
        fechaAct.textContent = actividades[i].date;
        act.appendChild(fechaAct);
        listatareas.appendChild(act);
    }
    cuerpo.appendChild(listatareas);
    elemento.appendChild(titulo);
    elemento.appendChild(cuerpo);
    borrarElementos();
    $("#workercontainer").append(elemento);
}

$(function(){
    var a = [{nombre:"blabla",date:"1999-1-2"},{nombre:"blabla",date:"2015-1-2"}];
    crearElementoTrabajador("T1",a);
});

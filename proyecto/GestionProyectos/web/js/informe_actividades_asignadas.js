/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


function borrarElementos() {
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
        act.setAttribute("class", "list-group-item");
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
    $(elemento).hide();
    $("#workercontainer").append(elemento);
    $(elemento).slideDown(500);
}

function cargarProyectos() {
    $.getJSON('/GestionProyectos/webresources/SimpleRoot/proyectos/jefe/' + getCookie("username") + '/todos',
            function (data) {
                var selectProject = $("#selectproject").get(0);
                var b;
                if (data.length > 0) {
                    for (i = 0; i < data.length; i++) {
                        b = document.createElement("option");
                        b.text = data[i].nombre;
                        b.setAttribute("projectid", data[i].id);
                        selectProject.appendChild(b);
                    }
                }

            });
}

function getDatosInforme() {
    var selectproject = document.getElementById("selectproject");
    var pjId = selectproject.options[selectproject.selectedIndex].getAttribute("projectid");
    $.getJSON(patronurl + '/proyecto/' + pjId + '/informes/aa/' + $("#startdate").val(),
            function (data) {
                borrarElementos();
                var i;
                for (i = 0; i < data.length; i++) {
                    crearElementoTrabajador(data[i].nick, data[i].actividades);
                }
            });
}

$(function () {
    var a = [{nombre: "blabla", date: "1999-1-2"}, {nombre: "blabla", date: "2015-1-2"}];
    cargarProyectos();
    $("#startdate").change(function () {
        getDatosInforme();
    });
});

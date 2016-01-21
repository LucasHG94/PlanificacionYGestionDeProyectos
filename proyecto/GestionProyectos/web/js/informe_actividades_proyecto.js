/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var plantillas = '<a href="#" class="list-group-item"> <h4 class="list-group-item-heading">${nombre}</h4> <p class="list-group-item-text">Duracion estimada: ${duracionestimada}</p><p class="list-group-item-text">Duracion real: ${duracionreal}</p><div class="progress"> <div class="progress-bar ${suc} ${active}" role="progressbar" style="width: ${progreso}%"> <span class="sr-only"></span> </div></div></a>';
var plantillad = '<a href="#" class="list-group-item"> <h4 class="list-group-item-heading">${nombre}</h4> <p class="list-group-item-text">Duracion estimada: ${duracionestimada}</p><p class="list-group-item-text">Duracion real: ${duracionreal}</p><div class="progress"> <div class="progress-bar progress-bar-danger ${active}" role="progressbar" style="width: ${progreso}%"> <span class="sr-only"></span> </div></div></a>';

function addActividadA(data) {
    if (!data.excedido) {
        $.tmpl(plantillas, {
            nombre: data.nombre,
            duracionestimada: data.duracionestimada,
            duracionreal: data.duracionreal,
            active: "progress-bar-striped active",
            progreso: data.progreso
        }).appendTo("#aactivas");
    } else {
        $.tmpl(plantillad, {
            nombre: data.nombre,
            duracionestimada: data.duracionestimada,
            duracionreal: data.duracionreal,
            active: "progress-bar-striped active",
            progreso: data.progreso
        }).appendTo("#aactivas");
    }

}

function addActividadF(data) {
    if (!data.excedido) {
        $.tmpl(plantillas, {
            nombre: data.nombre,
            duracionestimada: data.duracionestimada,
            duracionreal: data.duracionreal,
            active: "",
            progreso: 100,
            suc: "progress-bar-success",
        }).appendTo("#afinalizadas");
    } else {
        $.tmpl(plantillad, {
            nombre: data.nombre,
            duracionestimada: data.duracionestimada,
            duracionreal: data.duracionreal,
            active: "",
            progreso: 100
        }).appendTo("#afinalizadas");
    }

}

function cargarDatos() {
    $.getJSON(patronurl + '/usuario/' + getCookie("username") + '/informes/iap/' + GetURLParameter('idP') + '/' + $("#fecha1").val() + '/' + $("#fecha2").val(),
            function (data) {
                $("#afinalizadas").children().remove();
                $("#aactivas").children().remove();
                var i = 0;
                for (i = 0; i < data.activas.length; i++) {
                    addActividadA(data.activas[i]);
                }
                for (i = 0; i < data.finalizadas.length; i++) {
                    addActividadF(data.finalizadas[i]);
                }
            });
}


$(function () {
    $('#datetimepicker6').datetimepicker({
        format: 'YYYY-MM-DD'
    });
    $('#datetimepicker7').datetimepicker({
        useCurrent: false,
        format: 'YYYY-MM-DD'
    });
    $("#datetimepicker6").on("dp.change", function (e) {
        $('#datetimepicker7').data("DateTimePicker").minDate(e.date);
        cargarDatos();
    });
    $("#datetimepicker7").on("dp.change", function (e) {
        $('#datetimepicker6').data("DateTimePicker").maxDate(e.date);
        cargarDatos();
    });
    
});

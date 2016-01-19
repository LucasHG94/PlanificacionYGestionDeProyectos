/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function scrolldown() {
    $('html, body').animate({
        scrollTop: $("#asignarpanel").offset().top
    }, 500);
}

function generarAsignacion(nick, por) {
    var lista = document.getElementById("lista-asignacion");
    var itemlista = document.createElement("li");
    var poritem = document.createElement("span");
    itemlista.setAttribute("class", "list-group-item");
    itemlista.setAttribute("style", "display:none");
    itemlista.textContent = nick;
    poritem.setAttribute("class", "badge");
    poritem.textContent = por;
    itemlista.appendChild(poritem);
    lista.appendChild(itemlista);
    $(itemlista).show(500);
}

function showPlanUpload() {
    $("#projectplanupload").show(500);
}

function cargarProyectos() {
    $.getJSON('/GestionProyectos/webresources/SimpleRoot/proyectos/jefe/' + getCookie("username") + '/noiniciados',
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
                    prepareProjectPlanUpload();
                    showPlanUpload();
                }

            });
}

function prepareProjectPlanUpload() {
    var selectproject = document.getElementById("selectproject");
    var pjId = selectproject.options[selectproject.selectedIndex].getAttribute("projectid");
    var url = '/GestionProyectos/webresources/SimpleRoot/proyectos/' + pjId + '/plan';
    $('#fileupload').fileupload({
        url: url,
        dataType: 'json',
        done: function (e, data) {
            var a = Boolean(data.result);
            if (a) {
                $("#planAlertGood").show();
                $("#selectproject").prop("disabled", true);
                $("#projectplanuploadbtn").hide(300);
                $("#asignarpanel").show(500);
                $("#botonTerminar").show();
                $("#botonTerminar").prop("disabled", true);
            } else {
                $("#planAlertBad").show();
            }
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#progress .progress-bar').css(
                    'width',
                    progress + '%'
                    );
        }
    }).prop('disabled', !$.support.fileInput)
            .parent().addClass($.support.fileInput ? undefined : 'disabled');
    //alert("Ready.");
}

function asignarTrabajador() {
    var selectproject = document.getElementById("selectproject");
    var pjId = selectproject.options[selectproject.selectedIndex].getAttribute("projectid");
    var nickt = $("#nickt").val();
    var porpar = $("#porpar").val();
    var url = '/GestionProyectos/webresources/SimpleRoot/proyectos/' + pjId + '/asignarParticipacion/' + nickt;
    $.getJSON(url,
            {por: porpar},
    function (data) {
        if (data.error) {
            $("#asignarAlertGood").hide();
            $("#asignarAlertBad").show(500);
            $("#aAlertBadText").text(data.mensaje);

        } else {
            $("#asignarAlertBad").hide();
            $("#asignarAlertGood").show(500);
            $("#aAlertGoodText").text(data.mensaje);
            generarAsignacion(data.nick,data.por);
            scrolldown();
            $("#botonTerminar").prop("disabled", false);
        }

    });

}


$(function () {
    checkSesionIniciada();
    cargarProyectos();
    $("#selectproject").change(prepareProjectPlanUpload);
    $("#addWorker").click(asignarTrabajador);
    $("#botonTerminar").click(function(){
        document.location.href="index.html";
    });

});

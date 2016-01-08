/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function showPlanUpload(){
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
                    prepareProjectPlanUpload()
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
                $("#selectproject").prop( "disabled", true );
                $("#projectplanuploadbtn").hide(300);
                $("#asignarpanel").show(500);
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
    alert("Ready.");
}


$(function () {
    checkSesionIniciada();
    cargarProyectos();
    $("#selectproject").change(prepareProjectPlanUpload);

});

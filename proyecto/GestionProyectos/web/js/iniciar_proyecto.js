/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function cargarProyectos() {
    $.getJSON('/GestionProyectos/webresources/SimpleRoot/proyectos/jefe/' + getCookie("username") + '/noiniciados',
            function (data) {
                var selectProject = $("#selectproject").get(0);
                var b;
                for (i = 0; i < data.length; i++) {
                    b = document.createElement("option");
                    b.text = data[i].nombre;
                    selectProject.appendChild(b);
                }
            });
}


$(function () {
    checkSesionIniciada();
    
    var url = '/GestionProyectos/webresources/SimpleRoot/proyectos/1/plan';
    $('#fileupload').fileupload({
        url: url,
        dataType: 'json',
        done: function (e, data) {
            var a = Boolean(data.result);
            if (a) {
                $("#planAlertGood").show();
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

    cargarProyectos();
    
});

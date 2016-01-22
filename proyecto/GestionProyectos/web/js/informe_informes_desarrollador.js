/* 
 * Obtenemos la relación de informes y mostramos su estado
 */
var templates = '<a href="#" class="list-group-item list-group-item-info active">Informe de actividad: ${nombre}<span class="badge">${fecha}</span><span class="badge">${estado}</span></a>';
var templatew = '<a href="#" class="list-group-item list-group-item-warning active">Informe de actividad: ${nombre}<span class="badge">${fecha}</span><span class="badge">${estado}</span></a>';
var templated = '<a href="#" class="list-group-item list-group-item-danger active">Informe de actividad: ${nombre}<span class="badge">${fecha}</span><span class="badge">${estado}</span></a>';
function cargarInforme(data) {
    if (data.estado === 'ACEPTADO') {
        $.tmpl(templates, data).appendTo("#listainformes");
    } else if (data.estado === 'RECHAZADO') {
        $.tmpl(templated, data).appendTo("#listainformes");
    } else {
        $.tmpl(templatew, data).appendTo("#listainformes");
    }

}

function cargarDatos() {
    $.getJSON(patronurl + '/usuario/' + getCookie("username") + '/informes/ia/' + $("#fecha1").val() + '/' + $("#fecha2").val(),
            function (data) {
                $("#listainformes").children().remove();
                var i = 0;
                for (i = 0; i < data.length; i++) {
                    cargarInforme(data[i]);
                }
                ;
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



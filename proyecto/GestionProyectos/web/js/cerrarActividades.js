/*
 * Metodos para cerrar las actividades, las obtienede la bd, las presenta y asigna fecha fin
 */


function cargarActividades() {
    $.getJSON('/GestionProyectos/webresources/SimpleRoot/proyectos/jefe/' + getCookie("username") + '/cerrar',
            function (data) {
                var selectActivity = $("#selectactivity").get(0);
                var b;
                if (data.length > 0) {
                    for (i = 0; i < data.length; i++) {
                        b = document.createElement("option");
                        b.text = data[i].nombre + "";
                        b.setAttribute("nombre", data[i].nombre);
                        selectActivity.appendChild(b);
                    }
                }

            });
}

function getFechaFin() {
    var selectact = document.getElementById("selectactivity");
    var actName = selectactivity.options[selectactivity.selectedIndex].getAttribute("nombre");  
    jQuery("#confirmBtn").click(function () {
    $.getJSON(patronurl + '/cerrarActividad/' + actName + '/fechaFin/' + $("#enddate").val(),
            function (data) {
                if(data){
                    window.alert("Actividad cerrada con fecha "+ $("#enddate").val() );
                    var pagina = 'registrado.html';
                    document.location.href = pagina;
                }else{
                    window.alert("No se puede cerrar con esa fecha");
                    var pagina = 'cerrarActividades.html';
                    document.location.href = pagina;
                }
        });
    });
}


cargarActividades();

$('#enddateb').datetimepicker({
        format: 'YYYY-MM-DD'
    });
$('#enddateb').on("dp.change",function(e){
        getFechaFin();
    });   
    
    
jQuery("#cancelBtn").click(function () {
        var pagina = 'registrado.html';
        document.location.href = pagina;
});



    
      
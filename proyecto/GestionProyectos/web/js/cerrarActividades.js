
//checkSesionIniciada();
/*jQuery(function () {
    var user = getCookie("username");   
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/cerrarActividad", {
        user: user
    }, function (resultado) {
        for (j = 0; j < resultado.length; j++) {
            var p = document.createElement("p");
            var t = document.createTextNode(resultado[j].nombre + " ");       
            p.appendChild(t);
            var btn = document.createElement("button");
            btn.setAttribute("id", "crear");
            btn.setAttribute("OnClick","location.href='formularioCierre.html'");
            var t = document.createTextNode("Cerrar");
            btn.appendChild(t);
            p.appendChild(btn);
            
            document.body.appendChild(p);
        }
    });
});*/


function cargarActividades() {
    $.getJSON('/GestionProyectos/webresources/SimpleRoot/proyectos/jefe/' + getCookie("username") + '/cerrar',
            function (data) {
                var selectActivity = $("#selectactivity").get(0);
                var b;
                if (data.length > 0) {
                    for (i = 0; i < data.length; i++) {
                        b = document.createElement("option");
                        b.text = data[i].nombre + "";
                        b.setAttribute("activityId", data[i].id);
                        selectActivity.appendChild(b);
                    }
                }

            });
}


cargarActividades();

$(function () {
    var a = [{nombre: "blabla", date: "1999-1-2"}, {nombre: "blabla", date: "2015-1-2"}];
    
    $('#enddateb').datetimepicker({
        format: 'YYYY-MM-DD'
    });
    
    $('#startdateb').on("dp.change",function(e){
        
    });
});
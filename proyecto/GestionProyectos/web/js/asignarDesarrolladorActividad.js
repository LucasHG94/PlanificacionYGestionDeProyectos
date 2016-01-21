/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function cargarActividades() {
    $.getJSON('/GestionProyectos/webresources/SimpleRoot/proyectos/jefe/' + getCookie("username") + '/actividadesAsignables',
            function (data) {
                var selectActivity = $("#selectactivity").get(0);
                var b;
                if (data.length > 0) {
                    for (i = 0; i < data.length; i++) {
                        b = document.createElement("option");
                        b.text = data[i].nombre + "";
                        b.setAttribute("activityId", data[i].id);
                        b.setAttribute("phaseId", data[i].idetapa);
                        b.setAttribute("proyectId", data[i].idproyecto);
                        selectActivity.appendChild(b);
                    }
                }

            });
}

function cargarDesarrolladores() {
    $.getJSON('/GestionProyectos/webresources/SimpleRoot/proyectos/jefe/' + getCookie("username") + '/desarrolladoresAsignables',
            function (data) {
                var selectActivity = $("#selectworker").get(0);
                var b;
                if (data.length > 0) {
                    for (i = 0; i < data.length; i++) {
                        b = document.createElement("option");
                        b.text = data[i].nombre + "";
                        b.setAttribute("nick", data[i].nick);
                        selectActivity.appendChild(b);
                    }
                }

            });
}

cargarActividades();
cargarDesarrolladores();

jQuery("#cancelBtn").click(function () {
        var pagina = 'registrado.html';
        document.location.href = pagina;
});

jQuery("#confirmBtn").click(function () {
    var fechaFin = $("#enddate").val();
   
        
});

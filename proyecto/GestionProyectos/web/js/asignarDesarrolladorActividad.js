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
                        b.setAttribute("activityId", data[i].actividadPK.id);
                        b.setAttribute("phaseId", data[i].actividadPK.idetapa);
                        b.setAttribute("proyectId", data[i].actividadPK.idproyecto);
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
                        b.text = data[i].nick + "";
                        b.setAttribute("nick", data[i].nick);
                        selectActivity.appendChild(b);
                    }
                }

            });
}


cargarActividades();
cargarDesarrolladores();

function asignarDesarrollador() {
    var selectproject = document.getElementById("selectactivity");
    var aId = selectproject.options[selectproject.selectedIndex].getAttribute("activityId");
    var eid = selectproject.options[selectproject.selectedIndex].getAttribute("phaseId");
    var pId = selectproject.options[selectproject.selectedIndex].getAttribute("proyectId");
    var selectprojectp = document.getElementById("selectworker");
    var nickk = selectprojectp.options[selectproject.selectedIndex].getAttribute("nick");
    $.getJSON(patronurl + '/asignar/desarrollador/actividad/'+nickk+'/'+pId+'/'+eid+'/'+aId+'',
            function (data) {
                var b = Boolean(data);
                if(b){
                    document.location.href = "registrado.html";
                }
                    
            });

}

jQuery("#cancelBtn").click(function () {
    var pagina = 'registrado.html';
    document.location.href = pagina;
});

jQuery("#confirmBtn").click(function () {
    
    asignarDesarrollador();


});

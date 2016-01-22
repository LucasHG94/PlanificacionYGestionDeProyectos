

jQuery(function () {
    var user = getCookie("username");
    var idP = location.search.split('idP=')[1];
    
    var btn = document.getElementById("b1");
    btn.setAttribute("onclick", "location.href='informe_actividades_asignadas.html'");
    
    var btn = document.getElementById("b2");
    btn.setAttribute("onclick", "location.href='informesPendienteEnviar.html?idP="+idP+"'");
    
    var btn = document.getElementById("b3");
    btn.setAttribute("onclick", "location.href='informesPendienteAprobar.html?idP="+idP+"'");
    
    var btn = document.getElementById("b4");
    btn.setAttribute("onclick", "location.href=''");
    
    var btn = document.getElementById("b5");
    btn.setAttribute("onclick", "location.href=''");
    
    var btn = document.getElementById("b6");
    btn.setAttribute("onclick", "location.href='informe_actividades_proyecto.html?idP="+idP+"'");
    
    var btn = document.getElementById("b7");
    btn.setAttribute("onclick", "location.href=''");
    
    var btn = document.getElementById("b8");
    btn.setAttribute("onclick", "location.href=''");
    
    var btn = document.getElementById("b9");
    btn.setAttribute("onclick", "location.href=''");
            
    
});



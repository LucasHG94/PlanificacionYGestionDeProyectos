/*
 * Presentación de los datos correspondientes a una actividad de un desarrollador
 */

jQuery(function (){
    var user = getCookie("username");
    var id = parametro("id",window.location.href );
    var nombre = parametro("nombre",window.location.href );
    var rol = parametro("rol",window.location.href );
    var fechaIni = parametro("fechaIni",window.location.href);
    var fechaFin = parametro("fechaFin",window.location.href);
    var esf = parametro("esf",window.location.href);
    
    var titulo = document.getElementById("titulo");
    var lista = document.getElementById("lista-asignacion");
    
    var replaced = nombre.replace(/%20/g, " ");
    titulo.textContent = replaced;
    
    var itemlista = document.createElement("li");
    itemlista.setAttribute("class", "list-group-item");
    itemlista.setAttribute("style", "display:none");
    itemlista.textContent = "identificador: "+id;
    lista.appendChild(itemlista);
    $(itemlista).show(500);
    
    var itemlista = document.createElement("li");
    itemlista.setAttribute("class", "list-group-item");
    itemlista.setAttribute("style", "display:none");
    itemlista.textContent = "rol: "+rol;
    lista.appendChild(itemlista);
    $(itemlista).show(500);
    
    var itemlista = document.createElement("li");
    itemlista.setAttribute("class", "list-group-item");
    itemlista.setAttribute("style", "display:none");
    itemlista.textContent = "fecha de inicio: "+fechaIni;
    lista.appendChild(itemlista);
    $(itemlista).show(500);
    
    var itemlista = document.createElement("li");
    itemlista.setAttribute("class", "list-group-item");
    itemlista.setAttribute("style", "display:none");
    itemlista.textContent = "fecha de finalizacion: "+fechaFin;
    lista.appendChild(itemlista);
    $(itemlista).show(500);
    
    var itemlista = document.createElement("li");
    itemlista.setAttribute("class", "list-group-item");
    itemlista.setAttribute("style", "display:none");
    itemlista.textContent = "esfuerzo estimado: "+esf;
    lista.appendChild(itemlista);
    $(itemlista).show(500);
        
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/actividades", {
        user: user, idP:idP
    }, function (resultado) {
        jQuery("#resultado span").text(resultado);
    });

});


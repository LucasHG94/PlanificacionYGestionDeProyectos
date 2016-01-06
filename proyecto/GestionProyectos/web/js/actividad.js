//TODO completar datos
function rellena() {
    var user = getCookie("username");
    var id = parametro("id",window.location.href );
    var nombre = parametro("nombre",window.location.href );
    var rol = parametro("rol",window.location.href );
        var p = document.createElement("p");
        var t = document.createTextNode("identificador: "+id);       
        p.appendChild(t);
        document.body.appendChild(p);
        
        var p = document.createElement("p");
        var t = document.createTextNode("nombre: "+nombre);       
        p.appendChild(t);
        document.body.appendChild(p);
        
        var p = document.createElement("p");
        var t = document.createTextNode("rol: "+rol);       
        p.appendChild(t);
        document.body.appendChild(p);

};


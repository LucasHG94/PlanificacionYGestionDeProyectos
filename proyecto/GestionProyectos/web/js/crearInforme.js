/*
 * Script para recoger los datos del informe rellenado por el desarrollador
 * 
 */

jQuery(function () {
    var user = getCookie("username");
    var idP = location.search.split('idP=')[1];
    jQuery.get("http://localhost:8080/GestionProyectos/webresources/SimpleRoot/actividades"/*+"Semana"*/, {
        user: user, idP:idP
    }, function (resultado) {
        for (j = 0; j < resultado.length; j++) {
            var p = document.createElement("p");
            var t = document.createTextNode(resultado[j].nombre + " ");       
            p.appendChild(t);
            //etnrada de datos
            var inp = document.createElement("input");
            inp.setAttribute("id","hora0"+j);
            inp.setAttribute("value",0);
            inp.setAttribute("type","text");
            p.appendChild(inp);
            var inp = document.createElement("input");
            inp.setAttribute("id","hora1"+j);
            inp.setAttribute("value",0);
            inp.setAttribute("type","text");
            p.appendChild(inp);
            var inp = document.createElement("input");
            inp.setAttribute("id","hora2"+j);
            inp.setAttribute("value",0);
            inp.setAttribute("type","text");
            p.appendChild(inp);
            
            document.body.appendChild(p);
        }
    });
    /*var p = document.createElement("p");
    var btn = document.createElement("button");
    btn.setAttribute("id", "crear");
    btn.setAttribute("onClick", "funcionCrear");
    btn.type = "button";
    var t = document.createTextNode("crear");
    btn.appendChild(t);
    p.appendChild(btn);

    document.body.appendChild(p);*/
});


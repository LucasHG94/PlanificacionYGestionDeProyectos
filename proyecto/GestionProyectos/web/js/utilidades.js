/*
 * Funciones útiles para el manejo de las cookies y para extraer parámetros
 * de una url
 */

var patronurl = '/GestionProyectos/webresources/SimpleRoot';
function setCookie(cname, cvalue, exdays) {
    var d = new Date();
    d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
    var expires = "expires=" + d.toUTCString();
    document.cookie = cname + "=" + cvalue + "; " + expires;
}
function getCookie(cname) {
    var name = cname + "=";
    var ca = document.cookie.split(';');
    for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) === ' ')
            c = c.substring(1);
        if (c.indexOf(name) === 0)
            return c.substring(name.length, c.length);
    }
    return "";
}

function checkCookie() {
    var user = getCookie("username");
    if (user !== "") {
        window.alert("Welcome again " + user);
    } else {
        user = window.prompt("Please enter your name:", "");
        if (user !== "" && user !== null) {
            setCookie("username", user, 365);
        }
    }
}

function parametro(name, url) {
    if (!url)
        url = location.href;
    name = name.replace(/[\[]/, "\\\[").replace(/[\]]/, "\\\]");
    var regexS = "[\\?&]" + name + "=([^&#]*)";
    var regex = new RegExp(regexS);
    var results = regex.exec(url);
    return results === null ? null : results[1];
}

function checkAdmin() {
    var user = getCookie("username");
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/admin", {
        user: user
    }, function (resultado) {
        if (resultado != 1) {
            window.alert("Acceso denegado");
            var pagina = 'index.html';
            document.location.href = pagina;

        }
    });
}

function checkSesionIniciada() {
    var user = getCookie("username");
    if (user === null) {
        window.alert("Inicio de sesion necesario");
        var pagina = 'index.html';
        document.location.href = pagina;
    }
}

function deshabilitarElemento(id,b){
    $("#"+id).prop("disabled", b);
}

function cambiarClase(id,clase,state){
    $("#"+id).toggleClass(clase,state);
}


function GetURLParameter(sParam)
{
    var sPageURL = window.location.search.substring(1);
    var sURLVariables = sPageURL.split('&');
    for (var i = 0; i < sURLVariables.length; i++)
    {
        var sParameterName = sURLVariables[i].split('=');
        if (sParameterName[0] == sParam)
        {
            return sParameterName[1];
        }
    }
}


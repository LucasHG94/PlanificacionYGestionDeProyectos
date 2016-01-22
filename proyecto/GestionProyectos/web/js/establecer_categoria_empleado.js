/*
 * Metodos para cambiar la categoría de un empleado
 */



function usuarioBien(estado) {
    cambiarClase("userformgroup", "has-success", estado);
    cambiarClase("userformgroup", "has-error", !estado);
}

function getNick(){
    return $("#user").val();
}

function getCat(){
    return $("#cat option:selected").text();
}

function isTrabajador() {
    var nick = getNick();
    $.getJSON(patronurl + '/trabajador/' + nick + '/existe',
            function (data) {
                var b = Boolean(data);
                deshabilitarElemento("cat",!b);
                deshabilitarElemento("asignarcat",!b);
                usuarioBien(b);
            });
}

function asignarCat(){
    $.getJSON(patronurl + '/trabajador/' + getNick() + '/'+getCat(),
            function (data) {
                var b = Boolean(data);
                if(b){
                    alert("Se asigno correctamente la categoria");
                }else{
                    alert("Ocurrio un error, vuelve a intentarlo.");
                }
            });
}



checkAdmin();
$(function () {
    deshabilitarElemento("cat", true);
    deshabilitarElemento("asignarcat",true);
    $("#user").keyup(isTrabajador);
    $("#asignarcat").click(asignarCat);
});


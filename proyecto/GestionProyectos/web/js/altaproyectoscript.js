/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//comprobar que la sesion esta iniciada como admin
var c1=false;
var c2=false;

function nombreDisponible(nombre, callback) {
    var url = "http://localhost:8080/GestionProyectos/webresources/SimpleRoot/proyectos/nombreDisponible";
    $.getJSON(url, {nombre: nombre},
            callback);
}

function jefeDisponible(nick, callback){
    var url = "http://localhost:8080/GestionProyectos/webresources/SimpleRoot/trabajador/"+nick+"/disponibleParaJefe";
    $.getJSON(url,callback);
}

function updateButton(){
    if(c1&&c2){
        enableButton();
    }else{
        disableButton();
    }
}

function onClickPname(event){
    //alert("haasadf");
    nombreDisponible($("#pname").val(),setValidation);
}

function onKeyUpPnick(){
    jefeDisponible($("#pnick").val(),setValidationTrabajador);
}

function disableButton(){
    var button = $("#crearProyecto");
    button.toggleClass("disabled", true);
    button.prop("disabled",true);
    
}

function enableButton(){
    var button = $("#crearProyecto");
    button.toggleClass("disabled", false);
    button.prop("disabled",false);
}

function setValidationTrabajador(b){
    var validation = $("#validacionJefe");
    b = Boolean(b);
    if(b===false){
        validation.show();
        c2=false;
    }else{
        validation.hide();
        c2=true;
    }
    updateButton();
    
}

function setValidation(b){
    var validacion = $("#validacion");
    b = Boolean(b);
    if(b===false){
        validacion.show();
        c1=false;
    }else{
        validacion.hide();
        c1=true;
    }
    updateButton();
}

function crearProyecto(){
    //alert("Creando.");
    var url = "http://localhost:8080/GestionProyectos/webresources/SimpleRoot/proyectos/nuevo";
    $.getJSON(url,{nombre: $("#pname").val(), nickjefe:$("#pnick").val()},function(json){
        alert("Proyecto creado con exito.");
    });
    
    
}

checkAdmin();
$(document).ready(function(){
    $("#pname").keyup(onClickPname);
    $("#pnick").keyup(onKeyUpPnick);
    $("#crearProyecto").click(crearProyecto);
});







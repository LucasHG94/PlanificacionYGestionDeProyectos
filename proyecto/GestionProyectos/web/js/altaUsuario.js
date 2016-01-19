/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
checkAdmin();

jQuery("#cancelBtn").click(function () {
        var pagina = 'admin.html';
        document.location.href = pagina;
});

jQuery("#confirmBtn").click(function () {
    var nick = $("#nick").val();
    var pass = $("#password").val();
    var pass2 = $("#password").val();
    var cat = $("#categoria").val();
    if(pass != pass2){
        window.alert("Las contraseñas no coinciden");
        var pagina = 'alta_Usuario.html';
        document.location.href = pagina;
    }
    if(cat<1 && cat>4){
        window.alert("La categoria debe estar entre 1 y 4");
        var pagina = 'alta_Usuario.html';
        document.location.href = pagina;
    }
    jQuery.get("/GestionProyectos/webresources/SimpleRoot/admin/newUser", {
        nick: nick, pass:pass, cat:cat
    }, function () {      
        var pagina = 'admin.html';
        document.location.href = pagina;
    });


});


/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
checkAdmin();

jQuery("#cancelBtn").click(function () {
        var pagina = 'http://virtual.lab.inf.uva.es:27092/GestionProyectos/admin.html';
        document.location.href = pagina;
});

jQuery("#confirmBtn").click(function () {
    var nick = $("#nick").val();
    var pass = $("#password").val();
    var pass2 = $("#password").val();
    var cat = $("#categoria").val();
    if(pass != pass2){
        window.alert("Las contraseñas no coinciden");
        var pagina = 'http://virtual.lab.inf.uva.es:27092/GestionProyectos/alta_Usuario.html';
        document.location.href = pagina;
    }
    if(cat<1 && cat>4){
        window.alert("La categoria debe estar entre 1 y 4");
        var pagina = 'http://virtual.lab.inf.uva.es:27092/GestionProyectos/alta_Usuario.html';
        document.location.href = pagina;
    }
    jQuery.get("http://virtual.lab.inf.uva.es:27092/GestionProyectos/webresources/SimpleRoot/admin/newUser", {
        nick: nick, pass:pass, cat:cat
    }, function () {      
        var pagina = 'http://virtual.lab.inf.uva.es:27092/GestionProyectos/admin.html';
        document.location.href = pagina;
    });


});


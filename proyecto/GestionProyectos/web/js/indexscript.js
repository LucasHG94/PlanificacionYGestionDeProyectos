/*
 * Script para iniciar la sesión de los distitntos usuarios de la aplicación
 * Valida los datos y distingue de tipo de usuario
 */
jQuery("#loguearBtn").click(function () {
                var user = jQuery("#user").val();
                var password = jQuery("#password").val();
                jQuery.get("/GestionProyectos/webresources/SimpleRoot/usuario", {
                    user: user, password: password
                }, function (resultado) {
                    //jQuery("#resultado span").text(resultado);
                    if (resultado == 0) {
                        window.alert("Datos incorrectos");
                    }else{
                        setCookie("username", user, 1);
                        if (resultado == 1) {
                            var pagina = 'admin.html';
                            document.location.href = pagina;
                        } else {
                            var pagina = 'registrado.html';
                            document.location.href = pagina;
                        }

                    }

                });
            });


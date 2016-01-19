/*
 * Script para iniciar la sesión de los distitntos usuarios de la aplicación
 */
jQuery("#loguearBtn").click(function () {
                var user = jQuery("#user").val();
                var password = jQuery("#password").val();
                jQuery.get("http://virtual.lab.inf.uva.es:27092/GestionProyectos/webresources/SimpleRoot/usuario", {
                    user: user, password: password
                }, function (resultado) {
                    //jQuery("#resultado span").text(resultado);
                    if (resultado != 0) {
                        setCookie("username", user, 1);
                        if (resultado == 1) {
                            var pagina = 'http://virtual.lab.inf.uva.es:27092/GestionProyectos/admin.html';
                            document.location.href = pagina;
                        } else {
                            var pagina = 'http://virtual.lab.inf.uva.es:27092/GestionProyectos/registrado.html';
                            document.location.href = pagina;
                        }

                    }

                });
            });


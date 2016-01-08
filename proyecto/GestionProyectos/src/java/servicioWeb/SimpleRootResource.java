/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicioWeb;

import com.owlike.genson.Genson;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import modelo.Actividad;
import modelo.ActividadPK;
import modelo.Administrador;
import modelo.Dedicacion;
import modelo.Etapa;
import modelo.EtapaPK;
import modelo.Proyecto;
import modelo.Trabajador;
import modelo.Vacaciones;
import modelo.VacacionesPK;
import org.joda.time.DateTime;
import org.json.JSONArray;
import org.json.JSONObject;
import persistencia.ActividadFacadeLocal;
import persistencia.AdministradorFacadeLocal;
import persistencia.DedicacionFacadeLocal;
import persistencia.EtapaFacadeLocal;
import persistencia.ProyectoFacadeLocal;
import persistencia.TrabajadorFacadeLocal;
import persistencia.VacacionesFacadeLocal;

/**
 * REST Web Service
 *
 * @author sturm
 */
@Path("SimpleRoot") //http://localhost:8080/GestionProyectos/webresources/SimpleRoot
public class SimpleRootResource {

    @Context
    private UriInfo context;

    @EJB
    private ProyectoFacadeLocal proyectoFacade;

    @EJB
    private TrabajadorFacadeLocal trabajadorFacade;

    @EJB
    private DedicacionFacadeLocal dedicacionFacade;

    @EJB
    private AdministradorFacadeLocal administradorFacade;

    @EJB
    private ActividadFacadeLocal actividadFacade;

    @EJB
    private VacacionesFacadeLocal vacacionesFacade;

    @EJB
    private EtapaFacadeLocal etapaFacade;

    /**
     * Creates a new instance of SimpleRootResource
     */
    public SimpleRootResource() {
    }

    /**
     * Retrieves representation of an instance of servicioWeb.SimpleRootResource
     *
     * @return an instance of java.lang.String
     *
     * @GET
     * @Produces("application/xml") public String getXml() { //TODO return
     * proper representation object throw new UnsupportedOperationException(); }
     */
    /**
     * PUT method for updating or creating an instance of SimpleRootResource
     *
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    //@Consumes("application/xml")
    public void getTest(String content) {
        /*Proyecto p = proyectoFacade.find(3);
         Etapa e = new Etapa(new EtapaPK(p.getId(), 1));
         e.setNombre("Primera");
         Actividad a = new Actividad(new ActividadPK(p.getId(), e.getEtapaPK().getId(), 1));
         a.setNombre("Una y ya");
         Actividad b = new Actividad(new ActividadPK(p.getId(), e.getEtapaPK().getId(), 2));
         b.setNombre("Otra y ya");
         //etapaFacade.create(e);
         actividadFacade.create(a);
         actividadFacade.create(b);
         System.out.println("Esta o que");*/
        System.out.println("aaaah");
    }

    @GET
    @Produces("application/json")
    @Path("/test/test1")
    public boolean test1(@QueryParam("testdata") String test) {
        /*Proyecto p = proyectoFacade.find(3);
         Etapa e = new Etapa(new EtapaPK(p.getId(), 1));
         e.setNombre("Primera");
         Actividad a = new Actividad(new ActividadPK(p.getId(), e.getEtapaPK().getId(), 1));
         a.setNombre("Una y ya");
         Actividad b = new Actividad(new ActividadPK(p.getId(), e.getEtapaPK().getId(), 2));
         b.setNombre("Otra y ya");
         etapaFacade.create(e);
         actividadFacade.create(a);
         actividadFacade.create(b);
         */

        Actividad una = actividadFacade.find(new ActividadPK(3, 1, 1));
        Actividad otra = actividadFacade.find(new ActividadPK(3, 1, 2));
        //una.getActividadCollection().add(otra);
        //actividadFacade.edit(una);

        System.out.println(una.getActividadCollection().size());

        System.out.println(test);
        return true;
    }

    @GET
    @Produces("application/json")
    @Path("/proyectos/nombreDisponible")
    public boolean nombreDisponible(@QueryParam("nombre") String nombre) {
        return proyectoFacade.getByName(nombre).isEmpty();
    }

    @POST
    @Produces("application/json")
    @Path("/proyectos/{id}/plan")
    public boolean asignarPlanProyecto(@PathParam("id") String id, String data) {
        try {
            String[] tmp = data.split("octet-stream");
            data = tmp[tmp.length - 1].split("------WebKit")[0];
            System.out.println("Peticion detectada: " + data);
            JSONArray plan = new JSONArray(data);
            JSONObject actividad;
            JSONArray pre;
            Proyecto proyecto = proyectoFacade.find(Integer.valueOf(id));
            DateTime today = new DateTime(new Date());
            proyecto.setFechainicio(today.toDate());
            List<Actividad> actividades = new ArrayList<>();
            Etapa etapa = new Etapa();
            List<Etapa> etapas = new ArrayList<>();
            String nombre;
            int duracionEstimada;
            String rol;
            JSONObject apjo; //actividad json object
            int etapaidcont = 1;
            int actividadidcont = 1;
            etapa = new Etapa(new EtapaPK(proyecto.getId(), etapaidcont++));
            for (Object j : plan) {
                actividad = (JSONObject) j;
                nombre = actividad.getString("nombre");
                System.out.println(nombre);
                duracionEstimada = actividad.getInt("duracion_estimada");
                System.out.println(duracionEstimada);
                if (duracionEstimada > 0) {
                    System.out.println(actividad.get("rol"));
                }

                Actividad a = new Actividad(new ActividadPK(proyecto.getId(), etapa.getEtapaPK().getId(), actividadidcont++));
                a.setNombre(nombre);
                a.setRol(actividad.getString("rol"));
                a.setEsfuerzoestimado(duracionEstimada);
                pre = actividad.getJSONArray("pre");
                if (pre.length() > 0) {
                    for (int i = 0; i < pre.length(); i++) {
                        Actividad atmp = actividades.get(pre.getInt(i));
                        if (atmp.getActividadCollection() == null) {
                            atmp.setActividadCollection(new ArrayList<>());
                            System.out.println("ActividadCollection vacio.");
                        }
                        actividades.get(pre.getInt(i)).getActividadCollection().add(a);
                    }
                } else {
                    //a.setFechainicio(today.toDate());
                    //a.setFechafin(new DateTime(a.getFechainicio()).plusDays(duracionEstimada));
                }

                if (actividad.getInt("duracion_estimada") == 0) {
                    System.out.println("Etapa encontrada.");
                    etapa.setNombre(nombre);
                    etapas.add(etapa);
                    etapa = new Etapa(new EtapaPK(proyecto.getId(), etapaidcont++));
                    actividadidcont = 1;
                }

                actividades.add(a);

                System.out.println();
                System.out.println("pre: " + pre.length());

            }
            
            
            for (Etapa eTest : etapas) {
                System.out.println("Etapa: " + eTest.getNombre());
                System.out.println("Id: " + eTest.getEtapaPK().getId());
                System.out.println("GUARDANDO");
                etapaFacade.create(eTest);
                System.out.println("Etapa guardada.");
            }
            
            for(int i=actividades.size()-1; i>=0; i--){
                Actividad aTest = actividades.get(i);
                System.out.println("----------");
                System.out.println("Actividad: " + aTest.getNombre());
                System.out.println("Actividad id: " + aTest.getActividadPK().getId());
                System.out.println("Esfuerzo estimado: " + aTest.getEsfuerzoestimado());
                System.out.println("Rol: " + aTest.getRol());
                System.out.println("Num etapa: " + aTest.getActividadPK().getIdetapa());
                if (aTest.getActividadCollection() != null) {
                    System.out.println("Precedidas: " + aTest.getActividadCollection().size());
                    if (aTest.getActividadCollection().size() > 0) {
                        System.out.println("Precedidas Ejemplo: " + new ArrayList<>(aTest.getActividadCollection()).get(0).getNombre());
                    }
                }
                System.out.println("----------");
                System.out.println("GUARDANDO");
                actividadFacade.create(aTest);
                System.out.println("Actividad guardada.");
            }

            
            
            
            

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @GET
    @Produces("application/json")
    @Path("/proyectos/jefe/{nick}/noiniciados")
    public List<Proyecto> getProyectosNoIniciados(@PathParam("nick") String nickJefe) {
        List<Proyecto> proyectos = getProyectosJefe(nickJefe);
        List<Proyecto> proyectosNoIniciados = new ArrayList<>();
        for (Proyecto p : proyectos) {
            if (p.getFechainicio() == null) {
                proyectosNoIniciados.add(p);
            }
        }
        return proyectosNoIniciados;
    }

    @GET
    @Produces("application/json")
    @Path("/trabajador/{nick}/disponibleParaJefe")
    public boolean trabajadorDisponibleJefe(@PathParam("nick") String nick) {
        System.out.println("Trabajador disponible para jefe");
        if (trabajadorFacade.find(nick) == null) {
            return false;
        }
        List<Proyecto> proyectos = getProyectosJefe(nick);
        Date hoy = new Date();
        for (Proyecto p : proyectos) {
            if (p.getFechafin() != null && p.getFechafin().compareTo(hoy) >= 0) {
                return false;
            }
        }
        return true;
    }

    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/proyectos/nuevo")
    public boolean nuevoProyecto(@QueryParam("nombre") String nombre, @QueryParam("nickjefe") String nickjefe) {
        int id = proyectoFacade.findAll().size() + 1;
        Proyecto p = new Proyecto(id, nickjefe);
        p.setNombre(nombre);
        proyectoFacade.create(p);
        return true;
    }

    @GET
    @Produces("application/json")
    @Path("/proyectos")
    public List<Proyecto> getProyectos(@QueryParam("user") String nombre) {
        List<Dedicacion> dedicaciones = dedicacionFacade.findAll();
        Trabajador t = trabajadorFacade.find(nombre);
        List<Dedicacion> dedicFilt = new ArrayList<>();
        for (Dedicacion item : dedicaciones) {
            if (item.getTrabajador().getNick().equals(t.getNick())) {
                dedicFilt.add(item);
            }
        }

        List<Proyecto> pFiltrado = new ArrayList<>();
        for (Dedicacion item : dedicFilt) {
            pFiltrado.add(item.getProyecto());
        }
        return pFiltrado;
    }

    @GET
    @Produces("application/json")
    @Path("/proyectosjefe")
    public List<Proyecto> getProyectosJefe(@QueryParam("user") String nombre) {
        Trabajador t = trabajadorFacade.find(nombre);
        List<Proyecto> pJefe = new ArrayList<>();
        List<Trabajador> trabajadores = trabajadorFacade.findAll();
        List<Proyecto> proyectos = proyectoFacade.findAll();
        for (Proyecto itemp : proyectos) {
            if (itemp.getNickjefe().equals(t.getNick())) {
                pJefe.add(itemp);
            }
        }
        return pJefe;
    }

    @GET
    @Path("/usuario")
    public int login(@QueryParam("user") String nombre, @QueryParam("password") String contraseña) {
        List<Trabajador> trabajadores = trabajadorFacade.findAll();
        List<Administrador> admins = administradorFacade.findAll();
        for (Trabajador item : trabajadores) {
            if (item.getNick().equals(nombre)) {
                if (item.getPassword().compareTo(contraseña) == 0) {
                    return 2;
                }
            }
        }
        for (Administrador item : admins) {
            if (item.getNick().equals(nombre)) {
                if (item.getPassword().compareTo(contraseña) == 0) {
                    return 1;
                }
            }
        }
        return 0;
    }

    @GET
    @Path("/admin")
    public int checkAdmin(@QueryParam("user") String nombre) {
        List<Administrador> admins = administradorFacade.findAll();
        for (Administrador item : admins) {
            if (item.getNick().equals(nombre)) {
                return 1;
            }
        }
        return 0;
    }

    @GET
    @Produces("application/json")
    @Path("/actividades")
    public List<Actividad> getActividades(@QueryParam("user") String nombre, @QueryParam("idP") int idProyecto) {
        Trabajador t = trabajadorFacade.find(nombre);
        Proyecto p = proyectoFacade.find(idProyecto);
        List<Actividad> actividades = actividadFacade.findAll();
        List<Actividad> actividadesTrabajador = new ArrayList<>();
        for (Actividad item : actividades) {
            if (item.getTrabajadorCollection().contains(t) & item.getEtapa().getProyecto().equals(p)) {
                actividadesTrabajador.add(item);
            }
        }
        return actividadesTrabajador;
    }

    @PUT
    @Produces("application/json")
    @Path("/vacaciones")
    public void setVacaciones(@QueryParam("user") String nombre,
            @QueryParam("ano1") int ano1, @QueryParam("mes1") int mes1, @QueryParam("dia1") int dia1,
            @QueryParam("ano2") int ano2, @QueryParam("mes2") int mes2, @QueryParam("dia2") int dia2) {
        Trabajador t = trabajadorFacade.find(nombre);
        List<Vacaciones> vacaciones = vacacionesFacade.findAll();
        List<Vacaciones> vacacionesTrabajador = new ArrayList<>();
        for (Vacaciones item : vacaciones) {
            if (item.getTrabajador().equals(t)) {
                vacacionesTrabajador.add(item);
            }
        }
        System.out.println("---->" + ano1);
        Date fecha1 = new Date();
        fecha1.setDate(dia1);
        fecha1.setMonth(mes1 - 1);
        fecha1.setYear(ano1 - 1900);//arreglar esto
        vacacionesTrabajador.get(0).getVacacionesPK().setFechasemana(fecha1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha1);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        vacacionesTrabajador.get(1).getVacacionesPK().setFechasemana(cal.getTime());

        Date fecha2 = new Date();
        fecha1.setDate(dia2);
        fecha1.setMonth(mes2 - 1);
        fecha1.setYear(ano2 - 1900);//arreglar esto
        vacacionesTrabajador.get(2).getVacacionesPK().setFechasemana(fecha2);
        cal.setTime(fecha1);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        vacacionesTrabajador.get(3).getVacacionesPK().setFechasemana(cal.getTime());
    }
}

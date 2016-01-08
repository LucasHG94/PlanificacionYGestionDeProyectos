/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicioWeb;

import com.owlike.genson.Genson;
import java.util.ArrayList;
import java.util.Calendar;
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
    }
    
    @GET
    @Consumes("application/json")
    @Path("/test/test1")
    public boolean test1(){
        Proyecto p = proyectoFacade.find(3);
        Etapa e = new Etapa(new EtapaPK(p.getId(), 1));
        e.setNombre("Primera");
        Actividad a = new Actividad(new ActividadPK(p.getId(), e.getEtapaPK().getId(), 1));
        a.setNombre("Una y ya");
        Actividad b = new Actividad(new ActividadPK(p.getId(), e.getEtapaPK().getId(), 2));
        b.setNombre("Otra y ya");
        //etapaFacade.create(e);
        actividadFacade.create(a);
        actividadFacade.create(b);
        System.out.println("Esta o que");
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
            Proyecto proyecto = proyectoFacade.find(id);
            List<Actividad> actividades = new ArrayList<>();
            List<Etapa> etapas = new ArrayList<>();
            for(Object j:plan){
                actividad = (JSONObject) j;
                System.out.println(actividad.get("nombre"));
            }
        }catch(Exception e){
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

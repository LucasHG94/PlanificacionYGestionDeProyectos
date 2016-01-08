/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicioWeb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
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
import modelo.Administrador;
import modelo.Dedicacion;
import modelo.Proyecto;
import modelo.Trabajador;
import modelo.Vacaciones;
import modelo.VacacionesPK;
import persistencia.ActividadFacadeLocal;
import persistencia.AdministradorFacadeLocal;
import persistencia.DedicacionFacadeLocal;
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

    /**
     * Creates a new instance of SimpleRootResource
     */
    public SimpleRootResource() {
    }

    /**
     * Retrieves representation of an instance of servicioWeb.SimpleRootResource
     * @return an instance of java.lang.String
     *
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }*/

    /**
     * PUT method for updating or creating an instance of SimpleRootResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    //@Consumes("application/xml")
    public String getTest(String content) {
        return "Funciono";
    }
    
    @GET
    @Produces("application/json")
    @Path("/proyectos/nombreDisponible")
    public boolean nombreDisponible(@QueryParam("nombre") String nombre){
        return proyectoFacade.getByName(nombre).isEmpty();
    }
    
    @GET
    @Produces("application/json")
    @Path("/trabajador/{nick}/disponibleParaJefe")
    public boolean trabajadorDisponibleJefe(@PathParam("nick") String nick){
        if(trabajadorFacade.find(nick)==null) return false;
        List<Proyecto> proyectos = getProyectosJefe(nick);
        Date hoy = new Date();
        for(Proyecto p : proyectos){
            if(p.getFechafin()!= null && p.getFechafin().compareTo(hoy)>=0) return false;
        }
        return true;
    }
    
    @GET
    @Produces("application/json")
    @Consumes("application/json")
    @Path("/proyectos/nuevo")
    public boolean nuevoProyecto(@QueryParam("nombre") String nombre, @QueryParam("nickjefe") String nickjefe){
        int id = proyectoFacade.findAll().size() + 1;
        Proyecto p = new Proyecto(id, nickjefe);
        p.setNombre(nombre);
        proyectoFacade.create(p);
        return true;
    }
    
    @GET
    @Produces("application/json")
    @Path("/proyectos")
    public List<Proyecto> getProyectos(@QueryParam("user") String nombre){
        List<Dedicacion> dedicaciones = dedicacionFacade.findAll();
        Trabajador t = trabajadorFacade.find(nombre);
        List<Dedicacion> dedicFilt = new ArrayList<>();
        for(Dedicacion item:dedicaciones){
            if(item.getTrabajador().getNick().equals(t.getNick())){
                dedicFilt.add(item);
            }
        }
        
        List<Proyecto> pFiltrado = new ArrayList<>();
        for(Dedicacion item:dedicFilt){
            pFiltrado.add(item.getProyecto());
        }
        return pFiltrado;
    }
    
    @GET
    @Produces("application/json")
    @Path("/proyectosjefe")
    public List<Proyecto> getProyectosJefe(@QueryParam("user") String nombre){
        Trabajador t = trabajadorFacade.find(nombre);
        List<Proyecto> pJefe = new ArrayList<>();
        List<Trabajador> trabajadores = trabajadorFacade.findAll();
        List<Proyecto> proyectos = proyectoFacade.findAll();
        for(Proyecto itemp:proyectos){
            if(itemp.getNickjefe().equals(t.getNick())){
                pJefe.add(itemp);
            }
        }
        return pJefe;
    }
    
    @GET
    @Path("/usuario")
    public int login(@QueryParam("user") String nombre, @QueryParam("password") String contraseña){
           List<Trabajador> trabajadores = trabajadorFacade.findAll();
           List<Administrador> admins = administradorFacade.findAll();
           for(Trabajador item : trabajadores){              
               if(item.getNick().equals(nombre)){
                   if(item.getPassword().compareTo(contraseña)==0){
                       return 2;
                   }
               }
           }
           for(Administrador item: admins){
               if(item.getNick().equals(nombre)){
                   if(item.getPassword().compareTo(contraseña)==0){
                       return 1;
                   }
               }              
           }
           return 0;
    }
    
    @GET
    @Path("/admin")
    public int checkAdmin(@QueryParam("user") String nombre){
           List<Administrador> admins = administradorFacade.findAll();           
           for(Administrador item: admins){
               if(item.getNick().equals(nombre)){
                  return 1;
               }              
           }
           return 0;
    }
    
    @GET
    @Produces("application/json")
    @Path("/actividades")
    public List<Actividad> getActividades(@QueryParam("user") String nombre, @QueryParam("idP") int idProyecto){
        Trabajador t = trabajadorFacade.find(nombre);
        Proyecto p = proyectoFacade.find(idProyecto);
        List<Actividad> actividades = actividadFacade.findAll();
        List<Actividad> actividadesTrabajador = new ArrayList<>();
        for(Actividad item : actividades){
            if(item.getTrabajadorCollection().contains(t) & item.getEtapa().getProyecto().equals(p) &
                    item.getEtapa().getProyecto().getFechafin().before(new Date())){
                actividadesTrabajador.add(item);
            }
        }
        return actividadesTrabajador;
    }

    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/vacaciones")
    public boolean setVacaciones(@QueryParam("user") String nombre, 
            @QueryParam("ano1") int ano1, @QueryParam("mes1") int mes1, @QueryParam("dia1") int dia1,
            @QueryParam("ano2") int ano2, @QueryParam("mes2") int mes2, @QueryParam("dia2") int dia2){
        Trabajador t = trabajadorFacade.find(nombre);
        Date fecha1 = new Date(ano1-1900,mes1-1,dia1);
        Date fecha2 = new Date(ano2-1900,mes2-1,dia2);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha1);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date fecha3 = cal.getTime();
        cal.setTime(fecha3);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date fecha3Fin = cal.getTime();
        
        cal.setTime(fecha2);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date fecha4 = cal.getTime();
        cal.setTime(fecha4);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        Date fecha4Fin = cal.getTime();
        
        boolean permitido = true;
        List<Actividad> actividades = actividadFacade.findAll();
        for(Actividad item : actividades){
            if(t.getActividadCollection().contains(item)){
                if(item.getFechainicio().after(fecha1) && item.getFechainicio().before(fecha3Fin)){
                    permitido = false;
                }
                else if(item.getFechainicio().after(fecha2) && item.getFechainicio().before(fecha4Fin)){
                    permitido = false;
                }
            }
        }
        
        
        if(permitido){
            VacacionesPK vpk1 = new VacacionesPK(fecha1, t.getNick());
            Vacaciones v1 = new Vacaciones(vpk1);       
            vacacionesFacade.create(v1);
            VacacionesPK vpk2 = new VacacionesPK(fecha3, t.getNick());
            Vacaciones v2 = new Vacaciones(vpk2);
            vacacionesFacade.create(v2);


            VacacionesPK vpk3 = new VacacionesPK(fecha2, t.getNick());
            Vacaciones v3 = new Vacaciones(vpk3);
            vacacionesFacade.create(v3);
            VacacionesPK vpk4 = new VacacionesPK(fecha4, t.getNick());
            Vacaciones v4 = new Vacaciones(vpk4);
            vacacionesFacade.create(v4);
        }
        return permitido;
    }
}
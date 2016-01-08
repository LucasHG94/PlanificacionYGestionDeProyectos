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
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
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
            if(item.getTrabajadorCollection().contains(t) & item.getEtapa().getProyecto().equals(p)){
                actividadesTrabajador.add(item);
            }
        }
        return actividadesTrabajador;
    }
    //TODO hacer que lea el objeto json
    @PUT
    @Consumes("application/json")
    @Path("/vacaciones")
    public void setVacaciones(@QueryParam("user") String nombre, 
            @QueryParam("ano1") int ano1, @QueryParam("mes1") int mes1, @QueryParam("dia1") int dia1,
            @QueryParam("ano2") int ano2, @QueryParam("mes2") int mes2, @QueryParam("dia2") int dia2){
        System.out.println(nombre);
        Trabajador t = trabajadorFacade.find(nombre);
        List<Vacaciones> vacaciones = vacacionesFacade.findAll();
        List<Vacaciones> vacacionesTrabajador = new ArrayList<>();
        for(Vacaciones item : vacaciones){
            if(item.getTrabajador().equals(t)){
                vacacionesTrabajador.add(item);
            }
        }
        //TODO dar error si coincide con alguna actividad 
        System.out.println("voy bien");
        Date fecha1 = new Date();
        fecha1.setDate(dia1);
        fecha1.setMonth(mes1-1);
        fecha1.setYear(ano1-1900);//arreglar esto
        vacacionesTrabajador.get(0).getVacacionesPK().setFechasemana(fecha1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha1);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        vacacionesTrabajador.get(1).getVacacionesPK().setFechasemana(cal.getTime());
        
        Date fecha2 = new Date();
        fecha1.setDate(dia2);
        fecha1.setMonth(mes2-1);
        fecha1.setYear(ano2-1900);//arreglar esto
        vacacionesTrabajador.get(2).getVacacionesPK().setFechasemana(fecha2);
        cal.setTime(fecha1);
        cal.add(Calendar.DAY_OF_MONTH, 7);
        vacacionesTrabajador.get(3).getVacacionesPK().setFechasemana(cal.getTime());
    }
}
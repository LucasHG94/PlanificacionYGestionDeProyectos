/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicioWeb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import modelo.Actividad;
import modelo.Administrador;
import modelo.Dedicacion;
import modelo.Proyecto;
import modelo.Trabajador;
import persistencia.ActividadFacadeLocal;
import persistencia.AdministradorFacadeLocal;
import persistencia.DedicacionFacadeLocal;
import persistencia.ProyectoFacadeLocal;
import persistencia.TrabajadorFacadeLocal;

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
}
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import modelo.Dedicacion;
import modelo.Proyecto;
import modelo.Trabajador;
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

    /**
     * Creates a new instance of SimpleRootResource
     */
    public SimpleRootResource() {
    }

    /**
     * Retrieves representation of an instance of servicioWeb.SimpleRootResource
     * @return an instance of java.lang.String
     */
    @GET
    @Produces("application/xml")
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of SimpleRootResource
     * @param content representation for the resource
     * @return an HTTP response with content of the updated or created resource.
     */
    @GET
    @Consumes("application/xml")
    public String getTest(String content) {
        return "Funciono";
    }
    
    @GET
    @Produces("application/json")
    @Path("/proyectos")
    public List<Proyecto> getProyectos(@QueryParam("user") String nombre){
        List<Proyecto> proyectos = proyectoFacade.findAll();
        List<Dedicacion> dedicaciones = dedicacionFacade.findAll();
        Trabajador t = trabajadorFacade.find(nombre);
        List<Dedicacion> dedicFilt = new ArrayList<Dedicacion>();
        for(Dedicacion item:dedicaciones){
            if(item.getTrabajador().getNick().equals(t.getNick())){
                dedicFilt.add(item);
            }
        }
        
        List<Proyecto> pFiltrado = new ArrayList<Proyecto>();
        for(Dedicacion item:dedicFilt){
            pFiltrado.add(item.getProyecto());
        }
        return proyectos;
    }
    
    @GET
    @Path("/usuario")
    public boolean login(@QueryParam("user") String nombre, @QueryParam("password") String contraseña){
           List<Trabajador> trabajadores = trabajadorFacade.findAll();
           for(Trabajador item : trabajadores){              
               if(item.getNick().equals(nombre)){
                   return item.getPassword().compareTo(contraseña)==0;
               }
           }
           return false;
    }
}
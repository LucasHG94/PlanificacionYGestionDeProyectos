/*
 * Clase con todos los metodos restful para la aplicación
 */
package servicioWeb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import modelo.Actividad;
import modelo.ActividadPK;
import modelo.Administrador;
import modelo.Categoriaroles;
import modelo.Datosconfigurables;
import modelo.Dedicacion;
import modelo.DedicacionPK;
import modelo.Etapa;
import modelo.EtapaPK;
import modelo.Informesemanal;
import modelo.InformesemanalPK;
import modelo.Proyecto;
import modelo.Trabajador;
import modelo.Vacaciones;
import modelo.VacacionesPK;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import persistencia.ActividadFacadeLocal;
import persistencia.AdministradorFacadeLocal;
import persistencia.CategoriarolesFacadeLocal;
import persistencia.DatosconfigurablesFacadeLocal;
import persistencia.DedicacionFacadeLocal;
import persistencia.EtapaFacadeLocal;
import persistencia.InformesemanalFacadeLocal;
import persistencia.ProyectoFacadeLocal;
import persistencia.TrabajadorFacadeLocal;
import persistencia.VacacionesFacadeLocal;

/**
 * REST Web Service
 *
 * @author sturm
 */
@Path("SimpleRoot") ///GestionProyectos/webresources/SimpleRoot
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

    @EJB
    private DatosconfigurablesFacadeLocal configFacade;

    @EJB
    private CategoriarolesFacadeLocal categoriaFacade;

    @EJB
    private InformesemanalFacadeLocal informeSemanalFacade;

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
    
    /**
     * Obtención de datos de informe actividades proyectos
     * @param nick
     * @param nump
     * @param f1
     * @param f2
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/usuario/{nick}/informes/iap/{nump}/{fecha1}/{fecha2}")
    public String getDatosInformeActividadesProyectos(@PathParam("nick") String nick, @PathParam("nump") String nump, @PathParam("fecha1") String f1, @PathParam("fecha2") String f2) {
        DateTime fecha1 = new DateTime(f1);
        DateTime fecha2 = new DateTime(f2);
        System.out.println("iap");
        System.out.println("fecha1: " + fecha1);
        System.out.println("fecha2: " + fecha2);
        int numP = Integer.valueOf(nump);
        List<Actividad> actividades = getActividadesProyecto(numP);
        System.out.println("numactpro: " + actividades.size());
        actividades = calcularFechasEstimadas(actividades);
        List<Actividad> resultado = new ArrayList<>();
        for (Actividad a : actividades) {
            DateTime fechaInicio = null;
            DateTime fechaFin = null;
            if (a.getFechainicio() != null) {
                fechaInicio = new DateTime(a.getFechainicio());
                if (a.getFechafin() != null) {
                    fechaFin = new DateTime(a.getFechafin());
                }
                if (fechaInicio.isBefore(fecha2)) {
                    if (fechaFin == null) {
                        resultado.add(a);
                    } else if (fechaFin.isAfter(fecha1)) {
                        resultado.add(a);
                    }
                }
            }

        }

        System.out.println("numactresult: " + resultado.size());

        JSONObject result = new JSONObject();
        JSONArray activas = new JSONArray();
        JSONArray finalizadas = new JSONArray();

        for (Actividad a : resultado) {
            JSONObject ajson = new JSONObject();
            ajson.put("nombre", a.getNombre());
            ajson.put("duracionestimada", getDuracionEstimadaDias(a) + " dias.");
            int d = Days.daysBetween(new DateTime(a.getFechainicio()).withTimeAtStartOfDay(), new DateTime().withTimeAtStartOfDay()).getDays();
            if (a.getFechafin() != null) {
                d = Days.daysBetween(new DateTime(a.getFechainicio()).withTimeAtStartOfDay(), new DateTime(a.getFechafin()).withTimeAtStartOfDay()).getDays();
            }
            ajson.put("duracionreal", d);
            if (d > getDuracionEstimadaDias(a)) {
                ajson.put("excedido", true);
            } else {
                ajson.put("excedido", false);
            }
            int progreso = Math.round(d * 1.0f / getDuracionEstimadaDias(a) * 100);
            ajson.put("progreso", progreso);
            if (a.getFechafin() == null) {
                activas.put(ajson);
                System.out.println("a");
            } else {
                finalizadas.put(ajson);
                System.out.println("b");
            }
        }

        result.put("activas", activas);
        result.put("finalizadas", finalizadas);

        return result.toString();
    }
    
    /**
     * Nos devuelve los informes entre las fechas dadas
     * @param nick
     * @param f1
     * @param f2
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/usuario/{nick}/informes/ia/{fecha1}/{fecha2}")
    public String obtenerInformesDeActividades(@PathParam("nick") String nick, @PathParam("fecha1") String f1, @PathParam("fecha2") String f2) {
        DateTimeFormatter dtfOut = DateTimeFormat.forPattern("EEEE MMM d yyyy");
        DateTime fecha1 = new DateTime(f1);
        DateTime fecha2 = new DateTime(f2);
        System.out.println("fecha1: " + fecha1);
        System.out.println("fecha2: " + fecha2);
        Trabajador t = trabajadorFacade.find(nick);
        List<Informesemanal> informes = new ArrayList<>(t.getInformesemanalCollection());
        List<Informesemanal> resultado = new ArrayList<>();
        for (Informesemanal i : informes) {
            DateTime fechaSemana = new DateTime(i.getInformesemanalPK().getFechasemana());
            if (fechaSemana.isAfter(fecha1) && fechaSemana.isBefore(fecha2)) {
                resultado.add(i);
            }
        }
        JSONArray result = new JSONArray();
        for (Informesemanal i : resultado) {
            JSONObject inf = new JSONObject();
            inf.put("nombre", i.getActividad().getNombre());
            inf.put("fecha", dtfOut.print(new DateTime(i.getInformesemanalPK().getFechasemana())));
            inf.put("estado", i.getEstado());
            result.put(inf);
        }

        return result.toString();
    }
    
    /**
     * Calcula la duración con los datos de las actividades
     * @param actividades
     * @return 
     */
    public List<Actividad> calcularFechasEstimadas(List<Actividad> actividades) {
        List<Actividad> resultado = new ArrayList<>();
        //List<Actividad> actividades = getActividadesProyecto(5);

        if (actividades.size() <= 0) {
            return null;
        }
        Date fechaIniciotmp = proyectoFacade.find(actividades.get(0).getActividadPK().getIdproyecto()).getFechainicio();
        System.out.println("Resultado: " + actividades.size());

        if (fechaIniciotmp == null) {
            return null;
        }
        System.out.println("Resultado: " + actividades.size());
        List<Actividad> precedidaspora;
        Actividad a = getActividad(actividades, 1, 1);
        precedidaspora = new ArrayList<>(a.getActividadCollection());
        a.setFechaAproximada(fechaIniciotmp);
        calcularFechasRecursivo(a, precedidaspora);
        return actividades;

    }
    
    /**
     * 
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/test/infact")
    public List<Actividad> calcularFechasEstimadasTest(/*List<Actividad> actividades*/) {
        List<Actividad> resultado = new ArrayList<>();
        List<Actividad> actividades = getActividadesProyecto(5);

        if (actividades.size() <= 0) {
            return null;
        }
        Date fechaIniciotmp = proyectoFacade.find(actividades.get(0).getActividadPK().getIdproyecto()).getFechainicio();
        System.out.println("Resultado: " + actividades.size());

        if (fechaIniciotmp == null) {
            return null;
        }
        System.out.println("Resultado: " + actividades.size());
        List<Actividad> precedidaspora;
        Actividad a = getActividad(actividades, 1, 1);
        Collection<Actividad> tmp = a.getActividadCollection();
        List<Actividad> tmp12 = new ArrayList<>(tmp);
        precedidaspora = new ArrayList<>(a.getActividadCollection());
        a.setFechaAproximada(fechaIniciotmp);
        calcularFechasRecursivo(a, precedidaspora);
        return actividades;

    }
    
    /**
     * Comprueba las fechas de las predecesoras para calcular la fecha estimada
     * @param a
     * @param precedidas 
     */
    public void calcularFechasRecursivo(Actividad a, List<Actividad> precedidas) {
        if (precedidas.size() <= 0) {
            return;
        }
        Date fechaReal = a.getFechainicio();
        Date fechaFinReal = a.getFechafin();
        DateTime fechaAproximadaA = new DateTime(a.getFechaAproximada());
        DateTime fechaAproximadaP;

        if (fechaFinReal != null) {
            fechaAproximadaP = new DateTime(fechaFinReal);
        } else {
            if (fechaReal == null) {
                fechaAproximadaP = getFechaAproximada(fechaAproximadaA, a);/*fechaAproximadaA.plusDays(a.getEsfuerzoestimado());*/

            } else {
                fechaAproximadaP = getFechaAproximada(new DateTime(fechaReal), a);
            }
        }

        DateTime fechaAproximadaPActual;

        for (Actividad p : precedidas) {
            if (p.getFechaAproximada() != null) {
                fechaAproximadaPActual = new DateTime(p.getFechaAproximada());
                if (fechaAproximadaPActual.isBefore(fechaAproximadaP)) {
                    p.setFechaAproximada(fechaAproximadaP.toDate());
                    calcularFechasRecursivo(p, new ArrayList<>(p.getActividadCollection()));
                }
            } else {
                p.setFechaAproximada(fechaAproximadaP.toDate());
                calcularFechasRecursivo(p, new ArrayList<>(p.getActividadCollection()));
            }

        }
    }
    /**
     * Obtenemos la fecha aproximada teniendo la duración
     * @param fechaInicio
     * @param a
     * @return 
     */
    public DateTime getFechaAproximada(DateTime fechaInicio, Actividad a) {
        List<Trabajador> trabajadores = new ArrayList<>(a.getTrabajadorCollection());
        int idProyecto = a.getActividadPK().getIdproyecto();
        float hombres = 0;
        for (Trabajador t : trabajadores) {
            Dedicacion d = dedicacionFacade.find(new DedicacionPK(t.getNick(), idProyecto));
            hombres = hombres + (d.getPorcentaje() / 100f);
        }
        float duracionEstimada;
        if (hombres <= 0) {
            duracionEstimada = a.getEsfuerzoestimado();
        } else {
            duracionEstimada = a.getEsfuerzoestimado() / hombres;
        }
        int diasEstimados = (int) Math.round(Math.ceil(duracionEstimada / 8));
        return fechaInicio.plusDays(diasEstimados);
    }

    public int getDuracionEstimadaDias(Actividad a) {
        List<Trabajador> trabajadores = new ArrayList<>(a.getTrabajadorCollection());
        int idProyecto = a.getActividadPK().getIdproyecto();
        float hombres = 0;
        for (Trabajador t : trabajadores) {
            Dedicacion d = dedicacionFacade.find(new DedicacionPK(t.getNick(), idProyecto));
            hombres = hombres + (d.getPorcentaje() / 100f);
        }
        float duracionEstimada;
        if (hombres <= 0) {
            duracionEstimada = a.getEsfuerzoestimado();
        } else {
            duracionEstimada = a.getEsfuerzoestimado() / hombres;
        }
        int diasEstimados = (int) Math.round(Math.ceil(duracionEstimada / 8));
        return diasEstimados;
    }
    
    /**
     * Obtenemos las actividades filtradas por id y etapa
     * @param actividades
     * @param etapa
     * @param id
     * @return 
     */
    public Actividad getActividad(List<Actividad> actividades, int etapa, int id) {
        ActividadPK pk;
        for (Actividad a : actividades) {
            pk = a.getActividadPK();
            if (pk.getId() == id && pk.getIdetapa() == etapa) {
                return a;
            }
        }
        return null;
    }

    public List<Actividad> getActividadesProyecto(int idproyecto) {
        List<Actividad> actividades = actividadFacade.findAll();
        List<Actividad> resultado = new ArrayList<>();
        for (Actividad a : actividades) {
            if (a.getActividadPK().getIdproyecto() == idproyecto) {
                resultado.add(a);
            }
        }

        return resultado;
    }
    
    /**
     * 
     * @param numP
     * @param fecha
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/proyecto/{numP}/informes/aa/{fecha}")
    public String getDatosInformeAA(@PathParam("numP") String numP, @PathParam("fecha") String fecha) {
        int idP = Integer.valueOf(numP);
        DateTime comienzoPeriodo = new DateTime(fecha);
        DateTime finPerido = comienzoPeriodo.plusWeeks(1);
        List<Actividad> actividades = getActividadesProyecto(idP);
        calcularFechasEstimadas(actividades);
        List<Actividad> actividadesEnPeriodo = new ArrayList<>();
        DateTime fechatmp;
        for (Actividad a : actividades) {
            fechatmp = new DateTime(a.getFechaAproximada());
            if (fechatmp.isAfter(comienzoPeriodo) && fechatmp.isBefore(finPerido)) {
                actividadesEnPeriodo.add(a);
            }
        }
        System.out.println("Num actividades en periodo: " + actividadesEnPeriodo.size());

        JSONArray result = new JSONArray();
        JSONObject trabajador;
        JSONArray lista;
        List<Trabajador> trabajadores;
        JSONObject tmp;
        JSONObject tmpa;
        for (Actividad a : actividadesEnPeriodo) {
            trabajadores = new ArrayList<>(a.getTrabajadorCollection());
            for (Trabajador t : trabajadores) {
                boolean nuevo = true;
                for (int i = 0; i < result.length(); i++) {
                    tmp = result.getJSONObject(i);
                    if (tmp.getString("nick").equals(t.getNick())) {
                        tmpa = new JSONObject();
                        tmpa.put("nombre", a.getNombre());
                        tmpa.put("date", a.getFechaAproximada().toString());
                        tmp.getJSONArray("actividades").put(tmpa);
                        nuevo = false;
                        break;
                    }
                }
                if (nuevo) {
                    tmpa = new JSONObject();
                    JSONObject tmpb = new JSONObject();
                    tmpa.put("nick", t.getNick());
                    tmpa.put("actividades", new JSONArray());
                    tmpb.put("nombre", a.getNombre());
                    tmpb.put("date", a.getFechaAproximada());
                    tmpa.getJSONArray("actividades").put(tmpb);
                    result.put(tmpa);
                }
            }
        }

        return result.toString();
    }
    
    /**
     * Comprueba si exite un trabajador dado
     * @param nick
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/trabajador/{nick}/existe")
    public boolean isTrabajador(@PathParam("nick") String nick) {
        Trabajador t = trabajadorFacade.find(nick);
        return t != null;
    }
    
    /**
     * Asignar en la base de datos una categoría a un trabajador
     * @param cat
     * @param nick
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/trabajador/{nick}/{categoria}")
    public boolean asignarCategoriaTrabajador(@PathParam("categoria") int cat, @PathParam("nick") String nick) {
        List<Categoriaroles> categorias = categoriaFacade.findAll();
        Trabajador t = trabajadorFacade.find(nick);
        for (Categoriaroles c : categorias) {

            if (c.getCategoriarolesPK().getCategoria() == cat) {
                t.setCategoria(cat);
                trabajadorFacade.edit(t);
                return true;
            }
        }
        return false;
    }

    @GET
    @Produces("application/json")
    @Path("/test/test1")
    public boolean test1(@QueryParam("testdata") String test) {
        Actividad una = actividadFacade.find(new ActividadPK(3, 1, 1));
        Actividad otra = actividadFacade.find(new ActividadPK(3, 1, 2));
        //una.getActividadCollection().add(otra);
        //actividadFacade.edit(una);

        System.out.println(una.getActividadCollection().size());

        System.out.println(test);
        return true;
    }
    
    /**
     * Nombre no usados para un proyecto
     * @param nombre
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/proyectos/nombreDisponible")
    public boolean nombreDisponible(@QueryParam("nombre") String nombre) {
        return proyectoFacade.getByName(nombre).isEmpty();
    }
    
    /**
     * Asigna plan a un proyecto
     * @param id
     * @param data
     * @return 
     */
    @POST
    @Produces("application/json")
    @Path("/proyectos/{id}/plan")
    public boolean asignarPlanProyecto(@PathParam("id") String id, String data) {
        boolean debug = false;
        if (debug) {
            return true;
        }
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
            proyectoFacade.edit(proyecto);
            List<Actividad> actividades = new ArrayList<>();
            Etapa etapa;
            List<Etapa> etapas = new ArrayList<>();
            String nombre;
            int duracionEstimada;
            String rol;
            JSONObject apjo; //actividad json object
            int etapaidcont = 1;
            int actividadidcont = 1;
            etapa = new Etapa(new EtapaPK(proyecto.getId(), etapaidcont++));
            boolean primera = true;
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
                if (primera) {
                    a.setFechainicio(today.toDate());
                    primera = false;
                }
                a.setNombre(nombre);
                a.setRol(actividad.getString("rol"));
                a.setEsfuerzoestimado(duracionEstimada);
                pre = actividad.getJSONArray("pre");
                if (pre.length() > 0) {
                    for (int i = 0; i < pre.length(); i++) {
                        Actividad atmp = actividades.get(pre.getInt(i));
                        if (atmp.getActividadCollection() == null) {
                            atmp.setActividadCollection(new ArrayList<Actividad>());
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

            for (int i = actividades.size() - 1; i >= 0; i--) {
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

        } catch (JSONException | NumberFormatException e) {
            return false;
        }

        return true;
    }
    
    /**
     * Proyectos aún no iniciados
     * @param nickJefe
     * @return 
     */
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
    
    /**
     * Poner como participante a un trabajador en un proyecto
     * @param id
     * @param nick
     * @param por
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/proyectos/{id}/asignarParticipacion/{nick}")
    public String asignarParticipacion(@PathParam("id") String id, @PathParam("nick") String nick, @QueryParam("por") String por) {
        /*if (trabajadorFacade.find(nick) == null) {
         return false;
         }
         boolean jefe = !trabajadorDisponibleJefe(nick);
         if(trabajadorDisponibleJefe(nick)){
            
         }
         return true;*/
        Datosconfigurables datos = configFacade.find("ProyectosAlMismoTiempo");
        JSONObject response = new JSONObject();
        int maxPActivos = (datos != null) ? datos.getValor() : 2;

        try {
            int idProyecto = Integer.valueOf(id);
            int porActual = 0;
            int porSolicitado = Integer.valueOf(por);
            int numPActivos = 0;
            if (porSolicitado > 100) {
                porSolicitado = 100;
            }
            Trabajador t = trabajadorFacade.find(nick);
            if (t == null) {
                response.put("error", true);
                response.put("mensaje", "Este nick no corresponde a ningun trabajador");
                return response.toString();
            }

            List<Dedicacion> dedicaciones = new ArrayList<>(t.getDedicacionCollection());

            for (Dedicacion d : dedicaciones) {
                if (d.getProyecto().getFechafin() == null || d.getProyecto().getFechafin().compareTo(new Date()) >= 0) {
                    porActual += d.getPorcentaje();
                    numPActivos++;
                }
            }
            System.out.println("Num proyectos activos: " + numPActivos);
            System.out.println("Por actual: " + porActual);

            if (numPActivos >= maxPActivos) {
                response.put("error", true);
                response.put("mensaje", "Este trabajador ya se encuentra trabajando en el numero maximo de proyectos permitido.");
                return response.toString();
            }

            if (porActual + porSolicitado > 100) {
                response.put("error", true);
                response.put("mensaje", "El porcentaje de participacion total superaria el permitido. Introduce un pocrcentaje menor.");
                return response.toString();
            }

            if (jefeActualmente(nick) != null) {
                int idPJefe = jefeActualmente(nick).getId();
                System.out.println("Jefe del proyecto: " + idPJefe);
                if (idPJefe == idProyecto) {
                    response.put("error", true);
                    response.put("mensaje", "El jefe de un proyecto no puede trabajar en el mismo.");
                    return response.toString();
                } else {
                    if (numPActivos != 0) {
                        response.put("error", true);
                        response.put("mensaje", "Un jefe de proyecto solo puede trabajar en otro a mayores. Este usuario ya se encuentra trabjando en otro proyecto");
                    }
                }
            }

            Dedicacion nueva = new Dedicacion(new DedicacionPK(nick, idProyecto));
            nueva.setPorcentaje(porSolicitado);
            dedicacionFacade.create(nueva);
            response.put("error", false);
            response.put("nick", nick);
            response.put("por", porSolicitado + "%");
            response.put("mensaje", "Trabajador añadido al proyecto");
            return response.toString();
        } catch (NumberFormatException | JSONException e) {
            response.put("error", true);
            response.put("mensaje", "Error desconocido.");
            return response.toString();
        }

    }
    
    
    /**
     * Obtenemos el proyecto de dado un jefe
     * @param nick
     * @return 
     */
    public Proyecto jefeActualmente(String nick) {
        List<Proyecto> proyectos = getProyectosJefe(nick);
        Date hoy = new Date();
        for (Proyecto p : proyectos) {
            if (p.getFechafin() == null) {
                return p;
            }
            if (p.getFechafin().compareTo(hoy) >= 0) {
                return p;
            }
        }
        return null;
    }
    
    /**
     * Nos informa si el trabajador dado puede ser jefe
     * @param nick
     * @return 
     */
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
            if (p.getFechafin() == null || p.getFechafin().compareTo(hoy) >= 0) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Dar de alta a un proyecto nuevo
     * @param nombre
     * @param nickjefe
     * @return 
     */
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
    
    /**
     * Obtiene los proyectos de un trabajador
     * @param nombre
     * @return 
     */
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
    @Path("/proyectos/jefe/{nick}/todos")
    public List<Proyecto> getProyectosJefe2(@PathParam("nick") String nick) {
        return getProyectosJefe(nick);
    }
    
    /**
     * Devuelve los proyectos de un jefe
     * @param nombre
     * @return 
     */
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
    
    /**
     * Identifica que tipo de empleado se ha logueado
     * @param nombre
     * @param contraseña
     * @return 
     */
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
    
    /**
     * Comprueba si es el admin el que esta logueado
     * @param nombre
     * @return 
     */
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
    
    /**
     * Actividades de un trabajador y de un proyecto
     * @param nombre
     * @param idProyecto
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/actividades")
    public List<Actividad> getActividades(@QueryParam("user") String nombre, @QueryParam("idP") int idProyecto) {
        Trabajador t = trabajadorFacade.find(nombre);
        Proyecto p = proyectoFacade.find(idProyecto);
        List<Actividad> actividades = actividadFacade.findAll();
        List<Actividad> actividadesTrabajador = new ArrayList<>();
        for (Actividad item : actividades) {
            if (item.getTrabajadorCollection().contains(t) && item.getEtapa().getProyecto().equals(p)
                    && item.getEtapa().getProyecto().getFechafin() != null
                    && item.getEtapa().getProyecto().getFechafin().before(new Date())) {
                actividadesTrabajador.add(item);
            }
        }
        return actividadesTrabajador;
    }
    
    /**
     * Devuelve las actividades de un trabajador para la semana actual
     * @param nombre
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/actividadesSemana")
    public List<Actividad> getActividadesSemana(@QueryParam("user") String nombre) {
        Trabajador t = trabajadorFacade.find(nombre);
        List<Actividad> actividades = actividadFacade.findAll();
        List<Actividad> actividadesTrabajador = new ArrayList<>();
        for (Actividad item : actividades) {
            if (item.getTrabajadorCollection().contains(t)
                    & item.getEtapa().getProyecto().getFechafin() == null) {//before.(new Date())
                actividadesTrabajador.add(item);
            }
        }
        List<Actividad> actividadesSemana = new ArrayList<>();
        for (Actividad item : actividadesTrabajador) {//TODO
            if (item.getFechainicio().before(new Date()) & item.getFechafin() == null) {//.after(new Date())
                actividadesSemana.add(item);
                //System.out.println(item.getFechainicio()+" -: "+item.getFechafin()+" -> "+new Date());
            }
        }
        return actividadesSemana; //actividadesSemana;
    }
    
    /**
     * Asigna las vacaciones al trabajador
     * @param nombre
     * @param ano1
     * @param mes1
     * @param dia1
     * @param ano2
     * @param mes2
     * @param dia2
     * @return 
     */
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/vacaciones")
    public boolean setVacaciones(@QueryParam("user") String nombre,
            @QueryParam("ano1") int ano1, @QueryParam("mes1") int mes1, @QueryParam("dia1") int dia1,
            @QueryParam("ano2") int ano2, @QueryParam("mes2") int mes2, @QueryParam("dia2") int dia2) {
        Trabajador t = trabajadorFacade.find(nombre);

        Date fecha1 = new Date(ano1 - 1900, mes1 - 1, dia1);
        Date fecha2 = new Date(ano2 - 1900, mes2 - 1, dia2);

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
        for (Actividad item : actividades) {
            if (t.getActividadCollection().contains(item)) {
                if (item.getFechainicio().after(fecha1) && item.getFechainicio().before(fecha3Fin)) {
                    permitido = false;
                } else if (item.getFechainicio().after(fecha2) && item.getFechainicio().before(fecha4Fin)) {
                    permitido = false;
                }
            }
        }
        List<Vacaciones> vacaciones = vacacionesFacade.findAll();
        for (Vacaciones item : vacaciones) {
            if (item.getTrabajador().getNick().equals(t.getNick())) {
                permitido = false;
            }
        }

        if ((new Date()).before(fecha1)) {
            permitido = false;
        }
        if ((new Date()).before(fecha2)) {
            permitido = false;
        }

        if (permitido) {
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
    
    /**
     * Crea un informes semanales de un trabajador (uno por actividad) con las 
     * comprobaciones necesarias
     * @param info
     * @return 
     */
    @GET
    @Consumes("application/json")
    @Produces("application/json")
    @Path("/informeSemanal")
    public boolean setInformeSemanal(@Context UriInfo info) {
        System.out.println("Llego");
        boolean permitido = true;
        String user = info.getQueryParameters().getFirst("user");
        List<Integer> horas = new ArrayList<>();
        Map map = info.getQueryParameters();
        String s;
        String s1;
        String s2;
        List<Integer> idPs = new ArrayList<>();
        List<Integer> idActividades = new ArrayList<>();
        List<Integer> idEtapas = new ArrayList<>();
        int i;
        System.out.println("-------->" + map.size());
        List<Double> tiempos = new ArrayList<>();
        for (i = 0; i < (map.size() - 1) / 9; i++) {//TODO
            s = map.get("idP" + i).toString();
            s1 = s.substring(0, s.length() - 1);
            s2 = s1.substring(1, s1.length());
            idPs.add(Integer.parseInt(s2));
            System.out.println("idP=" + i + " : " + idPs.get(i));

            s = map.get("idActividad" + i).toString();
            s1 = s.substring(0, s.length() - 1);
            s2 = s1.substring(1, s1.length());
            idActividades.add(Integer.parseInt(s2));
            System.out.println("idActividad=" + i + " : " + idActividades.get(i));

            s = map.get("idEtapa" + i).toString();
            s1 = s.substring(0, s.length() - 1);
            s2 = s1.substring(1, s1.length());
            idEtapas.add(Integer.parseInt(s2));
            System.out.println("idEtapa=" + i + " : " + idEtapas.get(i));
            tiempos.add(0.0);
            for (int j = 0; j < 6; j++) {
                s = map.get("hora" + j + "" + i).toString();
                s1 = s.substring(0, s.length() - 1);
                s2 = s1.substring(1, s1.length());
                horas.add(Integer.parseInt(s2));
                tiempos.set(i, tiempos.get(i) + Integer.parseInt(s2));
            }
        }
        int suma = 0;
        for (Integer hora : horas) {
            suma = suma + hora;
        }
        for (int m = 0; m < tiempos.size(); m++) {
            int procentaje = 40;
            Collection<Dedicacion> col = proyectoFacade.find(idPs.get(m)).getDedicacionCollection();
            for (Dedicacion d : col) {
                if (d.getProyecto().getId() == idPs.get(m)) {
                    procentaje = d.getPorcentaje();
                    System.out.println(procentaje);
                }
            }
            if (horas.size()!=1 && tiempos.get(m) < suma * procentaje / 100) {
                permitido = false;
            }
        }
        if (suma > 40) {
            permitido = false;
        }
        if (permitido) {
            for (int k = 0; k < i; k++) {
                InformesemanalPK informePK = new InformesemanalPK(user, idPs.get(k),
                        idActividades.get(k), idEtapas.get(k), new Date());
                Informesemanal informe = new Informesemanal(informePK, "PENDIENTE-APROBAR");
                informe.setHorastarea1(horas.get(0));
                informe.setHorastarea2(horas.get(1));
                informe.setHorastarea3(horas.get(2));
                informe.setHorastarea4(horas.get(3));
                informe.setHorastarea5(horas.get(4));
                informe.setHorastarea6(horas.get(5));
                try {
                    informeSemanalFacade.create(informe);
                } catch (Exception E) {
                }
                System.out.println(informe.getInformesemanalPK().getFechasemana());
            }
        }
        return permitido;
    }

    
    /**
     * Cambia el valor de los proyectos paralelos
     * @param numP 
     */
    @GET
    @Path("/admin/conf")
    public void setParalelos(@QueryParam("numP") String numP) {
        Integer paral = Integer.parseInt(numP);
        Datosconfigurables paralel;
        paralel = configFacade.findAll().get(0);
        paralel.setValor(paral);
        configFacade.edit(paralel);
    }

    
    /**
     * Crea un nuevo usuario en la base de datos
     * @param nick
     * @param pass
     * @param cat 
     */
    @GET
    @Path("/admin/newUser")
    public void createUsuario(@QueryParam("nick") String nick, @QueryParam("pass") String pass, @QueryParam("cat") String cat) {
        Integer categoria = Integer.parseInt(cat);
        Trabajador newUser = new Trabajador();
        newUser.setNick(nick);
        newUser.setPassword(pass);
        newUser.setCategoria(categoria);
        trabajadorFacade.create(newUser);
    }
    
    /**
     * devuelve todos los trabajadores
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/trabajadores")
    public List<Trabajador> getTrabajadores() {
        List<Trabajador> workers;
        workers = trabajadorFacade.findAll();
        return workers;
    }

    
    /**
     * Devuelve las actividades listas para su cierre
     * @param nombre
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/proyectos/jefe/{nick}/cerrar")
    public List<Actividad> getActividadesCierre(@PathParam("nick") String nombre) {
        Trabajador t = trabajadorFacade.find(nombre);
        System.out.println("Trabajador: " + t.getNick());
        List<Proyecto> proyectos = proyectoFacade.findAll();
        for (Proyecto p : proyectos) {
            if (t.getNick().compareTo(p.getNickjefe()) == 0 && p.getFechafin() == null) {
                System.out.println("Proyecto: " + p.getId());
                List<Actividad> actividades = actividadFacade.findAll();
                List<Actividad> actividadesProyecto = new ArrayList<>();
                for (Actividad item : actividades) {
                    int idP = item.getActividadPK().getIdproyecto();
                    List<Actividad> predecesoras = (List<Actividad>) item.getActividadCollection1();
                    if (idP == p.getId() && item.getFechainicio() != null && item.getFechafin() == null) {
                        if (predecesoras.size() != 0) {
                            for (Actividad pred : predecesoras) {
                                if (pred.getFechafin() != null) {
                                    actividadesProyecto.add(item);
                                }
                            }
                        } else {
                            actividadesProyecto.add(item);
                        }

                    }
                }
                return actividadesProyecto;
            }
        }
        return null;
    }

    
    /**
     * Devuele las actividades que se pueden iniciar actualmente
     * @param idProyecto
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/actividadesDisponibles")
    public List<Actividad> getActividadesDisponibles(@QueryParam("idP") int idProyecto) {
        Proyecto p = proyectoFacade.find(idProyecto);
        List<Actividad> actividades = actividadFacade.findAll();
        List<Actividad> actividadesFiltradas = new ArrayList<>();
        for (Actividad item : actividades) {
            if (item.getEtapa().getProyecto().equals(p) & item.getFechainicio() == null
                    & item.getEsfuerzoestimado() != 0) {
                List<Actividad> predecesoras = (List<Actividad>) item.getActividadCollection1();
                for (Actividad pred : predecesoras) {
                    if (pred.getFechafin() != null) {
                        actividadesFiltradas.add(item);
                    }
                }
            }
        }
        return actividadesFiltradas;
    }

    
    /**
     * Guarda la fecha de finalización de la actividad
     * @param nombre
     * @param fechaFin
     * @return 
     */
    @GET
    @Path("/cerrarActividad/{nombre}/fechaFin/{fechaFin}")
    public boolean setFechaFin(@PathParam("nombre") String nombre, @PathParam("fechaFin") String fechaFin) {
        String nombreAct = nombre;
        DateTime ahora = new DateTime();
        DateTime fechaCierre = new DateTime(fechaFin);
        List<Actividad> actividades = actividadFacade.findAll();
        for (Actividad item : actividades) {
            if (nombreAct.compareTo(item.getNombre()) == 0) {
                DateTime fechaIni = new DateTime(item.getFechainicio());
                if ((fechaCierre.isAfter(fechaIni) || fechaCierre.isEqual(fechaIni))) {
                    item.setFechafin(fechaCierre.toDate());
                    actividadFacade.edit(item);
                    if (item.getActividadCollection().size() <= 0) {
                        Proyecto p = proyectoFacade.find(item.getActividadPK().getIdproyecto());
                        p.setFechafin(fechaCierre.toDate());
                        proyectoFacade.edit(p);
                    }
                    return true;
                }
            }
        }
        return false;
    }

    
    /**
     * Guarda la fecha de inicio de la actividad
     * @param idP
     * @param idA
     * @param idE
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/setFechaInicio")
    public boolean setFechaInicio(@QueryParam("idP") int idP, @QueryParam("idA") int idA,
            @QueryParam("idE") int idE) {
        boolean permitido = true;
        List<Actividad> actividades = actividadFacade.findAll();
        Actividad a = null;
        for (Actividad item : actividades) {
            if (item.getActividadPK().getId() == idA & item.getActividadPK().getIdproyecto() == idP
                    & item.getActividadPK().getIdetapa() == idE) {
                a = item;
            }
        }
        try {
            a.setFechainicio(new Date());
            actividadFacade.edit(a);
        } catch (Exception E) {
            permitido = false;
        }

        return permitido;
    }
    
    /**
     * Actividades que podemos asignar a un trabajador
     * @param nombre
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/proyectos/jefe/{nick}/actividadesAsignables")
    public List<Actividad> getActividadesAsignables(@PathParam("nick") String nombre) {
        Trabajador t = trabajadorFacade.find(nombre);
        List<Proyecto> proyectos = proyectoFacade.findAll();
        for (Proyecto p : proyectos) {
            System.out.println("p: " + p.getNombre());
            if (t.getNick().compareTo(p.getNickjefe()) == 0) {
                System.out.println("Ah ");
                List<Actividad> actividades = actividadFacade.findAll();
                List<Actividad> actividadesProyecto = new ArrayList<>();
                for (Actividad item : actividades) {
                    int idP = item.getActividadPK().getIdproyecto();
                    List<Actividad> predecesoras = (List<Actividad>) item.getActividadCollection1();
                    System.out.println("Item: " + item.getNombre());
                    if (idP == p.getId() /*&& item.getFechainicio()!=null*/) {

                        System.out.println("Item:if1 " + item.getNombre());
                        for (Actividad pred : predecesoras) {
                            if (pred.getFechafin() != null) {
                                System.out.println("Item:if2 " + item.getNombre());
                                actividadesProyecto.add(item);
                            }
                        }

                    }
                }
                return actividadesProyecto;
            }
        }
        return null;
    }

    @GET
    @Produces("application/json")
    @Path("/asignar/desarrollador/actividad/{nick}/{idp}/{ide}/{id}")
    public boolean asignarDesarroladorActividaad(@PathParam("nick") String nick, @PathParam("idp") String sidp, @PathParam("ide") String side, @PathParam("id") String sid) {
        boolean resultado = false;
        try {
            int idp = Integer.valueOf(sidp);
            int ide = Integer.valueOf(side);
            int id = Integer.valueOf(sid);
            System.out.println("IDP, IDE,ID: "+idp+" "+ide+" "+id);
            Actividad a = actividadFacade.find(new ActividadPK(idp, ide, id));
            Trabajador t = trabajadorFacade.find(nick);
            System.out.println("Nick: " + t.getNick());
            List<Trabajador> trabajadores = new ArrayList<>(a.getTrabajadorCollection());
            trabajadores.add(t);
            a.setTrabajadorCollection(trabajadores);
            t.getActividadCollection().add(a);
            trabajadorFacade.edit(t);
            actividadFacade.edit(a);
            resultado = true;
        } catch (Exception e) {
            resultado = false;
        }
        return resultado;

    }

    @GET
    @Produces("application/json")
    @Path("/proyectos/jefe/{nick}/desarrolladoresAsignables")
    public List<Trabajador> getDesarrolladoresAsignables(@PathParam("nick") String nombre) {
        Trabajador t = trabajadorFacade.find(nombre);
        List<Proyecto> proyectos = proyectoFacade.findAll();
        List<Trabajador> disponibles = new ArrayList<>();
        for (Proyecto p : proyectos) {
            if (t.getNick().compareTo(p.getNickjefe()) == 0) {
                List<Dedicacion> dedicaciones = (List<Dedicacion>) p.getDedicacionCollection();
                for (Dedicacion d : dedicaciones) {
                    Trabajador worker = d.getTrabajador();

                    if (worker.getDedicacionCollection().size() < 4) {
                        disponibles.add(worker);
                    }
                }
            }
        }
        return disponibles;
    }
    
    /**
     * Informes que se encuentran en estado PENDIENTE-APROBAR
     * @param idP
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/informesPendienteAprobar")
    public List<Informesemanal> getInformesPendienteAprobar(@QueryParam("idP") int idP) {
        List<Informesemanal> informes = informeSemanalFacade.findAll();
        List<Informesemanal> informesFiltrados = new ArrayList<>();
        for (Informesemanal item : informes) {
            if (item.getActividad().getActividadPK().getIdproyecto() == idP & item.getEstado().equals("PENDIENTE-APROBAR")) {
                informesFiltrados.add(item);
                item.getInformesemanalPK().getFechasemana();
            }
        }

        return informesFiltrados;
    }

    
    /**
     * Informes que están pendientes de enviar
     * @param idP
     * @return 
     */
    @GET
    @Produces("application/json")
    @Path("/informesPendienteEnviar")
    public List<Informesemanal> getInformesPendienteEnviar(@QueryParam("idP") int idP) {
        List<Informesemanal> informes = informeSemanalFacade.findAll();
        List<Informesemanal> informesFiltrados = new ArrayList<>();
        for (Informesemanal item : informes) {
            if (item.getActividad().getActividadPK().getIdproyecto() == idP & item.getEstado().equals("PENDIENTE-ENVIAR")) {
                informesFiltrados.add(item);
                item.getInformesemanalPK().getFechasemana();
            }
        }

        return informesFiltrados;
    }

    @GET
    @Produces("application/json")
    @Path("/aprobar")
    public boolean setAprobar(@QueryParam("user") String user, @QueryParam("idP") int idP, @QueryParam("idA") int idA) {
        boolean permitido = true;
        List<Informesemanal> informes = informeSemanalFacade.findAll();
        System.out.println(user + ":" + idP + ":" + idA);
        for (Informesemanal item : informes) {
            if (item.getInformesemanalPK().getIdproyecto() == idP & item.getInformesemanalPK().getIdactividad() == idA
                    & item.getInformesemanalPK().getNicktrabajador().equals(user) & item.getEstado().equals("PENDIENTE-APROBAR")) {
                item.setEstado("ACEPTADO");
                informeSemanalFacade.edit(item);
                System.out.println("vdfv");
            }
        }
        return permitido;
    }

}

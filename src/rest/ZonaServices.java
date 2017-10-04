package rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Zona;

@Path("zonas")
public class ZonaServices {

	/**
	 * Atributo que usa la anotacion @Context para tener el ServletContext de la conexion actual.
	 */
	@Context
	private ServletContext context;

	/**
	 * Metodo que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	private String getPath() {
		return context.getRealPath("WEB-INF/ConnectionData");
	}
	
	
	private String doErrorMessage(Exception e){
		return "{ \"ERROR\": \""+ e.getMessage() + "\"}" ;
	}
	

	/**
	 * Metodo que expone servicio REST usando GET que da todos los restaurantes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @return Json con todos los videos de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getZonas() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Zona> zonas;
		try {
			zonas = tm.darZonas();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zonas).build();
	}

	public static Comparator<Zona>  nomComparator = new Comparator<Zona>()
	{
		public int compare(Zona o1, Zona o2) {
			return o1.getNombre().compareTo(o2.getNombre());
		}            

	};	
	
	 /**
     * Metodo que expone servicio REST usando GET que busca el video con el id que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/<<id>>" para la busqueda"
     * @param name - Nombre del video a buscar que entra en la URL como parametro 
     * @return Json con el/los videos encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path("Nombre")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getZonasPorNombre()
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		List<Zona> zonas = new ArrayList<>();
		try
		{
			zonas = tm.darZonas();
			Collections.sort(zonas, nomComparator);
			return Response.status( 200 ).entity(zonas).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}

	
	public static Comparator<Zona>  capacidadComparator = new Comparator<Zona>()
	{
		public int compare(Zona o1, Zona o2) {
			return o2.getCapacidad() - o1.getCapacidad();
		}            

	};	
	
	 /**
     * Metodo que expone servicio REST usando GET que busca el video con el id que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/<<id>>" para la busqueda"
     * @param name - Nombre del video a buscar que entra en la URL como parametro 
     * @return Json con el/los videos encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path("Capacidad")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getZonasPorCapacidad()
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		List<Zona> zonas = new ArrayList<>();
		try
		{
			zonas = tm.darZonas();
			Collections.sort(zonas, capacidadComparator);
			return Response.status( 200 ).entity(zonas).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	public static Comparator<Zona>  abiertoComparator = new Comparator<Zona>()
	{
		public int compare(Zona o1, Zona o2) {
			return o2.getAbierto() - o1.getAbierto();
		}            

	};	
	
	 /**
     * Metodo que expone servicio REST usando GET que busca el video con el id que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/<<id>>" para la busqueda"
     * @param name - Nombre del video a buscar que entra en la URL como parametro 
     * @return Json con el/los videos encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path("Abierta")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getZonasPorAbierto()
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		List<Zona> zonas = new ArrayList<>();
		try
		{
			zonas = tm.darZonas();
			Collections.sort(zonas, abiertoComparator);
			return Response.status( 200 ).entity(zonas).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
	public static Comparator<Zona>  discapacidadComparator = new Comparator<Zona>()
	{
		public int compare(Zona o1, Zona o2) {
			return o1.getAptoDiscapacidad() - o2.getAptoDiscapacidad();
		}            

	};	
	
	 /**
     * Metodo que expone servicio REST usando GET que busca el video con el id que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/<<id>>" para la busqueda"
     * @param name - Nombre del video a buscar que entra en la URL como parametro 
     * @return Json con el/los videos encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
	@GET
	@Path("Discapacidad")
	@Produces( { MediaType.APPLICATION_JSON } )
	public Response getZonasPorDiscapacidad()
	{
		RotondAndesTM tm = new RotondAndesTM( getPath( ) );
		List<Zona> zonas = new ArrayList<>();
		try
		{
			zonas = tm.darZonas();
			Collections.sort(zonas, discapacidadComparator);
			return Response.status( 200 ).entity(zonas).build( );			
		}
		catch( Exception e )
		{
			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
		}
	}
	
    /**
     * Metodo que expone servicio REST usando GET que busca el video con el nombre que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/nombre/nombre?nombre=<<nombre>>" para la busqueda"
     * @param name - Nombre del video a buscar que entra en la URL como parametro 
     * @return Json con el/los videos encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
//     */
//	@GET
//	@Path( "{nombre}" )
//	@Produces( { MediaType.APPLICATION_JSON } )
//	public Response getVideoName( @QueryParam("nombre") String name) {
//		VideoAndesTM tm = new VideoAndesTM(getPath());
//		List<Video> videos;
//		try {
//			if (name == null || name.length() == 0)
//				throw new Exception("Nombre del video no valido");
//			videos = tm.buscarVideosPorName(name);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(videos).build();
//	}


    /**
     * Requerimiento F3
     * Metodo que expone servicio REST usando POST que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addZona(Zona zona) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addZona(zona);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(zona).build();
	}
	
    /**
     * Metodo que expone servicio REST usando PUT que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
     * @param video - video a actualizar. 
     * @return Json con el video que actualizo o Json con el error que se produjo
     */
//	@PUT
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response updateVideo(Video video) {
//		VideoAndesTM tm = new VideoAndesTM(getPath());
//		try {
//			tm.updateVideo(video);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(video).build();
//	}
	
    /**
     * Metodo que expone servicio REST usando DELETE que elimina el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
     * @param video - video a aliminar. 
     * @return Json con el video que elimino o Json con el error que se produjo
     */
//	@DELETE
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response deleteVideo(Video video) {
//		VideoAndesTM tm = new VideoAndesTM(getPath());
//		try {
//			tm.deleteVideo(video);
//		} catch (Exception e) {
//			return Response.status(500).entity(doErrorMessage(e)).build();
//		}
//		return Response.status(200).entity(video).build();
//	}

}

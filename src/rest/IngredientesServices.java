package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Informacion;
import vos.Ingredientes;

@Path("ingredientes")
public class IngredientesServices {

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
	public Response getIngredientes() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Ingredientes> ingredientes;
		try {
			ingredientes = tm.darIngredientes();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingredientes).build();
	}

	 /**
     * Metodo que expone servicio REST usando GET que busca el video con el id que entra como parametro
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/<<id>>" para la busqueda"
     * @param name - Nombre del video a buscar que entra en la URL como parametro 
     * @return Json con el/los videos encontrados con el nombre que entra como parametro o json con 
     * el error que se produjo
     */
//	@GET
//	@Path( "{id: \\d+}" )
//	@Produces( { MediaType.APPLICATION_JSON } )
//	public Response getVideo( @PathParam( "id" ) Long id )
//	{
//		VideoAndesTM tm = new VideoAndesTM( getPath( ) );
//		try
//		{
//			Video v = tm.buscarVideoPorId( id );
//			return Response.status( 200 ).entity( v ).build( );			
//		}
//		catch( Exception e )
//		{
//			return Response.status( 500 ).entity( doErrorMessage( e ) ).build( );
//		}
//	}

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
     * Requerimiento F5
     * Metodo que expone servicio REST usando POST que agrega el ingrediente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addIngrediente(Ingredientes ingrediente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addIngrediente(ingrediente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingrediente).build();
	}
	
	 /**
     *
     * Metodo que expone servicio REST usando POST que agrega el ingrediente que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addEquivalencia(Ingredientes ingrediente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addIngrediente(ingrediente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(ingrediente).build();
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
	
	
//	@Path("equivalentes/{productos}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void addEquivalencia(@QueryParam("productos") String productos) throws Exception {
	
	/**
     * Requerimiento F11
     * Metodo que expone servicio REST usando POST que agrega la equivalencia entre ingredientes 
     * para un restaurante
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Path("equivalentes")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public void addEquiv(Informacion informacionEq) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addEquivalenciaIngrediente(informacionEq.getInformacion());
		} catch (Exception e) {	}
	}
}

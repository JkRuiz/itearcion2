package rest;

import java.util.List;

import javax.servlet.ServletContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import tm.RotondAndesTM;
import vos.Funcionamiento;
import vos.Informacion;
import vos.Pedido;
import vos.PedidoConsolidado;

@Path("pedidos")
public class PedidoServices {

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
	public Response getPedidos() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Pedido> pedidos;
		try {
			pedidos = tm.darPedidos();
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedidos).build();
	}

	/**
	 * Metodo que expone servicio REST usando GET que da todos los restaurantes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @return Json con todos los videos de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Path( "{email}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPedidosCliente(@QueryParam("email") String emailCliente) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Pedido> pedidos;
		try {
			pedidos = tm.darPedidosCliente(emailCliente);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedidos).build();
	}
	
	/**
	 * Requerimiento C8
	 * Metodo que expone servicio REST usando GET que da todos los restaurantes de la base de datos.
	 * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
	 * @return Json con todos los videos de la base de datos o json con 
     * el error que se produjo
	 */
	@GET
	@Path( "restaurante/{restaurante}" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getPedidos(@QueryParam("restaurante") String restaurante) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		PedidoConsolidado pedido;
		try {
			pedido = tm.darPedidosRestaurante(restaurante);
		} catch (Exception e) {
			return Response.status(400).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}
	
	/**
	 * RFC11
	 * @return
	 */
	@GET
	@Path( "funcionamiento" )
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getFuncionamiento() {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		List<Funcionamiento> funcionamiento;
		try {
			funcionamiento = tm.darFuncionamiento();
		} catch (Exception e) {
			return Response.status(400).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(funcionamiento).build();
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
     * Requerimiento F9
     * Metodo que expone servicio REST usando POST que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Path( "plato/{idPlato: \\d+}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPedidoPlato(Pedido pedido,  @PathParam( "idPlato" ) int idPlato) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addPedidoPlato(pedido, idPlato);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}
	
	/**
     * Metodo que expone servicio REST usando POST que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Path( "menu/{idMenu: \\d+}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPedidoMenu(Pedido pedido,  @PathParam( "idMenu" ) int idMenu) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addPedidoMenu(pedido, idMenu);
		} catch (Exception e) {
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}
	

	/**
	 * Requerimiento F14
     * Metodo que expone servicio REST usando POST que agrega el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos/video
     * @param video - video a agregar
     * @return Json con el video que agrego o Json con el error que se produjo
     */
	@POST
	@Path( "menu/{idMenu: \\d+}/{equivalencia}" )
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPedidoMenuEquivalencia(Pedido pedido,  @PathParam( "idMenu" ) int idMenu, @QueryParam( "equivalencia" ) String equivalencia) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.addPedidoMenuEquivalencia(pedido, idMenu, equivalencia);
		} catch (Exception e) {
			return Response.status(400).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}
	
    /**
     * Requerimiento RF10 y RF16
     * Metodo que expone servicio REST usando PUT que actualiza el video que recibe en Json
     * <b>URL: </b> http://"ip o nombre de host":8080/VideoAndes/rest/videos
     * @param video - video a actualizar. 
     * @return Json con el video que actualizo o Json con el error que se produjo
     */
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response updatePedido(Pedido pedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.pedidoEntregado(pedido);
		} catch (Exception e) {
			return Response.status(400).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(pedido).build();
	}
	
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
	
	/**
     * Requerimiento F15
     */
	@POST
	@Path("mesa/{datos}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response registrarPedidoMesa(Pedido pedido, @PathParam("datos") String datos) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.registrarPedidoMesa(pedido, datos);
		} catch (Exception e) {	
			return Response.status(400).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(new Informacion()).build();
	}
	
	/**
     * Requerimiento F17
     */
	@DELETE
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response cancelarPedido(Pedido pedido) {
		RotondAndesTM tm = new RotondAndesTM(getPath());
		try {
			tm.cancelarPedido(pedido);
		} catch (Exception e) { 
			return Response.status(500).entity(doErrorMessage(e)).build();
		}
		return Response.status(200).entity(new Informacion()).build();
	}
}

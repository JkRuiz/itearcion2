package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import sun.util.BuddhistCalendar;
import vos.Pedido;
import vos.PedidoMenu;
import vos.PedidoPlato;


public class DAOTablaPedidoMenu {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPedidoMenu
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPedidoMenu() {
		recursos = new ArrayList<Object>();
	}

	/**
	 * Metodo que cierra todos los recursos que estan enel arreglo de recursos
	 * <b>post: </b> Todos los recurso del arreglo de recursos han sido cerrados
	 */
	public void cerrarRecursos() {
		for(Object ob : recursos){
			if(ob instanceof PreparedStatement)
				try {
					((PreparedStatement) ob).close();
				} catch (Exception ex) {
					ex.printStackTrace();
				}
		}
	}

	/**
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los pedidosMenu de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PEDIDO_MENU;
	 * @return Arraylist con los pedidosMenu de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<PedidoMenu> darPedidosMenu() throws SQLException, Exception {
		ArrayList<PedidoMenu> pedidosMenu = new ArrayList<PedidoMenu>();

		String sql = "SELECT * FROM PEDIDO_MENUS";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int numPedido = rs.getInt("NUM_PEDIDO");
			int idMenu = rs.getInt("ID_MENU");
			pedidosMenu.add(new PedidoMenu(numPedido, idMenu));
		}
		return pedidosMenu;
	}


	/**
	 * Metodo que busca el/los pedidosMenu con el nombre que entra como parametro.
	 * @param name - Nombre de el/los pedidosMenu a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
//	public ArrayList<Video> buscarVideosPorName(String name) throws SQLException, Exception {
//		ArrayList<Video> videos = new ArrayList<Video>();
//
//		String sql = "SELECT * FROM VIDEO WHERE NAME ='" + name + "'";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String name2 = rs.getString("NAME");
//			Long id = rs.getLong("ID");
//			Integer duration = rs.getInt("DURATION");
//			videos.add(new Video(id, name2, duration));
//		}
//
//		return videos;
//	}
	
	/**
	 * Metodo que busca el video con el id que entra como parametro.
	 * @param name - Id de el video a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
//	public Video buscarVideoPorId(Long id) throws SQLException, Exception 
//	{
//		Video video = null;
//
//		String sql = "SELECT * FROM VIDEO WHERE ID =" + id;
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		if(rs.next()) {
//			String name = rs.getString("NAME");
//			Long id2 = rs.getLong("ID");
//			Integer duration = rs.getInt("DURATION");
//			video = new Video(id2, name, duration);
//		}
//
//		return video;
//	}

	/**
	 * Metodo que agrega el pedidoMenu que entra como parametro a la base de datos.
	 * @param pedidoMenu - el pedidoMenu a agregar. pedidoMenu !=  null
	 * <b> post: </b> se ha agregado el pedidoMenu a la base de datos en la transaction actual. pendiente que el pedidoMenu master
	 * haga commit para que el pedidoMenu baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el pedidoMenu a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedidoMenu(PedidoMenu pedidoMenu) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO_MENUS VALUES (";
		sql += pedidoMenu.getNumPedido() + ",";
		sql += pedidoMenu.getIdMenu() + ")";
		
		System.out.println("ADD PEDIDO_MENU: "+ sql);
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	
	/**
	 * Metodo que actualiza el video que entra como parametro en la base de datos.
	 * @param video - el video a actualizar. video !=  null
	 * <b> post: </b> se ha actualizado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
//	public void updateVideo(Video video) throws SQLException, Exception {
//
//		String sql = "UPDATE VIDEO SET ";
//		sql += "NAME='" + video.getName() + "',";
//		sql += "DURATION=" + video.getDuration();
//		sql += " WHERE ID = " + video.getId();
//
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}

	/**
	 * Metodo que elimina el video que entra como parametro en la base de datos.
	 * @param video - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
//	public void deleteVideo(Video video) throws SQLException, Exception {
//
//		String sql = "DELETE FROM VIDEO";
//		sql += " WHERE ID = " + video.getId();
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}
	
	public ArrayList<PedidoMenu> bucarPedidoMenuPorIdPedido(int id) throws SQLException, Exception 
	{
		ArrayList<PedidoMenu>  pedidosMenu = new ArrayList<>();

		String sql = "SELECT * FROM PEDIDO_MENUS WHERE NUM_PEDIDO =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int numPedido = rs.getInt("NUM_PEDIDO");
			int idMenu = rs.getInt("ID_MENU");
			pedidosMenu.add(new PedidoMenu(numPedido, idMenu));
		}
		return pedidosMenu;
	}

	public void removerPedidos(Pedido pedido) throws SQLException, Exception {
		String sql = "DELETE FROM PEDIDO_MENUS WHERE NUM_PEDIDO =" + pedido.getNumPedido();

		System.out.println("REMOVER PEDIDOS: " + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}

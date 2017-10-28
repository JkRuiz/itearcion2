package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Pedido;
import vos.PedidoPlato;


public class DAOTablaPedidoPlato {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPedidoPlato
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPedidoPlato() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los pedidosPlato de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PEDIDO_PLATO;
	 * @return Arraylist con los pedidosPlato de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<PedidoPlato> darPedidosPlato() throws SQLException, Exception {
		ArrayList<PedidoPlato> pedidosPlato = new ArrayList<PedidoPlato>();

		String sql = "SELECT * FROM PEDIDO_PLATO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int numPedido = rs.getInt("NUM_PEDIDO");
			int idPlato = rs.getInt("ID_PLATO");
			pedidosPlato.add(new PedidoPlato(numPedido, idPlato));
		}
		return pedidosPlato;
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
	 * @param name - Id de el pedido a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<PedidoPlato> bucarPedidoPlatoPorIdPedido(int id) throws SQLException, Exception 
	{
		ArrayList<PedidoPlato>  pedidosPlato = new ArrayList<>();

		String sql = "SELECT * FROM PEDIDO_PLATO WHERE NUM_PEDIDO =" + id;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int numPedido = rs.getInt("NUM_PEDIDO");
			int idPlato = rs.getInt("ID_PLATO");
			pedidosPlato.add(new PedidoPlato(numPedido, idPlato));
		}
		return pedidosPlato;
	}

	/**
	 * Metodo que agrega el pedidoPlato que entra como parametro a la base de datos.
	 * @param pedidoPlato - el pedidoPlato a agregar. pedidoMenu !=  null
	 * <b> post: </b> se ha agregado el pedidoPlato a la base de datos en la transaction actual. pendiente que el pedidoPlato master
	 * haga commit para que el pedidoPlato baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el pedidoPlato a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedidoPlato(PedidoPlato pedidoPlato) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO_PLATO VALUES (";
		sql += pedidoPlato.getNumPedido() + ",";
		sql += pedidoPlato.getIdPlato() + ")";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	public void removerPedidos(Pedido pedido) throws SQLException, Exception {
		String sql = "DELETE * FROM PEDIDO_PLATO WHERE NUM_PEDIDO =" + pedido.getNumPedido();

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
}

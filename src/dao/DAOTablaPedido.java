package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Pedido;

public class DAOTablaPedido {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPedido
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPedido() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los pedidos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PEDIDOS;
	 * @return Arraylist con los pedidos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Pedido> darPedidos() throws SQLException, Exception {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int numPedido = rs.getInt("NUM_PEDIDO");
			float precio = rs.getFloat("PRECIO");
			String fecha = rs.getString("FECHA");
			String emailUser = rs.getString("EMAIL_USER");
			int pagado = rs.getInt("PAGADO");
			int entregado = rs.getInt("ENTREGADO");

			pedidos.add(new Pedido(numPedido, precio, fecha, emailUser, pagado, entregado));
		}
		return pedidos;
	}



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
	 * Metodo que agrega el video que entra como parametro a la base de datos.
	 * @param video - el video a agregar. video !=  null
	 * <b> post: </b> se ha agregado el video a la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que el video baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el video a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPedido(Pedido pedido) throws SQLException, Exception {

		String sql = "INSERT INTO PEDIDO VALUES (";
		sql += pedido.getNumPedido() + ",";
		sql += pedido.getPrecio() + ",";
		sql += "TO_DATE('" + pedido.getFecha() + "', 'DD/MM/YYYY')"  + ",'";
		sql += pedido.getEmailUser() + "',";
		sql += pedido.getPagado() + ",";
		sql += pedido.getEntregado() + ")";

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
		public void updatePedido(Pedido pedido) throws SQLException, Exception {
	
			String sql = "UPDATE PEDIDO SET ";
			sql += "PAGADO=" + pedido.getPagado() + ",";
			sql += "ENTREGADO=" + pedido.getEntregado();
			sql += " WHERE NUM_PEDIDO = " + pedido.getNumPedido();
	
	
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			prepStmt.executeQuery();
		}

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

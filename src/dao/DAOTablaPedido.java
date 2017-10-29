package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Pedido;
import vos.PedidoConsolidado;
import vos.ProductoConsolidado;

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
			String hora = rs.getString("HORA");
			String cambio = rs.getString("CAMBIO");
			pedidos.add(new Pedido(numPedido, precio, fecha, emailUser, pagado, entregado, hora, cambio));
		}
		return pedidos;
	}

	public PedidoConsolidado darPedidosConsolidadosRestaurante(String restaurante) throws SQLException, Exception {
		ArrayList<ProductoConsolidado> productos = new ArrayList<ProductoConsolidado>();
		int consumidoRegistrados = 0;
		int consumidoNoRegistrados = 0;
		
		String sql = "SELECT NOMBRE,PRECIOVENTA,VENDIDOS,ID_PLATO FROM PLATO WHERE RESTAURANTE='"+ restaurante + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomProducto = rs.getString("NOMBRE");
			int precioVenta = rs.getInt("PRECIOVENTA");
			int vendidos = rs.getInt("VENDIDOS");
			int idPlato = rs.getInt("ID_PLATO");
			int ventaTotalDinero = precioVenta*vendidos;
			
			//Se mira quienes pidieron el plato correspondiente, verificando si es o no un usuario registrado
			String sqlAux = "SELECT EMAIL_USER FROM PEDIDO_PLATO NATURAL JOIN PEDIDO NATURAL JOIN PLATO WHERE ID_PLATO ="+ idPlato;
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			while (rsAux.next())
			{
				String emailUser = rsAux.getString("EMAIL_USER");
				
				String sqlAux2 = "SELECT count(*) FROM CLIENTEFR WHERE EMAIL = '" + emailUser + "'";
				PreparedStatement prepStmtAux2 = conn.prepareStatement(sqlAux2);
				recursos.add(prepStmtAux2);
				ResultSet rsAux2 = prepStmtAux2.executeQuery();
				if (rsAux2.next())
				{
					int count = rsAux2.getInt("COUNT(*)");
					if (count == 0) consumidoNoRegistrados++;
					else consumidoRegistrados++;
				}
			}
			
			//Se mira quienes pidieron el plato correspondiente POR MEDIO DE UN MENU, verificando si es o no un usuario registrado
			String sqlAux2 = "select EMAIL_USER from pedido natural join pedido_menus where id_menu = (select id_menu from MENU_PLATO where id_plato ="+ idPlato + ")";
			PreparedStatement prepStmtAux2 = conn.prepareStatement(sqlAux2);
			recursos.add(prepStmtAux2);
			ResultSet rsAux2 = prepStmtAux2.executeQuery();
			while (rsAux2.next())
			{
				String emailUser = rsAux2.getString("EMAIL_USER");
				
				String sqlAux3 = "SELECT count(*) FROM CLIENTEFR WHERE EMAIL = '" + emailUser + "'";
				PreparedStatement prepStmtAux3 = conn.prepareStatement(sqlAux3);
				recursos.add(prepStmtAux3);
				ResultSet rsAux3 = prepStmtAux3.executeQuery();
				if (rsAux3.next())
				{
					int count = rsAux3.getInt("COUNT(*)");
					if (count == 0) consumidoNoRegistrados++;
					else consumidoRegistrados++;
				}
			}
			
			productos.add(new ProductoConsolidado(nomProducto, ventaTotalDinero, vendidos));
		}
		return new PedidoConsolidado(productos, consumidoRegistrados, consumidoNoRegistrados);
	}
	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los pedidos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PEDIDOS;
	 * @return Arraylist con los pedidos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Pedido> darPedidosCliente(String emailCliente) throws SQLException, Exception {
		ArrayList<Pedido> pedidos = new ArrayList<Pedido>();

		String sql = "SELECT * FROM PEDIDO WHERE EMAIL_USER ='" + emailCliente + "'";

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
			String hora = rs.getString("HORA");
			String cambio = rs.getString("CAMBIO");
			pedidos.add(new Pedido(numPedido, precio, fecha, emailUser, pagado, entregado, hora, cambio));
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

		String sql = "INSERT INTO PEDIDO ("
				+ "NUM_PEDIDO, PRECIO, FECHA, EMAIL_USER,"
				+ "PAGADO, ENTREGADO, HORA, CAMBIO) VALUES (";
		sql += pedido.getNumPedido() + ",";
		sql += ((int)pedido.getPrecio()) + ",";
		sql += "TO_DATE('" + pedido.getFecha() + "', 'DD/MM/YYYY')"  + ",'";
		sql += pedido.getEmailUser() + "',";
		sql += pedido.getPagado() + ",";
		sql += pedido.getEntregado() + ",'";
		sql += pedido.getHora() + "','";
		sql += pedido.getCambios() + "')";
		
		System.out.println("ADD PEDIDO: "+ sql);

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

		public Pedido darPedido(int numPedido) throws SQLException {
			Pedido pedido = null;
			String sql = "SELECT * FROM PEDIDO WHERE NUM_PEDIDO=" + numPedido;
			
			PreparedStatement prepStmt = conn.prepareStatement(sql);
			recursos.add(prepStmt);
			ResultSet rs  = prepStmt.executeQuery();
			
			if(rs.next()) {
				float precio = rs.getFloat("PRECIO");
				String fecha = rs.getString("FECHA");
				String emailUser = rs.getString("EMAIL_USER");
				int pagado = rs.getInt("PAGADO");
				int entregado = rs.getInt("ENTREGADO");
				String hora = rs.getString("HORA");
				String cambio = rs.getString("CAMBIO");
				pedido = new Pedido(numPedido, precio, fecha, emailUser, pagado, entregado, hora, cambio);
			}
			return pedido;
		}
		
	public void removerPedido(Pedido pedido) throws SQLException, Exception {
		String sql = "DELETE FROM PEDIDO WHERE NUM_PEDIDO =" + pedido.getNumPedido();

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

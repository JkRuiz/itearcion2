package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.ClienteAux;
import vos.ClienteFrecuente;
import vos.Pedido;


public class DAOTablaClienteFrecuente {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOCliente
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaClienteFrecuente() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los clientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CLIENTESFR;
	 * @return Arraylist con los clientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ClienteFrecuente> darClientes() throws SQLException, Exception {
		ArrayList<ClienteFrecuente> clientes = new ArrayList<ClienteFrecuente>();
		ArrayList<Pedido> pedidos;
		String sql = "SELECT * FROM CLIENTEFR";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String email = rs.getString("EMAIL");
			String password = rs.getString("PASSWORD");
			String username = rs.getString("USERNAME");
			float preferenciaPrecio = rs.getFloat("PREFERENCIAPRECIO");
			String preferenciaCategoria = rs.getString("PREFERENCIACATEGORIA");
			String nombre = rs.getString("NOMBRE");
			long identificacion = rs.getLong("IDENTIFICACION");
			String sqlAux = "SELECT * FROM PEDIDO WHERE EMAIL_USER='"+email+"'";
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			pedidos = new ArrayList<Pedido>();
			while (rsAux.next())
			{
				int numPedido = rsAux.getInt("NUM_PEDIDO");
				float precio = rsAux.getFloat("PRECIO");
				String fecha = rsAux.getString("FECHA");
				String emailUser = rsAux.getString("EMAIL_USER");
				int pagado = rsAux.getInt("PAGADO");
				int entregado = rsAux.getInt("ENTREGADO");
				String hora = rsAux.getString("HORA");
				String cambio = rsAux.getString("CAMBIO");
				pedidos.add(new Pedido(numPedido, precio, fecha, emailUser, pagado, entregado, hora, cambio));
			}
			clientes.add(new ClienteFrecuente(email, password, username, preferenciaPrecio, preferenciaCategoria, nombre, identificacion, pedidos));			
		}
		return clientes;
	}

	/**
	 * Metodo que busca el cliente con el email que entra como parametro.
	 * @param email - email del cliente a buscar
	 * @return Cliente encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ClienteFrecuente buscarClientePorEmail(String email) throws SQLException, Exception 
	{
		ClienteFrecuente cliente = null;

		String sql = "SELECT * FROM CLIENTEFR WHERE EMAIL ='" + email + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String email2 = rs.getString("EMAIL");
			String password = rs.getString("PASSWORD");
			String username = rs.getString("USERNAME");
			float preferenciaPrecio = rs.getFloat("PREFERENCIAPRECIO");
			String preferenciaCategoria = rs.getString("PREFERENCIACATEGORIA");
			String nombre = rs.getString("NOMBRE");
			long identificacion = rs.getLong("IDENTIFICACION");
			cliente = new ClienteFrecuente(email2, password, username, preferenciaPrecio, preferenciaCategoria, nombre, identificacion, null);
		}

		return cliente;
	}

	/**
	 * 
	 * @param email
	 * @return
	 * @throws SQLException
	 * @throws Exception
	 */
	public ArrayList<ClienteAux> darBuenosClientes() throws SQLException, Exception 
	{
		ArrayList<ClienteAux> clientes = new ArrayList<ClienteAux>();

		String sql1 = "SELECT EMAIL, NOMBRE, IDENTIFICACION FROM(SELECT SUM(NUMWEEKS) AS TOTALWEEKS " + 
				"FROM (SELECT COUNT(DISTINCT(WEEK)) AS NUMWEEKS,YEAR FROM(SELECT NUM_PEDIDO, EXTRACT(YEAR FROM FECHA) AS YEAR," + 
				"to_number(to_char(FECHA,'WW')) AS WEEK FROM (PEDIDO)) GROUP BY YEAR)) NATURAL JOIN (SELECT EMAIL, NOMBRE, IDENTIFICACION," + 
				"SUM(NUMWEEKS) AS TOTALWEEKS FROM (SELECT COUNT(*) AS NUMWEEKS, EMAIL, NOMBRE, IDENTIFICACION, YEAR FROM " + 
				"(SELECT EXTRACT(YEAR FROM FECHA) AS YEAR, EMAIL, NOMBRE, IDENTIFICACION, to_number(to_char(FECHA,'WW')) AS WEEK " + 
				"FROM (PEDIDO INNER JOIN CLIENTEFR ON PEDIDO.EMAIL_USER = CLIENTEFR.EMAIL)) GROUP BY EMAIL, NOMBRE, IDENTIFICACION, YEAR) GROUP BY EMAIL, NOMBRE, IDENTIFICACION, YEAR)";

		System.out.println(sql1);
		String sql2= "SELECT CLIENTEFR.EMAIL, CLIENTEFR.NOMBRE, CLIENTEFR.IDENTIFICACION FROM CLIENTEFR WHERE EMAIL NOT IN (SELECT EMAIL_USER FROM PEDIDO NATURAL JOIN PEDIDO_PLATO INNER JOIN PLATO ON PEDIDO_PLATO.ID_PLATO = PLATO.ID_PLATO where PRECIO < 36885 AND CATEGORIA != 'FUERTE')";
		System.out.println(sql2);
		String sql3 = "SELECT NOMBRE, EMAIL, IDENTIFICACION FROM (PEDIDO NATURAL JOIN PEDIDO_PLATO NATURAL JOIN CLIENTEFR) WHERE EMAIL NOT IN (SELECT EMAIL_USER " + 
				"FROM (PEDIDO NATURAL JOIN PEDIDO_MENUS))";
		System.out.println(sql3);
		
		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		ResultSet rs1 = prepStmt1.executeQuery();

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		ResultSet rs2 = prepStmt2.executeQuery();

		PreparedStatement prepStmt3 = conn.prepareStatement(sql3);
		recursos.add(prepStmt3);
		ResultSet rs3 = prepStmt3.executeQuery();

		int count = 0;
		while (rs1.next() && count < 1000) {
			System.out.println("Entro al while");
			String email = rs1.getString("EMAIL");
			System.out.println("Columna EMAIL correcta");
			String nombre = rs1.getString("NOMBRE");
			System.out.println("Columna NOMBRE correcta");
			long identificacion = rs1.getLong("IDENTIFICACION");
			System.out.println("Columna IDENTIFICACION correcta");

			clientes.add(new ClienteAux(email, nombre, identificacion));			
			count++;
		}

		while (rs2.next() && count < 1000) {

			String email = rs2.getString("EMAIL");
			String nombre = rs2.getString("NOMBRE");
			long identificacion = rs2.getLong("IDENTIFICACION");

			clientes.add(new ClienteAux(email, nombre, identificacion));			
			count++;
		}
		
		while (rs3.next() && count < 1000) {

			String email = rs3.getString("EMAIL");
			String nombre = rs3.getString("NOMBRE");
			long identificacion = rs3.getLong("IDENTIFICACION");

			clientes.add(new ClienteAux(email, nombre, identificacion));			
			count++;
		}

		return clientes;
	}
	
	/**
	 * Metodo que agrega el cliente que entra como parametro a la base de datos.
	 * @param cliente - el cliente a agregar. cliente !=  null
	 * <b> post: </b> se ha agregado el cliente a la base de datos en la transaction actual. pendiente que el cliente master
	 * haga commit para que el cliente baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el cliente a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCliente(ClienteFrecuente cliente) throws SQLException, Exception {

		String sql = "INSERT INTO CLIENTEFR VALUES ('";
		sql += cliente.getEmail() + "','";
		sql += cliente.getPassword() + "','";
		sql += cliente.getUsername() + "',";
		sql += cliente.getPreferenciaPrecio() + ",'";
		sql += cliente.getPreferenciaCategoria() + "','";
		sql += cliente.getNombre() + "',";
		sql += cliente.getIdentificacion() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza el cliente que entra como parametro en la base de datos.
	 * @param cliente - el cliente a actualizar. cliente !=  null
	 * <b> post: </b> se ha actualizado el cliente en la base de datos en la transaction actual. pendiente que el cliente master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el cliente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateCliente(ClienteFrecuente cliente) throws SQLException, Exception {

		String sql = "UPDATE CLIENTEFR SET ";
		sql += "PASSWORD='" + cliente.getPassword() + "',";
		sql += "USERNAME='" + cliente.getUsername() + "',";
		sql += "PREFERENCIACATEGORIA='" + cliente.getPreferenciaCategoria() + "',";
		sql += "PREFERENCIAPRECIO=" + cliente.getPreferenciaPrecio() + ",";
		sql += "NOMBRE='" + cliente.getNombre() + "',";
		sql += "IDENTIFICACION=" + cliente.getIdentificacion();
		sql += " WHERE EMAIL = '" + cliente.getEmail() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el cliente que entra como parametro en la base de datos.
	 * @param cliente - el cliente a borrar. cliente !=  null
	 * <b> post: </b> se ha borrado el cliente en la base de datos en la transaction actual. pendiente que el cliente master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el cliente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCliente(ClienteFrecuente cliente) throws SQLException, Exception {


		String sql = "DELETE FROM ZONAS_CLIENTESFR";
		sql += " WHERE EMAIL_CLIENTEFR = '" + cliente.getEmail() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM RESERVA";
		sql += " WHERE EMAIL_CLIENTE = '" + cliente.getEmail() + "'";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM CLIENTEFR";
		sql += " WHERE EMAIL = '" + cliente.getEmail() + "'";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM USUARIOS";
		sql += " WHERE EMAIL = '" + cliente.getEmail() + "'";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}

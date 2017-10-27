package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingredientes;
import vos.Restaurante;

public class DAOTablaRestaurante {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAORestaurante
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaRestaurante() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los restaurantes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM RESTAURANTES;
	 * @return Arraylist con los restaurantes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Restaurante> darRestaurantes() throws SQLException, Exception {
		ArrayList<Restaurante> restaurantes = new ArrayList<Restaurante>();

		String sql = "SELECT * FROM RESTAURANTES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String representante = rs.getString("REPRESENTANTE");
			String tipoComida = rs.getString("TIPOCOMIDA");
			String paginaWeb = rs.getString("PAGINAWEB");
			String zona = rs.getString("ZONA");
			float valorCostos = rs.getFloat("VALOR_COSTOS");
			float valorVentas = rs.getFloat("VALOR_VENTAS");
			restaurantes.add(new Restaurante(nombre, representante, tipoComida, paginaWeb, zona, valorCostos, valorVentas));
		}
		return restaurantes;
	}


	/**
	 * Metodo que busca el restaurante con el nombre que entra como parametro.
	 * @param name - Nombre de el restaurante a buscar
	 * @return Restaurante el restaurante encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Restaurante buscarRestaurantePorNombre(String nombre) throws SQLException, Exception {
		Restaurante restaurante = null;

		String sql = "SELECT * FROM RESTAURANTES WHERE NOMBRE ='" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			String nombre2 = rs.getString("NOMBRE");
			String representante = rs.getString("REPRESENTANTE");
			String tipoComida = rs.getString("TIPOCOMIDA");
			String paginaWeb = "";
			if(rs.getString("PAGINAWEB") != null)
			paginaWeb = rs.getString("PAGINAWEB");
			
			String zona = rs.getString("ZONA");
			float valorCostos = rs.getFloat("VALOR_COSTOS");
			float valorVentas = rs.getFloat("VALOR_VENTAS");
			restaurante = new Restaurante(nombre2, representante, tipoComida, paginaWeb, zona, valorCostos, valorVentas);
		}
		if(restaurante == null) throw new Exception("No existe restaurante con el nombre: " + nombre);
		return restaurante;
	}

	/**
	 * Metodo que agrega el restaurante que entra como parametro a la base de datos.
	 * @param restaurante - el restaurante a agregar. restaurante !=  null
	 * <b> post: </b> se ha agregado el restaurante a la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que el restaurante baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el restaurante a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "INSERT INTO RESTAURANTES VALUES ('";
		sql += restaurante.getNombre() + "','";
		sql+= restaurante.getRepresentante() + "','";
		sql += restaurante.getTipoComida() + "',";
		if (restaurante.getPaginaWeb() != "" && restaurante.getPaginaWeb() != null)
		sql += "'" + restaurante.getPaginaWeb() + "','";
		else sql += "null,'";
		sql += restaurante.getZona() + "',";
		sql += restaurante.getValorCostos() + ",";
		sql += restaurante.getValorVentas() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza el restaurante que entra como parametro en la base de datos.
	 * @param restaurante - el restaurante a actualizar. restaurante !=  null
	 * <b> post: </b> se ha actualizado el restaurante en la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "UPDATE RESTAURANTES SET ";
		sql+= "REPRESENTANTE='" + restaurante.getRepresentante() + "',";
		sql += "TIPOCOMIDA='" + restaurante.getTipoComida() + "',";
		sql += "PAGINAWEB='" + restaurante.getPaginaWeb() + "',";
		sql += "ZONA='" + restaurante.getZona() + "',";
		sql += "VALORCOSTOS=" + restaurante.getValorCostos() + ",";
		sql += "VALORVENTAS=" + restaurante.getValorVentas();
		sql += "WHERE NOMBRE='" + restaurante.getNombre() + "',";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el restaurante que entra como parametro en la base de datos.
	 * @param restaurante - el restaurante a borrar. restaurante !=  null
	 * <b> post: </b> se ha borrado el restaurante en la base de datos en la transaction actual. pendiente que el restaurante master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el restaurante.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteRestaurante(Restaurante restaurante) throws SQLException, Exception {

		String sql = "DELETE FROM PLATO";
		sql += " WHERE RESTAURANTE = '" + restaurante.getNombre() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM MENU";
		sql += " WHERE NOMRESTAURANTE = '" + restaurante.getNombre() + "'";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		
		sql = "DELETE FROM RESTAURANTES";
		sql += " WHERE NOMBRE = '" + restaurante.getNombre() + "'";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}

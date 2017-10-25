package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Ingredientes;


public class DAOTablaIngredientes {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOIngrediente
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaIngredientes() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los ingredientes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM INGREDIENTES;
	 * @return Arraylist con los ingredientes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Ingredientes> darIngredientes() throws SQLException, Exception {
		ArrayList<Ingredientes> ingredientes = new ArrayList<Ingredientes>();

		String sql = "SELECT * FROM INGREDIENTES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			ingredientes.add(new Ingredientes(nombre, descripcion, traduccion));
		}
		return ingredientes;
	}


	/**
	 * Metodo que busca el/los ingredientes con el nombre que entra como parametro.
	 * @param nombre - Nombre de el/los ingredientes a buscar
	 * @return ArrayList con los ingredientes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Ingredientes buscarIngredientePorName(String nombre) throws SQLException, Exception {
		Ingredientes ingrediente = null;

		String sql = "SELECT * FROM INGREDIENTES WHERE NOMBRE ='" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if (rs.next()) {
			String nombre2 = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			ingrediente = new Ingredientes(nombre2, descripcion, traduccion);
		}

		return ingrediente;
	}


	/**
	 * Metodo que agrega el ingrediente que entra como parametro a la base de datos.
	 * @param ingrediente - el ingrediente a agregar. video !=  null
	 * <b> post: </b> se ha agregado el ingrediente a la base de datos en la transaction actual. pendiente que el ingrediente master
	 * haga commit para que el ingrediente baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el ingrediente a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addIngrediente(Ingredientes ingrediente) throws SQLException, Exception {

		String sql = "INSERT INTO INGREDIENTES VALUES ('";
		sql += ingrediente.getNombre() + "','";
		sql += ingrediente.getDescripcion()+ "','";
		sql += ingrediente.getTraduccion() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza el ingrediente que entra como parametro en la base de datos.
	 * @param ingrediente - el ingrediente a actualizar. ingrediente !=  null
	 * <b> post: </b> se ha actualizado el ingrediente en la base de datos en la transaction actual. pendiente que el ingrediente master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el ingrediente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateIngrediente(Ingredientes ingrediente) throws SQLException, Exception {

		String sql = "UPDATE INGREDIENTES SET ";
		sql += "DESCRIPCION = '" + ingrediente.getDescripcion() + "'";
		sql += "TRADUCCION = '" + ingrediente.getTraduccion() + "'";
		sql += "WHERE NOMBRE = '" + ingrediente.getNombre() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el ingrediente que entra como parametro en la base de datos.
	 * @param ingrediente - el ingrediente a borrar. video !=  null
	 * <b> post: </b> se ha borrado el ingrediente en la base de datos en la transaction actual. pendiente que el ingrediente master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el ingrediente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteIngrediente(Ingredientes ingrediente) throws SQLException, Exception {

		String sql = "DELETE FROM INGREDIENTES_PLATO";
		sql += " WHERE NOM_INGR = '" + ingrediente.getNombre() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM EQUIVALENTES";
		sql += " WHERE INGREDIENTE1 = '" + ingrediente.getNombre() + "'";
		sql += " OR INGREDIENTE2 = '" + ingrediente.getNombre() + "'";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM INGREDIENTES";
		sql += " WHERE NOMBRE = '" + ingrediente.getNombre() + "'";

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}

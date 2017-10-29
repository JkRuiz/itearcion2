package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Equivalentes;
import vos.Ingredientes;
import vos.Restaurante;

public class DAOTablaEquivalentes {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOEquivalentes
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaEquivalentes() {
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
		if(ingrediente == null) throw new Exception("No existe ingrediente con nombre");
		return ingrediente;
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
	 * Metodo que inicializa la connection del DAO a la base de datos con la conexión que entra como parametro.
	 * @param con  - connection a la base de datos
	 */
	public void setConn(Connection con){
		this.conn = con;
	}


	/**
	 * Metodo que, usando la conexión a la base de datos, saca todos los equivalentes de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM EQUIVALENTES;
	 * @return Arraylist con los equivalentes de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Equivalentes> darEquivalentes() throws SQLException, Exception {
		ArrayList<Equivalentes> equivalentes = new ArrayList<Equivalentes>();

		String sql = "SELECT * FROM EQUIVALENTES";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String ingrediente1 = rs.getString("INGREDIENTE1");
			String ingrediente2 = rs.getString("INGREDIENTE2");
			equivalentes.add(new Equivalentes(ingrediente1, ingrediente2));
		}
		return equivalentes;
	}


	/**
	 * Metodo que busca el/los equivalentes con el nombre que entra como parametro.
	 * @param nombreIngr1 - Nombre de el/los equivalentes a buscar
	 * @return ArrayList con los equivalentes encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Equivalentes> buscarEquivalentesPorNombre(String nombreIngr1) throws SQLException, Exception {
		ArrayList<Equivalentes> equivalentes = new ArrayList<Equivalentes>();

		String sql = "SELECT * FROM EQUIVALENTES WHERE INGREDIENTE1 ='" + nombreIngr1 + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String ingrediente1 = rs.getString("INGREDIENTE1");
			String ingrediente2 = rs.getString("INGREDIENTE2");
			equivalentes.add(new Equivalentes(ingrediente1, ingrediente2));
		}

		return equivalentes;
	}
	

	/**
	 * Metodo que agrega el equivalente que entra como parametro a la base de datos.
	 * @param equivalente - el equivalente a agregar. equivalente !=  null
	 * <b> post: </b> se ha agregado el equivalente a la base de datos en la transaction actual. pendiente que el equivalente master
	 * haga commit para que el equivalente baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el equivalente a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addEquivalente(Equivalentes equivalente) throws SQLException, Exception {

		String sql = "INSERT INTO EQUIVALENTES VALUES ('";
		sql += equivalente.getNomIngrediente1() + "','";
		sql += equivalente.getNomIngrediente2() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que elimina el equivalente que entra como parametro en la base de datos.
	 * @param equivalente - el equivalente a borrar. equivalente !=  null
	 * <b> post: </b> se ha borrado el equivalente en la base de datos en la transaction actual. pendiente que el equivalente master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el equivalente.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteEquivalente(Equivalentes equivalente) throws SQLException, Exception {

		String sql = "DELETE FROM EQUIVALENTES";
		sql += " WHERE INGREDIENET1 = '" + equivalente.getNomIngrediente1() + "'"
				+ " AND INGREDIENTE2 = '" + equivalente.getNomIngrediente2() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	@SuppressWarnings("static-access")
	public void addEquivalente(String str) throws NumberFormatException, Exception {
		String[] params = str.split("-");

		Ingredientes ing1 = buscarIngredientePorName(params[0]);
		Ingredientes ing2 = buscarIngredientePorName(params[1]);
		if (ing1.getNombre().equals(ing2.getNombre())) {
			throw new Exception("Los ingredientes son los mismos");
		}
		
		buscarRestaurantePorNombre(params[2]);
		
		String sql = "INSERT INTO EQUIVALENTES VALUES ('"+ params[0] + "' ,'" + 
				params[1] + "', '" + params[2] + "')";
		System.out.println("ADD EQUIV: "+ sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}

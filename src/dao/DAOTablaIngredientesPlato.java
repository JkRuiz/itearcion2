package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.IngredientesPlato;


public class DAOTablaIngredientesPlato {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOIngredientesPlato
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaIngredientesPlato() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los ingredientesPlato de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM INGREDIENTES_PLATO;
	 * @return Arraylist con los videos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<IngredientesPlato> darVideos() throws SQLException, Exception {
		ArrayList<IngredientesPlato> ingredientesPlato = new ArrayList<IngredientesPlato>();

		String sql = "SELECT * FROM INGREDIENTES_PLATO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idPlato = rs.getInt("ID_PLATO");
			String nombreIngrediente = rs.getString("NOM_INGR");
			ingredientesPlato.add(new IngredientesPlato(idPlato, nombreIngrediente));
		}
		return ingredientesPlato;
	}


	/**
	 * Metodo que busca el/los ingredientesPlato con el nombre que entra como parametro.
	 * @param nombreIngr - Nombre del ingrediente por buscar en los ingredientesPlato a buscar
	 * @return ArrayList con los ingredientesPlato encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<IngredientesPlato> buscarIngredientesPlatoPorIngrediente(String nombreIngr) throws SQLException, Exception {
		ArrayList<IngredientesPlato> ingredientesPlato = new ArrayList<IngredientesPlato>();

		String sql = "SELECT * FROM INGREDIENTES_PLATO WHERE NOM_INGR ='" + nombreIngr + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idPlato = rs.getInt("ID_PLATO");
			String nombreIngrediente = rs.getString("NOM_INGR");
			ingredientesPlato.add(new IngredientesPlato(idPlato, nombreIngrediente));
		}

		return ingredientesPlato;
	}
	

	/**
	 * Metodo que busca el/los ingredientesPlato con el id que entra como parametro.
	 * @param idPlato - id del plato por buscar en los ingredientesPlato a buscar
	 * @return ArrayList con los ingredientesPlato encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<IngredientesPlato> buscarIngredientesPlatoPorId(Long idPlato) throws SQLException, Exception {
		ArrayList<IngredientesPlato> ingredientesPlato = new ArrayList<IngredientesPlato>();

		String sql = "SELECT * FROM INGREDIENTES_PLATO WHERE ID_PLATO =" + idPlato;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idPlato2 = rs.getInt("ID_PLATO");
			String nombreIngrediente = rs.getString("NOM_INGR");
			ingredientesPlato.add(new IngredientesPlato(idPlato2, nombreIngrediente));
		}
		return ingredientesPlato;
	}
	
	
	/**
	 * Metodo que busca el/los ingredientesPlato con el nombre y id que entra como parametro.
	 * @param idPlato - id del plato por buscar en los ingredientesPlato a buscar
	 * @param nomIngr - nombre del ingrediente por buscar en los ingredientesPlato a buscar
	 * @return ArrayList con los ingredientesPlato encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<IngredientesPlato> buscarIngredientePlatoPorIdAndNombre(Long idPlato, String nomIngr) throws SQLException, Exception {
		ArrayList<IngredientesPlato> ingredientesPlato = new ArrayList<IngredientesPlato>();

		String sql = "SELECT * FROM INGREDIENTES_PLATO WHERE ID_PLATO =" + idPlato
				+ " AND NOM_INGR = '" + nomIngr + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idPlato2 = rs.getInt("ID_PLATO");
			String nombreIngrediente = rs.getString("NOM_INGR");
			ingredientesPlato.add(new IngredientesPlato(idPlato2, nombreIngrediente));
		}
		return ingredientesPlato;
	}
	

	/**
	 * Metodo que agrega el ingredientesPlato que entra como parametro a la base de datos.
	 * @param ingredientesPlato - el ingredientesPlato a agregar. video !=  null
	 * <b> post: </b> se ha agregado el ingredientesPlato a la base de datos en la transaction actual. pendiente que el ingredientesPlato master
	 * haga commit para que el ingredientesPlato baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el ingredientesPlato a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addIngredientesPlato(IngredientesPlato ingredientePlato) throws SQLException, Exception {

		String sql = "INSERT INTO INGREDIENTES_PLATO VALUES (";
		sql += ingredientePlato.getIdPlato() + ",'";
		sql += ingredientePlato.getNombreIngrdiente() + "')";
		
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que elimina el ingredientesPlato que entra como parametro en la base de datos.
	 * @param ingredientesPlato - el ingredientesPlato a borrar. ingredientesPlato !=  null
	 * <b> post: </b> se ha borrado el ingredientesPlato en la base de datos en la transaction actual. pendiente que el ingredientesPlato master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el ingredientesPlato.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteIngredientesPlato(IngredientesPlato ingredientePlato) throws SQLException, Exception {

		String sql = "DELETE FROM INGREDIENTES_PLATO";
		sql += " WHERE ID_PLATO = " + ingredientePlato.getIdPlato();
		sql += " AND NOM_INGR = '" + ingredientePlato.getNombreIngrdiente() + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

}

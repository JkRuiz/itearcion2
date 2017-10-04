package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Categoria;


public class DAOTablaCategoria {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOCategoria
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaCategoria() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todas las categorias de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM CATEGORIA;
	 * @return Arraylist con las categorias de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Categoria> darCategorias() throws SQLException, Exception {
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();

		String sql = "SELECT * FROM CATEGORIA";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomCategoria = rs.getString("NOMCATEGORIA");
			categorias.add(new Categoria(nomCategoria));
		}
		return categorias;
	}


	/**
	 * Metodo que busca la/las categorias con el nombre que entra como parametro.
	 * @param name - Nombre de la/las categorias a buscar
	 * @return ArrayList con las categorias encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Categoria> buscarCategoriasPorNombre(String nomCategoria) throws SQLException, Exception {
		ArrayList<Categoria> categorias = new ArrayList<Categoria>();

		String sql = "SELECT * FROM CATEGORIA WHERE NOMCATEGORIA ='" + nomCategoria + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nomCategoria2 = rs.getString("NOMCATEGORIA");
			categorias.add(new Categoria(nomCategoria2));
		}

		return categorias;
	}
	
	 /**
	 * Metodo que agrega la categoria que entra como parametro a la base de datos.
	 * @param  categoria - la categoria a agregar. categoria !=  null
	 * <b> post: </b> se ha agregado la categoria la base de datos en la transaction actual. pendiente que la categoria master
	 * haga commit para que la categoria baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la categoria a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addCategoria(Categoria categoria) throws SQLException, Exception {

		String sql = "INSERT INTO CATEGORIA VALUES ('";
		sql += categoria.getNombre() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que elimina la categoria que entra como parametro en la base de datos.
	 * @param categoria - la categoria a borrar. categoria !=  null
	 * <b> post: </b> se ha borrado la categoria en la base de datos en la transaction actual. pendiente que la categoria master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar  la categoria.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteCategoria(Categoria categoria) throws SQLException, Exception {

		String sql = "DELETE FROM CATEGORIA";
		sql += " WHERE NOMCATEGORIA = " + categoria.getNombre();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}

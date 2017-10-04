package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.MenuPlato;


public class DAOTablaMenuPlato {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOMenuPlato
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaMenuPlato() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los menuPlatos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM MENU_PLATO;
	 * @return Arraylist con los menuPlatos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<MenuPlato> darMenuPlato() throws SQLException, Exception {
		ArrayList<MenuPlato> menusPlato = new ArrayList<MenuPlato>();

		String sql = "SELECT * FROM MENU_PLATO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idMenu = rs.getInt("ID_MENU");
			int idPlato = rs.getInt("ID_PLATO");
			menusPlato.add(new MenuPlato(idMenu, idPlato));
		}
		return menusPlato;
	}


	
	/**
	 * Metodo que busca el menuPlato con el id que entra como parametro.
	 * @param idMenu - Id del menu a buscar
	 * @param idPlato - Id del plato a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public MenuPlato buscarMenuPlatoPorIds(Long idMenu, Long idPlato) throws SQLException, Exception 
	{
		MenuPlato menuPlato = null;

		String sql = "SELECT * FROM MENU_PLATO WHERE ID_MENU =" + idMenu
				+ " AND ID_PLATO =" + idPlato;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			int idMenu2 = rs.getInt("ID_MENU");
			int idPlato2 = rs.getInt("ID_PLATO");
			menuPlato = new MenuPlato(idMenu2, idPlato2);
		}

		return menuPlato;
	}
	
	/**
	 * Metodo que busca el menuPlato con el id que entra como parametro.
	 * @param idMenu - Id del menu a buscar
	 * @param idPlato - Id del plato a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<MenuPlato> buscarMenuPlatoPorIdPlato(int idPlato) throws SQLException, Exception 
	{
		ArrayList<MenuPlato> menusPlato = new ArrayList<MenuPlato>();

		String sql = "SELECT * FROM MENU_PLATO WHERE ID_PLATO =" + idPlato;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			int idMenu = rs.getInt("ID_MENU");
			int idPlato2 = rs.getInt("ID_PLATO");
			menusPlato.add(new MenuPlato(idMenu, idPlato2));
		}
		return menusPlato;
	}

	/**
	 * Metodo que agrega el menuPlato que entra como parametro a la base de datos.
	 * @param menuPlato - el menuPlato a agregar. menuPlato !=  null
	 * <b> post: </b> se ha agregado el menuPlato a la base de datos en la transaction actual. pendiente que el menuPlato master
	 * haga commit para que el menuPlato baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el menuPlato a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMenuPlato(MenuPlato menuPlato) throws SQLException, Exception {

		String sql = "INSERT INTO MENU_PLATO VALUES (";
		sql += menuPlato.getIdMenu() + ",";
		sql += menuPlato.getIdPlato() + ")";

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
	 *
	public void updateMenuPlato(MenuPlato menuPlato) throws SQLException, Exception {

		String sql = "UPDATE VIDEO SET ";
		sql += "NAME='" + video.getName() + "',";
		sql += "DURATION=" + video.getDuration();
		sql += " WHERE ID = " + video.getId();


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	} */

	/**
	 * Metodo que elimina el video que entra como parametro en la base de datos.
	 * @param video - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 *
	public void deleteVideo(Video video) throws SQLException, Exception {

		String sql = "DELETE FROM VIDEO";
		sql += " WHERE ID = " + video.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}*/
}

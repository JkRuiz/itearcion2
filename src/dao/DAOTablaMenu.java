package dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.Menu;
import vos.Pedido;


public class DAOTablaMenu {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOMenu
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaMenu() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los menus de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM MENU;
	 * @return Arraylist con los menus de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Menu> darMenus() throws SQLException, Exception {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		ArrayList<Integer> productos = new ArrayList<Integer>();
		
		String sql = "SELECT * FROM MENU";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			productos = new ArrayList<>();
			String nombre = rs.getString("NOMBRE");
			float costoProd = rs.getFloat("COSTOPROD");
			float precioVent = rs.getFloat("PRECIOVENT");
			int vendidos = rs.getInt("VENDIDOS");
			int disponibles = rs.getInt("DISPONIBLES");
			String restaurante = rs.getString("NOMRESTAURANTE");
			int idMenu = rs.getInt("ID_MENU");
			
			String sqlAux = "SELECT * FROM MENU_PLATO WHERE ID_MENU ="+idMenu;
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			while (rsAux.next())
			{
				int idPlato = rsAux.getInt("ID_PLATO");
				productos.add(idPlato);
			}
			menus.add(new Menu(nombre, costoProd, precioVent, vendidos, disponibles, restaurante, idMenu, productos));
		}
		return menus;
	}


	/**
	 * Metodo que busca el/los menus con el nombre que entra como parametro.
	 * @param nombre - Nombre de el/los menus a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Menu> buscarMenusPorNombre(String nombre) throws SQLException, Exception {
		ArrayList<Menu> menus = new ArrayList<Menu>();
		ArrayList<Integer> productos = new ArrayList<Integer>();

		String sql = "SELECT * FROM MENU WHERE NOMBRE ='" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre2 = rs.getString("NOMBRE");
			float costoProd = rs.getFloat("COSTOPROD");
			float precioVent = rs.getFloat("PRECIOVENT");
			int vendidos = rs.getInt("VENDIDOS");
			int disponibles = rs.getInt("DISPONIBLES");
			String restaurante = rs.getString("NOMRESTAURANTE");
			int idMenu = rs.getInt("ID_MENU");
			
			String sqlAux = "SELECT * FROM MENU_PLATO WHERE ID_MENU ="+idMenu;
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			while (rsAux.next())
			{
				int idPlato = rsAux.getInt("ID_PLATO");
				productos.add(idPlato);
			}
			menus.add(new Menu(nombre2, costoProd, precioVent, vendidos, disponibles, restaurante, idMenu, productos));
		}

		return menus;
	}

	/**
	 * Metodo que busca el menus con el id que entra como parametro.
	 * @param idMenu2 - Id de el menus a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Menu buscarMenuPorId(int idMenu2) throws SQLException, Exception 
	{
		ArrayList<Integer> productos = new ArrayList<Integer>();
		Menu menu = null;

		String sql = "SELECT * FROM MENU WHERE ID_MENU =" + idMenu2;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre = rs.getString("NOMBRE");
			float costoProd = rs.getFloat("COSTOPROD");
			float precioVent = rs.getFloat("PRECIOVENT");
			int vendidos = rs.getInt("VENDIDOS");
			int disponibles = rs.getInt("DISPONIBLES");
			String restaurante = rs.getString("NOMRESTAURANTE");
			int idMenu = rs.getInt("ID_MENU");
			
			String sqlAux = "SELECT * FROM MENU_PLATO WHERE ID_MENU ="+idMenu;
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			while (rsAux.next())
			{
				int idPlato = rsAux.getInt("ID_PLATO");
				productos.add(idPlato);
			}
			menu = new Menu(nombre, costoProd, precioVent, vendidos, disponibles, restaurante, idMenu, productos);
		}
		return menu;
	}

	/**
	 * Metodo que agrega el menu que entra como parametro a la base de datos.
	 * @param menu - el menu a agregar. menu !=  null
	 * <b> post: </b> se ha agregado el menu a la base de datos en la transaction actual. pendiente que el menu master
	 * haga commit para que el menu baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el menu a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addMenu(Menu menu) throws SQLException, Exception {

		String sql = "INSERT INTO MENU VALUES ('";
		sql += menu.getNombre() + "',";
		sql += menu.getCostoProd() + ",";
		sql += menu.getPrecioVent() + ",";
		sql += menu.getVendidos() + ",";
		sql += menu.getDisponibles() + ",'";
		sql += menu.getRestaurante() + "',";
		sql += menu.getId() + ")";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza el menu que entra como parametro en la base de datos.
	 * @param menu - el menu a actualizar. menu !=  null
	 * <b> post: </b> se ha actualizado el menu en la base de datos en la transaction actual. pendiente que el menu master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el menu.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updateMenu(Menu menu) throws SQLException, Exception {

		String sql = "UPDATE MENU SET ";
		sql += "NOMBRE='" + menu.getNombre() + "',";
		sql += "COSTOPROD=" + menu.getCostoProd() + ",";
		sql += "PRECIOVENT=" + menu.getPrecioVent() + ",";
		sql += "VENDIDOS=" + menu.getVendidos() + ",";
		sql += "DISPONIBLES=" + menu.getDisponibles() + ",";
		sql += "NOMRESTAURANTE='" + menu.getRestaurante() + "'";
		sql += " WHERE ID_MENU = " + menu.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el menu que entra como parametro en la base de datos.
	 * @param menu - el menu a borrar. menu !=  null
	 * <b> post: </b> se ha borrado el menu en la base de datos en la transaction actual. pendiente que el menu master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el menu.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deleteMenu(Menu menu) throws SQLException, Exception {

		String sql = "DELETE FROM MENU_PLATO";
		sql += " WHERE ID_MENU = " + menu.getId();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM PEDIDO_MENU";
		sql += " WHERE ID_MENU = " + menu.getId();

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM RESERVA";
		sql += " WHERE ID_MENU = " + menu.getId();

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM MENU";
		sql += " WHERE ID_MENU = " + menu.getId();

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void surtir(String nombre) throws SQLException, Exception {
		String sql1= "SELECT DISPONIBLES, MAX_DISP FROM MENU WHERE NOMBRESTAURANTE = '" + nombre + "' FOR UPDATE";
		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		prepStmt1.executeQuery();
		
		String sql2 = "UPDATE MENU SET DISPONIBLES = MAX_DISP WHERE NOMBRERESTAURANTE = '" + nombre + "'";

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
	}

	public void disminuirDisponibilidad(int idMenu, int i) throws SQLException {
		String sql = "UPDATE MENU SET DISPONIBLES = DISPONIBLES - " + i + " WHERE ID_PLATO = '" + idMenu + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
	
	public void aumentarDisponibilidad(int idMenu, int i) throws SQLException {
		String sql = "UPDATE MENU SET DISPONIBLES = DISPONIBLES + " + i + " WHERE ID_PLATO = '" + idMenu + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}
}

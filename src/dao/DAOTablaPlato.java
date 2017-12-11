package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vos.ClienteFrecuente;
import vos.Pedido;
import vos.PedidoMenu;
import vos.Plato;

public class DAOTablaPlato {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOPlato
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaPlato() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todos los platos de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM PLATOS;
	 * @return Arraylist con los platos de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Plato> darPlatos() throws SQLException, Exception {
		ArrayList<Plato> platos = new ArrayList<Plato>();
		ArrayList<Plato> equivalentes = new ArrayList<Plato>();

		String sql = "SELECT * FROM PLATO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			float tiempoPreparacion = rs.getFloat("TIEMPOPREPARACION");
			float costoProduccion = rs.getFloat("COSTOPRODUCCION");
			float precioVenta = rs.getFloat("PRECIOVENTA");
			String restaurante = rs.getString("RESTAURANTE");
			int id = rs.getInt("ID_PLATO");
			int vendidos = rs.getInt("VENDIDOS");
			int disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			String categoria = rs.getString("CATEGORIA");
			int max_disp = rs.getInt("MAX_DISP");

			String sqlAux = "SELECT * FROM EQUIVALENTES_PRODUCTOS WHERE PRODUCTO1 = "+ id + "OR PRODUCTO2 =" + id;
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			equivalentes = new ArrayList<Plato>();
			while (rsAux.next())
			{
				Plato equiv = null;
				int idProd1 = rsAux.getInt("PRODUCTO1");
				int idProd2 = rsAux.getInt("PRODUCTO2");
				if (idProd1==id) equiv = getEquivalentePorId(idProd2);
				else equiv = getEquivalentePorId(idProd1);
				equivalentes.add(equiv);
			}
			platos.add(new Plato(nombre, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
					restaurante, id, vendidos, disponibles, tipo, categoria, equivalentes, max_disp));
		}
		return platos;
	}

	public ArrayList<Plato> darPlatosFiltro(String filtro) throws SQLException, Exception {
		ArrayList<Plato> platos = new ArrayList<Plato>();
		ArrayList<Plato> equivalentes = new ArrayList<Plato>();

		String filtros [] = filtro.split(";");
		String pos [] = new String [6];
		pos[2] = "restaurante";
		pos[3] = "categoria";
		pos[4] = "precioventa > ";
		pos[5] = "precioventa < ";
		String where = "";
		String order = "";
		for (int i = 2; i < filtros.length-1 && filtros[i] != null; i++) {
			if (filtros[i] != "") {
				if (where!="") {
					if(i<4)
						where += " AND " + pos[i] + "= '" + filtros[i] + "'";
					else
						where += " AND " + pos[i]  + filtros[i];
				}
				else {
					if(i<4)
						where  = " where "+ pos[i] + "= '" + filtros[i] + "'";
					else
						where  = " where "+ pos[i] + filtros[i];
				}
			}
			else {
				if (order!="") {
					if (i < 4)
						order += "," + pos[i];
					else 
						order += ", precioventa";
				}
				else {
					if (i < 4)
						order  = " order by "+ pos[i];
					else
						order  = " order by precioventa";
				}
			}
		}
		String sql = "SELECT * FROM PLATO " + where + order;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			float tiempoPreparacion = rs.getFloat("TIEMPOPREPARACION");
			float costoProduccion = rs.getFloat("COSTOPRODUCCION");
			float precioVenta = rs.getFloat("PRECIOVENTA");
			String restaurante = rs.getString("RESTAURANTE");
			int id = rs.getInt("ID_PLATO");
			int vendidos = rs.getInt("VENDIDOS");
			int disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			String categoria = rs.getString("CATEGORIA");
			int max_disp = rs.getInt("MAX_DISP");

			String sqlAux = "SELECT * FROM EQUIVALENTES_PRODUCTOS WHERE PRODUCTO1 = "+ id + "OR PRODUCTO2 =" + id;
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			equivalentes = new ArrayList<Plato>();
			while (rsAux.next())
			{
				Plato equiv = null;
				int idProd1 = rsAux.getInt("PRODUCTO1");
				int idProd2 = rsAux.getInt("PRODUCTO2");
				if (idProd1==id) equiv = getEquivalentePorId(idProd2);
				else equiv = getEquivalentePorId(idProd1);
				equivalentes.add(equiv);
			}
			platos.add(new Plato(nombre, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
					restaurante, id, vendidos, disponibles, tipo, categoria, equivalentes, max_disp));
		}
		return platos;
	}

	public Plato getEquivalentePorId(int id1) throws Exception
	{
		Plato plato = null;

		String sql = "SELECT * FROM PLATO WHERE ID_PLATO =" + id1;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre2 = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			float tiempoPreparacion = rs.getFloat("TIEMPOPREPARACION");
			float costoProduccion = rs.getFloat("COSTOPRODUCCION");
			float precioVenta = rs.getFloat("PRECIOVENTA");
			String restaurante = rs.getString("RESTAURANTE");
			int id = rs.getInt("ID_PLATO");
			int vendidos = rs.getInt("VENDIDOS");
			int disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			String categoria = rs.getString("CATEGORIA");
			int max_disp = rs.getInt("MAX_DISP");
			plato = new Plato(nombre2, descripcion, traduccion, tiempoPreparacion,
					costoProduccion, precioVenta, restaurante, id, vendidos, disponibles, tipo, categoria, null, max_disp);
		}
		if (plato == null) throw new Exception("No existe el plato con id " + id1);
		return plato;
	}

	public List<Integer> getIdPlatosEquivalentes(int id) throws SQLException
	{
		List<Integer> idsEquiv = new ArrayList<>();
		String sql = "SELECT * FROM EQUIVALENTES_PRODUCTOS WHERE PRODUCTO1 ="+ id + " OR PRODUCTO2 =" + id;
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();	

		while (rs.next())
		{
			int idProd1 = rs.getInt("PRODUCTO1");
			int idProd2 = rs.getInt("PRODUCTO2");
			if (idProd1 == id) idsEquiv.add(idProd2);
			else idsEquiv.add(idProd1);
		}
		return idsEquiv;
	}

	public void addEquivalentes(String platos) throws NumberFormatException, Exception {
		String[] productos = platos.split("-");

		String sql = "INSERT INTO EQUIVALENTES_PRODUCTOS VALUES ("+ productos[0] + "," + 
				productos[1] + ", '" + productos[2] + "')";


		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();	
	}


	public void SeVendioProducto(int id1) throws Exception
	{
		String sql = "UPDATE PLATO SET VENDIDOS = VENDIDOS+1, DISPONIBLES = DISPONIBLES-1 WHERE ID_PLATO=" + id1;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}
	//	public ArrayList<Plato> darPlatosPorRango(String filtro) throws SQLException, Exception {
	//		ArrayList<Plato> platos = new ArrayList<Plato>();
	//		String[] fechas = filtro.split("-");
	//		for (int i = 0; i < fechas.length; i++) System.out.println(fechas[i]);
	//		String sql = "SELECT * FROM PEDIDO WHERE FECHA > '" + fechas[0] + "' AND FECHA < '"+ fechas[1] + "'";
	//
	//		PreparedStatement prepStmt = conn.prepareStatement(sql);
	//		recursos.add(prepStmt);
	//		ResultSet rs = prepStmt.executeQuery();
	//
	//		while (rs.next()) {
	//			String nombre = rs.getString("NOMBRE");
	//			String descripcion = rs.getString("DESCRIPCION");
	//			String traduccion = rs.getString("TRADUCCION");
	//			float tiempoPreparacion = rs.getFloat("TIEMPOPREPARACION");
	//			float costoProduccion = rs.getFloat("COSTOPRODUCCION");
	//			float precioVenta = rs.getFloat("PRECIOVENTA");
	//			String restaurante = rs.getString("RESTAURANTE");
	//			int id = rs.getInt("ID_PLATO");
	//			int vendidos = rs.getInt("VENDIDOS");
	//			int disponibles = rs.getInt("DISPONIBLES");
	//			String tipo = rs.getString("TIPO");
	//			String categoria = rs.getString("CATEGORIA");
	//
	//			platos.add(new Plato(nombre, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
	//					restaurante, id, vendidos, disponibles, tipo, categoria));
	//		}
	//		return platos;
	//	}

	public ArrayList<Plato> darPlatosRentZonas(String fecha1, String fecha2) throws SQLException, Exception {
		ArrayList<Plato> platos = new ArrayList<Plato>();
		ArrayList<Plato> equivalentes = new ArrayList<Plato>();

		String sql = "SELECT * FROM (PLATO NATURAL JOIN PEDIDO_PLATO) NATURAL JOIN PEDIDO";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			float tiempoPreparacion = rs.getFloat("TIEMPOPREPARACION");
			float costoProduccion = rs.getFloat("COSTOPRODUCCION");
			float precioVenta = rs.getFloat("PRECIOVENTA");
			String restaurante = rs.getString("RESTAURANTE");
			int id = rs.getInt("ID_PLATO");
			int vendidos = rs.getInt("VENDIDOS");
			int disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			String categoria = rs.getString("CATEGORIA");
			int max_disp = rs.getInt("MAX_DISP");

			String sqlAux = "SELECT * FROM EQUIVALENTES_PRODUCTOS WHERE PRODUCTO1 = "+ id + "OR PRODUCTO2 =" + id;
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			equivalentes = new ArrayList<Plato>();
			while (rsAux.next())
			{
				Plato equiv = null;
				int idProd1 = rsAux.getInt("PRODUCTO1");
				int idProd2 = rsAux.getInt("PRODUCTO2");
				if (idProd1==id) equiv = getEquivalentePorId(idProd2);
				else equiv = getEquivalentePorId(idProd1);
				equivalentes.add(equiv);
			}
			platos.add(new Plato(nombre, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
					restaurante, id, vendidos, disponibles, tipo, categoria, equivalentes, max_disp));
		}
		return platos;
	}

	/**
	 * Metodo que busca el/los platos con el nombre que entra como parametro.
	 * @param nombre - Nombre de el/los platos a buscar
	 * @return ArrayList con los platos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<Plato> buscarPlatosPorNombre(String nombre) throws SQLException, Exception {
		ArrayList<Plato> platos = new ArrayList<Plato>();
		ArrayList<Plato> equivalentes = new ArrayList<Plato>();

		String sql = "SELECT * FROM PLATO WHERE NOMBRE ='" + nombre + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre2 = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			float tiempoPreparacion = rs.getFloat("TIEMPOPREPARACION");
			float costoProduccion = rs.getFloat("COSTOPRODUCCION");
			float precioVenta = rs.getFloat("PRECIOVENTA");
			String restaurante = rs.getString("RESTAURANTE");
			int id = rs.getInt("ID_PLATO");
			int vendidos = rs.getInt("VENDIDOS");
			int disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			String categoria = rs.getString("CATEGORIA");
			int max_disp = rs.getInt("MAX_DISP");

			String sqlAux = "SELECT * FROM EQUIVALENTES_PRODUCTOS WHERE PRODUCTO1 = "+ id + "OR PRODUCTO2 =" + id;
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			equivalentes = new ArrayList<Plato>();
			while (rsAux.next())
			{
				Plato equiv = null;
				int idProd1 = rsAux.getInt("PRODUCTO1");
				int idProd2 = rsAux.getInt("PRODUCTO2");
				if (idProd1==id) equiv = getEquivalentePorId(idProd2);
				else equiv = getEquivalentePorId(idProd1);
				equivalentes.add(equiv);
			}
			platos.add(new Plato(nombre2, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
					restaurante, id, vendidos, disponibles, tipo, categoria, equivalentes, max_disp));
		}
		return platos;
	}

	/**
	 * Metodo que busca el plato con el id que entra como parametro.
	 * @param id - Id del plato a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public Plato buscarPlatoPorId(int id1) throws SQLException, Exception 
	{
		ArrayList<Plato> equivalentes = new ArrayList<Plato>();
		Plato plato = null;

		String sql = "SELECT * FROM PLATO WHERE ID_PLATO =" + id1;

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		if(rs.next()) {
			String nombre2 = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			float tiempoPreparacion = rs.getFloat("TIEMPOPREPARACION");
			float costoProduccion = rs.getFloat("COSTOPRODUCCION");
			float precioVenta = rs.getFloat("PRECIOVENTA");
			String restaurante = rs.getString("RESTAURANTE");
			int id = rs.getInt("ID_PLATO");
			int vendidos = rs.getInt("VENDIDOS");
			int disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			String categoria = rs.getString("CATEGORIA");
			int max_disp = rs.getInt("MAX_DISP");

			String sqlAux = "SELECT * FROM EQUIVALENTES_PRODUCTOS WHERE PRODUCTO1 = "+ id + "OR PRODUCTO2 =" + id;
			PreparedStatement prepStmtAux = conn.prepareStatement(sqlAux);
			recursos.add(prepStmtAux);
			ResultSet rsAux = prepStmtAux.executeQuery();
			equivalentes = new ArrayList<Plato>();
			while (rsAux.next())
			{
				Plato equiv = null;
				int idProd1 = rsAux.getInt("PRODUCTO1");
				int idProd2 = rsAux.getInt("PRODUCTO2");
				if (idProd1==id) equiv = getEquivalentePorId(idProd2);
				else equiv = getEquivalentePorId(idProd1);
				equivalentes.add(equiv);
			}
			plato = new Plato(nombre2, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
					restaurante, id, vendidos, disponibles, tipo, categoria, equivalentes, max_disp);	
		}
		if (plato == null) throw new Exception("No existe ningun plato con el id " + id1);
		return plato;
	}

	/**
	 * Metodo que agrega el plato que entra como parametro a la base de datos.
	 * @param plato - el plato a agregar. video !=  null
	 * <b> post: </b> se ha agregado el plato a la base de datos en la transaction actual. pendiente que el plato master
	 * haga commit para que el plato baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar el plato a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addPlato(Plato plato) throws SQLException, Exception {

		String sql = "INSERT INTO PLATO VALUES ('";
		sql += plato.getNombre() + "','";
		sql += plato.getDescripcion()  + "','";
		sql += plato.getTraduccion()  + "',";
		sql += plato.getTiempoPreparacion() + ",";
		sql += plato.getCostoProduccion() + ",";
		sql += plato.getPrecioVenta() + ",'";
		sql += plato.getRestaurante() + "',";
		sql += plato.getIdPlato() + ",";
		sql += plato.getVendidos() + ",";
		sql += plato.getDisponibles() + ",'";
		sql += plato.getTipo() + "','";
		sql += plato.getCategoria() + "')";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

	}

	/**
	 * Metodo que actualiza el plato que entra como parametro en la base de datos.
	 * @param plato - el plato a actualizar. plato !=  null
	 * <b> post: </b> se ha actualizado el plato en la base de datos en la transaction actual. pendiente que el plato master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el plato.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void updatePlato(Plato plato) throws SQLException, Exception {

		String sql = "UPDATE PLATO SET ";

		sql += "NOMBRE='" + plato.getNombre() + "',";
		sql += "DESCRIPCION='" + plato.getDescripcion()  + "',";
		sql += "TRADUCCION='" + plato.getTraduccion()  + "',";
		sql += "TIEMPOPREPARACION=" + plato.getTiempoPreparacion() + ",";
		sql += "COSTOPRODUCCION=" + plato.getCostoProduccion() + ",";
		sql +="PRECIOVENTA=" +  plato.getPrecioVenta() + ",";
		sql += "RESTAURANTE='" + plato.getRestaurante() + "',";
		sql += "VENDIDOS=" + plato.getVendidos() + ",";
		sql += "DISPONIBLES=" + plato.getDisponibles() + ",";
		sql += "TIPO='" + plato.getTipo() + "',";
		sql += "CATEGORIA='" + plato.getCategoria() + "'";

		sql += " WHERE ID_PLATO = " + plato.getIdPlato();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	/**
	 * Metodo que elimina el plato que entra como parametro en la base de datos.
	 * @param plato - el plato a borrar. video !=  null
	 * <b> post: </b> se ha borrado el plato en la base de datos en la transaction actual. pendiente que el plato master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el plato.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void deletePlato(Plato plato) throws SQLException, Exception {


		String sql = "DELETE FROM INGREDIENTES_PLATO";
		sql += " WHERE ID_PLATO = " + plato.getIdPlato();

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();


		sql = "DELETE FROM MENU_PLATO";
		sql += " WHERE ID_PLATO = " + plato.getIdPlato();

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();

		sql = "DELETE FROM PEDIDO_PLATO";
		sql += " WHERE ID_PLATO = " + plato.getIdPlato();

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();


		sql = "DELETE FROM PLATO";
		sql += " WHERE ID_PLATO = " + plato.getIdPlato();

		prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void surtir(String nombre) throws SQLException, Exception {
		String sql1= "SELECT DISPONIBLES, MAX_DISP FROM PLATO WHERE RESTAURANTE = '" + nombre + "' FOR UPDATE";
		PreparedStatement prepStmt1 = conn.prepareStatement(sql1);
		recursos.add(prepStmt1);
		prepStmt1.executeQuery();

		String sql2 = "UPDATE PLATO SET DISPONIBLES = MAX_DISP WHERE RESTAURANTE = '" + nombre + "'";

		PreparedStatement prepStmt2 = conn.prepareStatement(sql2);
		recursos.add(prepStmt2);
		prepStmt2.executeQuery();
	}

	public void disminuirDisponibilidad(int idPlato, int i) throws SQLException {
		String sql = "UPDATE PLATO SET DISPONIBLES = DISPONIBLES - " + i + " WHERE ID_PLATO = '" + idPlato + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public void aumentarDisponibilidad(int idPlato, int i) throws SQLException {
		String sql = "UPDATE PLATO SET DISPONIBLES = DISPONIBLES + " + i + " WHERE ID_PLATO = '" + idPlato + "'";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		prepStmt.executeQuery();
	}

	public List<Plato> darPlatosCliente(ClienteFrecuente cliente) throws SQLException, Exception {
		List<Plato> platos = new ArrayList<>();
		String sql = "SELECT * FROM PLATO WHERE ID_PLATO "
				+ "IN (SELECT ID_PLATO FROM (PEDIDO_PLATO NATURAL JOIN PEDIDO) "
				+ "WHERE EMAIL_USER = '" + cliente.getEmail() +"') OR ID_PLATO IN "
				+ "(SELECT ID_PLATO FROM MENU_PLATO WHERE ID_MENU IN "
				+ "(SELECT ID_MENU FROM PEDIDO_MENUS NATURAL JOIN PEDIDO "
				+ "WHERE EMAIL_USER = '" + cliente.getEmail() +"' ))";
		System.out.println("BUSQUEDA C7: \n" + sql);
		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String nombre2 = rs.getString("NOMBRE");
			String descripcion = rs.getString("DESCRIPCION");
			String traduccion = rs.getString("TRADUCCION");
			float tiempoPreparacion = rs.getFloat("TIEMPOPREPARACION");
			float costoProduccion = rs.getFloat("COSTOPRODUCCION");
			float precioVenta = rs.getFloat("PRECIOVENTA");
			String restaurante = rs.getString("RESTAURANTE");
			int id = rs.getInt("ID_PLATO");
			int vendidos = rs.getInt("VENDIDOS"); 
			int disponibles = rs.getInt("DISPONIBLES");
			String tipo = rs.getString("TIPO");
			String categoria = rs.getString("CATEGORIA");
			int max_disp = rs.getInt("MAX_DISP");
			platos.add(new Plato(nombre2, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
					restaurante, id, vendidos, disponibles, tipo, categoria, null, max_disp));
		}
		return platos;
	}
}

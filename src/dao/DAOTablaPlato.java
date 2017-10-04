package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

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

			platos.add(new Plato(nombre, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
					restaurante, id, vendidos, disponibles, tipo, categoria));
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

			platos.add(new Plato(nombre2, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
					restaurante, id, vendidos, disponibles, tipo, categoria));		
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

			plato = new Plato(nombre2, descripcion, traduccion, tiempoPreparacion, costoProduccion, precioVenta,
					restaurante, id, vendidos, disponibles, tipo, categoria);	
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

}

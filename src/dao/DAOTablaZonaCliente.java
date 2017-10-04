package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import vos.ZonaCliente;


public class DAOTablaZonaCliente {

	/**
	 * Arraylits de recursos que se usan para la ejecución de sentencias SQL
	 */
	private ArrayList<Object> recursos;

	/**
	 * Atributo que genera la conexión a la base de datos
	 */
	private Connection conn;

	/**
	 * Metodo constructor que crea DAOZonaCliente
	 * <b>post: </b> Crea la instancia del DAO e inicializa el Arraylist de recursos
	 */
	public DAOTablaZonaCliente() {
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
	 * Metodo que, usando la conexión a la base de datos, saca todas las zonasCliente de la base de datos
	 * <b>SQL Statement:</b> SELECT * FROM ZONAS_CLIENTESFR;
	 * @return Arraylist con zonasCliente de la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public ArrayList<ZonaCliente> darZonas() throws SQLException, Exception {
		ArrayList<ZonaCliente> zonasCliente = new ArrayList<ZonaCliente>();

		String sql = "SELECT * FROM ZONAS_CLIENTESFR";

		PreparedStatement prepStmt = conn.prepareStatement(sql);
		recursos.add(prepStmt);
		ResultSet rs = prepStmt.executeQuery();

		while (rs.next()) {
			String emailCliente = rs.getString("EMAIL_CLIENTEFR");
			String preferenciaZona = rs.getString("PREFERENCIA_ZONA");
			zonasCliente.add(new ZonaCliente(emailCliente, preferenciaZona));
		}
		return zonasCliente;
	}


	/**
	 * Metodo que busca el/los videos con el nombre que entra como parametro.
	 * @param name - Nombre de el/los videos a buscar
	 * @return ArrayList con los videos encontrados
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
//	public ArrayList<Video> buscarVideosPorName(String name) throws SQLException, Exception {
//		ArrayList<Video> videos = new ArrayList<Video>();
//
//		String sql = "SELECT * FROM VIDEO WHERE NAME ='" + name + "'";
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		while (rs.next()) {
//			String name2 = rs.getString("NAME");
//			Long id = rs.getLong("ID");
//			Integer duration = rs.getInt("DURATION");
//			videos.add(new Video(id, name2, duration));
//		}
//
//		return videos;
//	}
	
	/**
	 * Metodo que busca el video con el id que entra como parametro.
	 * @param name - Id de el video a buscar
	 * @return Video encontrado
	 * @throws SQLException - Cualquier error que la base de datos arroje.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
//	public Video buscarVideoPorId(Long id) throws SQLException, Exception 
//	{
//		Video video = null;
//
//		String sql = "SELECT * FROM VIDEO WHERE ID =" + id;
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		ResultSet rs = prepStmt.executeQuery();
//
//		if(rs.next()) {
//			String name = rs.getString("NAME");
//			Long id2 = rs.getLong("ID");
//			Integer duration = rs.getInt("DURATION");
//			video = new Video(id2, name, duration);
//		}
//
//		return video;
//	}

	/**
	 * Metodo que agrega la zonasCliente que entra como parametro a la base de datos.
	 * @param zona - la zonasCliente a agregar. zonasCliente !=  null
	 * <b> post: </b> se ha agregado la zozonasClientena a la base de datos en la transaction actual. pendiente que la zonasCliente master
	 * haga commit para que la zonasClienteona baje  a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo agregar la zonasCliente a la base de datos
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
	public void addZona(ZonaCliente zonasCliente) throws SQLException, Exception {

		String sql = "INSERT INTO ZONAS_CLIENTESFR VALUES ('";
		sql += zonasCliente.getEmailCliente() + "','" ;
		sql += zonasCliente.getPreferenciaZona() + "')";

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
	 */
//	public void updateVideo(Video video) throws SQLException, Exception {
//
//		String sql = "UPDATE VIDEO SET ";
//		sql += "NAME='" + video.getName() + "',";
//		sql += "DURATION=" + video.getDuration();
//		sql += " WHERE ID = " + video.getId();
//
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}

	/**
	 * Metodo que elimina el video que entra como parametro en la base de datos.
	 * @param video - el video a borrar. video !=  null
	 * <b> post: </b> se ha borrado el video en la base de datos en la transaction actual. pendiente que el video master
	 * haga commit para que los cambios bajen a la base de datos.
	 * @throws SQLException - Cualquier error que la base de datos arroje. No pudo actualizar el video.
	 * @throws Exception - Cualquier error que no corresponda a la base de datos
	 */
//	public void deleteVideo(Video video) throws SQLException, Exception {
//
//		String sql = "DELETE FROM VIDEO";
//		sql += " WHERE ID = " + video.getId();
//
//		PreparedStatement prepStmt = conn.prepareStatement(sql);
//		recursos.add(prepStmt);
//		prepStmt.executeQuery();
//	}


}

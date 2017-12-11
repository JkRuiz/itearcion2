package tm;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import dao.DAOTablaClienteFrecuente;
import dao.DAOTablaEquivalentes;
import dao.DAOTablaIngredientes;
import dao.DAOTablaIngredientesPlato;
import dao.DAOTablaMenu;
import dao.DAOTablaMenuPlato;
import dao.DAOTablaPedido;
import dao.DAOTablaPedidoMenu;
import dao.DAOTablaPedidoPlato;
import dao.DAOTablaPlato;
import dao.DAOTablaRestaurante;
import dao.DAOTablaUsuario;
import dao.DAOTablaZona;
import dtm.RotondAndesDistributed;
import vos.ClienteAux;
import vos.ClienteFrecuente;
import vos.Funcionamiento;
import vos.Informacion;
import vos.Ingredientes;
import vos.IngredientesPlato;
import vos.Menu;
import vos.MenuPlato;
import vos.Pedido;
import vos.PedidoConsolidado;
import vos.PedidoMenu;
import vos.PedidoPlato;
import vos.Plato;
import vos.Rentabilidad;
import vos.Restaurante;
import vos.Usuario;
import vos.Zona;
import vos.InfoPedido;
import vos.InfoPedido.ItemPedido;
import java.sql.*;
import javax.sql.*;
import oracle.jdbc.driver.*;
import oracle.jdbc.pool.*;
import oracle.jdbc.xa.OracleXid;
import oracle.jdbc.xa.OracleXAException;
import oracle.jdbc.xa.client.*;
import javax.transaction.xa.*;

public class RotondAndesTM {

	/**
	 * Atributo estatico que contiene el path relativo del archivo que tiene los datos de la conexion
	 */
	private static final String CONNECTION_DATA_FILE_NAME_REMOTE = "/conexion.properties";

	/**
	 * Atributo estatico que contiene el path absoluto del archivo que tiene los datos de la conexion
	 */
	private  String connectionDataPath;

	/**
	 * Atributo que guarda el usuario que se va a usar para conectarse a la base de datos.
	 */
	private String user;

	/**
	 * Atributo que guarda la clave que se va a usar para conectarse a la base de datos.
	 */
	private String password;

	/**
	 * Atributo que guarda el URL que se va a usar para conectarse a la base de datos.
	 */
	private String url;

	/**
	 * Atributo que guarda el driver que se va a usar para conectarse a la base de datos.
	 */
	private String driver;

	/**
	 * conexion a la base de datos
	 */
	private Connection conn;

	private RotondAndesDistributed dtm;


	/**
	 * Metodo constructor de la clase VideoAndesMaster, esta clase modela y contiene cada una de las 
	 * transacciones y la logica de negocios que estas conllevan.
	 * <b>post: </b> Se crea el objeto VideoAndesTM, se inicializa el path absoluto del archivo de conexion y se
	 * inicializa los atributos que se usan par la conexion a la base de datos.
	 * @param contextPathP - path absoluto en el servidor del contexto del deploy actual
	 */
	public RotondAndesTM(String contextPathP) {
		connectionDataPath = contextPathP + CONNECTION_DATA_FILE_NAME_REMOTE;
		initConnectionData();
		System.out.println("Instancing DTM... ");
		dtm=RotondAndesDistributed.getInstance(this);
		System.out.println("Done...");
	}

	/**
	 * Metodo que  inicializa los atributos que se usan para la conexion a la base de datos.
	 * <b>post: </b> Se han inicializado los atributos que se usan par la conexion a la base de datos.
	 */
	private void initConnectionData() {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			this.url = prop.getProperty("url");
			this.user = prop.getProperty("usuario");
			this.password = prop.getProperty("clave");
			this.driver = prop.getProperty("driver");
			Class.forName(driver);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo que  retorna la conexion a la base de datos
	 * @return Connection - la conexion a la base de datos
	 * @throws SQLException - Cualquier error que se genere durante la conexion a la base de datos
	 */
	private Connection darConexion() throws SQLException {
		System.out.println("Connecting to: " + url + " With user: " + user);
		return DriverManager.getConnection(url, user, password);
	}

	////////////////////////////////////////
	///////Transacciones////////////////////
	////////////////////////////////////////


	/**
	 * REQUERIMIENTO FC1
	 * Metodo que modela la transaccion que retorna todos los platos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de platos. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Plato> darProductosFiltro(String filtro) throws Exception {
		List<Plato> platos;
		DAOTablaPlato daoPlatos = new DAOTablaPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPlatos.setConn(conn);
			platos = daoPlatos.darPlatosFiltro(filtro);
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPlatos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return platos;
	}

	/**
	 * RFC9
	 * @param informacionEq
	 * @return
	 * @throws Exception
	 */
	public List<Usuario> darUsuariosMayorConsumo(String informacionEq) throws Exception {
		List<Usuario> usuarios;
		DAOTablaUsuario daoUsuarios = new DAOTablaUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoUsuarios.setConn(conn);
			String info[] = informacionEq.split("-");
			//if (info.length < 4) throw new Exception("La informacion dada esta incompleta");
			usuarios = daoUsuarios.darUsuariosConsumoAlto(info[0], info[1],info[2], info[3]);
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	/**
	 * RFC10
	 * @param informacionEq
	 * @return
	 * @throws Exception
	 */
	public List<Usuario> darUsuariosMenorConsumo(String informacionEq) throws Exception {
		List<Usuario> usuarios;
		DAOTablaUsuario daoUsuarios = new DAOTablaUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoUsuarios.setConn(conn);
			String info[] = informacionEq.split("-");
			//if (info.length < 4) throw new Exception("La informacion dada esta incompleta");
			usuarios = daoUsuarios.darUsuariosConsumoBajo(info[0], info[1],info[2], info[3]);
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuarios.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	/**
	 * RFC11
	 * @param informacionEq
	 * @return
	 * @throws Exception
	 */
	public List<Funcionamiento> darFuncionamiento() throws Exception {
		List<Funcionamiento> funcionamiento;
		DAOTablaPedido daoPedidos = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedidos.setConn(conn);
			funcionamiento = daoPedidos.darFuncionamientoRotond();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return funcionamiento;
	}

	/**
	 * RFC11
	 * @param informacionEq
	 * @return
	 * @throws Exception
	 */
	public List<ClienteAux> darBuenosClientes() throws Exception {
		List<ClienteAux> clientes;
		DAOTablaClienteFrecuente daoCliente = new DAOTablaClienteFrecuente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			clientes = daoCliente.darBuenosClientes();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}

	/**
	 * REQUERIMIENTO F11
	 * Agrega una equivalencia entre ingredientes para un restaurante.
	 * @param ingredientes <nombre ing1>-<nombre ing2>-<nombre restaurante>
	 * @throws Exception en caso de que sean el mismo ingrediente,
	 * de que alguno de los ingredientes no exista, o de que el restaurante no exista.
	 */
	public void addEquivalenciaIngrediente(String ingredientes) throws Exception {
		DAOTablaEquivalentes daoEquiv = new DAOTablaEquivalentes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoEquiv.setConn(conn);
			daoEquiv.addEquivalente(ingredientes);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoEquiv.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F12
	 * @param productos
	 * @throws Exception
	 */
	public void addEquivalenciaPlato(String productos) throws Exception {
		DAOTablaPlato daoPlatos = new DAOTablaPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPlatos.setConn(conn);
			String[] producto = productos.split("-");

			Plato plato1 = daoPlatos.getEquivalentePorId(Integer.parseInt(producto[0]));
			Plato plato2 = daoPlatos.getEquivalentePorId(Integer.parseInt(producto[1]));
			if (plato1.getRestaurante().equalsIgnoreCase(plato2.getRestaurante()) == false) throw new Exception("Los productos son de restaurantes diferentes");
			if(!plato1.getRestaurante().equalsIgnoreCase(producto[2])) throw new Exception("Los productos no son de ese restaurante");
			if(!plato1.getCategoria().equalsIgnoreCase(plato2.getCategoria())) throw new Exception("Los productos son de diferente categoria");

			daoPlatos.addEquivalentes(productos);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPlatos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F13
	 * Surte todos los pruductos del restaurante indicado.
	 * @param nombre nombre del restaurante
	 * @throws Exception en caso de que no se logre re surtir el restaurante.
	 */
	public void surtirRestaurante(String nombre) throws Exception {
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		DAOTablaPlato daoPlato = new DAOTablaPlato();
		DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoMenu.setConn(conn);
			daoPlato.setConn(conn);
			daoRestaurante.setConn(conn);
			if(daoRestaurante.buscarRestaurantePorNombre(nombre) == null)  {
				throw new Exception("No exise restaurante con el nombre: " + nombre);
			}
			daoMenu.surtir(nombre);
			daoPlato.surtir(nombre);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				daoPlato.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F15
	 * Registra el pedido de productos de una mesa.
	 * @param pedido Pedido de la mesa que se esta registrando.
	 * @param info <Num prods pedido>-(<id producto>-<categoria>)*
	 * @throws Exception en caso de que el pedido ya exista, o que algun producto no exista.
	 */
	public void registrarPedidoMesa(Pedido pedido, String info) throws Exception {
		DAOTablaPedidoMenu daoPedidoMenu = new DAOTablaPedidoMenu();
		DAOTablaPedidoPlato daoPedidoPlato = new DAOTablaPedidoPlato();
		//DAOTablaMenu daoMenu = new DAOTablaMenu();
		//DAOTablaPlato daoPlato = new DAOTablaPlato();
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedidoMenu.setConn(conn);
			daoPedidoPlato.setConn(conn);
			daoPedido.setConn(conn);
			//daoMenu.setConn(conn);
			//daoPlato.setConn(conn);

			daoPedido.addPedido(pedido);

			String[] params = info.split("-");
			int count = Integer.parseInt(params[0]);
			for(int i = 0; i < count; i++) {
				if(params[2*i + 2].compareTo("P") == 0) {
					int idPlato = Integer.parseInt(params[2*i + 1]);
					//daoPlato.disminuirDisponibilidad(idPlato, 1);
					daoPedidoPlato.addPedidoPlato(new PedidoPlato(pedido.getNumPedido(), idPlato));
				} else {
					int idMenu = Integer.parseInt(params[2*i + 1]);
					//daoMenu.disminuirDisponibilidad(idMenu, 1);
					daoPedidoMenu.addPedidoMenu(new PedidoMenu(pedido.getNumPedido(), idMenu));
				}
			}

			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {  
			try {
				daoPedidoMenu.cerrarRecursos();
				daoPedidoPlato.cerrarRecursos();
				daoPedido.cerrarRecursos();
				//daoMenu.cerrarRecursos();
				//daoPlato.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * REQUERIMIENTO F17
	 * Cancela un pedido que no se haya servido.
	 * @param pedido Pedido que se esta cancelado.
	 * @throws Exception en caso de que no se pueda cancelar el pedido.
	 */
	public void cancelarPedido(Pedido pedido) throws Exception {
		DAOTablaPedidoMenu daoPedidoMenu = new DAOTablaPedidoMenu();
		DAOTablaPedidoPlato daoPedidoPlato = new DAOTablaPedidoPlato();
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedidoMenu.setConn(conn);
			daoPedidoPlato.setConn(conn);
			daoPedido.setConn(conn);

			daoPedidoMenu.removerPedidos(pedido);
			daoPedidoPlato.removerPedidos(pedido);
			daoPedido.removerPedido(pedido);

			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedidoMenu.cerrarRecursos();
				daoPedidoPlato.cerrarRecursos();
				daoPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO FC2
	 * Metodo que modela la transaccion que retorna todos las zonas de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de zonas. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<Zona> darZonas() throws Exception {
		List<Zona> zonas;
		DAOTablaZona daoZona = new DAOTablaZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoZona.setConn(conn);
			zonas = daoZona.darZonas();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}

	/**
	 * REQUERIMIENTO FC3
	 * Metodo que modela la transaccion que retorna todos los platos de la base de datos.
	 * @return ListaVideos - objeto que modela  un arreglo de platos. este arreglo contiene el resultado de la busqueda
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public List<ClienteFrecuente> darClientes() throws Exception {
		List<ClienteFrecuente> clientes;
		DAOTablaClienteFrecuente daoCliente = new DAOTablaClienteFrecuente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			clientes = daoCliente.darClientes();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return clientes;
	}

	public List<Pedido> darPedidosCliente(String emailCliente) throws Exception {
		List<Pedido> pedidosCliente;
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		DAOTablaClienteFrecuente daoCliente = new DAOTablaClienteFrecuente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			daoPedido.setConn(conn);
			pedidosCliente = daoPedido.darPedidosCliente(emailCliente);
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedidosCliente;
	}

	public List<Zona> darZonasFiltro(String filtro) throws Exception {
		List<Zona> zonas;
		DAOTablaZona daoZona = new DAOTablaZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoZona.setConn(conn);
			zonas = daoZona.darZonasFiltro(filtro);
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return zonas;
	}

	/**
	 * RFC5
	 * @param fecha1
	 * @param fecha2
	 * @return
	 * @throws Exception
	 */
	public Rentabilidad darRentabilidad(String fecha1, String fecha2, String nomRestaurante) throws Exception {
		Rentabilidad rent;
		DAOTablaPlato daoPlatos = new DAOTablaPlato();
		DAOTablaRestaurante daoRestaurantes = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			daoPlatos.setConn(conn);
			rent = daoRestaurantes.getRentabilidad(fecha1, fecha2, nomRestaurante);

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPlatos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return rent;
	}


	/**
	 * Metodo no necesario, pero bueno para probar todo
	 * @return
	 * @throws Exception
	 */
	public List<Restaurante> darRestaurantes() throws Exception {
		List<Restaurante> restaurantes;
		DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoRestaurante.setConn(conn);
			restaurantes = daoRestaurante.darRestaurantes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return restaurantes;
	}

	/**
	 * Metodo no necesario, pero bueno para probar todo
	 * @return
	 * @throws Exception
	 */
	public List<Usuario> darUsuarios() throws Exception {
		List<Usuario> usuarios;
		DAOTablaUsuario daoUsuario = new DAOTablaUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoUsuario.setConn(conn);
			usuarios = daoUsuario.darUsuarios();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return usuarios;
	}

	/**
	 * Metodo no necesario, pero bueno para probar todo
	 * @return
	 * @throws Exception
	 */
	public List<Menu> darMenus() throws Exception {
		List<Menu> menus;
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoMenu.setConn(conn);
			menus = daoMenu.darMenus();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return menus;
	}

	/**
	 * Metodo no necesario, pero bueno para probar todo
	 * @return
	 * @throws Exception
	 */
	public List<Ingredientes> darIngredientes() throws Exception {
		List<Ingredientes> ingredientes;
		DAOTablaIngredientes daoIngredientes = new DAOTablaIngredientes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoIngredientes.setConn(conn);
			ingredientes = daoIngredientes.darIngredientes();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngredientes.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return ingredientes;
	}

	/**
	 * Metodo no necesario, pero bueno para probar todo
	 * @return
	 * @throws Exception
	 */
	public List<Pedido> darPedidos() throws Exception {
		List<Pedido> pedidos;
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedido.setConn(conn);
			pedidos = daoPedido.darPedidos();

		} catch (SQLException e) {
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedidos;
	}

	/**
	 * Requerimiento C8
	 */
	public PedidoConsolidado darPedidosRestaurante(String restaurante) throws Exception {
		PedidoConsolidado pedido;
		DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setReadOnly(true);;
			daoPedido.setConn(conn);
			daoRestaurante.setConn(conn);
			if (daoRestaurante.buscarRestaurantePorNombre(restaurante) == null)
				throw new Exception ("No existe el restaurante con el nombre " + restaurante);
			pedido = daoPedido.darPedidosConsolidadosRestaurante(restaurante);

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return pedido;
	}

	/**
	 * REQUERIMIENTO FC4
	 * Metodo que modela la transaccion que retorna el plato mas ofrecido en la base de datos
	 * @return platoMasOfrecido - objeto que modela  el plato mas ofrecido.
	 * @throws Exception -  cualquier error que se genere durante la transaccion
	 */
	public Plato darProductoMasOfrecidos() throws Exception {
		Plato platoMasOfercido = null;
		List<Plato> platos;
		DAOTablaPlato daoPlatos = new DAOTablaPlato();
		DAOTablaMenuPlato daoMenuPlato = new DAOTablaMenuPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPlatos.setConn(conn);
			daoMenuPlato.setConn(conn);
			platos = daoPlatos.darPlatos();
			int cantMenus = 0;
			for (Plato p : platos)
				if (daoMenuPlato.buscarMenuPlatoPorIdPlato(p.getIdPlato()).size() > cantMenus)
					platoMasOfercido = p;

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPlatos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return platoMasOfercido;
	}

	/**
	 * REQUERIMIENTO FC7
	 * Retorna los platos que ha consumido un cliente frecuente de la rotonda
	 * @return platos - platos que ha consumido el cliente indicado por parametro
	 * @throws Exception - cualquier error que se genere durante la busqueda
	 */
	public List<Plato> darProductosConsumidos(ClienteFrecuente cliente) throws Exception {
		List<Plato> platos;
		DAOTablaPlato daoPlatos = new DAOTablaPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setReadOnly(true);
			daoPlatos.setConn(conn);
			platos = daoPlatos.darPlatosCliente(cliente);
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPlatos.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
		return platos;
	}

	/**
	 * REQUERIMIENTO F1
	 * Metodo que modela la transaccion que agrega un solo usuario a la base de datos.
	 * <b> post: </b> se ha agregado el usuario que entra como parametro
	 * @param usuario - el usuario a agregar. usuario != null
	 * @throws Exception - cualquier error que se genere agregando el usuario
	 */
	public void addUsuario(Usuario usuario) throws Exception {
		DAOTablaUsuario daoUsuario = new DAOTablaUsuario();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoUsuario.setConn(conn);
			daoUsuario.addUsuario(usuario);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}


	/**
	 * REQUERIMIENTO F2
	 * Metodo que modela la transaccion que agrega un solo cliente a la base de datos.
	 * <b> post: </b> se ha cliente el usuario que entra como parametro
	 * @param cliente - el cliente a agregar. cliente != null
	 * @throws Exception - cualquier error que se genere agregando el usuario
	 */
	public void addCliente(ClienteFrecuente cliente) throws Exception {
		DAOTablaClienteFrecuente daoCliente = new DAOTablaClienteFrecuente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			daoCliente.addCliente(cliente);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F3
	 * Metodo que modela la transaccion que agrega un solo restaurante a la base de datos.
	 * <b> post: </b> se ha agregado el restaurante que entra como parametro
	 * @param restaurante - el restaurante a agregar. restaurante != null
	 * @throws Exception - cualquier error que se genere agregando el restaurante
	 */
	public void addRestaurante(Restaurante restaurante) throws Exception {
		DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoRestaurante.setConn(conn);
			daoRestaurante.addRestaurante(restaurante);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F4
	 * Metodo que modela la transaccion que agrega un solo plato a la base de datos.
	 * <b> post: </b> se ha agregado el plato que entra como parametro
	 * @param plato - el plato a agregar. plato != null
	 * @throws Exception - cualquier error que se genere agregando el restaurante
	 */
	public void addPlato(Plato plato) throws Exception {
		DAOTablaPlato daoPlato = new DAOTablaPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPlato.setConn(conn);
			daoPlato.addPlato(plato);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPlato.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F5
	 * Metodo que modela la transaccion que agrega un solo ingrediente a la base de datos.
	 * <b> post: </b> se ha agregado el ingrediente que entra como parametro
	 * @param ingrediente - el ingrediente a agregar. ingrediente != null
	 * @throws Exception - cualquier error que se genere agregando el ingrediente
	 */
	public void addIngrediente(Ingredientes ingrediente) throws Exception {
		DAOTablaIngredientes daoIngrediente = new DAOTablaIngredientes();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoIngrediente.setConn(conn);
			daoIngrediente.addIngrediente(ingrediente);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoIngrediente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F6
	 * Metodo que modela la transaccion que agrega un solo menu a la base de datos.
	 * <b> post: </b> se ha agregado el menu que entra como parametro
	 * @param menu - el menu a agregar. menu != null
	 * @throws Exception - cualquier error que se genere agregando el menu
	 */
	public void addMenu(Menu menu) throws Exception {
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoMenu.setConn(conn);
			daoMenu.addMenu(menu);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F7
	 * Metodo que modela la transaccion que agrega una sola zona a la base de datos.
	 * <b> post: </b> se ha agregado la zona que entra como parametro
	 * @param zona - la zona a agregar. zona != null
	 * @throws Exception - cualquier error que se genere agregando la zona
	 */
	public void addZona(Zona zona) throws Exception {
		DAOTablaZona daoZona = new DAOTablaZona();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoZona.setConn(conn);
			daoZona.addZona(zona);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoZona.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F8
	 * Metodo que modela la transaccion que agrega un solo cliente a la base de datos.
	 * <b> post: </b> se ha agregado el cliente que entra como parametro
	 * @param cliente - el cliente a agregar. cliente != null
	 * @throws Exception - cualquier error que se genere agregando el cliente
	 */
	public void addPreferenciCliente(ClienteFrecuente cliente) throws Exception {
		DAOTablaClienteFrecuente daoCliente = new DAOTablaClienteFrecuente();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoCliente.setConn(conn);
			daoCliente.updateCliente(cliente);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoCliente.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * Metodo que modela la transaccion que agrega un solo pedidoPlato a la base de datos.
	 * <b> post: </b> se ha agregado el pedidoPlato que entra como parametro
	 * @param pedidoPlato - el pedidoPlato a agregar. pedidoPlato != null
	 * @throws Exception - cualquier error que se genere agregando el pedidoPlato
	 */
	public void addPedidoMenu(Pedido pedido, int idMenu) throws Exception {
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		DAOTablaPedidoMenu daoPedidoMenu = new DAOTablaPedidoMenu();
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		DAOTablaPlato daoPlato = new DAOTablaPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedido.setConn(conn);
			daoPedidoMenu.setConn(conn);
			daoMenu.setConn(conn);
			daoPlato.setConn(conn);
			Menu menu = daoMenu.buscarMenuPorId(idMenu);
			if (menu.getDisponibles() < 1)
				throw new Exception("No hay disponibilidad del menu " + menu.getNombre());

			daoPedido.addPedido(pedido);
			PedidoMenu pedidoMenu = new PedidoMenu(pedido.getNumPedido(), idMenu);
			daoPedidoMenu.addPedidoMenu(pedidoMenu);
			for (int i = 0; i <  menu.getProductos().size(); i++)
				daoPlato.SeVendioProducto((int) menu.getProductos().get(i));
			daoMenu.SeVendioMenu(idMenu);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				daoPedidoMenu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * RF14
	 * @param pedido
	 * @param idMenu
	 * @param equivalencia
	 * @throws Exception
	 */
	public void addPedidoMenuEquivalencia(Pedido pedido, int idMenu, String equivalencia) throws Exception {
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		DAOTablaPedidoMenu daoPedidoMenu = new DAOTablaPedidoMenu();
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		DAOTablaPlato daoPlato = new DAOTablaPlato();
		try 
		{
			String[]eq = equivalencia.split("-");

			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedido.setConn(conn);
			daoPedidoMenu.setConn(conn);
			daoMenu.setConn(conn);
			daoPlato.setConn(conn);
			Plato plato1 = daoPlato.buscarPlatoPorId(Integer.parseInt(eq[0]));
			Plato plato2 = daoPlato.buscarPlatoPorId(Integer.parseInt(eq[1]));
			if (!daoMenu.buscarMenuPorId(idMenu).getProductos().contains(plato1.getIdPlato()))
				throw new Exception("El menu con id " + idMenu + " no posee el producto " + plato1.getNombre());
			if (!daoPlato.getIdPlatosEquivalentes(plato1.getIdPlato()).contains(plato2.getIdPlato()))
				throw new Exception("Los productos " + plato1.getNombre() + " y " + plato2.getNombre() + " no son equivalentes");
			if (plato1.getRestaurante().equalsIgnoreCase(eq[2]) == false)
				throw new Exception("Los productos no pertenecen al restaurante " + eq[2]);
			pedido.setCambios("Cambiar el producto " + plato1.getNombre() + " por el producto " + plato2.getNombre());
			daoPedido.addPedido(pedido);
			PedidoMenu pedidoMenu = new PedidoMenu(pedido.getNumPedido(), idMenu);
			daoPedidoMenu.addPedidoMenu(pedidoMenu);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				daoPedidoMenu.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F9
	 * Metodo que modela la transaccion que agrega un solo pedidoPlato a la base de datos.
	 * <b> post: </b> se ha agregado el pedidoPlato que entra como parametro
	 * @param pedidoPlato - el pedidoPlato a agregar. pedidoPlato != null
	 * @throws Exception - cualquier error que se genere agregando el pedidoPlato
	 */
	public void addPedidoPlato(Pedido pedido, int idPlato) throws Exception {
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		DAOTablaPedidoPlato daoPedidoPlato = new DAOTablaPedidoPlato();
		DAOTablaPlato daoPlato = new DAOTablaPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedido.setConn(conn);
			daoPedidoPlato.setConn(conn);
			daoPlato.setConn(conn);
			if (daoPlato.buscarPlatoPorId(idPlato).getDisponibles() < 1)
				throw new Exception("No hay disponibilidad del plato " + daoPlato.buscarPlatoPorId(idPlato).getNombre());

			daoPedido.addPedido(pedido);
			PedidoPlato pedidoPlato = new PedidoPlato(pedido.getNumPedido(), idPlato);
			daoPedidoPlato.addPedidoPlato(pedidoPlato);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				daoPedidoPlato.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F10 y RF16
	 * Metodo que modela la transaccion que agrega un solo pedidoPlato a la base de datos.
	 * <b> post: </b> se ha agregado el pedidoPlato que entra como parametro
	 * @param pedidoPlato - el pedidoPlato a agregar. pedidoPlato != null
	 * @throws Exception - cualquier error que se genere agregando el pedidoPlato
	 */
	public void pedidoEntregado(Pedido pedido) throws Exception {
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		DAOTablaPedidoPlato daoPedidoPlato = new DAOTablaPedidoPlato();
		DAOTablaPedidoMenu daoPedidoMenu = new DAOTablaPedidoMenu(); 
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		DAOTablaPlato daoPlato = new DAOTablaPlato();
		ArrayList<PedidoPlato> pedidosPlatos = new ArrayList<>();
		ArrayList<PedidoMenu> pedidosMenus = new ArrayList<>();
		try 
		{	
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedido.setConn(conn);
			daoPedidoPlato.setConn(conn);
			daoPedidoMenu.setConn(conn);
			daoMenu.setConn(conn);
			daoPlato.setConn(conn);
			daoPedidoMenu.setConn(conn);
			daoMenu.setConn(conn);
			if (daoPedido.darPedido(pedido.getNumPedido()) == null)
				throw new Exception ("No existe ningun pedido con el numero " + pedido.getNumPedido());
			daoPedido.updatePedido(pedido);
			pedidosPlatos = daoPedidoPlato.bucarPedidoPlatoPorIdPedido(pedido.getNumPedido());
			for (PedidoPlato p : pedidosPlatos)
				daoPlato.SeVendioProducto(p.getIdPlato());

			pedidosMenus = daoPedidoMenu.bucarPedidoMenuPorIdPedido(pedido.getNumPedido());
			for (PedidoMenu m : pedidosMenus)
				daoMenu.SeVendioMenu(m.getIdMenu());
			conn.commit();
		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				daoPedidoPlato.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * 
	 * Metodo que modela la transaccion que agrega un solo plato a la base de datos.
	 * <b> post: </b> se ha agregado el plato que entra como parametro
	 * @param plato - el plato a agregar. plato != null
	 * @throws Exception - cualquier error que se genere agregando el restaurante
	 */
	public void addIngredienteAPlato(int idPlato, String nomIngrediente) throws Exception {
		DAOTablaPlato daoPlato = new DAOTablaPlato();
		DAOTablaIngredientesPlato daoPlatoIngrediente = new DAOTablaIngredientesPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPlato.setConn(conn);
			daoPlatoIngrediente.setConn(conn);
			IngredientesPlato ingPlato = new IngredientesPlato(idPlato, nomIngrediente);
			daoPlatoIngrediente.addIngredientesPlato(ingPlato);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPlato.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F6.2
	 * Metodo que modela la transaccion que agrega un solo pedidoPlato a la base de datos.
	 * <b> post: </b> se ha agregado el pedidoPlato que entra como parametro
	 * @param pedidoPlato - el pedidoPlato a agregar. pedidoPlato != null
	 * @throws Exception - cualquier error que se genere agregando el pedidoPlato
	 */
	public void addMenuPlato(Menu menu, int idPlato) throws Exception {
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		DAOTablaMenuPlato daoMenuPlato = new DAOTablaMenuPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoMenu.setConn(conn);
			daoMenuPlato.setConn(conn);
			daoMenu.addMenu(menu);
			MenuPlato menuPlato = new MenuPlato(menu.getIdMenu(), idPlato);
			daoMenuPlato.addMenuPlato(menuPlato);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				daoMenuPlato.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO F6.3
	 * Metodo que modela la transaccion que agrega un solo pedidoPlato a la base de datos.
	 * <b> post: </b> se ha agregado el pedidoPlato que entra como parametro
	 * @param pedidoPlato - el pedidoPlato a agregar. pedidoPlato != null
	 * @throws Exception - cualquier error que se genere agregando el pedidoPlato
	 */
	public void addPlatoMenu(Menu menu, int idPlato) throws Exception {
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		DAOTablaMenuPlato daoMenuPlato = new DAOTablaMenuPlato();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoMenu.setConn(conn);
			daoMenuPlato.setConn(conn);
			MenuPlato menuPlato = new MenuPlato(menu.getIdMenu(), idPlato);
			daoMenuPlato.addMenuPlato(menuPlato);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoMenu.cerrarRecursos();
				daoMenuPlato.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	/**
	 * REQUERIMIENTO RF18
	 */
	public InfoPedido resgistrarProductosMesaGlobal(InfoPedido infoPedido) throws Exception {
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		DAOTablaUsuario daoUsuario = new DAOTablaUsuario();
		InfoPedido retorno = new InfoPedido(new ArrayList<ItemPedido>(), infoPedido.getEmail(), infoPedido.getZonaRotonda());
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_SERIALIZABLE);
			daoPedido.setConn(conn);
			daoUsuario.setConn(conn);
			daoUsuario.addUsuarioDinamico(infoPedido.getEmail());
			List<ItemPedido> pedidos = daoPedido.registrarPedidoGlobal(infoPedido);
			retorno.setPedidos(pedidos);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoPedido.cerrarRecursos();
				daoUsuario.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}

		return retorno;
	}

	/**
	 * REQUERIMIENTO RF19
	 */
	public void disableRestaurante(String nombre) throws Exception {
		DAOTablaRestaurante daoRestaurante = new DAOTablaRestaurante();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoRestaurante.setConn(conn);
			daoRestaurante.disable(nombre);
			conn.commit();

		} catch (SQLException e) {
			conn.rollback();
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
			conn.rollback();
			System.err.println("GeneralException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} finally {
			try {
				daoRestaurante.cerrarRecursos();
				if(this.conn!=null)
					this.conn.close();
			} catch (SQLException exception) {
				System.err.println("SQLException closing resources:" + exception.getMessage());
				exception.printStackTrace();
				throw exception;
			}
		}
	}

	public void twoPhaseCommit(String name) throws Exception {
		try {
			File arch = new File(this.connectionDataPath);
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(arch);
			prop.load(in);
			in.close();
			String user2 = prop.getProperty("usuario2");
			String password2 = prop.getProperty("clave2");
			String user3=prop.getProperty("usuario3");
			String password3=prop.getProperty("clave3");
			String sql1=prop.getProperty("sql1").replaceAll("nombreRestaurante", name);
			String sql2=prop.getProperty("sql2").replaceAll("nombreRestaurante", name);
			String sql3=prop.getProperty("sql3").replaceAll("nombreRestaurante", name);

			// Create a XADataSource instance
			OracleXADataSource oxds1 = new OracleXADataSource();
			oxds1.setURL(url);
			oxds1.setUser(user);
			oxds1.setPassword(password);

			System.out.println(oxds1.getURL()+";;;"+oxds1.getUser());

			OracleXADataSource oxds2 = new OracleXADataSource();

			oxds2.setURL(url);
			oxds2.setUser(user2);
			oxds2.setPassword(password2);

			System.out.println(oxds2.getURL()+";;;"+oxds2.getUser());
			//Hasta tener la tercera conexión

			OracleXADataSource oxds3 = new OracleXADataSource();

			oxds3.setURL(url);
			oxds3.setUser(user3);
			oxds3.setPassword(password3);

			System.out.println(oxds3.getURL()+";;;"+oxds3.getUser());

			// Get a XA connection to the underlying data source
			XAConnection pc1 = oxds1.getXAConnection();

			// We can use the same data source
			XAConnection pc2 = oxds2.getXAConnection();

			// Get the Physical Connections
			Connection conn1 = pc1.getConnection();
			Connection conn2 = pc2.getConnection();

			// Get the XA Resources
			XAResource oxar1 = pc1.getXAResource();
			XAResource oxar2 = pc2.getXAResource();

			// Create the Xids With the Same Global Ids
			Xid xid1 = createXid(1);
			Xid xid2 = createXid(2);
			// Start the Resources
			oxar1.start (xid1, XAResource.TMNOFLAGS);
			oxar2.start (xid2, XAResource.TMNOFLAGS);
			//RESOURCE 3
			XAConnection pc3=oxds3.getXAConnection();
			Connection conn3=pc3.getConnection();
			XAResource oxar3=pc3.getXAResource();
			Xid xid3=createXid(3);
			oxar3.start (xid3, XAResource.TMNOFLAGS);

			// Do  something with conn1 and conn2
			System.out.println(1);
			doSomeWork (conn1,sql1);
			System.out.println(2);
			//doSomeWork (conn2,sql2);
			System.out.println(3);
			doSomeWork(conn3,sql3);

			// END both the branches -- THIS IS MUST
			oxar1.end(xid1, XAResource.TMSUCCESS);
			oxar2.end(xid2, XAResource.TMSUCCESS);
			oxar3.end(xid3,XAResource.TMSUCCESS);
			// Prepare the RMs
			int prp1 = oxar1.prepare (xid1);
			int prp2 = oxar2.prepare (xid2);
			int prp3=oxar3.prepare(xid3);
			System.out.println("Return value of prepare 1 is " + prp1);
			System.out.println("Return value of prepare 2 is " + prp2);
			System.out.println("Return value of prepare 3 is " + prp3);

			boolean do_commit = true;

			if (!((prp1 == XAResource.XA_OK) || (prp1 == XAResource.XA_RDONLY)))
				do_commit = false;

			if (!((prp2 == XAResource.XA_OK) || (prp2 == XAResource.XA_RDONLY)))
				do_commit = false;

			if (!((prp3 == XAResource.XA_OK) || (prp3 == XAResource.XA_RDONLY)))
				do_commit = false;

			System.out.println("do_commit is " + do_commit);
			System.out.println("Is oxar1 same as oxar2 ? " + oxar1.isSameRM(oxar2));
			System.out.println("Is oxar1 same as oxar3 ? " + oxar1.isSameRM(oxar3));
			System.out.println("Is oxar2 same as oxar3 ? " + oxar2.isSameRM(oxar3));

			if (prp1 == XAResource.XA_OK)
				if (do_commit)
					oxar1.commit (xid1, false);
				else
					oxar1.rollback (xid1);

			if (prp2 == XAResource.XA_OK)
				if (do_commit)
					oxar2.commit (xid2, false);
				else
					oxar2.rollback (xid2);

			if (prp3 == XAResource.XA_OK)
				if (do_commit)
					oxar3.commit (xid3, false);
				else
					oxar3.rollback (xid3);

			// Close connections
			conn1.close();
			conn1 = null;
			conn2.close();
			conn2 = null;

			//conn3.close();
			//conn3 = null;

			pc1.close();
			pc1 = null;
			pc2.close();
			pc2 = null;
			pc3.close();
			pc3 = null;

		} catch (SQLException sqe)
		{
			sqe.printStackTrace();
		} catch (XAException xae)
		{
			if (xae instanceof OracleXAException) {
				System.out.println("XA Error is " +
						((OracleXAException)xae).getXAError());
				System.out.println("SQL Error is " +
						((OracleXAException)xae).getOracleError());
			}
		}
	}

	Xid createXid(int bids) throws XAException {
		byte[] gid = new byte[1]; gid[0]= (byte) 9;
		byte[] bid = new byte[1]; bid[0]= (byte) bids;
		byte[] gtrid = new byte[64];
		byte[] bqual = new byte[64];
		System.arraycopy (gid, 0, gtrid, 0, 1);
		System.arraycopy (bid, 0, bqual, 0, 1);
		Xid xid = new OracleXid(0x1234, gtrid, bqual);
		return xid;
	}

	private void doSomeWork (Connection conn, String sql) throws SQLException {
		// Create a Statement
		PreparedStatement stmt = conn.prepareStatement(sql);

		ResultSet rs=stmt.executeQuery();
		System.out.println(sql);
		stmt.close();
		stmt = null;}
}

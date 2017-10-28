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
import vos.ClienteFrecuente;
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
		return platos;
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
			daoPlatos.addEquivalentes(productos);
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
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoMenu.setConn(conn);
			daoPlato.setConn(conn);
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
		DAOTablaMenu daoMenu = new DAOTablaMenu();
		DAOTablaPlato daoPlato = new DAOTablaPlato();
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedidoMenu.setConn(conn);
			daoPedidoPlato.setConn(conn);
			daoPedido.setConn(conn);
			daoMenu.setConn(conn);
			daoPlato.setConn(conn);
			
			daoPedido.addPedido(pedido);
			
			String[] params = info.split("-");
			int count = Integer.parseInt(params[0]);
			for(int i = 0; i < count; i++) {
				if(params[2*i + 2].compareTo("P") == 0) {
					int idPlato = Integer.parseInt(params[2*i + 1]);
					daoPlato.disminuirDisponibilidad(idPlato, 1);
					daoPedidoPlato.addPedidoPlato(new PedidoPlato(pedido.getNumPedido(), idPlato));
				} else {
					int idMenu = Integer.parseInt(params[2*i + 1]);
					daoMenu.disminuirDisponibilidad(idMenu, 1);
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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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

//	public Rentabilidad darRentabilidad(String fecha1, String fecha2) throws Exception {
//		Rentabilidad rent;
//		DAOTablaPlato daoPlatos = new DAOTablaPlato();
//		try 
//		{
//			//////transaccion
//			this.conn = darConexion();
//			daoPlatos.setConn(conn);
//			r = daoPlatos.darPlatosFiltro(filtro);
//
//		} catch (SQLException e) {
//			System.err.println("SQLException:" + e.getMessage());
//			e.printStackTrace();
//			throw e;
//		} catch (Exception e) {
//			System.err.println("GeneralException:" + e.getMessage());
//			e.printStackTrace();
//			throw e;
//		} finally {
//			try {
//				daoPlatos.cerrarRecursos();
//				if(this.conn!=null)
//					this.conn.close();
//			} catch (SQLException exception) {
//				System.err.println("SQLException closing resources:" + exception.getMessage());
//				exception.printStackTrace();
//				throw exception;
//			}
//		}
//		return rent;
//	}
	
	
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
		DAOTablaPedido daoPedido = new DAOTablaPedido();
		try 
		{
			//////transaccion
			this.conn = darConexion();
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPedido.setConn(conn);
			pedido = daoPedido.darPedidosConsolidadosRestaurante(restaurante);

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
			conn.setTransactionIsolation(conn.TRANSACTION_READ_COMMITTED);
			daoPlatos.setConn(conn);
			platos = daoPlatos.darPlatosCliente(cliente);

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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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
			pedido.setCambios("Cambiar el plato " + plato1.getNombre() + " por el plato " + plato2.getNombre());
			daoPedido.addPedido(pedido);
			PedidoMenu pedidoMenu = new PedidoMenu(pedido.getNumPedido(), idMenu);
			daoPedidoMenu.addPedidoMenu(pedidoMenu);
			conn.commit();
			
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
			daoPlato.setConn(conn);
			daoPedido.updatePedido(pedido);
			pedidosPlatos = daoPedidoPlato.bucarPedidoPlatoPorIdPedido(pedido.getNumPedido());
			for (PedidoPlato p : pedidosPlatos)
				daoPlato.SeVendioProducto(p.getIdPlato());
			
			pedidosMenus = daoPedidoMenu.bucarPedidoMenuPorIdPedido(pedido.getNumPedido());
			for (PedidoMenu m : pedidosMenus)
				daoMenu.SeVendioMenu(m.getIdMenu());
			conn.commit();
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
			System.err.println("SQLException:" + e.getMessage());
			e.printStackTrace();
			throw e;
		} catch (Exception e) {
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

}

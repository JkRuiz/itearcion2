package dtm;


import java.io.IOException;




import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueReceiver;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.rabbitmq.jms.admin.RMQConnectionFactory;
import com.rabbitmq.jms.admin.RMQDestination;

import jms.*;
import tm.RotondAndesTM;

public class RotondAndesDistributed 
{
	private final static String QUEUE_NAME = "java:global/RMQAppQueue";
	private final static String MQ_CONNECTION_NAME = "java:global/RMQClient";
	
	private static final String SPLIT_MESSAGE="...";
	
	private static final String SPLIT_PARAM=";;;";
	
	private static RotondAndesDistributed instance;
	
	private RotondAndesTM tm;
	
	private QueueConnectionFactory queueFactory;
	
	private TopicConnectionFactory factory;
	
	
	private ConsultarProdMDB consultarProdMQ;
	
	private ConsultarRentabilidadMDB consultarRentabilidadMQ;

	private PedidoMenuMDB pedidoMenuMQ;
	
	private PedidoProdMDB pedidoProdMQ;
	
	private RetirarRestauranteMDB retirarRestauranteMQ;
	
	private static String path;


	private RotondAndesDistributed() throws NamingException, JMSException
	{
		InitialContext ctx = new InitialContext();
		factory = (RMQConnectionFactory) ctx.lookup(MQ_CONNECTION_NAME);
		//Inicializicación mqs
		consultarProdMQ=new ConsultarProdMDB(factory,ctx);
		consultarRentabilidadMQ= new ConsultarRentabilidadMDB(factory,ctx);
		pedidoMenuMQ=new PedidoMenuMDB(factory,ctx);
		pedidoProdMQ=new PedidoProdMDB(factory,ctx);
		retirarRestauranteMQ=new RetirarRestauranteMDB(factory,ctx);
		
		
		//Start MQ
		consultarProdMQ.start();
		consultarRentabilidadMQ.start();
		pedidoMenuMQ.start();
		pedidoProdMQ.start();
		retirarRestauranteMQ.start();
		
	}
	
	public void stop() throws JMSException
	{
		consultarProdMQ.close();
		consultarRentabilidadMQ.close();
		pedidoMenuMQ.close();
		pedidoProdMQ.close();
		retirarRestauranteMQ.close();
	}
	
	/**
	 * Método que retorna el path de la carpeta WEB-INF/ConnectionData en el deploy actual dentro del servidor.
	 * @return path de la carpeta WEB-INF/ConnectionData en el deploy actual.
	 */
	public static void setPath(String p) {
		path = p;
	}
	
	public void setUpTransactionManager(RotondAndesTM tm)
	{
	   this.tm = tm;
	}
	
	private static RotondAndesDistributed getInst()
	{
		return instance;
	}
	
	public static RotondAndesDistributed getInstance(RotondAndesTM tm)
	{
		if(instance == null)
		{
			try {
				instance = new RotondAndesDistributed();
			} catch (NamingException e) {
				e.printStackTrace();
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		instance.setUpTransactionManager(tm);
		return instance;
	}
	
	public static RotondAndesDistributed getInstance()
	{
		if(instance == null)
		{
			RotondAndesTM tm = new RotondAndesTM(path);
			return getInstance(tm);
		}
		if(instance.tm != null)
		{
			return instance;
		}
		RotondAndesTM tm = new RotondAndesTM(path);
		return getInstance(tm);
	}
	//Para RFC13 usando RFC1
	public List<Object> consultarProductos(String msg) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return consultarProdMQ.consultarProductos(msg);
	}
	
	public Object consultarProductosLocal(String s) throws Exception
	{
		return null;
	}
	//Para RFC14 usando RFC5
	public List<Object> consultarRentabilidadZonaGlobal(String msg) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		return consultarRentabilidadMQ.consultarRentabilidadZona(msg);
	}
	
	
	public Object consultarRentabilidadZonaLocal(String message) throws Exception
	{
		return null;
	}
	
	//RF18
	public void pedidoProdMesaGlobal(String msj) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		pedidoProdMQ.pedidoProdMesa(msj);
	}
	
	public String pedidoProdMesaLocal(String mensaje) throws Exception
	{
		String[]datos=mensaje.split(":");
		String mesa=datos[0];
		String correo=datos[1];
		ArrayList<String> list = new ArrayList<String>();
		datos=datos[1].split(";;;");
		for(String s:datos)
		{
			list.add(s);
		}
		try
		{
			tm.resgistrarProductosMesaGlobal(null);
			return "OK";
		}
		catch(Exception e)
		{
			return "NO OK";
		}
	}
	
	public void pedidoMenuMesaGlobal(String mensaje) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		pedidoMenuMQ.pedidoMenuMesa(mensaje);
	}
	
	public String pedidoMenuMesaLocal(String mensaje) throws Exception
	{
		//Falta definir las entradas
		String[]datos=mensaje.split(":");
		String mesa=datos[0];
		String correo=datos[1];
		ArrayList<String> list = new ArrayList<String>();
		datos=datos[1].split(";;;");
		for(String s:datos)
		{
			list.add(s);
		}
		try
		{
			tm.resgistrarProductosMesaGlobal(null);
			return "OK";
		}
		catch(Exception e)
		{
			return "NO OK";
		}
	}
	
	//RF19
	
	public String retirarRestauranteLocal(String nombreRestaurante) throws Exception
	{
		//Función para retirar el restaurante a nivel local
		try
		{
			tm.disableRestaurante(nombreRestaurante);
		}
		catch(Exception e)
		{
			return"NO OK";
		}
		return "OK";
	}
	
	public void retirarRestauranteGlobal(String nombreRestaurante) throws JsonGenerationException, JsonMappingException, JMSException, IOException, NonReplyException, InterruptedException, NoSuchAlgorithmException
	{
		retirarRestauranteMQ.retirarRestaurante(nombreRestaurante);
	}
	

	
	
}

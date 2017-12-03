package vos;



import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido {

	@JsonProperty(value="numPedido")
	private int numPedido;
	
	@JsonProperty(value="precio")
	private float precio;
	
	@JsonProperty(value="fecha")
	private String fecha;
	
	@JsonProperty(value="emailUser")
	private String emailUser;
	
	@JsonProperty(value="pagado")
	private int pagado;
	
	@JsonProperty(value="entregado")
	private int entregado;
	
	@JsonProperty(value="hora")
	private String hora;

	@JsonProperty(value="cambios")
	private String cambios;
	
	@JsonProperty(value="zona_rotonda")
	private String zona_rotonda;

	public Pedido(@JsonProperty(value="numPedido")int numPedido, @JsonProperty(value="precio")float precio, @JsonProperty(value="fecha")String fecha, 
			@JsonProperty(value="emailUser")String emailUser, @JsonProperty(value="pagado")int pagado, @JsonProperty(value="entregado")int entregado
			, @JsonProperty(value="hora")String hora, @JsonProperty(value="cambios") String cambios) {
		super();
		this.numPedido = numPedido;
		this.precio = precio;
		this.fecha = fecha;
		this.emailUser = emailUser;
		this.pagado = pagado;
		this.entregado = entregado;
		this.hora = hora;
		if (cambios != null && cambios != "") this.cambios = cambios;
		else cambios = "Sin cambios";
		this.zona_rotonda = "";
	}
	
	public Pedido(@JsonProperty(value="numPedido")int numPedido, @JsonProperty(value="precio")float precio, @JsonProperty(value="fecha")String fecha, 
			@JsonProperty(value="emailUser")String emailUser, @JsonProperty(value="pagado")int pagado, @JsonProperty(value="entregado")int entregado
			, @JsonProperty(value="hora")String hora, @JsonProperty(value="cambios") String cambios, @JsonProperty(value="zona_rotonda") String zona_rotonda) {
		super();
		this.numPedido = numPedido;
		this.precio = precio;
		this.fecha = fecha;
		this.emailUser = emailUser;
		this.pagado = pagado;
		this.entregado = entregado;
		this.hora = hora;
		if (cambios != null && cambios != "") this.cambios = cambios;
		else cambios = "Sin cambios";
		this.zona_rotonda = zona_rotonda;
	}

	public Pedido(int pedido) {
		this.numPedido = pedido;
	}

	public String getCambios() {
		return cambios;
	}

	public void setCambios(String cambios) {
		this.cambios = cambios;
	}

	public int getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(int numPedido) {
		this.numPedido = numPedido;
	}

	public float getPrecio() {
		return precio;
	}

	public void setPrecio(float precio) {
		this.precio = precio;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getEmailUser() {
		return emailUser;
	}

	public void setEmailUser(String emailUser) {
		this.emailUser = emailUser;
	}

	public int getPagado() {
		return pagado;
	}

	public void setPagado(int pagado) {
		this.pagado = pagado;
	}

	public int getEntregado() {
		return entregado;
	}

	public void setEntregado(int entregado) {
		this.entregado = entregado;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getZona_rotonda() {
		return zona_rotonda;
	}

	public void setZona_rotonda(String zona_rotonda) {
		this.zona_rotonda = zona_rotonda;
	}
}

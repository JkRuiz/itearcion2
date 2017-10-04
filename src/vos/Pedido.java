package vos;



import org.codehaus.jackson.annotate.JsonProperty;

public class Pedido {

	@JsonProperty(value="NUM_PEDIDO")
	private int numPedido;
	
	@JsonProperty(value="PRECIO")
	private float precio;
	
	@JsonProperty(value="FECHA")
	private String fecha;
	
	@JsonProperty(value="EMAIL_USER")
	private String emailUser;
	
	@JsonProperty(value="PAGADO")
	private int pagado;
	
	@JsonProperty(value="ENTREGADO")
	private int entregado;

	public Pedido(@JsonProperty(value="NUM_PEDIDO")int numPedido, @JsonProperty(value="PRECIO")float precio, @JsonProperty(value="FECHA")String fecha, 
			@JsonProperty(value="EMAIL_USER")String emailUser, @JsonProperty(value="PAGADO")int pagado, @JsonProperty(value="ENTREGADO")int entregado) {
		super();
		this.numPedido = numPedido;
		this.precio = precio;
		this.fecha = fecha;
		this.emailUser = emailUser;
		this.pagado = pagado;
		this.entregado = entregado;
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
	
	
	
	
}

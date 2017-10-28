package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Informacion {

	@JsonProperty(value="informacion")
	private String informacion;

	@JsonProperty(value="pedido")
	private int pedido;
	
	@JsonProperty(value="producto")
	private int producto;
	
	@JsonProperty(value="ingrediente")
	private String ingrediente;
	
	public Informacion(@JsonProperty(value="informacion") String informacion, @JsonProperty(value="pedido") int pedido,
			@JsonProperty(value="producto") int producto, @JsonProperty(value="ingrediente")String ingrediente) {
		super();
		this.informacion = informacion;
		this.pedido = pedido;
		this.producto = producto;
		this.ingrediente = ingrediente;
	}

	public Informacion() {
		informacion = "Se realizo correctamente la operacion.";
	}

	public String getIngrediente() {
		return ingrediente;
	}

	public void setIngrediente(String ingrediente) {
		this.ingrediente = ingrediente;
	}

	public int getPedido() {
		return pedido;
	}

	public void setPedido(int pedido) {
		this.pedido = pedido;
	}

	public int getProducto() {
		return producto;
	}

	public void setProducto(int producto) {
		this.producto = producto;
	}

	public String getInformacion() {
		return informacion;
	}

	public void setInformacion(String informacion) {
		this.informacion = informacion;
	}
	
}

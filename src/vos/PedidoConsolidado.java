package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoConsolidado {

	@JsonProperty(value="productos")
	private List productos;

	@JsonProperty(value="consumidoRegistrados")
	private int consumidoRegistrados;

	@JsonProperty(value="consumidoNoRegistrados")
	private int consumidoNoRegistrados;

	public PedidoConsolidado(@JsonProperty(value="productos")List productos, 	@JsonProperty(value="consumidoRegistrados")int consumidoRegistrados,
			@JsonProperty(value="consumidoNoRegistrados") int consumidoNoRegistrados) {
		super();
		this.productos = productos;
		this.consumidoRegistrados = consumidoRegistrados;
		this.consumidoNoRegistrados = consumidoNoRegistrados;
	}

	public List getProductos() {
		return productos;
	}

	public void setProductos(List productos) {
		this.productos = productos;
	}

	public int getConsumidoRegistrados() {
		return consumidoRegistrados;
	}

	public void setConsumidoRegistrados(int consumidoRegistrados) {
		this.consumidoRegistrados = consumidoRegistrados;
	}

	public int getConsumidoNoRegistrados() {
		return consumidoNoRegistrados;
	}

	public void setConsumidoNoRegistrados(int consumidoNoRegistrados) {
		this.consumidoNoRegistrados = consumidoNoRegistrados;
	}
	
	
	
	

}

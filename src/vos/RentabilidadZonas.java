package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RentabilidadZonas {

	@JsonProperty(value="zona")
	private String zona;
	
	@JsonProperty(value="productosVendidos")
	private int productosVendidos;
	
	@JsonProperty(value="costoTotal")
	private int costoTotal;
	
	@JsonProperty(value="valorTotal")
	private int valorTotal;

	
	
	public RentabilidadZonas(@JsonProperty(value="zona") String zona, @JsonProperty(value="productosVendidos")int productosVendidos, @JsonProperty(value="costoTotal")int costoTotal, 
			@JsonProperty(value="valorTotal") int valorTotal) {
		super();
		this.zona = zona;
		this.productosVendidos = productosVendidos;
		this.costoTotal = costoTotal;
		this.valorTotal = valorTotal;
	}

	public int getProductosVendidos() {
		return productosVendidos;
	}

	public void setProductosVendidos(int productosVendidos) {
		this.productosVendidos = productosVendidos;
	}

	public int getCostoTotal() {
		return costoTotal;
	}

	public void setCostoTotal(int costoTotal) {
		this.costoTotal = costoTotal;
	}

	public int getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(int valorTotal) {
		this.valorTotal = valorTotal;
	}
	
	
	
	
}

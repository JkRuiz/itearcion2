package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RentabilidadProductos {

	@JsonProperty(value="producto")
	private String producto;

	@JsonProperty(value="productosVendidos")
	private int productosVendidos;

	@JsonProperty(value="costoTotal")
	private int costoTotal;

	@JsonProperty(value="valorTotal")
	private int valorTotal;

	public RentabilidadProductos(@JsonProperty(value="producto") String producto, @JsonProperty(value="productosVendidos")int productosVendidos, @JsonProperty(value="costoTotal")int costoTotal, 
			@JsonProperty(value="valorTotal") int valorTotal) {
		super();
		this.producto = producto;
		this.productosVendidos = productosVendidos;
		this.costoTotal = costoTotal;
		this.valorTotal = valorTotal;
	}
	
	public String getProducto() {
		return producto;
	}
	
	public void setProducto(String producto) {
		this.producto = producto;
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


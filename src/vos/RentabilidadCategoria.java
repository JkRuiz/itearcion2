package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RentabilidadCategoria {

	@JsonProperty(value="categoria")
	private String categoria;

	@JsonProperty(value="productosVendidos")
	private int productosVendidos;

	@JsonProperty(value="costoTotal")
	private int costoTotal;

	@JsonProperty(value="valorTotal")
	private int valorTotal;

	public RentabilidadCategoria(@JsonProperty(value="categoria") String categoria, @JsonProperty(value="productosVendidos")int productosVendidos, @JsonProperty(value="costoTotal")int costoTotal, 
			@JsonProperty(value="valorTotal") int valorTotal) {
		super();
		this.categoria = categoria;
		this.productosVendidos = productosVendidos;
		this.costoTotal = costoTotal;
		this.valorTotal = valorTotal;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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

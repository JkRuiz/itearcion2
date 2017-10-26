package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ProductoConsolidado {
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="ventasTotalesDinero")
	private int ventasTotalesDinero;
	
	@JsonProperty(value="ventasTotalesCantidad")
	private int ventasTotalesCantidad;

	public ProductoConsolidado(@JsonProperty(value="nombre") String nombre, @JsonProperty(value="ventasTotalesDinero") int ventasTotalesDinero, @JsonProperty(value="ventasTotalesCantidad")int ventasTotalesCantidad) {
		super();
		this.nombre = nombre;
		this.ventasTotalesDinero = ventasTotalesDinero;
		this.ventasTotalesCantidad = ventasTotalesCantidad;
	}

	public int getVentasTotalesDinero() {
		return ventasTotalesDinero;
	}

	public void setVentasTotalesDinero(int ventasTotalesDinero) {
		ventasTotalesDinero = ventasTotalesDinero;
	}

	public int getVentasTotalesCantidad() {
		return ventasTotalesCantidad;
	}

	public void setVentasTotalesCantidad(int ventasTotalesCantidad) {
		ventasTotalesCantidad = ventasTotalesCantidad;
	}
	
	


}

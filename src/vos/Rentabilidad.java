package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Rentabilidad {
	
	@JsonProperty(value="RentabilidadZonas")
	List rentZonas;
	
	@JsonProperty(value="RentabilidadProductos")
	List rentProductos;
	
	@JsonProperty(value="RentabilidadCategorias")
	List rentCategorias;

	public Rentabilidad(@JsonProperty(value="RentabilidadZonas")List rentZonas, @JsonProperty(value="RentabilidadProductos")List rentProductos, 
			@JsonProperty(value="RentabilidadCategorias")List rentCategorias) {
		super();
		this.rentZonas = rentZonas;
		this.rentProductos = rentProductos;
		this.rentCategorias = rentCategorias;
	}

	public List getRentZonas() {
		return rentZonas;
	}

	public void setRentZonas(List rentZonas) {
		this.rentZonas = rentZonas;
	}

	public List getRentProductos() {
		return rentProductos;
	}

	public void setRentProductos(List rentProductos) {
		this.rentProductos = rentProductos;
	}

	public List getRentCategorias() {
		return rentCategorias;
	}

	public void setRentCategorias(List rentCategorias) {
		this.rentCategorias = rentCategorias;
	}
	
	

}

package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class RestauranteDetalle {

	@JsonProperty(value="nombre")
	private String nombre;

	@JsonProperty(value="numVentas")
	private int numVentas;
	
	@JsonProperty(value="dia")
	private String dia;

	public RestauranteDetalle(String nombre, int numVentas, String dia) {
		super();
		this.nombre = nombre;
		this.numVentas = numVentas;
		this.dia = dia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getNumVentas() {
		return numVentas;
	}

	public void setNumVentas(int numVentas) {
		this.numVentas = numVentas;
	}

	public String getDia() {
		return dia;
	}

	public void setDia(String dia) {
		this.dia = dia;
	}

	
}

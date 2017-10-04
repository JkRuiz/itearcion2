package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingredientes {

	@JsonProperty(value="NOMBRE")
	private String nombre;
	
	@JsonProperty(value="DESCRIPCION")
	private String descripcion;

	public Ingredientes(@JsonProperty(value="NOMBRE")String nombre, @JsonProperty(value="DESCRIPCION")String descripcion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	
}

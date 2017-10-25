package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Ingredientes {

	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="descripcion")
	private String descripcion;
	
	@JsonProperty(value="traduccion")
	private String traduccion;

	public Ingredientes(@JsonProperty(value="nombre")String nombre, @JsonProperty(value="descripcion")String descripcion
			, @JsonProperty(value="traduccion") String traduccion) {
		super();
		this.nombre = nombre;
		this.descripcion = descripcion;
		this.traduccion = traduccion;
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

	public String getTraduccion() {
		return traduccion;
	}

	public void setTraduccion(String traduccion) {
		this.traduccion = traduccion;
	}
	
	
}

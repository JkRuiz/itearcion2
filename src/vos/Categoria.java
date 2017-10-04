package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Categoria {

	
	@JsonProperty(value="NOMCATEGORIA")
	private String nombre;

	public Categoria(@JsonProperty(value="NOMCATEGORIA")String nombre) {
		super();
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}

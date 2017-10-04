package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Usuario {

	@JsonProperty(value="EMAIL")
	private String email;
	
	@JsonProperty(value="NOMBRE")
	private String nombre;
	
	@JsonProperty(value="IDENTIFICACION")
	private long identificacion;

	public Usuario(@JsonProperty(value="EMAIL")String email, @JsonProperty(value="NOMBRE") String nombre,@JsonProperty(value="IDENTIFICACION") long identificacion ) {
		super();
		this.email = email;
		this.nombre = nombre;
		this.identificacion = identificacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(long identificacion) {
		this.identificacion = identificacion;
	}
	
	
	
}

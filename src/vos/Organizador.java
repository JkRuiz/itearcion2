
package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Organizador {

	@JsonProperty(value="EMAIL")
	private String email;
	
	@JsonProperty(value="PASSWORD")
	private String password;
	
	@JsonProperty(value="USERNAME")
	private String username;
	
	@JsonProperty(value="NOMBRE")
	private String nombre;
	
	@JsonProperty(value="IDENTIFICACION")
	private Long identificacion;

	public Organizador(@JsonProperty(value="EMAIL")String email, @JsonProperty(value="PASSWORD")String password,
			@JsonProperty(value="USERNAME")String username, @JsonProperty(value="NOMBRE") String nombre,
			@JsonProperty(value="IDENTIFICACION") Long identificacion) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.nombre = nombre;
		this.identificacion = identificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Long getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(Long identificacion) {
		this.identificacion = identificacion;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
}

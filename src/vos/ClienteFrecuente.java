package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ClienteFrecuente {

	@JsonProperty(value="EMAIL")
	private String email;
	
	@JsonProperty(value="PASSWORD")
	private String password;
	
	@JsonProperty(value="USERNAME")
	private String username;
	
	@JsonProperty(value="PREFERENCIAPRECIO")
	private float preferenciaPrecio;
	
	@JsonProperty(value="PREFERENCIACATEGORIA")
	private String preferenciaCategoria;
	
	@JsonProperty(value="NOMBRE")
	private String nombre;
	
	@JsonProperty(value="IDENTIFICACION")
	private long identificacion;
	
	

	public ClienteFrecuente(@JsonProperty(value="EMAIL")String email,@JsonProperty(value="PASSWORD") String password, 
			@JsonProperty(value="USERNAME")String username,@JsonProperty(value="PREFERENCIAPRECIO") float preferenciaPrecio,
			@JsonProperty(value="PREFERENCIACATEGORIA")String preferenciaCategoria, @JsonProperty(value="NOMBRE") String nombre,
			@JsonProperty(value="IDENTIFICACION") long identificacion) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.preferenciaPrecio = preferenciaPrecio;
		this.preferenciaCategoria = preferenciaCategoria;
		this.nombre = nombre;
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

	public float getPreferenciaPrecio() {
		return preferenciaPrecio;
	}

	public void setPreferenciaPrecio(float preferenciaPrecio) {
		this.preferenciaPrecio = preferenciaPrecio;
	}

	public String getPreferenciaCategoria() {
		return preferenciaCategoria;
	}

	public void setPreferenciaCategoria(String preferenciaCategoria) {
		this.preferenciaCategoria = preferenciaCategoria;
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

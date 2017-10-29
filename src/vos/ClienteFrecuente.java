package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ClienteFrecuente {

	@JsonProperty(value="email")
	private String email;
	
	@JsonProperty(value="password")
	private String password;
	
	@JsonProperty(value="username")
	private String username;
	
	@JsonProperty(value="preferenciaPrecio")
	private float preferenciaPrecio;
	
	@JsonProperty(value="preferenciaCategoria")
	private String preferenciaCategoria;
	
	@JsonProperty(value="nombre")
	private String nombre;
	
	@JsonProperty(value="identificacion")
	private long identificacion;
	
	@JsonProperty(value="historialPlatos")
	private List pedidos;

	public ClienteFrecuente(@JsonProperty(value="email")String email,@JsonProperty(value="password") String password, 
			@JsonProperty(value="username")String username,@JsonProperty(value="preferenciaPrecio") float preferenciaPrecio,
			@JsonProperty(value="preferenciaCategoria")String preferenciaCategoria, @JsonProperty(value="nombre") String nombre,
			@JsonProperty(value="identificacion") long identificacion, @JsonProperty(value="historialPlatos") List pedidos) {
		super();
		this.email = email;
		this.password = password;
		this.username = username;
		this.preferenciaPrecio = preferenciaPrecio;
		this.preferenciaCategoria = preferenciaCategoria;
		this.nombre = nombre;
		this.identificacion = identificacion;
		this.pedidos = pedidos;
	}

	public ClienteFrecuente() {
	}

	public List getHistorialPlatos() {
		return pedidos;
	}

	public void setHistorialPlatos(List historialPlatos) {
		this.pedidos = historialPlatos;
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

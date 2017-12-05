package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class InfoPedido {
	@JsonProperty(value="email")
	private String email;
	
	@JsonProperty(value="zonaRotonda")
	private String zonaRotonda;
	
	@JsonProperty(value="pedidos")
	private List<ItemPedido> pedidos;

	public InfoPedido(@JsonProperty(value="pedidos") List<ItemPedido> pedidos,
			@JsonProperty(value="email") String email, @JsonProperty(value="zonaRotonda") String zonaRotonda) {
		this.pedidos = pedidos;
		this.email = email;
		this.zonaRotonda = zonaRotonda;
	}
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<ItemPedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<ItemPedido> pedidos) {
		this.pedidos = pedidos;
	}

	public String getZonaRotonda() {
		return zonaRotonda;
	}

	public void setZonaRotonda(String zonaRotonda) {
		this.zonaRotonda = zonaRotonda;
	}

	public class ItemPedido {
		@JsonProperty(value="nomPlato")
		private String nombrePlato;
		
		@JsonProperty(value="nomRestaurante")
		private String nombreRestaurante;

		public String getNombrePlato() {
			return nombrePlato;
		}

		public void setNombrePlato(String nombrePlato) {
			this.nombrePlato = nombrePlato;
		}

		public String getNombreRestaurante() {
			return nombreRestaurante;
		}

		public void setNombreRestaurante(String nombreRestaurante) {
			this.nombreRestaurante = nombreRestaurante;
		}
	}
}

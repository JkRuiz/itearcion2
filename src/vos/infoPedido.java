package vos;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class infoPedido {
	@JsonProperty(value="email")
	private String email;
	
	@JsonProperty(value="pedidos")
	private List<ItemPedido> pedidos;

	public infoPedido(@JsonProperty(value="pedidos") List<ItemPedido> pedidos,
			@JsonProperty(value="email") String email) {
		this.pedidos = pedidos;
		this.email = email;
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

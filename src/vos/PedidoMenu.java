package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoMenu {

	@JsonProperty(value="NUM_PEDIDO")
	private int numPedido;
	
	@JsonProperty(value="ID_MENU")
	private int idMenu;

	public PedidoMenu(@JsonProperty(value="NUM_PEDIDO")int numPedido, @JsonProperty(value="ID_MENU")int idMenu) {
		super();
		this.numPedido = numPedido;
		this.idMenu = idMenu;
	}

	public int getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(int numPedido) {
		this.numPedido = numPedido;
	}

	public int getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}
	
	
	
	
}

package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoPlato {

	@JsonProperty(value="NUM_PEDIDO")
	private int numPedido;
	
	@JsonProperty(value="ID_PLATO")
	private int idPlato;

	public PedidoPlato(@JsonProperty(value="NUM_PEDIDO")int numPedido,@JsonProperty(value="ID_PLATO") int idPlato) {
		super();
		this.numPedido = numPedido;
		this.idPlato = idPlato;
	}

	public int getNumPedido() {
		return numPedido;
	}

	public void setNumPedido(int numPedido) {
		this.numPedido = numPedido;
	}

	public int getIdPlato() {
		return idPlato;
	}

	public void setIdPlato(int idPlato) {
		this.idPlato = idPlato;
	}
	
	
}

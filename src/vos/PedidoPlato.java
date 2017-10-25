package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class PedidoPlato {

	@JsonProperty(value="numPedido")
	private int numPedido;
	
	@JsonProperty(value="idPlato")
	private int idPlato;

	public PedidoPlato(@JsonProperty(value="numPedido")int numPedido,@JsonProperty(value="idPlato") int idPlato) {
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

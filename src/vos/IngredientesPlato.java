package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class IngredientesPlato {

	@JsonProperty(value="ID_PLATO")
	private int idPlato;
	
	@JsonProperty(value="NOM_INGR")
	private String nombreIngrdiente;

	public IngredientesPlato(@JsonProperty(value="ID_PLATO")int idPlato, @JsonProperty(value="NOM_INGR")String nombreIngrdiente) {
		super();
		this.idPlato = idPlato;
		this.nombreIngrdiente = nombreIngrdiente;
	}

	public int getIdPlato() {
		return idPlato;
	}

	public void setIdPlato(int idPlato) {
		this.idPlato = idPlato;
	}

	public String getNombreIngrdiente() {
		return nombreIngrdiente;
	}

	public void setNombreIngrdiente(String nombreIngrdiente) {
		this.nombreIngrdiente = nombreIngrdiente;
	}
	
}

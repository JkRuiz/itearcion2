package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class Equivalentes {

	@JsonProperty(value="INGREDIENTE1")
	private String nomIngrediente1;

	@JsonProperty(value="INGREDIENTE2")
	private String nomIngrediente2;

	public Equivalentes(@JsonProperty(value="INGREDIENTE1")String nomIngrediente1, @JsonProperty(value="INGREDIENTE2")String nomIngrediente2) {
		super();
		this.nomIngrediente1 = nomIngrediente1;
		this.nomIngrediente2 = nomIngrediente2;
	}

	public String getNomIngrediente1() {
		return nomIngrediente1;
	}

	public void setNomIngrediente1(String nomIngrediente1) {
		this.nomIngrediente1 = nomIngrediente1;
	}

	public String getNomIngrediente2() {
		return nomIngrediente2;
	}

	public void setNomIngrediente2(String nomIngrediente2) {
		this.nomIngrediente2 = nomIngrediente2;
	}

}

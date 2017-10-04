package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class MenuPlato {

	@JsonProperty(value="ID_MENU")
	private int idMenu;
	
	@JsonProperty(value="ID_PLATO")
	private int idPlato;

	public MenuPlato(@JsonProperty(value="ID_MENU")int idMenu, @JsonProperty(value="ID_PLATO")int idPlato) {
		super();
		this.idMenu = idMenu;
		this.idPlato = idPlato;
	}

	public int getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}

	public int getIdPlato() {
		return idPlato;
	}

	public void setIdPlato(int idPlato) {
		this.idPlato = idPlato;
	}
	
	
	
}

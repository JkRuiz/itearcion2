package vos;

import java.sql.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class Reserva {

	@JsonProperty(value="FECHA")
	private Date fecha;
	
	@JsonProperty(value="INVITADOS")
	private int invitados;
	
	@JsonProperty(value="ZONA")
	private String zona;
	
	@JsonProperty(value="ID_MENU")
	private int idMenu;
	
	@JsonProperty(value="EMAIL_CLIENTE")
	private String emailCliente;

	public Reserva(@JsonProperty(value="FECHA")Date fecha, @JsonProperty(value="INVITADOS")int invitados, 
			@JsonProperty(value="ZONA")String zona, @JsonProperty(value="ID_MENU")int idMenu, 
			@JsonProperty(value="EMAIL_CLIENTE")String emailCliente) {
		super();
		this.fecha = fecha;
		this.invitados = invitados;
		this.zona = zona;
		this.idMenu = idMenu;
		this.emailCliente = emailCliente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public int getInvitados() {
		return invitados;
	}

	public void setInvitados(int invitados) {
		this.invitados = invitados;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public int getIdMenu() {
		return idMenu;
	}

	public void setIdMenu(int idMenu) {
		this.idMenu = idMenu;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	
	
	
	
}


package vos;

import org.codehaus.jackson.annotate.JsonProperty;

public class ZonaCliente {

	@JsonProperty(value="EMAIL_CLIENTEFR")
	private String emailCliente;
	
	@JsonProperty(value="PREFERENCIA_ZONA")
	private String preferenciaZona;

	public ZonaCliente(@JsonProperty(value="EMAIL_CLIENTEFR")String emailCliente, @JsonProperty(value="PREFERENCIA_ZONA")String preferenciaZona) {
		super();
		this.emailCliente = emailCliente;
		this.preferenciaZona = preferenciaZona;
	}

	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getPreferenciaZona() {
		return preferenciaZona;
	}

	public void setPreferenciaZona(String preferenciaZona) {
		this.preferenciaZona = preferenciaZona;
	}
	
	
	
	
}

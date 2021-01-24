package com.ricbap.bazar.resources.exceptions;

import java.io.Serializable;

public class FieldMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private String CampoNome;
	private String mensagem;
	
	
	public FieldMessage() {}

	public FieldMessage(String campoNome, String mensagem) {
		super();
		CampoNome = campoNome;
		this.mensagem = mensagem;
	}
	
	
	public String getCampoNome() {
		return CampoNome;
	}
	public void setCampoNome(String campoNome) {
		CampoNome = campoNome;
	}
	public String getMensagem() {
		return mensagem;
	}
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}	

}

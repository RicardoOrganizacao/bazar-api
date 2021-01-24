package com.ricbap.bazar.resources.exceptions;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {
	private static final long serialVersionUID = 1L;
	
	
	private List<FieldMessage> erros = new ArrayList<>();
	
	
	public ValidationError(Integer status, String msg, OffsetDateTime dataErro) {
		super(status, msg, dataErro);
	}
	

	public List<FieldMessage> getCampos() {
		return erros;
	}
	public void addError(String campoNome, String mensagem) {
		erros.add(new FieldMessage(campoNome, mensagem));
	}

}

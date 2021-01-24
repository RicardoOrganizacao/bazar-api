package com.ricbap.bazar.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Integer id;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Length(min = 5, max = 50, message = "O tamanho deve estar entre 5 e 50 caracteres")
	private String nome;
	
	@NotEmpty(message = "Preenchimento obrigatório")
	@Email(message = "Email inválido")
	private String email;
	
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
}

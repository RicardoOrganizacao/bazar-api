package com.ricbap.bazar.resources.exceptions;

import java.io.Serializable;
import java.time.OffsetDateTime;

public class StandardError implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Integer status;
	private String msg;
	private OffsetDateTime dataErro;
	
	
	public StandardError() {}

	public StandardError(Integer status, String msg, OffsetDateTime dataErro) {
		super();
		this.status = status;
		this.msg = msg;
		this.dataErro = dataErro;
	}
	

	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public OffsetDateTime getDataErro() {
		return dataErro;
	}
	public void setDataErro(OffsetDateTime dataErro) {
		this.dataErro = dataErro;
	}

}

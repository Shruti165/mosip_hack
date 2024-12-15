package com.saarthi.commons;
import lombok.Data;

public class GenericResponse {
	private boolean status;
	private Object message;
	public GenericResponse(Object message) {
		this.message = message;
		status = true;
	}
	public GenericResponse(Object message, boolean status) {
		this.message = message;
		this.status = status;
	}
	public boolean isStatus() {
		return status;
	}
	public Object getMessage() {
		return message;
	}
	
}

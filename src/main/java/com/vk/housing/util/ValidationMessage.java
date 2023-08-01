package com.vk.housing.util;

public class ValidationMessage {

	String message;

	boolean value;

	public ValidationMessage(String message, boolean value) {
		super();
		this.message = message;
		this.value = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}
}

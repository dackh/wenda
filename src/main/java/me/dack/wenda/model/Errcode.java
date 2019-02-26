package me.dack.wenda.model;

public enum Errcode {

	Null(0),
	Auth(2),
	Error(3);
	
	private int value;
	
	Errcode(int value) {
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}

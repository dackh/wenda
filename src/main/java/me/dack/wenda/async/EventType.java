package me.dack.wenda.async;

public enum EventType {
	LIKE(0),
	COMMENT(1),
	LOGIN(2),
	FOLLOW(3),
	RECOMMEND(4);
	
	private int value;
	
	EventType(int value){
		this.value = value;
	}
	public int getValue() {
		return value;
	}
}

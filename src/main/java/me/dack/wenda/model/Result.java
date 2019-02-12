package me.dack.wenda.model;

public class Result {

	private boolean flag;
	private String message;
	private Object res;
	
	public Result(){}
	public Result(boolean flag,String message){
		this.flag = flag;
		this.message = message;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getRes() {
		return res;
	}
	public void setRes(Object res) {
		this.res = res;
	}
	
	
	
}

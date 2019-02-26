package me.dack.wenda.model;

public class Result {

	private Errcode errcode;
	private String message;
	private Object res;
	
	public Result(){}
	public Result(Errcode errcode,String message){
		this.errcode = errcode;
		this.message = message;
	}
	
	public int getErrcode() {
		return errcode.getValue();
	}
	public void setErrcode(Errcode errcode) {
		this.errcode = errcode;
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

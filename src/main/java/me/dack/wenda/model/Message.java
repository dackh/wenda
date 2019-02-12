package me.dack.wenda.model;

import java.util.Date;

public class Message {

	private int id;
	private int fromId;
	private int toId;
	private String content;
	private Date createTime;
	private int hasRead;
	private String conversationId;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFromId() {
		return fromId;
	}
	public void setFromId(int fromId) {
		this.fromId = fromId;
	}
	public int getToId() {
		return toId;
	}
	public void setToId(int toId) {
		this.toId = toId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int isHasRead() {
		return hasRead;
	}
	public void setHasRead(int hasRead) {
		this.hasRead = hasRead;
	}
	public String getConversationId() {
		if(fromId > toId){
			return toId + "_" + fromId;
		}else{
			return fromId + "_" + toId;
		}
	}
	public void setConversationId(String conversationId) {
		this.conversationId = conversationId;
	}
	
}

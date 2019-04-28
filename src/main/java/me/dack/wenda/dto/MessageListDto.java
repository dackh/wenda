package me.dack.wenda.dto;

import me.dack.wenda.model.Message;

public class MessageListDto {

	private Message message;
    private int unReadCount;
	
	public Message getMessage() {
		return message;
	}
	public void setMessage(Message message) {
		this.message = message;
	}
	public int getUnReadCount() {
		return unReadCount;
	}
	public void setUnReadCount(int unReadCount) {
		this.unReadCount = unReadCount;
	}
}

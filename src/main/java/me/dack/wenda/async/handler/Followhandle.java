package me.dack.wenda.async.handler;

import java.util.List;

import me.dack.wenda.async.EventHandler;
import me.dack.wenda.async.EventModel;
import me.dack.wenda.async.EventType;
import me.dack.wenda.model.Message;

public class Followhandle implements EventHandler{

	@Override
	public void doHandle(EventModel model) {
		Message message = new Message();
		message.setFromId(0);
		message.setToId(1);
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		// TODO Auto-generated method stub
		return null;
	}

}

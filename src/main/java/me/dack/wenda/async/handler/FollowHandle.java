package me.dack.wenda.async.handler;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import me.dack.wenda.async.EventHandler;
import me.dack.wenda.async.EventModel;
import me.dack.wenda.async.EventType;
import me.dack.wenda.model.Message;
import me.dack.wenda.model.User;
import me.dack.wenda.service.UserService;

@Component
public class FollowHandle implements EventHandler{

	@Autowired
	private UserService userService;
	
	@Override
	public void doHandle(EventModel model) {
		Message message = new Message();
		message.setFromId(0);
		message.setToId(model.getEntityId());
		
		User user = userService.getUserById(model.getActorId());
		message.setContent("用户"+user.getName()+"关注了你");
	}

	@Override
	public List<EventType> getSupportEventTypes() {
		return Arrays.asList(EventType.FOLLOW);
	}

}

package me.dack.wenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import me.dack.wenda.dao.MessageDao;
import me.dack.wenda.model.Message;

@Service
public class MessageService {

	@Autowired
	private MessageDao messageDao;
	@Autowired
	private SensitiveService sensitiveService;
	
	public int addMessage(Message message){
		message.setContent(HtmlUtils.htmlEscape(message.getContent()));
		message.setContent(sensitiveService.filter(message.getContent()));
		return messageDao.addMessage(message);
	}
	
	public List<Message> getConversationDetail(String conversationId,int offset,int limit){
		return messageDao.getConversationDetail(conversationId, offset, limit);
	}
	
	public List<Message> getConversationList(int userId,int offset,int limit){
		return messageDao.getConversationList(userId, offset, limit);
	}
	
	public int getConversationUnReadCount(int userId,String conversationId){
		return messageDao.getConversationUnReadCount(userId, conversationId);
	}
	
	public int updateConversationHasRead(int userId,String conversationId){
		return messageDao.updateConversationHasRead(userId, conversationId);
	}
}

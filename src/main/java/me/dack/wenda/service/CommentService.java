package me.dack.wenda.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import me.dack.wenda.dao.CommentDao;
import me.dack.wenda.dao.QuestionDao;
import me.dack.wenda.model.Comment;
import me.dack.wenda.model.EntityType;

@Service
public class CommentService {

	@Autowired
	private CommentDao commentDao;
	@Autowired
	private SensitiveService sensitiveService;
	@Autowired
	private QuestionDao questionDao;
	
	public int addComment(Comment comment){
		comment.setContent(HtmlUtils.htmlEscape(comment.getContent()));
		//敏感词过滤
		comment.setContent(sensitiveService.filter(comment.getContent()));
		if(comment.getEntityType() == EntityType.QUESTION_ENTITY){
//			questionDao.updateQuestionCount(comment.getEntityId(), commentDao.getCommentCount(comment.getEntityId(), EntityType.QUESTION_ENTITY));			
		}
		return commentDao.addComment(comment);
	}
	
	public int updateCommentContent(String content,int id){
		//敏感词过滤
		content= sensitiveService.filter(content);
		return commentDao.updateCommentContent(content, id);
	}
	
	public int deleteComment(int id){
		Comment comment = commentDao.getCommentById(id);
		if(comment.getEntityType() == EntityType.QUESTION_ENTITY){
			questionDao.updateQuestionCount(comment.getEntityId(), commentDao.getCommentCount(comment.getEntityId(), EntityType.QUESTION_ENTITY));			
		}
		return commentDao.updateCommentStatus(id, 1);
	}
	
	public List<Comment> queryComment(int entityId,int entityType){
		return commentDao.queryComment(entityId, entityType);
	}
	
	public Comment getCommentById(int id) {
		return commentDao.getCommentById(id);
	}
}

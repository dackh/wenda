package me.dack.wenda.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import me.dack.wenda.async.EventModel;
import me.dack.wenda.async.EventProducer;
import me.dack.wenda.async.EventType;
import me.dack.wenda.model.Comment;
import me.dack.wenda.model.EntityType;
import me.dack.wenda.model.Errcode;
import me.dack.wenda.model.HostHolder;
import me.dack.wenda.model.Question;
import me.dack.wenda.model.Result;
import me.dack.wenda.model.User;
import me.dack.wenda.service.CommentService;
import me.dack.wenda.service.QuestionService;

@RestController
@RequestMapping("/comment")
@CrossOrigin
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private EventProducer eventProducer;
	@Autowired
	private QuestionService questionService;
	
	@RequestMapping("addComment")
	public Result addComment(@RequestParam("content")String content,
			@RequestParam("entityId")int entityId,
			@RequestParam("entityType")int entityType){
		try{	
			if (entityType == EntityType.COMMENT_ENTITY) {
				Comment parentComment = commentService.getCommentById(entityId);
				if (parentComment == null) {
					return new Result(Errcode.Error,"找不到entity_id对应评论");
				}
				eventProducer.produceEvent(new EventModel(EventType.COMMENT)
						.setActorId(hostHolder.getUser().getId())
						.setEntityId(entityId)
						.setEntityType(entityType)
						.setEntityOwnerId(parentComment.getUserId())
						.setExt("commentId", String.valueOf(entityId)));
			}else if(entityType == EntityType.QUESTION_ENTITY) {
				Question question = questionService.getQuestionById(entityId);
				if (question == null) {
					return new Result(Errcode.Error,"找不到entity_id对应问题");
				}
				eventProducer.produceEvent(new EventModel(EventType.COMMENT)
						.setActorId(hostHolder.getUser().getId())
						.setEntityId(entityId)
						.setEntityType(entityType)
						.setEntityOwnerId(question.getUserId())
						.setExt("questionId", String.valueOf(entityId)));
				questionService.updateQuestionCount(entityId, questionService.getQuestionById(entityId).getCommentCount()+1);
			}else {
				return new Result(Errcode.Error,"参数entity_type值异常");
			}
			eventProducer.produceEvent(new EventModel(EventType.RECOMMEND)
					.setEntityId(entityId));
			
			Comment comment = new Comment();
			comment.setContent(content);
			comment.setEntityId(entityId);
			comment.setEntityType(entityType);
			comment.setCreateTime(new Date());
			comment.setStatus(0);
			User user = hostHolder.getUser();
			comment.setUserId(user.getId()); 
			if(commentService.addComment(comment) > 0){
				return new Result(Errcode.Null,"添加成功");
			}
		}catch (Exception e) {
			logger.error("添加评论失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"添加失败");
	}
	
	@RequestMapping("/updateCommentContent")
	public Result updateCommentContent(@RequestParam("content")String content,
			@RequestParam("id")int id){
		try{	
			if(commentService.updateCommentContent(content, id) > 0){
				return new Result(Errcode.Null,"修改成功");
			}
		}catch (Exception e) {
			logger.error("修改评论失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"修改失败");
	}
	
	@RequestMapping("deleteComment")
	public Result deleteComment(@RequestParam("id")int id){
		try{	
			if(commentService.deleteComment(id) > 0){
				return new Result(Errcode.Null,"删除成功");
			}
		}catch (Exception e) {
			logger.error("删除评论失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"删除失败");
	}
	
	@RequestMapping("queryComment")
	public Result queryComment(@RequestParam("entityId")int entityId,
			@RequestParam("entityType")int entityType){
		try{	
			List<Comment> queryComment = commentService.queryComment(entityId,entityType);
			Result result = new Result(Errcode.Null,"查找成功");
			result.setRes(queryComment);
			return result;
			
		}catch (Exception e) {
			logger.error("查找评论失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"查找失败");
	}
	
}

package me.dack.wenda.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import me.dack.wenda.model.Result;
import me.dack.wenda.model.User;
import me.dack.wenda.service.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private EventProducer eventProducer;
	
	@RequestMapping("addComment")
	public Result addComment(@RequestParam("content")String content,
			@RequestParam("entityId")int entityId,
			@RequestParam("entityType")int entityType){
		
		try{	
			Comment comment = commentService.getCommentById(commentId);
			comment.setContent(content);
			comment.setEntityId(comment.getEntityId());
			comment.setEntityType(comment.getEntityType());
			comment.setCreateTime(new Date());
			comment.setStatus(0);
			User user = hostHolder.getUser();
			comment.setUserId(user.getId()); 
			eventProducer.produceEvent(new EventModel(EventType.COMMENT)
					.setActorId(hostHolder.getUser().getId())
					.setEntityId(EntityType.COMMENT_ENTITY)
					.setEntityOwnerId(comment.getUserId())
					.setExt("questionId", String.valueOf(comment.getEntityId())));
			
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
			@RequestParam("commentId")int commentId){
		try{	
			if(commentService.updateCommentContent(content, commentId) > 0){
				return new Result(Errcode.Null,"修改成功");
			}
		}catch (Exception e) {
			logger.error("修改评论失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"修改失败");
	}
	
	@RequestMapping("deleteComment")
	public Result deleteComment(@RequestParam("commentId")int commentId){
		try{	
			if(commentService.deleteComment(commentId) > 0){
				return new Result(Errcode.Null,"删除成功");
			}
		}catch (Exception e) {
			logger.error("删除评论失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"删除失败");
	}
	
	@RequestMapping("queryComment")
	public Result queryComment(int entity_id,int entity_type){
		try{	
			List<Comment> queryComment = commentService.queryComment(entity_id,entity_type);
			Result result = new Result(Errcode.Null,"查找成功");
			result.setRes(queryComment);
			return result;
			
		}catch (Exception e) {
			logger.error("查找评论失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"查找失败");
	}
	
}

package me.dack.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import me.dack.wenda.async.EventModel;
import me.dack.wenda.async.EventProducer;
import me.dack.wenda.async.EventType;
import me.dack.wenda.model.Comment;
import me.dack.wenda.model.EntityType;
import me.dack.wenda.model.Errcode;
import me.dack.wenda.model.HostHolder;
import me.dack.wenda.model.Result;
import me.dack.wenda.service.CommentService;
import me.dack.wenda.service.LikeService;

@RestController
@RequestMapping("/like")
@CrossOrigin
public class LikeController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);

	
	@Autowired
	private LikeService likeService;
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private EventProducer eventProducer;
	@Autowired
	private CommentService commentService;
	

	//先只做评论的点赞，问题的点赞后续再加上
	@RequestMapping("/like")
	@ResponseBody
	public Result Like(@RequestParam("commentId")int commentId) {
		
		//TODO   每次调用接口都会发送消息
		try{
			Comment comment = commentService.getCommentById(commentId);
			eventProducer.produceEvent(new EventModel(EventType.LIKE)
					.setActorId(hostHolder.getUser().getId())
					.setEntityId(EntityType.COMMENT_ENTITY)
					.setEntityOwnerId(comment.getUserId())
					.setExt("questionId", String.valueOf(comment.getEntityId())));		
			
			long likeCount = likeService.like(hostHolder.getUser().getId(), comment.getEntityType(), comment.getEntityId());
			Result result =  new Result(Errcode.Null,"点赞成功");
			result.setRes(likeCount);
			return result;
		}catch (Exception e) {
			logger.error("点赞喜欢失败"+e);
		}
		return new Result(Errcode.Error,"点赞失败");
	}
	
	@RequestMapping("/disLike")
	@ResponseBody
	public Result disLike(@RequestParam("commentId")int commentId) {
		
		try{
			Comment comment = commentService.getCommentById(commentId);
			long likeCount = likeService.disLike(hostHolder.getUser().getId(), comment.getEntityType() ,comment.getEntityId());
			Result result =  new Result(Errcode.Null,"点踩成功");
			result.setRes(likeCount);
			return result;
		}catch (Exception e) {
			logger.error("点赞不喜欢失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"点赞失败");
	}
	
	// @RequestMapping("/getLikeCount")
	// @ResponseBody
	// public Result getLikeCount(@RequestParam("entityType")int entityType,
	// 		@RequestParam("entityId")int entityId) {
		
	// 	try{
	// 		long likeCount = likeService.getLikeCount(entityType, entityId);
	// 		Result result =  new Result(Errcode.Null,"获取成功");
	// 		result.setRes(likeCount);
	// 		return result;
	// 	}catch (Exception e) {
	// 		logger.error("获取点赞数失败"+e.getMessage());
	// 	}
	// 	return new Result(Errcode.Error,"获取失败");
	// }
	@RequestMapping("/getLikeCount")
	@ResponseBody
	public Result getLikeCount(@RequestParam("commentId")int commentId) {
		
		try{
			Comment comment = commentService.getCommentById(commentId);
			long likeCount = likeService.getLikeCount(comment.getEntityType(), comment.getEntityId());
			Result result =  new Result(Errcode.Null,"获取成功");
			result.setRes(likeCount);
			return result;
		}catch (Exception e) {
			logger.error("获取点赞数失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"获取失败");
	}
	
	@RequestMapping("/getLikeStatus")
	@ResponseBody
	public Result getLikeStatus(@RequestParam("commentId")int commentId) {
		
		try{
			Comment comment = commentService.getCommentById(commentId);
			long likeCount = likeService.getLikeStatus(hostHolder.getUser().getId(),comment.getEntityType(), comment.getEntityId());
			Result result =  new Result(Errcode.Null,"获取成功");
			result.setRes(likeCount);
			return result;
		}catch (Exception e) {
			logger.error("获取点赞状态失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"获取失败");
	}
}

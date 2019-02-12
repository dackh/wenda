package me.dack.wenda.controller;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.dack.wenda.model.Comment;
import me.dack.wenda.model.Result;
import me.dack.wenda.service.CommentService;

@Controller
@RequestMapping("/comment")
public class CommentController {

	private static final Logger logger = LoggerFactory.getLogger(CommentController.class);
	
	@Autowired
	private CommentService commentService;
	
	@RequestMapping("addComment")
	@ResponseBody
	public Result addComment(@RequestParam("content")String content,
			@RequestParam("entityId")int entityId,
			@RequestParam("entityType")int entityType){
		Comment comment = new Comment();
		comment.setContent(content);
		comment.setEntityId(entityId);
		comment.setEntityType(entityType);
		comment.setCreateTime(new Date());
		comment.setStatus(0);
		comment.setUserId(0);  //user_id
		try{	
			if(commentService.addComment(comment) > 0){
				return new Result(true,"添加成功");
			}
		}catch (Exception e) {
			logger.error("添加评论失败"+e.getMessage());
		}
		return new Result(false,"添加失败");
	}
	
	@RequestMapping("/updateCommentContent")
	@ResponseBody
	public Result updateCommentContent(@RequestParam("content")String content,
			@RequestParam("id")int id){
		try{	
			if(commentService.updateCommentContent(content, id) > 0){
				return new Result(true,"修改成功");
			}
		}catch (Exception e) {
			logger.error("修改评论失败"+e.getMessage());
		}
		return new Result(false,"修改失败");
	}
	
	@RequestMapping("deleteComment")
	@ResponseBody
	public Result deleteComment(@RequestParam("id")int id){
		try{	
			if(commentService.deleteComment(id) > 0){
				return new Result(true,"删除成功");
			}
		}catch (Exception e) {
			logger.error("删除评论失败"+e.getMessage());
		}
		return new Result(false,"删除失败");
	}
	
	@RequestMapping("queryComment")
	@ResponseBody
	public Result queryComment(int entity_id,int entity_type){
		try{	
			List<Comment> queryComment = commentService.queryComment(entity_id,entity_type);
			Result result = new Result(true,"查找成功");
			result.setRes(queryComment);
			return result;
			
		}catch (Exception e) {
			logger.error("查找评论失败"+e.getMessage());
		}
		return new Result(false,"查找失败");
	}
	
}

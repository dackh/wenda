package me.dack.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.dack.wenda.async.EventProducer;
import me.dack.wenda.model.Comment;
import me.dack.wenda.model.EntityType;
import me.dack.wenda.model.Errcode;
import me.dack.wenda.model.HostHolder;
import me.dack.wenda.model.Result;
import me.dack.wenda.model.User;
import me.dack.wenda.service.CommentService;
import me.dack.wenda.service.FollowService;
import me.dack.wenda.service.LikeService;

@Controller("/follow")
@CrossOrigin
public class FollowController {

	private static final Logger logger = LoggerFactory.getLogger(FollowController.class);

	@Autowired
	private FollowService followService;
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private EventProducer eventProducer;

	
	@RequestMapping("/follwe")
	@ResponseBody
	public Result follwe(int entityType,int entityId) {
		
		User user = hostHolder.getUser();
		try{
			boolean follow = followService.follow(user.getId(), entityType, entityId);
			
			long followerCount = followService.getFollowerCount(entityType, entityId);
			Result result =  new Result(Errcode.Null,"关注成功");
			result.setRes(followerCount);
			return result;
		}catch (Exception e) {
			logger.error("点赞不喜欢失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"点赞失败");
	}
}

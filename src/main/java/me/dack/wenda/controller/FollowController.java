package me.dack.wenda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import me.dack.wenda.async.EventModel;
import me.dack.wenda.async.EventProducer;
import me.dack.wenda.async.EventType;
import me.dack.wenda.model.EntityType;
import me.dack.wenda.model.Errcode;
import me.dack.wenda.model.HostHolder;
import me.dack.wenda.model.Result;
import me.dack.wenda.model.User;
import me.dack.wenda.service.FollowService;

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
	public Result follweUser(@RequestParam("userId")int userId) {
		
		User user = hostHolder.getUser();
		try{
			eventProducer.produceEvent(new EventModel(EventType.FOLLOW)
					.setActorId(user.getId())
					.setEntityType(EntityType.USER_ENTITY)
					.setEntityId(userId));
			
			boolean ret = followService.follow(user.getId(), EntityType.USER_ENTITY, userId);
			long followerCount = followService.getFollowerCount(EntityType.USER_ENTITY, userId);
			if(ret){
				Result result =  new Result(Errcode.Null,"关注成功");
				result.setRes(followerCount);
				return result;				
			}
		}catch (Exception e) {
			logger.error("关注失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"点赞失败");
	}
	
	@RequestMapping("unFollowUser")
	@ResponseBody
	public Result unFollowUser(@RequestParam("userId")int userId){
		User user = hostHolder.getUser();
		try {
			boolean ret = followService.unFollow(user.getId(), user.getId(), userId);
			if(ret){
				Result result = new Result(Errcode.Null,"取消关注成功");
				return result;
			}
		} catch (Exception e) {
			logger.error("取消关注失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"取消失败");
	}
}

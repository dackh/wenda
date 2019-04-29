package me.dack.wenda.controller;

import java.util.ArrayList;
import java.util.List;

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
import me.dack.wenda.dto.FollowDto;
import me.dack.wenda.model.EntityType;
import me.dack.wenda.model.Errcode;
import me.dack.wenda.model.HostHolder;
import me.dack.wenda.model.Result;
import me.dack.wenda.model.User;
import me.dack.wenda.service.FollowService;
import me.dack.wenda.service.UserService;

@RestController
@RequestMapping("/follow")
@CrossOrigin
public class FollowController {

	private static final Logger logger = LoggerFactory.getLogger(FollowController.class);

	@Autowired
	private FollowService followService;
	@Autowired
	private HostHolder hostHolder;
	@Autowired
	private EventProducer eventProducer;
	@Autowired
	private UserService userService;

	
	@RequestMapping("/follow")
	@ResponseBody
	public Result follweUser(@RequestParam("userId")int userId) {
		
		User user = hostHolder.getUser();
		if( user.getId() == userId ){
			return new Result(Errcode.Error,"不能关注自己");
		}
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
	
	@RequestMapping("unFollow")
	@ResponseBody
	public Result unFollowUser(@RequestParam("userId")int userId){
		User user = hostHolder.getUser();
		try {
			boolean ret = followService.unFollow(user.getId(),  EntityType.USER_ENTITY, userId);
			long followerCount = followService.getFollowerCount(EntityType.USER_ENTITY, userId);
			if(ret){
				Result result = new Result(Errcode.Null,"取消关注成功");
				result.setRes(followerCount);
				return result;
			}
		} catch (Exception e) {
			logger.error("取消关注失败"+e.getMessage());
		}
		return new Result(Errcode.Error,"取消失败");
	}

	@RequestMapping("getFollowers")
	@ResponseBody
	public Result getFollowers(@RequestParam("userId")int userId,@RequestParam("offset")int offset,@RequestParam("limit")int limit){
		try{
			List<Integer> followers = followService.getFollowers(EntityType.USER_ENTITY, userId, offset, limit);
			long followerCount = followService.getFollowerCount(EntityType.USER_ENTITY, userId);
			FollowDto followDto = new FollowDto(getUserInfos(followers),followerCount);

			Result result = new Result(Errcode.Null,"获取成功");
			result.setRes(followDto);
			return result;
		}catch(Exception e){
			logger.error("获取关注我的人列表失败", e.getMessage());
		}
		return new Result(Errcode.Error,"获取失败");
	}

	@RequestMapping("getFollowees")
	@ResponseBody
	public Result getFollowees(@RequestParam("userId")int userId,@RequestParam("offset")int offset,@RequestParam("limit")int limit){
		try{
			List<Integer> followees = followService.getFollowees(EntityType.USER_ENTITY, userId, offset, limit);
			long followeeCount = followService.getFolloweeCount(EntityType.USER_ENTITY, userId);
			FollowDto followDto = new FollowDto(getUserInfos(followees),followeeCount);

			Result result = new Result(Errcode.Null,"获取成功");
			result.setRes(followDto);
			return result;
		}catch(Exception e){
			logger.error("获取我关注的人列表失败", e.getMessage());
		}
		return new Result(Errcode.Error,"获取失败");
	}

	@RequestMapping("isFollower")
	@ResponseBody
	public Result isFollower(@RequestParam("userId")int userId){
		User user = hostHolder.getUser();
		try{
			boolean isFollower = followService.isFollower(user.getId(),EntityType.USER_ENTITY, userId);
			Result result = new Result(Errcode.Null,"查看成功");
			result.setRes(isFollower ? 1 : 0);
			return result;
		}catch(Exception e){
			logger.error("查看是否关注某用户失败", e.getMessage());
		}
		return new Result(Errcode.Error,"查看成功");
	}

	public List<User> getUserInfos(List<Integer> userIds){
		List<User> users = new ArrayList<>();
		for(int userId : userIds){
			users.add(userService.getUserById(userId));
		}
		return users;
	}
}

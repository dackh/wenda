package me.dack.wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dack.wenda.utils.JedisAdapter;
import me.dack.wenda.utils.RedisKeyUtils;

@Service
public class LikeService {

	@Autowired
	JedisAdapter jedisAdapter;
	
	public long getLikeCount(int entityType,int entityId){
		String likeKey = RedisKeyUtils.getLikeKey(entityType, entityId);
		return jedisAdapter.scard(likeKey);
	}
	
	public int getLikeStatus(int userId,int entityType,int entityId){
		String likeKey = RedisKeyUtils.getLikeKey(entityType, entityId);
		if(jedisAdapter.sismember(likeKey, String.valueOf(userId))){
			return 1;
		}
		String disLikeKey = RedisKeyUtils.getDisLikeKey(entityType, entityId);
		return jedisAdapter.sismember(disLikeKey, String.valueOf(userId));
	}
}

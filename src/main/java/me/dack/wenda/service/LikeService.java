package me.dack.wenda.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dack.wenda.utils.JedisAdapter;
import me.dack.wenda.utils.RedisKeyUtils;

@Service
public class LikeService {

	@Autowired
	private JedisAdapter jedisAdapter;
	
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
		return jedisAdapter.sismember(disLikeKey, String.valueOf(userId)) ? -1 : 0;
	}
	
	public long like(int userId,int entityType,int entityId) {
		String likeKey = RedisKeyUtils.getLikeKey(entityType, entityId);
		jedisAdapter.sadd(likeKey, String.valueOf(userId));		
		
		String disLikeKey = RedisKeyUtils.getDisLikeKey(entityType, entityId);
		jedisAdapter.srem(disLikeKey, String.valueOf(userId));
		
		return jedisAdapter.scard(likeKey);
	}
	
	public long disLike(int userId,int entityType,int entityId) {
		String disLikekey = RedisKeyUtils.getDisLikeKey(entityType, entityId);
		jedisAdapter.sadd(disLikekey, String.valueOf(userId));
		
		String likeKey = RedisKeyUtils.getLikeKey(entityType, entityId);
		jedisAdapter.srem(likeKey, String.valueOf(userId));
		
		return jedisAdapter.scard(likeKey);
	}
}

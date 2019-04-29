package me.dack.wenda.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import me.dack.wenda.utils.JedisAdapter;
import me.dack.wenda.utils.RedisKeyUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

@Service
public class FollowService {
	
	@Autowired
	private JedisAdapter adapter;
	
	public boolean follow(int userId,int entityType,int entityId) {
		String followerKey = RedisKeyUtils.getFollowerKey(entityId, entityType);
		String followeeKey = RedisKeyUtils.getFolloweeKey(userId, entityType);
		Date date = new Date();
		Jedis jedis = adapter.getJedis();
		Transaction tx = adapter.multi(jedis);
		
		tx.zadd(followerKey, date.getTime(),String.valueOf(userId));
		tx.zadd(followeeKey, date.getTime(),String.valueOf(entityId));
		List<Object> res = adapter.exec(tx, jedis);
		return res.size() == 2;
	}
	
	public boolean unFollow(int userId,int entityType,int entityId) {
		String followerKey = RedisKeyUtils.getFollowerKey(entityId, entityType);
		String followeeKey = RedisKeyUtils.getFolloweeKey(userId, entityType);
		Jedis jedis = adapter.getJedis();
		Transaction tx = adapter.multi(jedis);
		
		tx.zrem(followerKey, String.valueOf(userId));
		tx.zrem(followeeKey, String.valueOf(entityId));
		List<Object> res = adapter.exec(tx, jedis);
		return res.size() == 2;
	}
	
	public List<Integer> getFollowers(int entityType,int entityId,int offset,int limit){
		String followerKey = RedisKeyUtils.getFollowerKey(entityId, entityType);
		return getIdFromSet(adapter.zrevrange(followerKey, offset, offset+limit));
	}
	
	public List<Integer> getFollowees(int entityType,int entityId,int offset,int limit){
		String followeeKey = RedisKeyUtils.getFolloweeKey(entityId, entityType);
		return getIdFromSet(adapter.zrevrange(followeeKey, offset, offset+limit));
	}
	
	public long getFollowerCount(int entityType,int entityId) {
		String followerKey = RedisKeyUtils.getFollowerKey(entityId, entityType);
		return adapter.zcard(followerKey);
	}
	
	public long getFolloweeCount(int entityType,int entityId) {
		String followeeKey = RedisKeyUtils.getFolloweeKey(entityId, entityType);
		return adapter.zcard(followeeKey);
	}
	
	public boolean isFollower(int userId,int entityType,int entityId) {
		String followerKey = RedisKeyUtils.getFollowerKey(entityId, entityType);
		return adapter.zscore(followerKey, String.valueOf(userId)) != null;
	}
	
	
	public List<Integer> getIdFromSet(Set<String> idSet){
		List<Integer> ids = new ArrayList<>();
		for(String str : idSet) {
			ids.add(Integer.parseInt(str));
		}
		return ids;
	}
	
}

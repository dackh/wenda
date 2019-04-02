package me.dack.wenda.utils;

public class RedisKeyUtils {
	private static String SPLIT = ":";
	private static String BIZ_LIKE = "LIKE";
	private static String BIZ_DISLIKE = "DISLIKE";
	private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";
	private static String BIZ_FOLLOWER = "FOLLOWER";
	private static String BIZ_FOLLOWEE = "FOLLOWEE";
	private static String BIZ_RECOMMEND = "RECOMMEND";
	
	public static String getLikeKey(int entityType,int entityId){
		return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
	}
	
	public static String getDisLikeKey(int entityType,int entityId){
		return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT + String.valueOf(entityId);
	}
	
	public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }
	
	public static String getFollowerKey(int entityId,int entityType) {
		return BIZ_FOLLOWER + SPLIT + String.valueOf(entityId) + SPLIT + String.valueOf(entityType);
	}
	
	public static String getFolloweeKey(int userId,int entityType) {
		return BIZ_FOLLOWEE + SPLIT + String.valueOf(userId) + SPLIT + String.valueOf(entityType);
	}
	
	public static String getRecommendKey() {
		return BIZ_RECOMMEND;
	}
}
